package com.herman.smart4j.bean;

import com.herman.smart4j.utils.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 *
 * @author hsh
 * @create 2018-04-29 11:33
 **/
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    /**
     * 根据参数名称获取long字段
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }


}
