package com.designpattern.template;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/8/17 9:51
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/8/17   新建
 * -------------------------------------------------
 * </pre>
 */
public class Cricket extends Game {
    @Override
    void initialize() {
        System.out.println("Cricket游戏初始化");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket游戏开始");
    }

    @Override
    void endPlay() {
        System.out.println("Cricket游戏结束");
    }
}
