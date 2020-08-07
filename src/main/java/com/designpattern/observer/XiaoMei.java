package com.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class XiaoMei {

    private List<Observer> list = new ArrayList<>();

    public void addPerson(Observer person) {
        list.add(person);
    }

    public void notifyPerson() {
        for (Observer person : list) {
            person.getMessage("一起去吃饭");
        }
    }

}
