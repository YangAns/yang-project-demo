package com.yang.common.mybatis.schema;

import lombok.Data;
import org.apache.ibatis.io.Resources;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * <p>
 * mybatis schema 配置
 * </p>
 *
 * @author YangAns
 * @since 2024/9/21
 */
@Data
@ConfigurationProperties(prefix = "framework.mybatis.schema")
public class SchemaAnalyserProperties {
    // 是否开启自动建表，更新添加字段总开关
    private boolean enableAutoAlterTable = false;
    // 是否需要自动建表
    private boolean enableAutoAlterTableCreate = true;
    private boolean enableGenerateDeleteScript = false;
    // 是否需要自动添加表字段
    private boolean enableAutoAlterTableAddColumn = true;
    // 是否需要自动更新表字段
    private boolean enableAutoAlterTableModifyColumn = false;
    // 表所在的包
    private String[] tableNamePackage = new String[]{"com.yang"};

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

    public Set<Class<?>> scanClasses() throws IOException {
        Set<Class<?>> classes = new HashSet<>();
        String[] packagePatternArray = tokenizeToStringArray("com.yang",
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        for (String packagePattern : packagePatternArray) {
            Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
            for (Resource resource : resources) {
                try {
                    ClassMetadata classMetadata = METADATA_READER_FACTORY.getMetadataReader(resource).getClassMetadata();
                    Class<?> clazz = Resources.classForName(classMetadata.getClassName());
//                    if (assignableType == null || assignableType.isAssignableFrom(clazz)) {
                        classes.add(clazz);
//                    }
                } catch (Throwable e) {
                    // ignore
                }
            }
        }
        return classes;
    }

}
