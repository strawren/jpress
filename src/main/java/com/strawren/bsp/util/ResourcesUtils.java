/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-8 上午11:41:33
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-8        Initailized
 */

package com.strawren.bsp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;


/**
 * 资源工具类
 *
 */
public class ResourcesUtils {
private ResourcesUtils(){
        
    }
    
    public static URL getResourceURL(String resource) throws IOException {
        return getResourceURL(ClassLoaderUtils.getDefaultClassLoader(), resource);
    }
    
    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = null;
        if (loader != null){
            url = loader.getResource(resource);
        }
        if (url == null){
            url = ClassLoader.getSystemResource(resource);
        }
        if (url == null){
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }
    
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(ClassLoaderUtils.getDefaultClassLoader(), resource);
    }
    
    public static InputStream getResourceAsStream(ClassLoader loader,
                                                  String resource)
            throws IOException {
        InputStream in = null;
        if (loader != null){
            in = loader.getResourceAsStream(resource);
        }
        if (in == null){
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if(in == null){
            in = new FileInputStream(new File(resource));
        }
        return in;
    }
    
    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        in = getResourceAsStream(propfile);
        props.load(in);
        in.close();
        return props;
    }
    
    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        in = getResourceAsStream(loader, propfile);
        props.load(in);
        in.close();
        return props;
    }
    
    public static Reader getResourceAsReader(String resource) throws IOException {
        return new InputStreamReader(getResourceAsStream(resource));
    }

    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        return new InputStreamReader(getResourceAsStream(loader, resource));
    }
    
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }
    
    public static File getResourceAsFile(ClassLoader loader, String resource)
            throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }
    
    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }
    
    public static Reader getUrlAsReader(String urlString) throws IOException {
        return new InputStreamReader(getUrlAsStream(urlString));
    }
    
    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = urlString;
        in = getUrlAsStream(propfile);
        props.load(in);
        in.close();
        return props;
    }
    
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            clazz = ClassLoaderUtils.getDefaultClassLoader().loadClass(className);
        } 
        catch (Exception e) {
        }
        if (clazz == null) {
            clazz = Class.forName(className);
        }
        return clazz;
    }
    
    public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return instantiate(classForName(className));
    }
    
    public static Object instantiate(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
