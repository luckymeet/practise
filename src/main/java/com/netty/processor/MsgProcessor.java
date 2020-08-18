package com.netty.processor;

import com.netty.protocol.IMDecoder;
import com.netty.protocol.IMEncoder;
import com.netty.protocol.IMMessage;
import com.netty.protocol.IMP;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.simple.JSONObject;

/**
 * 用于自定义协议内容的逻辑处理
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/17 15:35
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/17   新建
 * -------------------------------------------------
 * </pre>
 */
public class MsgProcessor {
    /**
     * 记录在线用户数
     */
    private static ChannelGroup onLineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final AttributeKey<String> NICK_NAME = AttributeKey.valueOf("nickName");
    public static final AttributeKey<String> IP_ADDR = AttributeKey.valueOf("ipAddr");
    public static final AttributeKey<JSONObject> ATTRS = AttributeKey.valueOf("attrs");
    public static final AttributeKey<String> FROM = AttributeKey.valueOf("from");

    private IMEncoder encoder = new IMEncoder();
    private IMDecoder decoder = new IMDecoder();

    public void sendMsg(Channel channel, IMMessage msg) {
        this.sendMsg(channel, encoder.encode(msg));
    }

    public void sendMsg(Channel channel, String msg) {
        IMMessage message = decoder.decode(msg);
        if (null == message) {
            return;
        }
        String addr = getAddress(channel);
        String cmd = message.getCmd();
        if (cmd.equals(IMP.LOGIN.getName())) {
            channel.attr(NICK_NAME).getAndSet(message.getSender());
            channel.attr(IP_ADDR).getAndSet(addr);
            channel.attr(FROM).getAndSet(message.getTerminal());
            onLineUsers.add(channel);

            for (Channel user : onLineUsers) {
                boolean isSelf = user == channel;
                if (isSelf) {
                    message = new IMMessage(IMP.SYSTEM.getName(), sysTime(), onLineUsers.size(), "已与服务器建立连接");
                } else {
                    message = new IMMessage(IMP.SYSTEM.getName(), sysTime(), onLineUsers.size(), getNickName(channel) + "加入");
                }
                if ("Console".equals(user.attr(FROM).get())) {
                    channel.writeAndFlush(message);
                    continue;
                }
                String content = encoder.encode(message);
                user.writeAndFlush(new TextWebSocketFrame(content));
            }
        } else if (cmd.equals(IMP.CHAT.getName())) {
            for (Channel user : onLineUsers) {
                boolean isSelf = user == channel;
                if (isSelf) {
                    message.setSender("you");
                } else {
                    message.setSender(getNickName(channel));
                }
                message.setTime(sysTime());

                if("Console".equals(channel.attr(FROM).get()) & !isSelf){
                    user.writeAndFlush(message);
                    continue;
                }
                String content = encoder.encode(message);
                user.writeAndFlush(new TextWebSocketFrame(content));
            }
        } else if (message.getCmd().equals(IMP.FLOWER.getName())) {
            JSONObject attrs = getAttrs(channel);
            long currTime = sysTime();
            if (null != attrs) {
                long lastTime = (long) attrs.get("lastFlowerTime");
                //60秒之内不允许重复刷鲜花
                int secends = 10;
                long sub = currTime - lastTime;
                if (sub < 1000 * secends) {
                    message.setSender("you");
                    message.setCmd(IMP.SYSTEM.getName());
                    message.setContent("您送鲜花太频繁," + (secends - Math.round(sub / 1000)) + "秒后再试");

                    String content = encoder.encode(message);
                    channel.writeAndFlush(new TextWebSocketFrame(content));
                    return;
                }
            }
            //正常送花
            for (Channel user : onLineUsers) {
                if (channel == channel) {
                    message.setSender("you");
                    message.setContent("你给大家送了一波鲜花雨");
                    setAttrs(channel, "lastFlowerTime", currTime);
                }else{
                    message.setSender(getNickName(channel));
                    message.setContent(getNickName(channel) + "送来一波鲜花雨");
                }
                message.setTime(sysTime());

                String content = encoder.encode(message);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
        }
    }

    private void setAttrs(Channel channel, String lastFlowerTime, long currTime) {
        try {
            JSONObject jsonObject = channel.attr(ATTRS).get();
            jsonObject.put(lastFlowerTime, currTime);
            channel.attr(ATTRS).set(jsonObject);
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(lastFlowerTime, currTime);
            channel.attr(ATTRS).set(jsonObject);
        }
    }

    private String getNickName(Channel channel) {
        return channel.attr(NICK_NAME).get();
    }

    private long sysTime() {
        return System.currentTimeMillis();
    }

    public String getAddress(Channel channel) {
        return channel.remoteAddress().toString().replaceAll("/", "");
    }

    public JSONObject getAttrs(Channel channel) {
        try {
            return channel.attr(ATTRS).get();
        } catch (Exception e) {
            return null;
        }
    }
}
