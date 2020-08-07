package com.designpattern.factory;

public class FactoryTest {
    public static void main(String[] args) {
        SenderFactory senderFactory = new SenderFactory();
        Sender sender = senderFactory.getSender("sms");
        sender.send();
    }
}
