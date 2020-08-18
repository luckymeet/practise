package com.designpattern.template;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/17 9:56
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/17   新建
 * -------------------------------------------------
 * </pre>
 */
public class TemplateTest {

    public static void main(String[] args) {
        Game cricket = new Cricket();
        cricket.play();
        Game football = new Football();
        football.play();
    }

}
