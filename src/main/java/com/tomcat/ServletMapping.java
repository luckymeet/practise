package com.tomcat;

/**
 * Servlet请求资源映射对象
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/11 17:05
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/11   新建
 * -------------------------------------------------
 * </pre>
 */
public class ServletMapping {

    private String servletName;
    private String url;
    private String clazz;

    public ServletMapping(String servletName, String url, String clazz) {
        this.servletName = servletName;
        this.url = url;
        this.clazz = clazz;
    }

    public String getServletName() {
        return servletName;
    }

    public String getUrl() {
        return url;
    }

    public String getClazz() {
        return clazz;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
