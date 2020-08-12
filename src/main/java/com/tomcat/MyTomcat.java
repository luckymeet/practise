package com.tomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 简单实现tomcat
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/11 16:46
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/11   新建
 * -------------------------------------------------
 * </pre>
 */
public class MyTomcat {

    private int port;
    private Map<String, String> urlServletMap = new HashMap<>();
    private Selector selector;
    private ExecutorService executorService;

    public MyTomcat() {
        this.port = 8080;
    }

    public MyTomcat(int port) {
        this.port = port;
        int cpuNum = Runtime.getRuntime().availableProcessors();
        this.executorService = new ThreadPoolExecutor(cpuNum + 1, 2 * cpuNum, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000));
    }

    public void start() throws IOException {
        initServletMapping();
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel channel = ServerSocketChannel.open();
        try {
            channel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(port);
            channel.bind(address);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("MyTomcat is start...\tport:" + port);
            ConcurrentLinkedQueue<MyRequest> requestList = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<MyResponse> responseList = new ConcurrentLinkedQueue<>();
            while (!Thread.currentThread().isInterrupted()) {
                if (selector.select() == 0) {
                    System.out.println("目前没有连接");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    try {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            doRead(selectionKey);
                        } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                            requestList.add(new MyRequest(selectionKey));
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                        } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                            responseList.add(new MyResponse(selectionKey));
                        }
                        if (!requestList.isEmpty() && !requestList.isEmpty()) {
                            dispatch(requestList.poll(), responseList.poll());
                        }
                        iterator.remove();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            channel.close();
            selector.close();
        }
    }

    private void dispatch(final MyRequest request, final MyResponse response) throws IOException {
        final String clazz = urlServletMap.get(request.getUrl());
        if (clazz == null || clazz.equals("")) {
            response.write("404 NOT FOUND");
            return;
        }
        executorService.execute(() -> {
            try {
                Class<MyHttpServlet> myHttpServletClass = (Class<MyHttpServlet>) Class.forName(clazz);
                MyHttpServlet myHttpServlet = myHttpServletClass.newInstance();
                myHttpServlet.service(request, response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private void doRead(SelectionKey selectionKey) {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initServletMapping() {
        for (ServletMapping servletMapping : ServletMappingConfig.servletMappingList) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    public static void main(String[] args) {
        try {
            new MyTomcat().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
