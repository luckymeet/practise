package com.tomcat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/11 17:35
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/11   新建
 * -------------------------------------------------
 * </pre>
 */
public class MyRequest {

    private String method;
    private String url;
    private Map<String, String> paramMap = new HashMap<String, String>();

    public MyRequest(SelectionKey selectionKey) throws IOException {
        // 获取通道
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 * 1024);
        System.out.println(channel);
        try {
            if (channel.read(byteBuffer) > 0) {
                String httpRequest = new String(byteBuffer.array()).trim();
                System.out.println("服务端接收请求：" + httpRequest);
                // 获取请求头
                String httpHead = httpRequest.split("\n")[0];
                // 获取请求方法类型
                method = httpHead.split("\\s")[0];
                String path = httpHead.split("\\s")[1];
                // 获取请求url
                url = path.split("\\?")[0];
                // 获取请求参数
                String[] params = path.indexOf("?") > 0 ? path.split("\\&") : null;
                if (params != null) {
                    for (String s : params) {
                        paramMap.put(s.split("\\=")[0], s.split("\\=")[1]);
                    }
                }
            } else {
                selectionKey.cancel();
            }
        } finally {
            byteBuffer.flip();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }
}
