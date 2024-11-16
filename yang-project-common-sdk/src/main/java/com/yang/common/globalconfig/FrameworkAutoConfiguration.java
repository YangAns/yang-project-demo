package com.yang.common.globalconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 *  todo
 * </p>
 *
 * @author YangAns
 * @since 2024/9/20
 */
@Configuration
@EnableConfigurationProperties(FrameworkProperties.class)
public class FrameworkAutoConfiguration {
    private final ApplicationContext applicationContext;


    public FrameworkAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private <T> void getBeansThen(Class<T> clazz, Consumer<List<T>> consumer) {
        if (this.applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            final Map<String, T> beansOfType = this.applicationContext.getBeansOfType(clazz);
            List<T> clazzList = new ArrayList<>();
            beansOfType.forEach((k, v) -> clazzList.add(v));
            consumer.accept(clazzList);
        }
    }
}
