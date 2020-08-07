package com;

import com.mybatis.mapper.ArticleMapper;
import com.mybatis.mapper.UserMapper;
import com.mybatis.spring.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(MyImportBeanDefinitionRegistrar.class)
@ComponentScan
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Application.class);
        applicationContext.refresh();

        /*mybatis 测试*/
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        ArticleMapper articleMapper = applicationContext.getBean(ArticleMapper.class);
        userMapper.getList();
        articleMapper.getList();
    }

}
