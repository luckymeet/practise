package com.designpattern.template;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/17 9:54
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/17   新建
 * -------------------------------------------------
 * </pre>
 */
public class Football extends Game {
    @Override
    void initialize() {
        System.out.println("Football游戏初始化");
    }

    @Override
    void startPlay() {
        System.out.println("Football游戏开始");
    }

    @Override
    void endPlay() {
        System.out.println("Football游戏结束");
    }
}
