package com.designpattern.observer;

public class ObserverTest {

    public static void main(String[] args) {
        XiaoMei xiaoMei = new XiaoMei();
        XiaoMing xiaoMing = new XiaoMing();
        XiaoGang xiaoGang = new XiaoGang();
        xiaoMei.addPerson(xiaoMing);
        xiaoMei.addPerson(xiaoGang);
        xiaoMei.notifyPerson();
    }

}
