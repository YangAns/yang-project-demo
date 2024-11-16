package com.yang.securitydemoservice.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yang.common.minio.FileWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file_info")
@NoArgsConstructor
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("original_file_name")
    private String originalFileName;

    @TableField("ext_name")
    private String extName;

    @TableField("new_file_name")
    private String newFileName;

    @TableField("file_size")
    private Long fileSize;

    @TableField("folder")
    private String folder;

    @TableField("is_init_file")
    private Integer isInitFile;

    @TableField("is_keep_alive")
    private Integer isKeepAlive;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Long createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Long updateDate;

    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Integer delFlag;


    public FileInfo(FileWrapper fileWrapper) {
        this.id = fileWrapper.getId();
        this.originalFileName = fileWrapper.getOriginalFilename();
        this.extName = fileWrapper.getExtName();
        this.newFileName = fileWrapper.getNewFileName();
        this.fileSize = fileWrapper.getFileSize();
        this.folder = fileWrapper.getFolder();
    }

    public FileWrapper toFileWrapper() {
        FileWrapper fileWrapper = new FileWrapper();
        fileWrapper.setId(this.id);
        fileWrapper.setOriginalFilename(this.originalFileName);
        fileWrapper.setExtName(this.extName);
        fileWrapper.setNewFileName(this.newFileName);
        fileWrapper.setFileSize(this.fileSize);
        return fileWrapper;
    }
}
