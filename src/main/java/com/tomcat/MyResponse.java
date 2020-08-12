package com.tomcat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/12 16:18
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/12   新建
 * -------------------------------------------------
 * </pre>
 */
public class MyResponse {

    private SelectionKey selectionKey;

    public MyResponse(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public void write(String content) throws IOException {
        StringBuilder httpContent = new StringBuilder();
        httpContent.append("HTTP/1.1 200 OK\n").append("Content-Type:text/html\n").append("\r\n")
                .append("<html><body>").append(content).append("<html><body>");
        ByteBuffer byteBuffer = ByteBuffer.wrap(httpContent.toString().getBytes(UTF_8));
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        try {
            channel.write(byteBuffer);
            selectionKey.channel();
        } finally {
            byteBuffer.flip();
            channel.close();
        }
    }
}
