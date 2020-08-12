package com.designpattern.facade;

public class ModuleA {

    /**
     * 提供给系统外部调用
     */
    public void a1() {
        System.out.println("调用ModuleA中的a1方法");
    }

    /**
     * 提供给系统内部调用
     */
    private void a2() {
        System.out.println("调用ModuleA中的a2方法");
    }

}
