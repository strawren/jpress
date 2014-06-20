/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-5-12 上午5:38:46
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-5-12        Initailized
 */

package com.strawren.bsp.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;


/**
 * org.apache.commons.beanutils.BeanUtils类的扩展类，对date转换作了特殊处理
 *
 */
public final class BeanExtUtils extends BeanUtils{
    //private static Map cache = new HashMap();
    //private static Log logger = LogFactory.getFactory().getInstance(BeanUtilsExt.class);

    private BeanExtUtils() {
    }

    static {
      //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
      ConvertUtils.register(new DateConverter(), java.sql.Date.class);
      //ConvertUtils.register(new SqlTimestampConverter(), java.sql.Timestamp.class);
      //注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
      ConvertUtils.register(new DateConverter(), java.util.Date.class);
      ConvertUtils.register(new DateConverter(), java.sql.Timestamp.class);
    }

    public static void copyProperties(Object target, Object source) throws
        InvocationTargetException, IllegalAccessException {
      //支持对日期copy
      org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }
}

class DateConverter implements Converter {

    @SuppressWarnings("rawtypes")
    public Object convert(Class type, Object value) {
    if(value == null) {
        return null;
    }
    
    if(value instanceof Date) {
        return value;
    }
    
    if(value instanceof Long) {
        Long longValue = (Long) value;
        return new Date(longValue.longValue());
    }
    
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
            } catch (Exception ex) {
                throw new ConversionException(e);
            }
            
        }
    }
}
