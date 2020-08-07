package com.designpattern.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("买房前的准备");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("买房后的装修");
        return invoke;
    }
}
