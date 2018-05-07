package com.herman.smart4j.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合辅助工具类
 *
 * @author hsh
 * @create 2018-04-27 15:45
 **/
public class CollectionUtil {

    /**
     * 判断Collection是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     */
    public static boolean isEmpty(Map<?, ?> collection) {
        return MapUtils.isEmpty(collection);
    }

    /**
     * 判断Map是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> collection) {
        return MapUtils.isNotEmpty(collection);
    }
}
