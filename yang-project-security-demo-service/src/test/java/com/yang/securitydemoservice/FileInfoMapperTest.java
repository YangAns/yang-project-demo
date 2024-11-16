package com.yang.securitydemoservice;

import com.yang.securitydemoservice.domain.entity.FileInfo;
import com.yang.securitydemoservice.mapper.FileInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/10
 */
@SpringBootTest(classes = DemoApplication.class)
public class FileInfoMapperTest {


    @Autowired
    private FileInfoMapper fileInfoMapper;


    @Test
    void testSelect() {
        String fileId = "fd95d221-1247-4c6d-9a0e-b451efb578dc"; // 确保这是一个 String
        FileInfo fileInfo = fileInfoMapper.selectById(fileId);
        System.out.println(fileInfo);
    }
}
