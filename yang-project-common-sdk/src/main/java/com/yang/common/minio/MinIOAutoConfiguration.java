package com.yang.common.minio;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MinIOConfigProperties.class})
public class MinIOAutoConfiguration {

    /**
     * minioClient minio客户端
     */
    @Bean
    public MinioClient minioClient(MinIOConfigProperties minioProperties){
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Bean
    public MinIOTemplate minIOTemplate(MinioClient minioClient, MinIOConfigProperties minioProperties, ServerProperties serverProperties){
        MinIOTemplate minIOTemplate = new MinIOTemplate();
        minIOTemplate.setMinioClient(minioClient);
        minIOTemplate.setMinioProperties(minioProperties);
        minIOTemplate.setServerProperties(serverProperties);
        return minIOTemplate;
    }
}
