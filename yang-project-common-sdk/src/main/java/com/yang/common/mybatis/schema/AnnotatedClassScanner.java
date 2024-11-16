package com.yang.common.mybatis.schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

@Service
public class AnnotatedClassScanner {

    private static final Logger logger = LoggerFactory.getLogger(AnnotatedClassScanner.class);

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

    /**
     * 方法一：在现有扫描方法中添加注解过滤
     */
    public Set<Class<?>> scanAnnotatedClassesMethod1(String basePackage, Class<? extends Annotation> annotationCls) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String resourcePattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.class";
        Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(resourcePattern);

        for (Resource resource : resources) {
            try {
                ClassMetadata classMetadata = METADATA_READER_FACTORY.getMetadataReader(resource).getClassMetadata();
                Class<?> clazz = Class.forName(classMetadata.getClassName());
                if (clazz.isAnnotationPresent(annotationCls)) {
                    // 进一步过滤具体类（非接口、非抽象类）
                    if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
                        classes.add(clazz);
                    }
                }
            } catch (ClassNotFoundException e) {
                logger.warn("Class not found for resource: {}", resource.getFilename(), e);
            } catch (IOException e) {
                logger.error("I/O error reading resource: {}", resource.getFilename(), e);
            } catch (Throwable e) {
                logger.error("Unexpected error processing resource: {}", resource.getFilename(), e);
            }
        }

        return classes;
    }

    /**
     * 方法二：使用 ClassPathScanningCandidateComponentProvider 扫描带有特定注解的类
     */
    public Set<Class<?>> scanAnnotatedClassesMethod2(String basePackage, Class<? extends Annotation> annotationCls) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        // 创建扫描器，false 表示不使用默认的过滤器
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        // 添加注解过滤器
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationCls));

        // 扫描候选组件
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String className = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                logger.warn("Class not found for bean definition: {}", className, e);
            }
        }

        return classes;
    }
}