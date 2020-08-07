package com.designpattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class ProxyTest {
    @Test
    public void test() {
        /*静态代理，可通过实现原类接口也可以通过继承原类实现*/
        System.out.println("=====静态代理=====");
        BuyHouse buyHouse = new BuyHouseProxy(new BuyHouseImpl());
        buyHouse.buyHouse();

        /*JDK动态代理，必须实现接口，原理：创建一个代理类实现原类的接口，对原类的方法进行包装*/
        System.out.println("=====JDK动态代理=====");
        BuyHouse jdkProxy = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(), new Class[]{BuyHouse.class}, new JdkProxyHandler(new BuyHouseImpl()));
        jdkProxy.buyHouse();

        /*Cglib动态代理，无需实现接口，原理：将要代理的类作为父类，创建一个子类对象作为代理类重写父类的方法进行增强*/
        System.out.println("=====Cglib动态代理=====");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BuyHouseImpl.class);
        enhancer.setCallback(new CglibProxy());
        BuyHouse cglibProxy = (BuyHouse) enhancer.create();
        cglibProxy.buyHouse();
    }
}
