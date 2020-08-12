package com.tomcat;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/12 17:15
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/12   新建
 * -------------------------------------------------
 * </pre>
 */
public class FindGirlServlet extends MyHttpServlet {

    @Override
    protected void doGet(MyRequest request, MyResponse response) {
        try {
            response.write("get girl...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(MyRequest request, MyResponse response) {
        try {
            response.write("post girl...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
