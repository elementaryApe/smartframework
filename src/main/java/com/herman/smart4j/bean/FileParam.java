package com.herman.smart4j.bean;

import java.io.InputStream;

/**
 * 文件上传参数
 *
 * @author hsh
 * @create 2018-05-07 17:57
 **/
public class FileParam {

    /**
     * 文件表单字段名
     */
    private String fieldName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 上传文件的Content-Type(文件类型)
     */
    private String contentType;

    /**
     * 上传文件的字节输入流
     */
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
