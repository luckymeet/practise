package com;

import com.mybatis.service.I;
import com.mybatis.spring.MyImportBeanDefinitionRegistrar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAspectJAutoProxy(proxyTargetClass = false)
@Import(MyImportBeanDefinitionRegistrar.class)
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Mapper.class})})
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Application.class);
//        applicationContext.addBeanFactoryPostProcessor(new ConfigurationClassPostProcessor());
        applicationContext.refresh();

        System.out.println("\n==========容器初始化完成==========\n");

        /*mybatis 测试*/
//        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10000);
        for (int i = 0; i < 1; i++) {
            threadPoolExecutor.execute(()->{
                I userService = (I) applicationContext.getBean("userService");
//        userService.getList();
                userService.saveUse();
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("============" + (System.currentTimeMillis() - start) + "============");
    }

}
