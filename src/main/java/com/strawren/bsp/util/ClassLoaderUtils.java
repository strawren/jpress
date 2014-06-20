/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-3 下午05:17:28
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-3        Initailized
 */

package com.strawren.bsp.util;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 类加载器的帮助类
 *
 */
public class ClassLoaderUtils {
    protected static Log log = LogFactory.getLog(ClassLoaderUtils.class);

    private ClassLoaderUtils() {

    }

    public static void addURL(String url, ClassLoader classLoader) {
        log.debug("addURL(),url->" + url);

        if (StringUtils.isBlank(url)) {
            return;
        }
        ClassLoader threadContextClassLoader = classLoader;
        if (threadContextClassLoader == null) {
            threadContextClassLoader = Thread.currentThread().getContextClassLoader();
        }
        boolean is = threadContextClassLoader instanceof URLClassLoader;
        if (is) {
            try {
                URL realUrl = new URL(url);
                URLClassLoader curr = (URLClassLoader) threadContextClassLoader;
                Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
                add.setAccessible(true);
                add.invoke(curr, new Object[] { realUrl });
            } 
            catch (Exception e) {
                log.warn("WARN", e);
                throw new IllegalArgumentException(e);
            }
        } else {
            log.warn("the classloader is not a URLClassLoader type!");
            throw new IllegalArgumentException("the classloader is not a URLClassLoader type!");
        }
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } 
        catch (Throwable e) {
            log.info("INFO", e);
        }
        if (cl == null) {
            cl = ClassLoaderUtils.class.getClassLoader();
        }
        return cl;
    }
    
    public static void removeURL(String url, ClassLoader classLoader){
        //TODO 等待实现，从classloader中删除某个url
    }
    
    public static void removeURL(String [] urls, ClassLoader classLoader){
        if(urls != null && urls.length > 0 && classLoader != null){
            for(String url : urls){
                removeURL(url, classLoader);
            }
        }
    }

    public static void removeURL(List<String> urlList, ClassLoader classLoader){
        if(urlList != null && urlList.size() > 0 && classLoader != null){
            for(String url : urlList){
                removeURL(url, classLoader);
            }
        }
    }
}
