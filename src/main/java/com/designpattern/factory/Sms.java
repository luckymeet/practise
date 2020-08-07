package com.designpattern.factory;

public class Sms implements Sender{
    @Override
    public void send() {
        System.out.println("sms send");
    }
}
