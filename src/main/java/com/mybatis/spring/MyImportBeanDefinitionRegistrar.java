package com.mybatis.spring;

import com.mybatis.mapper.ArticleMapper;
import com.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        /*模拟扫描Mapper文件*/
        ArrayList<Class> classList = new ArrayList<>();
        classList.add(UserMapper.class);
        classList.add(ArticleMapper.class);

        for (Class clazz : classList) {
            /*构建每个Mapper接口的MapperFactoryBean类的BeanDefition*/
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionBuilder.getBeanDefinition();
            beanDefinition.setBeanClass(MyMapperFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);

            String className = clazz.getName();
            String beanName = className.substring(className.lastIndexOf('.') + 1);
            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
            // 将beanName和bean对应的FactoryBean对象放入spring容器，之后getBean(beanName)
            // 的时候实际是从bean对应的FactoryBean对象的getObject()方法获取bean
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
        System.out.println("MyImportBeanDefinitionRegistrar is invoke");
    }
}
