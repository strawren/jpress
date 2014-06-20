/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-11-1 上午11:04:12
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-11-1        Initailized
 */

package com.strawren.bsp.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 对象操作工具类
 *
 */
public class ObjectUtils {
    private static final Log log = LogFactory.getLog(ObjectUtils.class);
    
    private ObjectUtils(){
        
    }
    
    /**
     * 得到方法的值
     * @param property
     * @param object
     * @return
     */
    public static Object getMethodValue(String property, Object object) {
        /*Field[] fields = object.getClass().getDeclaredFields();
        Object valueObject = null;
        for (int j = 0; j < fields.length; j++) {
            if (fields[j].getName().equals(property)) {
                String firstChar = fields[j].getName().substring(0, 1);
                String leaveChar = fields[j].getName().substring(1);
                String methodName = firstChar.toUpperCase() + leaveChar;

                try {
                    Method meth = object.getClass().getMethod("get" + methodName, null);
                    valueObject = meth.invoke(object, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        return valueObject;*/
        
        //add by zhouxushun 2011-11-22 to support map
        if(object instanceof Map){
            Map<?, ?> objMap = (Map<?, ?>)object;
            return objMap.get(property);
        }
        //end 
        
        String firstChar = property.substring(0, 1);
        String leaveChar = property.substring(1);
        String methodName = firstChar.toUpperCase() + leaveChar;
        Object ret = null;
        
        try {
            Method method = getAllMethod("get" + methodName, object.getClass());
            if(method != null){
                //ret = method.invoke(object, null);
                ret = method.invoke(object, new Object[]{});
            }
        }
        catch(Exception e){
            log.warn(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 递归获取对象的值
     * @param property
     * @param object
     * @return
     */
    public static Object getMethodValueForRecursion(String property, Object object) {
        if (property == null || object == null) {
            return null;
        }
        if (property.indexOf(".") == -1) {
            return getMethodValue(property, object);
        } else {
            String theFirstProperty = property.substring(0, property.indexOf("."));
            String theSecondProperty = property.substring(property.indexOf(".") + 1);

            Object firstObj = getMethodValue(theFirstProperty, object);
            return getMethodValueForRecursion(theSecondProperty, firstObj);

        }
    }

    /**
     * 对象复制
     * @param srcObject
     * @param targetClassName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object objectCopy(Object srcObject, String targetClassName) {
        Object targetObject = null;
        if ((srcObject == null) || (targetClassName == null)) {
            return null;
        }

        if (srcObject instanceof Collection) {
            Iterator<Object> iter = ((Collection<Object>) srcObject).iterator();
            Collection<Object> coll = new ArrayList<Object>();
            Object item = null;
            Object targetItem = null;
            while (iter.hasNext()) {
                item = iter.next();
                targetItem = deepObjectCopy(item, targetClassName);
                coll.add(targetItem);
            }
            targetObject = coll;
        } 
        else {
            targetObject = deepObjectCopy(srcObject, targetClassName);
        }
        return targetObject;
    }

    /**
     * 
     * @param srcObject
     * @param targetClassName
     * @return
     */
    private static Object deepObjectCopy(Object srcObject, String targetClassName) {
        Class<?> targetClass = null;
        Object targetObject = null;
        try {
            targetClass = Class.forName(targetClassName);
            targetObject = targetClass.newInstance();
            PropertyUtils.copyProperties(targetObject, srcObject);
        } 
        catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return targetObject;
    }

    /**
     * 需要重构，简单的基本类型进行拷贝
     * @param srcObject
     * @param targetClassName
     * @return
     */
    public static Object objectPrimitiveCopy(Object srcObject, String targetClassName){
        try {
            Object targetObject = Class.forName(targetClassName).newInstance();
            return objectPrimitiveCopy(srcObject,targetObject,false);
        } 
        catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 需要重构，简单的基本类型进行拷贝
     * @param srcObject
     * @param targetClassName
     * @return
     */
    public static Object objectPrimitiveCopy(Object srcObject, Object targetObject, boolean valueFromSrc){
        Class<?> valueClass = null;
        if(valueFromSrc){
            valueClass = srcObject.getClass();
        }
        else{
            valueClass = targetObject.getClass();
        }
        
        try {
            Field[] fields = valueClass.getDeclaredFields();
            for(Field field : fields){
                Class<?> fieldType = field.getType();
                
                //如果是基本类型，字符串，boolean、integer、long、double类型
                if(fieldType.isPrimitive() || fieldType.getName().equals(String.class.getName()) || 
                        fieldType.getName().equals(Boolean.class.getName()) || 
                        fieldType.getName().equals(Integer.class.getName()) ||
                        fieldType.getName().equals(Long.class.getName()) ||
                        fieldType.getName().equals(Double.class.getName())  ){
                    
                    String property = field.getName();
                    Object value = getMethodValue(property,srcObject);
                    setMethodValue(property,targetObject,value);
                }
            }
        } 
        catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return targetObject;
        
    }
    
    public static String strArroStringSQL(String[] strArr) {
        if (strArr == null || strArr.length == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArr.length; i++) {
            sb.append("'" + strArr[i] + "',");
        }
        String returnValue = sb.toString();
        returnValue = returnValue.substring(0, returnValue.length() - 1);
        return returnValue;
    }

    public static boolean isHaveProperty(String property, Class<?> objClass) {
        Field[] fields = objClass.getDeclaredFields();
        boolean flag = false;
        for (int j = 0; j < fields.length; j++) {
            if (fields[j].getName().equals(property)) {
                flag = true;
                break;
            }

        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    public static void setMethodValue(String property, Object object, Object propertyValue) {
        String firstChar = property.substring(0, 1);
        String leaveChar = property.substring(1);
        String methodName = firstChar.toUpperCase() + leaveChar;
        
        //add by zhouxushun 2011-11-22 to support map
        if(object instanceof Map){
            Map<String, Object> objMap = (Map<String, Object>)object;
            objMap.put(property, propertyValue);
            return;
        }
        //end 
        
        try {
            Method meth = null;
            Class<?> fieldType = null;
            
            //Method method = object.getClass().getDeclaredMethod("get" + methodName,null);
            Method method = getAllMethod("get" + methodName, object.getClass());
            if(method != null){
                fieldType = method.getReturnType();
            }
            else{
                fieldType = object.getClass().getDeclaredField(property).getType();
            }
            
            if(fieldType != null){
                if(propertyValue == null){
                    return;
                }
                
                //如果为基本类型
                if(fieldType.isPrimitive()){
                    if(propertyValue instanceof Boolean){
                        meth = object.getClass().getMethod("set" + methodName, Boolean.TYPE);
                    }
                    else if(propertyValue instanceof Integer){
                        meth = object.getClass().getMethod("set" + methodName, Integer.TYPE);
                    }
                    else if(propertyValue instanceof Long){
                        meth = object.getClass().getMethod("set" + methodName, Long.TYPE);
                    }
                    else if(propertyValue instanceof Double){
                        meth = object.getClass().getMethod("set" + methodName, Double.TYPE);
                    }
                }
                else{
                    meth = object.getClass().getMethod("set" + methodName, propertyValue.getClass());
                }
                
                if(meth != null){
                    meth.invoke(object, propertyValue);
                }
            }
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setMethodValueForRecursion(String property, Object object, Object value) {
        if (property == null || object == null) {
            return;
        }
        if (property.indexOf(".") == -1) {
            setMethodValue(property, object,value);
        } 
        else {
            String theFirstProperty = property.substring(0, property.indexOf("."));
            String theSecondProperty = property.substring(property.indexOf(".") + 1);

            Object firstObj = getMethodValue(theFirstProperty, object);
            setMethodValueForRecursion(theSecondProperty, firstObj, value);
        }
    }
    
    protected static Method getAllMethod(String methodName, Class<?> clazz){
        if(clazz==null){
            return null;
        }
        
        try{
            //Method method = object.getClass().getDeclaredMethod("get" + methodName, Null.class);
            Method method = clazz.getDeclaredMethod(methodName, new Class[]{});
            return method;
        }
        catch(Exception e){
            return getAllMethod(methodName, clazz.getSuperclass());
        }
    }
}
