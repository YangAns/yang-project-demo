package com.yang.oauth2.logindemoservice.config;

import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityConfig {
    public static String generateSafeSecretKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HMACSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(256); // 初始化密钥生成器为256位
        SecretKey key = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded()); // 返回 Base64 编码的密钥字符串
    }

    public static void main(String[] args) {
            String secretKey = generateSafeSecretKey();
            System.out.println(secretKey);
    }
}
