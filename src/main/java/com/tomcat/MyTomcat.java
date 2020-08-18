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
        int cpuNum = Runtime.getRuntime().availableProcessors();
        this.executorService = new ThreadPoolExecutor(cpuNum + 1, 2 * cpuNum, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000));
    }

    public MyTomcat(int port) {
        this.port = port;
        int cpuNum = Runtime.getRuntime().availableProcessors();
        this.executorService = new ThreadPoolExecutor(cpuNum + 1, 2 * cpuNum, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000));
    }

    public void start() throws IOException {
        initServletMapping();
        // 启动Selector
        selector = Selector.open();
        // 打开通道
        ServerSocketChannel channel = ServerSocketChannel.open();
        try {
            // 配置通道为非阻塞
            channel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(port);
            // 监听端口
            channel.bind(address);
            // 将通道注册给Selector
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("MyTomcat is start...\tport:" + port);
            // 轮询进行监听请求
            listen();
        } finally {
            channel.close();
            selector.close();
        }
    }

    private void listen() throws IOException {
        ConcurrentLinkedQueue<MyRequest> requestList = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<MyResponse> responseList = new ConcurrentLinkedQueue<>();
        while (!Thread.currentThread().isInterrupted()) {
            // 选择一组channel已准备好I/O操作的keys，只有channel被选中或者selector.wakeup()
            // 方法被调用或者当前线程被中断时才会进行返回，否则一直阻塞，返回的结果为keys的数量
            if (selector.select() == 0) {
                System.out.println("目前没有连接");
                continue;
            }
            System.out.println(Thread.currentThread().getName());
            // 获取所有的SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                try {
                    SelectionKey selectionKey = iterator.next();
                    // 接受新连接事件
                    if (selectionKey.isAcceptable()) {
                        doRead(selectionKey);
                    }
                    // 可读事件
                    else if (selectionKey.isValid() && selectionKey.isReadable()) {
                        requestList.add(new MyRequest(selectionKey));
                        selectionKey.interestOps(SelectionKey.OP_WRITE);
                    }
                    // 可写事件
                    else if (selectionKey.isValid() && selectionKey.isWritable()) {
                        responseList.add(new MyResponse(selectionKey));
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                    // 等待请求和响应对象都封装好了进行业务处理
                    if (!requestList.isEmpty() && !responseList.isEmpty()) {
                        dispatch(requestList.poll(), responseList.poll());
                    }
                    // 删除事件，避免以后重复处理该事件
                    iterator.remove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRead(SelectionKey next) {
        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
        SocketChannel socketChannel = null;
        try {
            socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
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
