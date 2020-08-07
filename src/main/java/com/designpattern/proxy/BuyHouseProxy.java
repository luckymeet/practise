package com.designpattern.proxy;

public class BuyHouseProxy implements BuyHouse {

    private BuyHouse buyHouse;

    public BuyHouseProxy(final BuyHouse buyHouse) {
        this.buyHouse = buyHouse;
    }

    @Override
    public void buyHouse() {
        System.out.println("买房前的准备");
        buyHouse.buyHouse();
        System.out.println("买房后装修");
    }
}
