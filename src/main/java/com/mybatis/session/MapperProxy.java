package com.mybatis.session;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MapperProxy implements InvocationHandler {

    private Class<?> mapperInterface;

    public MapperProxy(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        if (Object.class.equals(declaringClass)) {
            // 实现类
            method.invoke(mapperInterface, args);
        } else {
            // 接口
            Select select = method.getAnnotation(Select.class);
            String[] sql = select.value();
            System.out.println(Arrays.toString(sql));
        }
        return null;
    }

}
