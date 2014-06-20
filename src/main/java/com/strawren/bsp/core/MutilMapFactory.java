/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by jason at 2012-5-25 上午9:29:04
 * $URL$
 * 
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * jason 2012-5-25 Initailized
 */

package com.strawren.bsp.core;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.GenericCollectionTypeResolver;

/**
 * 多个map设置
 * 
 */
public class MutilMapFactory extends AbstractFactoryBean<Map<?, ?>> {
    private List<Map<?, ?>> sourceList;
    private Class<?> targetMapClass;
    
    public void setSourceList(List<Map<?, ?>> sourceList) {
        this.sourceList = sourceList;
    }
    
    public void setTargetMapClass(Class<?> targetMapClass) {
        if (targetMapClass == null) {
            throw new IllegalArgumentException("'targetMapClass' must not be null");
        }
        
        if (!(Map.class.isAssignableFrom(targetMapClass))){
            throw new IllegalArgumentException("'targetMapClass' must implement [java.util.Map]");
        }
        
        this.targetMapClass = targetMapClass;
    }
    
    @SuppressWarnings("rawtypes")
    public Class<Map> getObjectType() {
        return Map.class;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map<?, ?> createInstance() {
        if (this.sourceList == null)
            throw new IllegalArgumentException("'sourceList' is required");
        
        Map<Object, Object> result = null;
        if (this.targetMapClass != null) {
            result = (Map<Object, Object>) BeanUtils.instantiateClass(this.targetMapClass);
        }
        else{
            result = new LinkedHashMap<Object, Object>();
        }
        
        Class<?> keyType = null;
        Class<?> valueType = null;
        if (this.targetMapClass != null) {
            keyType = GenericCollectionTypeResolver.getMapKeyType((Class<? extends Map>) this.targetMapClass);
            valueType = GenericCollectionTypeResolver.getMapValueType((Class<? extends Map>) this.targetMapClass);
        }
        if ((keyType != null) || (valueType != null)) {
            TypeConverter converter = getBeanTypeConverter();
            for(Map<?, ?> map : sourceList){
                for (Iterator localIterator = map.entrySet().iterator(); localIterator.hasNext();) {
                    Map.Entry entry = (Map.Entry) localIterator.next();
                    Object convertedKey = converter.convertIfNecessary(entry.getKey(), keyType);
                    Object convertedValue = converter.convertIfNecessary(entry.getValue(), valueType);
                    result.put(convertedKey, convertedValue);
                }
            }
        }
        else {
            for(Map<?, ?> map : sourceList){
                result.putAll(map);
            }
        }
        return result;
    }
}