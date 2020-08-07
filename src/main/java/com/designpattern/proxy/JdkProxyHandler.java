package com.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxyHandler implements InvocationHandler {
    private BuyHouse buyHouse;

    public JdkProxyHandler(BuyHouse buyHouse) {
        this.buyHouse = buyHouse;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("买房前的准备");
        method.invoke(buyHouse, args);
        System.out.println("买房后的装修");
        return buyHouse;
    }
}
