package com.herman.smart4j.helper;

import com.herman.smart4j.constans.ConfigConstant;
import com.herman.smart4j.utils.PropsUtil;

import java.util.Properties;

/**
 * 属性文件辅助工具类
 *
 * @author hsh
 * @create 2018-04-28 10:37
 **/
public class ConfigHelper {

    public static final String DEFAULT_JSP_PATH = "/WEB-INF/view/";
    public static final String DEFAULT_ASSERT_PATH = "/assert/";


    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取驱动
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取url
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取密码
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取用户名
     */
    public static String getJdbcUserName() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取对应基础包名
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取对应jsp路径
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, DEFAULT_JSP_PATH);
    }

    /**
     * 获取静态资源文件路径
     */
    public static String getAppAssertPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSERT_PATH, DEFAULT_ASSERT_PATH);
    }
}
