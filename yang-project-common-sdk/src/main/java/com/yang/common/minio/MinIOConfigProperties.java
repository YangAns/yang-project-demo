package com.yang.common.minio;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "framework.minio")
public class MinIOConfigProperties {
    private String accessKey;
    private String secretKey;
    private String bucketPrefix;
    private String endpoint;
    private String readPath;
    private List<String> allowFileTypes = new ArrayList<>();
}
