package com.designpattern.factory;

public class SenderFactory {

    public Sender getSender(String name) {
        if ("sms".equals(name)) {
            return new Sms();
        } else if ("email".equals(name)) {
            return new Email();
        } else {
            return null;
        }
    }

}
