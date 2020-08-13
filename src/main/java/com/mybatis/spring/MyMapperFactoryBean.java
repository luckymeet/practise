package com.mybatis.spring;

import com.mybatis.session.MySqlSession;
import org.springframework.beans.factory.FactoryBean;

public class MyMapperFactoryBean implements FactoryBean {

    private Class<?> mapperInterface;

    public MyMapperFactoryBean(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object getObject() throws Exception {
        MySqlSession<Object> mySqlSession = new MySqlSession<>();
        return mySqlSession.getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
