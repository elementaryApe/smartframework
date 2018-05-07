package com.herman.smart4j.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 类型转换工具
 *
 * @author hsh
 * @create 2018-04-27 15:33
 **/
public class CastUtil {

    /**
     * 转换为String
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转换为String(默认值)
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转换为double(默认值为0)
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转换为double(默认值)
     */
    public static double castDouble(Object obj, double defaultValue) {
        double resultValue = defaultValue;
        if (obj != null) {
            String castString = castString(obj);
            if (StringUtils.isNotEmpty(castString)) {
                try {
                    defaultValue = Double.parseDouble(castString);
                } catch (NumberFormatException e) {
                    resultValue = defaultValue;
                }
            }
        }
        return resultValue;
    }

    /**
     * 转换为long(默认值为0)
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转换为long(默认值)
     */
    public static long castLong(Object obj, long defaultValue) {
        long resultValue = defaultValue;
        if (obj != null) {
            String castString = castString(obj);
            if (StringUtils.isNotEmpty(castString)) {
                try {
                    defaultValue = Long.parseLong(castString);
                } catch (NumberFormatException e) {
                    resultValue = defaultValue;
                }
            }
        }
        return resultValue;
    }

    /**
     * 转换为int(默认值为0)
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转换为int(默认值)
     */
    public static int castInt(Object obj, int defaultValue) {
        int resultValue = defaultValue;
        if (obj != null) {
            String castString = castString(obj);
            if (StringUtils.isNotEmpty(castString)) {
                try {
                    defaultValue = Integer.parseInt(castString);
                } catch (NumberFormatException e) {
                    resultValue = defaultValue;
                }
            }
        }
        return resultValue;
    }

    /**
     * 转换为boolean(默认值为false)
     */
    public static boolean castBooleant(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转换为boolean(默认值)
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean resultValue = defaultValue;
        if (obj != null) {
            String castString = castString(obj);
            if (StringUtils.isNotEmpty(castString)) {
                try {
                    defaultValue = Boolean.parseBoolean(castString);
                } catch (NumberFormatException e) {
                    resultValue = defaultValue;
                }
            }
        }
        return resultValue;
    }
}
