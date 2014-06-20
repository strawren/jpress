/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月24日 下午1:58:35
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月24日        Initailized
 */

package com.strawren.jpress.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * FreeMarker相关工具类
 * 根据key获取map中的value,并转换成相应的类型
 *
 */
@SuppressWarnings("rawtypes")
public abstract class FreemarkerParamUtils {

    private static final Log log = LogFactory.getLog(FreemarkerParamUtils.class);

    /**
     * 根据key获取value并转成Long类型
     * @param params
     * @param string
     * @return
     */
    public static Long getLongParam(Map params, String key) {
        Object value = params.get(key);
        Long param = null;
        if (value!=null) {
            try {
                param = Long.parseLong(value.toString());
            }
            catch (Exception e) {
                log.debug("getLongParam fail,key:"+key+",value:"+value);
            }
        }
        return param;
    }

    /**
     * 根据key获取value并转成Double类型
     * @param params
     * @param string
     * @return
     */
    public static Double getDoubleParam(Map params, String key) {
        Object value = params.get(key);
        Double param = null;
        if (value!=null) {
            try {
                param = Double.parseDouble(value.toString());
            }
            catch (Exception e) {
                log.debug("getDoubleParam fail,key:"+key+",value:"+value);
            }
        }
        return param;
    }

    /**
     * 根据key获取value并转成Double类型
     * @param params
     * @param string
     * @return
     */
    public static String getStringParam(Map params, String key) {
        Object value = params.get(key);
        String param = null;
        if (value!=null) {
           param = value.toString();
        }
        return param;
    }

    /**
     * 根据key获取value并转成Integer类型
     * @param params
     * @param string
     * @return
     */
    public static Integer getIntParam(Map params, String key) {
        Object value = params.get(key);
        Integer param = null;
        if (value!=null) {
            try {
                param = Integer.parseInt(value.toString());
            }
            catch (Exception e) {
                log.debug("getIntParam fail,key:"+key+",value:"+value);
            }
        }
        return param;
    }
}
