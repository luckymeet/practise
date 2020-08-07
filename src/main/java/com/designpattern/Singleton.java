package com.designpattern;

public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (null == instance) {
            synchronized (Singleton.class){
                if (null == instance) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
