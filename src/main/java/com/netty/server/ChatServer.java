package com.netty.server;

import com.netty.protocol.IMDecoder;
import com.netty.protocol.IMEncoder;
import com.netty.server.handler.HttpServerHandler;
import com.netty.server.handler.TerminalServerHandler;
import com.netty.server.handler.WebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/17 14:09
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/17   新建
 * -------------------------------------------------
 * </pre>
 */
@Slf4j
public class ChatServer {

    private int port = 8080;

    public ChatServer() {
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        /*解析自定义协议*/
                        // Inbound
                        pipeline.addLast(new IMDecoder());
                        // Outbound
                        pipeline.addLast(new IMEncoder());
                        // Inbound
                        pipeline.addLast(new TerminalServerHandler());

                        /*解析http请求*/
                        // Outbound
                        pipeline.addLast(new HttpServerCodec());
                        // Inbound 主要是将接收到的同一个http请求或响应的多个消息对象变成一个FullHttpRequest完整的消息对象
                        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                        // Inbound、Outbound 主要用于大数据流，比如一个1G大小的文件如果你直接传输肯定会撑爆jvm内存，加上这个就不用考虑这个问题了
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpServerHandler());

                        /*解析WebSocket请求*/
                        // Inbound
                        pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
                        // Inbound
                        pipeline.addLast(new WebSocketServerHandler());
                    }
                });
            ChannelFuture future = b.bind(port).sync();
            log.info("服务已启动，监听端口：" + this.port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}
