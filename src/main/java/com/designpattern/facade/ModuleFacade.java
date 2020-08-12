package com.designpattern.facade;

public class ModuleFacade {

    private ModuleA moduleA = new ModuleA();

    private ModuleB moduleB = new ModuleB();

    public void a1() {
        moduleA.a1();
    }

    public void b1() {
        moduleB.b1();
    }

}
