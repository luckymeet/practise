package com.spring.postprocessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        ArrayList<Class> classList = new ArrayList<>();
//        classList.add(UserMapper.class);
//        classList.add(ArticleMapper.class);
//
//        for (Class clazz : classList) {
//            /*构建每个Mapper接口的MapperFactoryBean类的BeanDefition*/
//            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionBuilder.getBeanDefinition();
//            beanDefinition.setBeanClass(MyMapperFactoryBean.class);
//            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
//
//            String className = clazz.getName();
//            String beanName = className.substring(className.lastIndexOf('.') + 1);
//            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
//            // 将beanName和bean对应的FactoryBean对象放入spring容器，之后getBean(beanName)
//            // 的时候实际是从bean对应的FactoryBean对象的getObject()方法获取bean
//            registry.registerBeanDefinition(beanName, beanDefinition);
//        }

        System.out.println(registry.getBeanDefinitionNames() + "MyBeanDefinitionRegistryPostProcessor-postProcessBeanDefinitionRegistry is invoke");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor-postProcessBeanFactory is invoke");
    }
}
