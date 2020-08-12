package com.tomcat;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;

/**
 * 简单Servlet接口
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/11 17:11
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/11   新建
 * -------------------------------------------------
 * </pre>
 */
public abstract class MyHttpServlet {

    private static final String METMOD_POST = "POST";

    private static final String METMOD_GET = "GET";

    protected abstract void doGet(MyRequest request, MyResponse response);

    protected abstract void doPost(MyRequest request, MyResponse response);

    protected void service(MyRequest request, MyResponse response) {
        if (request.getMethod().equals(METMOD_GET)) {
            doGet(request, response);
        } else if (request.getMethod().equals(METMOD_POST)) {
            doPost(request, response);
        }
    }

}
