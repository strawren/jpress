/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-23 下午12:02:25
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2013-8-23        Initailized
 */

package com.orientpay.clivia.portal.web.auths;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Json工具类
 *
 */
public class JsonUtils {

	public static List<?> jsonToList(String json, Class<?> clazz) {
        JSONArray jsonArray = JSONArray.fromObject(json); 
        List<Object> list = new ArrayList<Object>();
        int iSize = jsonArray.size();
        for(int i =0; i < iSize; i++) {
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
        
    	//日期值处理器 
    	JsonConfig jsonConfig = new JsonConfig(); 
    	jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor(){
    		private final String format = "yyyy-MM-dd HH:mm:ss"; 

    		public Object processArrayValue(Object value, JsonConfig jsonConfig) { 
    			return process(value, jsonConfig); 
    		}
	      
    		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) { 
    			return process(value, jsonConfig); 
    		} 

    		private Object process( Object value, JsonConfig jsonConfig ) { 
    			if (value instanceof Date) { 
    				String str = new SimpleDateFormat(format).format((Date) value); 
    				return str; 
    			} 
    			return value == null ? null : value.toString(); 
    		} 
      }); 
      
      if(object != null){ 
    	  if(object instanceof Collection<?> || object instanceof Object[]){ 
    		  jsonString = JSONArray.fromObject(object, jsonConfig).toString(); 
    	  }
    	  else{ 
    		  jsonString = JSONObject.fromObject(object, jsonConfig).toString(); 
    	  } 
      } 
      return jsonString == null ? "{}" : jsonString; 
    }
}
