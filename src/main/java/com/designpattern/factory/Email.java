package com.designpattern.factory;

public class Email implements Sender {
    @Override
    public void send() {
        System.out.println("email send");
    }
}
