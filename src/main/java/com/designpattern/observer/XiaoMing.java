package com.designpattern.observer;

public class XiaoMing implements Observer {

    private String name = "小明";

    @Override
    public void getMessage(String msg) {
        System.out.println(name + "接收到了小美的信息：" + msg);
    }
}
