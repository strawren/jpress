/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-9-16 下午03:30:43
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-9-16        Initailized
 * Zhouxushun     2011-11-14       add array property type
 */

package com.strawren.bsp.orm.query;

import java.util.Date;

import com.strawren.bsp.core.Constants;


/**
 *  属性数据类型.
 *
 */
public enum PropertyType {
    S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class), A(String.class);
    
    private Class<?> clazz;
    
    PropertyType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getValue() {
        return clazz;
    }
    
    public String expressStart(){
        if("S".equals(this.name())){
            return "'";
        }
        if("D".equals(this.name())){
            return "to_date('";
        }
        return "";
    }
    
    public String expressEnd(){
        if("S".equals(this.name())){
            return "'";
        }
        if("D".equals(this.name())){
            return "','" + Constants.ORACLE_LONG_TIME_FORMAT + "')";
        }
        return "";
    }
    
}