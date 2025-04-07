package com.yang.project.sa.token;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/28
 */
@SpringBootApplication
public class SaTokenApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaTokenApplication.class,args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
