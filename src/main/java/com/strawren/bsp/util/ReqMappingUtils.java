/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2012-4-18 下午1:04:13
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2012-4-18        Initailized
 */

package com.strawren.bsp.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.strawren.bsp.web.annotation.ReqMapping;


/**
 * 请求对象转换工具类
 *
 */
public class ReqMappingUtils {
    protected static final Log log = LogFactory.getLog(ReqMappingUtils.class);
    
    private ReqMappingUtils(){
        
    }
    
    public static void paramToObject(Object obj, HttpServletRequest request){
        if(obj == null || request == null){
            log.debug("obj or request is null");
            return;
        }

        try{
            List<?> fields = getRelativeFields(obj.getClass());
            for (Object o : fields) {
                Field field = (Field) o;
                log.debug("field ->" + field.getName());
                
                ReqMapping annotation = field.getAnnotation(ReqMapping.class);
                String paramName = annotation.paramName();
                if(StringUtils.isBlank(paramName)){
                    paramName = field.getName();
                }
                log.debug("paramName ->" + paramName);
                
                if(annotation.mutilFlag()){
                    String [] values = request.getParameterValues(paramName);
                    log.debug("mutil values ->" + values);
                    field.set(obj, values);
                }
                else{
                    String value = request.getParameter(paramName);
                    log.debug("value ->" + value);
                    field.set(obj, value);
                }
            }
        }
        catch(Exception e){
            log.warn(e.getMessage(), e);
        }
    }
    
    public static Object paramToObject(Class<?> clazz, HttpServletRequest request){
        Object targetObject = null;
        try {
            targetObject = clazz.newInstance();
            paramToObject(targetObject, request);
        } 
        catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return targetObject;
    }
    
    public static String objectToParam(Object obj){
        if(obj == null){
            log.debug("obj or request is null");
            return "";
        }

        StringBuilder sb = new StringBuilder();
        try{
            List<?> fields = getRelativeFields(obj.getClass());
            for (Object o : fields) {
                Field field = (Field) o;
                log.debug("field ->" + field.getName());
                
                ReqMapping annotation = field.getAnnotation(ReqMapping.class);
                String paramName = annotation.paramName();
                if(StringUtils.isBlank(paramName)){
                    paramName = field.getName();
                }
                log.debug("paramName ->" + paramName);
                
                if(annotation.mutilFlag()){
                    String [] values = (String [])field.get(obj);
                    log.debug("mutil values ->" + values);
                    for(String value : values){
                        if(sb.length() > 0){
                            sb.append("&");
                        }
                        sb.append(paramName + "=" + value);
                    }
                }
                else{
                    String value = (String)field.get(obj);
                    log.debug("value ->" + value);
                    if(sb.length() > 0){
                        sb.append("&");
                    }
                    sb.append(paramName + "=" + value);
                }
            }
        }
        catch(Exception e){
            log.warn(e.getMessage(), e);
        }
        return sb.toString();
    }
    
    protected static List<?> getRelativeFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> result = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ReqMapping.class)) {
                ((AccessibleObject) field).setAccessible(true);
                result.add(field);
            }
        }
        return result;
    }
}
