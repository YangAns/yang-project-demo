package com.yang.mybatisplusjoin.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/30
 */
@SpringBootApplication
@MapperScan("com.yang.mybatisplusjoin.demo.mapper")
public class MybatisPlusJoinApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(MybatisPlusJoinApplication.class, args);
    }
}
