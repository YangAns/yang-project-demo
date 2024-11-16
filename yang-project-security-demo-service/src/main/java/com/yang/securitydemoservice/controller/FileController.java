package com.yang.securitydemoservice.controller;

import cn.hutool.core.io.IoUtil;
import com.yang.common.minio.FileStoreService;
import com.yang.common.minio.FileWrapper;
import com.yang.common.utils.YListUtils;
import com.yang.securitydemoservice.domain.entity.FileInfo;
import com.yang.securitydemoservice.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/8
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private FileInfoMapper fileInfoMapper;


    /**
     * 上传文件
     *
     * @param file
     */
    @RequestMapping("/upload")
    public void testUpload(@RequestPart("file") MultipartFile file) {
        List<FileWrapper> fileWrappers = fileStoreService.upload(Collections.singletonList(file));
        List<FileInfo> fileInfos = YListUtils.list2list(fileWrappers, FileInfo::new);
        if (!fileInfos.isEmpty()) {
            fileInfoMapper.insert(fileInfos);
        }
    }


    /**
     * 上传文件
     */
    @RequestMapping("/uploads")
    public void testUploads(@RequestPart("files") List<MultipartFile> files) {
        fileStoreService.upload(files);
        System.out.println("上传成功");
    }


    /**
     * ByteArrayResource
     */
    //文件下载
    @RequestMapping("/downloadtest")
    public ResponseEntity<ByteArrayResource> testDownload() throws FileNotFoundException {
            FileInputStream fileInputStream = null;
            ByteArrayOutputStream outputStream = null;
            fileInputStream = new FileInputStream("C:\\Users\\YangAns\\Desktop\\其他\\tx\\1a.jpeg");
            //将输入流转换成字节输出流返回
            outputStream = new ByteArrayOutputStream();
            IoUtil.copy(fileInputStream, outputStream);

        // 将文件内容转换为 ByteArrayResource
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            //设置响应头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "1a.jpeg");
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(resource, httpHeaders, 200);
    }

    /**
     * byte[]数组
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> testDownload(String id) {
        FileInfo fileInfo = fileInfoMapper.selectById(id);
        if (fileInfo == null) {
            return null;
        }
        return fileStoreService.download(fileInfo.toFileWrapper());
    }

    @RequestMapping("/openImg")
    public ResponseEntity<byte[]> testOpenImage(String fileName) {
        return fileStoreService.openFile(fileName);
    }
}
