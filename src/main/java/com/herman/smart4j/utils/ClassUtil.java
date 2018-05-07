package com.herman.smart4j.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author hsh
 * @create 2018-04-28 11:15
 **/
public class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * 当前线程中类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className     类名
     * @param isInitialized 是否初始化(是否执行类中静态块代码)
     * @return Class
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("类加载失败", e);
            throw new RuntimeException();
        }
        return cls;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        //根据需要的包名将其转化为文件路径，读取class文件或jar包，获取指定的类名去加载类
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            try {
                Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    if (url != null) {
                        String protocol = url.getProtocol();
                        if (protocol.equals("file")) {
                            String packagePath = url.getPath().replaceAll("%20", "");
                            addClass(classSet, packagePath, packageName);
                        } else if (protocol.equals("jar")) {
                            JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                            url.openConnection();
                            if (urlConnection != null) {
                                JarFile jarFile = urlConnection.getJarFile();
                                if (jarFile != null) {
                                    Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                                    while (jarEntryEnumeration.hasMoreElements()) {
                                        JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                        String jarEntryName = jarEntry.getName();
                                        if (jarEntryName.endsWith(".class")) {
                                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                            doAddClass(classSet, className);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("获取类失败", e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        assert files != null;
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "/" + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> loadClass = loadClass(className, false);
        classSet.add(loadClass);
    }


}


