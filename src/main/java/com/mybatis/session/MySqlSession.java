package com.mybatis.session;

import java.lang.reflect.Proxy;

public class MySqlSession<T> {

    public <T> T getMapper(Class<T> mapperInterface) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(mapperInterface));
    }
}