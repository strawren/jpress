/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-5 下午05:48:35
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-5        Initailized
 */

package com.strawren.bsp.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 断言类 用于检查参数，抛出运行时异常
 * 
 */
public class AssertUtils {
    
    private AssertUtils() {
        
    }
    
    public static void isTrue(boolean expression, String message) {
        if (!(expression)){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }
    
    public static void isNull(Object object, String message) {
        if (object != null){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }
    
    public static void notNull(Object object, String message) {
        if (object == null){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }
    
    public static void hasLength(String text, String message) {
        if (StringUtils.isBlank(text)){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void hasLength(String text) {
        hasLength(text,
                  "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }
    
    public static void hasText(String text, String message) {
        if (StringUtils.isBlank(text)){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void hasText(String text) {
        hasText(text,
                "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }
    
    public static void doesNotContain(String textToSearch,
                                      String substring,
                                      String message) {
        if ((StringUtils.isBlank(textToSearch))
                || (StringUtils.isBlank(substring))
                || (textToSearch.indexOf(substring) == -1)){
            return;
        }
        throw new IllegalArgumentException(message);
    }
    
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch,
                       substring,
                       "[Assertion failed] - this String argument must not contain the substring ["
                               + substring
                               + "]");
    }
    
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notEmpty(Object[] array) {
        notEmpty(array,
                 "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }
    
    public static void noNullElements(Object[] array, String message) {
        if (array != null){
            for (int i = 0; i < array.length; ++i){
                if (array[i] == null){
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }
    
    public static void noNullElements(Object[] array) {
        noNullElements(array,
                       "[Assertion failed] - this array must not contain any null elements");
    }
    
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection,
                 "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }
    
    public static void notEmpty(Map<?,?> map, String message) {
        if (map == null || map.size() == 0){
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void notEmpty(Map<?,?> map) {
        notEmpty(map,
                 "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }
    
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }
    
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!(type.isInstance(obj)))
            throw new IllegalArgumentException(message
                    + "Object of class ["
                    + ((obj != null) ? obj.getClass().getName() : "null")
                    + "] must be an instance of "
                    + type);
    }
    
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }
    
    public static void isAssignable(Class<?> superType,
                                    Class<?> subType,
                                    String message) {
        notNull(superType, "Type to check against must not be null");
        if ((subType == null) || (!(superType.isAssignableFrom(subType))))
            throw new IllegalArgumentException(message
                    + subType
                    + " is not assignable to "
                    + superType);
    }
    
    public static void state(boolean expression, String message) {
        if (!(expression))
            throw new IllegalStateException(message);
    }
    
    public static void state(boolean expression) {
        state(expression,
              "[Assertion failed] - this state invariant must be true");
    }
}
