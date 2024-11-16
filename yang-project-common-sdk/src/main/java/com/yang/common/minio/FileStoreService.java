
package com.yang.common.minio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public interface FileStoreService {

    // 上传多文件
    default List<FileWrapper> upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        if (null == multipartRequest) {
            return Collections.emptyList();
        }
        List<MultipartFile> fileList = multipartRequest.getFiles("files");
        return upload(fileList);
    }

    default List<String> allowFileTypes() {
        return Collections.emptyList();
    }

    // 上传多文件
    List<FileWrapper> upload(List<MultipartFile> files);


    // 下载文件
    ResponseEntity<byte[]> download(FileWrapper fileWrapper);

    // 图片
    ResponseEntity<byte[]> openFile(String imageName);

    default ResponseEntity<byte[]> openAudio(String audioName) {
        return null;
    }


    default ResponseEntity<byte[]> openVideo(String audioName) {
        return null;
    }

    // 查看文件对象
//    List<ObjectItem> listObjects(String bucketName);

    // 批量删除文件对象
    void removeObjects(List<FileWrapper> fileInfos);

    // 判断bucket是否存在，不存在则创建
    void existOrCreateRepository(String repositoryName , boolean needWrapper);

    // 删除存储bucket
    Boolean removeRepository(String repositoryName);

    // 获取文件流
//    default InputStream getObjectStream(@NotNull FileWrapper fileWrapper) {
//        return null;
//    }

    default InputStream getObjectInputStream() {
        return null;
    }

    default InputStream getObjectInputStream(FileWrapper fileWrapper) {
        return null;
    }

    default boolean copyFile(String sourceFileName, String targetBucketName) {
        return false;
    }

    default String wrapperBucketName(String bucketName) {
        return null;
    }
}