package com.yang.securitydemoservice.config;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/9/27
 */
@Component
public class MyConfig implements ApplicationListener<ContextRefreshedEvent>, SmartInitializingSingleton {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        Configuration configuration = sqlSessionTemplate.getConfiguration();
//        MapperRegistry mapperRegistry = configuration.getMapperRegistry();
//        Collection<Class<?>> mappers = mapperRegistry.getMappers();
//        for (Class<?> mapper : mappers) {
//            Class<?> aClass = GenericTypeResolver.resolveTypeArgument(mapper, BaseMapper.class);
//            System.out.println(aClass);
//            BaseMapper<?> bean = (BaseMapper<?>) SpringUtil.getBean(mapper);
//        }
    }


    @Override
    public void afterSingletonsInstantiated() {


    }
}
