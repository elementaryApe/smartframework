package com.herman.smart4j.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件辅助工具类
 *
 * @author hsh
 * @create 2018-04-27 12:02
 **/
public final class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName) {
        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new FileNotFoundException(fileName + "文件未找到!");
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("load properties file failure", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("close inputStream  failure", e);
                }
            }
        }
        return properties;
    }

    /**
     * 获取字符型属性
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取字符型属性(可指定默认值)
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }
    /**
     * 获取数值型属性(默认为0)
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * 获取数值型属性(可指定默认值)
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = Integer.valueOf(props.getProperty(key));
        }
        return value;
    }
    /**
     * 获取boolean型属性(默认false)
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取boolean型属性(可指定默认值)
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.valueOf(props.getProperty(key));
        }
        return value;
    }
}
