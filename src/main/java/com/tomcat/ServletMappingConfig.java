package com.tomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟读取web.xml中配置的servletMapping
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/11 17:03
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/11   新建
 * -------------------------------------------------
 * </pre>
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new ServletMapping("findGirl", "/girl", "com.tomcat.FindGirlServlet"));
    }

}
