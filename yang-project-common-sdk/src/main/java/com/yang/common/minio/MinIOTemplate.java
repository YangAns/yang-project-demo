package com.yang.common.minio;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.yang.common.utils.YListUtils;
import com.yang.common.utils.YStrUtils;
import io.minio.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.*;

import static com.yang.common.utils.YListUtils.list2list;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/6
 */
@Slf4j
@Data
public class MinIOTemplate implements FileStoreService {

    private MinioClient minioClient;

    private MinIOConfigProperties minioProperties;

    private ServerProperties serverProperties;

    private final static Set<String> EXISTS_BUCKET = new HashSet<>();

    @Override
    public List<FileWrapper> upload(List<MultipartFile> files) {
        List<FileWrapper> wrappers = list2list(files, FileWrapper::new);
        wrappers.forEach(this::doUpload);
        return wrappers;
    }


    public void doUpload(FileWrapper fileWrapper) {
        // 校验扩展名是否非法
        checkExtName(fileWrapper);
        //扩展名
        String extName = fileWrapper.getExtName();
        // 检查bucket是否存在，不存在则创建
        existOrCreateRepository(extName, true);
        try {
            // 上传文件
            try (InputStream in = fileWrapper.getInputStream()) {
                PutObjectArgs putObjectArgs = PutObjectArgs
                        .builder()
                        .bucket(wrapperBucketName(fileWrapper.getExtName()))
                        .object(fileWrapper.getNewFileName())
                        .stream(in, in.available(), -1)
                        .contentType(fileWrapper.getContentType())
                        .build();
                minioClient.putObject(putObjectArgs);
            } catch (Exception e) {
                throw new RuntimeException("文件上传失败" + e.getMessage());
            }
        } catch (Exception e) {
            log.warn("文件上传失败-->{}", e.getMessage());
        }
    }


    /**
     * 检查文件扩展名
     *
     * @param fileWrapper 文件信息
     */
    private void checkExtName(FileWrapper fileWrapper) {
        String extName = fileWrapper.getExtName();
        if (YStrUtils.isNull(extName)) {
            throw new RuntimeException("不能确定文件类型");
        }
        List<String> allowFileTypes = allowFileTypes();
        if (YListUtils.isEmptyList(allowFileTypes)) {
            return;
        }
        if (YListUtils.anyMatch(allowFileTypes, a -> a.equalsIgnoreCase(extName))) {
            return;
        }
        throw new RuntimeException("非法类型文件");
    }


    @Override
    public void existOrCreateRepository(String extName, boolean needWrapper) {
        if (EXISTS_BUCKET.contains(extName)) {
            return;
        }
        try {
            String bucketName = needWrapper ? wrapperBucketName(extName) : extName;
            // 判断存储桶是否存在
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            // 不存在则创建
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                EXISTS_BUCKET.add(extName);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件仓库创建失败" + e.getMessage());
        }
    }

    @Override
    public String wrapperBucketName(String extName) {
        String bucketPrefix = minioProperties.getBucketPrefix();
        List<? extends Serializable> elements;
        extName = extName.toLowerCase();
        if (YStrUtils.isNotNull(bucketPrefix)) {
            elements = Arrays.asList("app", serverProperties.getPort(), bucketPrefix, extName);
        } else {
            elements = Arrays.asList("app", serverProperties.getPort(), extName);
        }
        return YStrUtils.join(elements, "-");
    }


    @Override
    public List<String> allowFileTypes() {
        return minioProperties.getAllowFileTypes();
    }

    @Override
    public ResponseEntity<byte[]> download(FileWrapper fileWrapper) {
        try (InputStream inputStream = getObjectInputStream(fileWrapper);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            IoUtil.copy(inputStream, byteArrayOutputStream);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setAccessControlExposeHeaders(Collections.singletonList("*"));
            httpHeaders.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileWrapper.getOriginalFilename(), "UTF-8"));
            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<byte[]> openFile(String imageName) {
        FileWrapper fileWrapper = new FileWrapper(imageName);
        try (InputStream inputStream = getObjectInputStream(fileWrapper);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            IoUtil.copy(inputStream, byteArrayOutputStream);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setPragma("no-cache");
            httpHeaders.setCacheControl("no-cache");
            httpHeaders.setExpires(0);
            String mimeType = FileUtil.getMimeType(imageName);
            MediaType mediaType = MediaType.parseMediaType(mimeType);
            httpHeaders.setContentType(mediaType);
            // 如果是直接在浏览器显示则设置inline ,下载则设置attachment
            httpHeaders.add("Content-Disposition", "inline;filename=" + URLEncoder.encode(FileUtil.getName(imageName), "UTF-8"));
            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObjects(List<FileWrapper> fileInfos) {

    }

    @Override
    public Boolean removeRepository(String repositoryName) {
        return null;
    }


    public InputStream getObjectInputStream(FileWrapper fileWrapper) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(wrapperBucketName(fileWrapper.getExtName()))  //存储桶
                            .object(fileWrapper.getNewFileName())  //文件名
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
