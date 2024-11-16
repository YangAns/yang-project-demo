package com.yang.securitydemoservice;

import cn.hutool.core.lang.UUID;
import com.yang.common.minio.FileStoreService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.io.FileInputStream;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/4
 */
@SpringBootTest(classes = DemoApplication.class)
public class MinioTest {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileStoreService fileStoreService;

    /**
     * 测试文件上传
     */
    @Test
    void testUpload()  {
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\YangAns\\Desktop\\pdf\\linux系统环境安装.pdf");
            String id = UUID.fastUUID().toString();
            String newFileName = id + ".pdf";
            String bucketName = "yangans-pdf";
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newFileName)
                            .stream(inputStream,inputStream.available() , -1)
                            .contentType(MediaType.APPLICATION_PDF_VALUE)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    @Test
//    void testMinIOTemplate() {
//        minIOTemplate.upload()
//    }
}
