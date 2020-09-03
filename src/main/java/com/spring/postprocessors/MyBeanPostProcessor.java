package com.spring.postprocessors;

import com.mybatis.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(UserService.class)) {
            System.out.println("UserService BeanPostProcessor before");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(UserService.class)) {
            System.out.println("UserService BeanPostProcessor after");
        }
        return bean;
    }
}
