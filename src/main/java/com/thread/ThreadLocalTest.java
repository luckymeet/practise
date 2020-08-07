package com.thread;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    @Test
    public void test() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(()->{
                try {
                    for (int j = 0; j < 5; j++) {
                        User.setAge(j);
                        Thread.yield();
                        System.out.println(Thread.currentThread().getName() + " age:" + User.getAge());
                    }
                } finally {
                    User.remove();
                }
            });
        }
        Thread.sleep(2000);
    }
}
