package com.herman.smart4j.helper;

import com.herman.smart4j.bean.FileParam;
import com.herman.smart4j.bean.FormParam;
import com.herman.smart4j.bean.Param;
import com.herman.smart4j.utils.CollectionUtil;
import com.herman.smart4j.utils.FileUtil;
import com.herman.smart4j.utils.StreamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传辅助工具类
 *
 * @author hsh
 * @create 2018-05-07 19:04
 **/
public final class UploadHelper {

    private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache Commons FileUpload 提供的Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.content.temdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为multipart
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        List<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
            //解析请求参数
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            //遍历请求参数来初始化formParamList、fileParamList
            if (CollectionUtil.isNotEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> fileItemListMapEntry : fileItemListMap.entrySet()) {
                    String filedName = fileItemListMapEntry.getKey();
                    List<FileItem> fileItemList = fileItemListMapEntry.getValue();
                    if (CollectionUtils.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            //普通表单字段
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(filedName, fieldValue));
                            } else {
                                //文件上传字段
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtils.isNotEmpty(fileName)) {
                                    long fileItemSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(filedName, fileName, fileItemSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            logger.error("创建参数 失败", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                BufferedInputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            logger.error("上传文件 失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (CollectionUtils.isNotEmpty(fileParamList)) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (Exception e) {
            logger.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

}
