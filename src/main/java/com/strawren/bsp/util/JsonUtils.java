package com.strawren.bsp.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Json工具类
 * 
 */
public class JsonUtils {
    public static final Log log = LogFactory.getLog(JsonUtils.class);
    
    public static List<?> jsonToList(String json, Class<?> clazz) {
        JSONArray jsonArray = JSONArray.fromObject(json);
        List<Object> list = new ArrayList<Object>();
        int iSize = jsonArray.size();
        for (int i = 0; i < iSize; i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Object obj = JSONObject.toBean(jsonObj, clazz);
            list.add(obj);
        }
        return list;
    }
    
    public static Object jsonToObject(String json, Class<?> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return JSONObject.toBean(jsonObject, clazz);
    }
    
    public static String objectToJson(Object object) {
        String jsonString = null;
        
        // 日期值处理器
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            
            private final String format = "yyyy-MM-dd HH:mm:ss";
            
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                return process(value, jsonConfig);
            }
            
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                return process(value, jsonConfig);
            }
            
            private Object process(Object value, JsonConfig jsonConfig) {
                if (value instanceof Date) {
                    String str = new SimpleDateFormat(format).format((Date) value);
                    return str;
                }
                return value == null ? null
                        : value.toString();
            }
        });
        
        if (object != null) {
            if (object instanceof Collection<?> || object instanceof Object[]) {
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();
            }
            else {
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();
            }
        }
        return jsonString == null ? "{}"
                : jsonString;
    }
    
    /**
     * 将Map转为bean对象
     * 
     * @param obj
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static Object mapToBean(Class clazz, Map<String, String> map) {
        
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
            Object obj = clazz.newInstance(); // 创建 JavaBean 对象
            
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    
                    Object[] args = new Object[1];
                    args[0] = value;
                    
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
            return obj;
        }
        catch (Exception e) {
            log.debug("map trans to bean error", e);
        }
        
        return null;
        
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(JSONObject jsonObject) {
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }
}