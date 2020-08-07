package com.designpattern.observer;

public class XiaoGang implements Observer {

    private String name = "小刚";

    @Override
    public void getMessage(String msg) {
        System.out.println(name + "接收到了小美的信息：" + msg);
    }
}
