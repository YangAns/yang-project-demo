package com.yang.common.mybatis.schema;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SchemaAnalyserProperties.class)
@ConditionalOnProperty(prefix = "framework.mybatis.schema", name = "enable-auto-alter-table", havingValue = "true")
@RequiredArgsConstructor
public class SchemaAnalyserAutoConfiguration {

    private final DataSource dataSource;
    private final SchemaAnalyserProperties frameworkProperties;
    private final DataSourceProperties dataSourceProperties;

    @Bean
    public JDBCSupport jdbcSupport() {
        return new JDBCSupport(dataSource);
    }

    @Bean
    public CreateTableHandler createTableHandler(JDBCSupport jdbcSupport) {
        return new CreateTableHandler(jdbcSupport, frameworkProperties);
    }

    @Bean
    public UpdateTableHandler updateTableHandler(JDBCSupport jdbcSupport) {
        return new UpdateTableHandler(jdbcSupport, frameworkProperties);
    }

    @Bean
    public SchemaAnalyserExecutor schemaAnalyserRunner(
            CreateTableHandler createTableHandler
            , UpdateTableHandler updateTableHandler
            , SchemaDefinitionLoader schemaDefinitionLoader
            , SchemaExistedDefinitionLoader2 schemaExistedDefinitionLoader2) {
        return new SchemaAnalyserExecutor(createTableHandler, updateTableHandler, frameworkProperties, schemaDefinitionLoader, schemaExistedDefinitionLoader2);
    }

    @Bean
    public SchemaDefinitionLoader schemaDefinitionLoader() {
        return new SchemaDefinitionLoader(frameworkProperties);
    }

    @Bean
    public SchemaExistedDefinitionLoader schemaExistedDefinitionLoader() {
        return new SchemaExistedDefinitionLoader(dataSourceProperties, dataSource);
    }

    @Bean
    public SchemaExistedDefinitionLoader2 schemaExistedDefinitionLoader2() {
        return new SchemaExistedDefinitionLoader2(dataSourceProperties, dataSource);
    }
}
