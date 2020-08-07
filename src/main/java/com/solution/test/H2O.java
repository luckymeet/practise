package com.solution.test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class H2O {

	private volatile boolean isRun = false;
    private volatile AtomicInteger hNum = new AtomicInteger(0);
    private volatile AtomicInteger oNum = new AtomicInteger(0);
    private CyclicBarrier latch = new CyclicBarrier(3);
    private volatile int[] seat = new int[5];

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
		while(hNum.intValue() == 2){
            if (oNum.intValue() == 1){
                hNum.set(0);
                oNum.set(0);
            } else {
                Thread.yield();
            }
        }
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while(oNum.intValue() == 1){
            if (hNum.intValue() == 2){
                hNum.set(0);
                oNum.set(0);
            } else {
                Thread.yield();
            }
        }
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
		releaseOxygen.run();
    }

	public static void main(String[] args) throws InterruptedException {
//		FooBar forBar = new FooBar(2);
//		new Thread(()->{
//			Runnable printFoo = () -> System.out.print("foo");
//			try {
//				forBar.foo(printFoo);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}).start();
//		new Thread(()->{
//			Runnable printBar = () -> System.out.print("bar");
//			try {
//				forBar.bar(printBar);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}).start();
	}
}
