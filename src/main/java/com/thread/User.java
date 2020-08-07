package com.thread;

public class User {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static int getAge() {
        return threadLocal.get();
    }

    public static void setAge(int age) {
        threadLocal.set(age);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
