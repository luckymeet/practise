package com.designpattern.facade;

public class ModuleB {

    /**
     * 提供给系统外部调用
     */
    public void b1() {
        System.out.println("调用ModuleB的b1方法");
    }

    /**
     * 提供给系统内部调用
     */
    private void b2() {
        System.out.println("调用ModuleB的b2方法");
    }

}
