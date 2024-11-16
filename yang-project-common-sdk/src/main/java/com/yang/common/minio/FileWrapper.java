
package com.yang.common.minio;

import cn.hutool.core.io.FileUtil;
import com.yang.common.utils.YStrUtils;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class FileWrapper {
    private String id;

    private String extName;

    private String originalFilename;

    private String newFileName;

    private Long fileSize;

    private MultipartFile file;

    private InputStream inputStream;

    private String ContentType;

    private String folder;

    public FileWrapper() {
        this.ContentType = MediaType.APPLICATION_OCTET_STREAM.toString();
    }

    public FileWrapper(MultipartFile file) {
        this(file, true);
    }

    public FileWrapper(MultipartFile file, boolean needFolder) {
        this.id = UUID.randomUUID().toString().toLowerCase();
        this.file = file;
        this.fileSize = file.getSize();
        try {
            this.inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        }
        this.originalFilename = file.getOriginalFilename(); //获取源文件名
        this.extName = FileUtil.extName(originalFilename);  //获取文件扩展名
        if (needFolder) {
            this.newFileName = wrapperNewFileNameByDate(id + YStrUtils.DOT + extName);
            this.folder = getFolderName();
        } else {
            this.newFileName = id + YStrUtils.DOT + extName;
        }
        this.ContentType = file.getContentType();
        if (YStrUtils.isNull(extName)) {
            throw new RuntimeException("没有找到文件扩展名");
        }
    }

    /**
     * 通过日期包装文件名
     */
    private String wrapperNewFileNameByDate(String newFileName) {
        return getFolderName() + newFileName;
    }

    private String getFolderName() {
        LocalDate now = LocalDate.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
    }

    public FileWrapper(String newFileName) {
        this.extName = FileUtil.extName(newFileName);
        this.newFileName = newFileName;
    }
}
