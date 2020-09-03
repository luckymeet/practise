package com;

import com.mybatis.mapper.ArticleMapper;
import com.mybatis.mapper.UserMapper;
import com.mybatis.service.UserService;
import com.mybatis.spring.MyImportBeanDefinitionRegistrar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Import(MyImportBeanDefinitionRegistrar.class)
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Mapper.class})})
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Application.class);
        applicationContext.refresh();

        System.out.println("\n==========容器初始化完成==========\n");

        /*mybatis 测试*/
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.getList();
    }

}
