package com.herman.smart4j.helper;

import com.herman.smart4j.bean.FormParam;
import com.herman.smart4j.bean.Param;
import com.herman.smart4j.utils.CodecUtil;
import com.herman.smart4j.utils.StreamUtil;
import com.herman.smart4j.utils.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求辅助工具类
 *
 * @author hsh
 * @create 2018-05-07 20:09
 **/
public final class RequestHelper {

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        formParamList.addAll(parseParameterName(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterName(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtils.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder valueStr = new StringBuilder("");
                    for (int i = 0; i < fieldValues.length; i++) {
                        if (i != fieldValues.length - 1) {
                            valueStr.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = valueStr.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtils.isNotEmpty(body)) {
            String[] kvs = StringUtils.split(body, "&");
            if (ArrayUtils.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] array = StringUtil.splitString(kv, "=");
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName, fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }
}
