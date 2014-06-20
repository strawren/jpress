/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-18 下午7:05:51
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-18        Initailized
 */

package com.strawren.bsp.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.strawren.bsp.core.BspUserDetails;
import com.strawren.bsp.core.Constants;


/**
 * model更新的辅助类，用于修改实体对象里面各个字段的信息
 *
 */
public class ModelUpdInfoUtils {
    //创建和修改信息
    public static final String CREATE_TIME =        "createTime";
    public static final String CREATE_OPER_ID =     "createOperId";
    public static final String CREATE_OPER_NAME =   "createOperName";
    public static final String LAST_MOD_TIME =      "updateTime";
    public static final String LAST_MOD_OPER_ID =   "updateOperId";
    public static final String LAST_MOD_OPER_NAME = "updateOperName";
    public static final String STATUS =             "status";
    
    //主键信息
    public static final String ID =                 "id";
    public static final String CODE =               "code";
    public static final String APP_CODE =           "appCode";
    
    private ModelUpdInfoUtils(){ }

    /**
     * 更新实体对象的最后修改时间
     * @param object 实体对象并且实现了Serializable接口的类
     */
    public static void updateModelUpdateTime(Object object){
        if(object == null){
            return;
        }

        if(ReflectionUtils.getDeclaredField(object,LAST_MOD_TIME) != null){
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_TIME, new Date());
        }
    }

    /**
     * 得到实体对象的最后修改时间
     * @param object 实体对象并且实现了Serializable接口的类
     */
    public static Date getModelUpdateTime(Object object){
        if(object == null){
            return null;
        }
        
        try{
            if(ReflectionUtils.getDeclaredField(object,LAST_MOD_TIME) != null){
                Object dateObj = ReflectionUtils.invokeGetterMethod(object, LAST_MOD_TIME);
                if(dateObj instanceof Date){
                    return (Date)dateObj;
                }
            }
        }
        catch(Exception e){
            
        }
        return null;
    }

    /**
     * 修改实体对象里面的创建时间，操作员ID，操作员姓名
     * @param object 实体类并且实现了Serializable接口的类
     * @param user 用户实体类
     */
    public static void updateModelInfo(Serializable object, BspUserDetails user){
        if(object == null || user == null){
            return;
        }
        
        if(ReflectionUtils.getDeclaredField(object,LAST_MOD_OPER_ID) != null){
            if(StringUtils.isNotBlank(user.getUserId())){
                Long operId = NumberUtils.toLong(user.getUserId());
                ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_ID, operId);
            }
        }
        if(ReflectionUtils.getDeclaredField(object,LAST_MOD_OPER_NAME) != null){
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_NAME, user.getRealname());
        }
        
        // 如果实体类存在主键 id，先设定实体对象主键值为null
        if(ReflectionUtils.getDeclaredField(object,ID) != null){
            Object idValue = ReflectionUtils.getFieldValue(object, ID);
            if(idValue == null || "".equals(idValue)){
                // 一定要设置为空，要不然无法插入数据库
                ReflectionUtils.setFieldValue(object, ID, null);
        // 修改实体对象里面的创建时间，操作员ID，操作员姓名
                updateModelCreateInfo(object,user);
                updateModelUpdateTime(object);
            }
        }
        else if(ReflectionUtils.getDeclaredField(object,CODE) != null || 
                ReflectionUtils.getDeclaredField(object,APP_CODE) != null){
            Object createTime = ReflectionUtils.getFieldValue(object, CREATE_TIME);
            if(createTime == null){
        // 修改实体对象里面的创建时间，操作员ID，操作员姓名
                updateModelCreateInfo(object,user);
                updateModelUpdateTime(object);
            }
        }
        // 修改实体对象里面的创建时间，操作员ID，操作员姓名
    }

    /**
     * 修改实体对象里面的创建时间，操作员ID，操作员姓名
     * @param object 实体类并且实现了Serializable接口的类
     * @param user 用户实体类
     */
     protected static void updateModelCreateInfo(Serializable object, BspUserDetails user){
         if(ReflectionUtils.getDeclaredField(object,CREATE_TIME) != null){
             ReflectionUtils.invokeSetterMethod(object, CREATE_TIME, DateTimeUtils.getCurrentDate());
         }
         if(ReflectionUtils.getDeclaredField(object,CREATE_OPER_ID) != null){
             if(StringUtils.isNotBlank(user.getUserId())){
                 Long operId = NumberUtils.toLong(user.getUserId());
                 ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_ID, operId);
             }
         }
         if(ReflectionUtils.getDeclaredField(object,CREATE_OPER_NAME) != null){
             ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_NAME, user.getRealname());
         }
     }

     /**
      * 修改实体对象里面的创建时间，操作员ID，操作员姓名和最后修改时间
      * @param object 实体类并且实现了Serializable接口的类
      * @param transCode 操作员姓名
      */
     public static void createModelInfoBySys(Serializable object, String transCode){
         if(ReflectionUtils.getDeclaredField(object,CREATE_TIME) != null){
             ReflectionUtils.invokeSetterMethod(object, CREATE_TIME, DateTimeUtils.getCurrentDate());
         }
         if(ReflectionUtils.getDeclaredField(object,CREATE_OPER_ID) != null){
             ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_ID, Constants.GLOBAL_SYSTEM_OPER_ID);
         }
         if(ReflectionUtils.getDeclaredField(object,CREATE_OPER_NAME) != null){
             ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_NAME, transCode);
         }
         if(ReflectionUtils.getDeclaredField(object,LAST_MOD_TIME) != null){
             ReflectionUtils.invokeSetterMethod(object, LAST_MOD_TIME, DateTimeUtils.getCurrentDate());
         }
         updateModelInfoBySys(object, transCode);
     }

     /**
      * 修改实体对象里面的操作员ID、操作员姓名，表明这个订单现在是系统在操作
      * @param object 实体类并且实现了Serializable接口的类
      * @param transCode 操作员姓名
      */
     public static void updateModelInfoBySys(Serializable object, String transCode){
         if(ReflectionUtils.getDeclaredField(object,LAST_MOD_OPER_ID) != null){
             ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_ID, Constants.GLOBAL_SYSTEM_OPER_ID);
         }
         if(ReflectionUtils.getDeclaredField(object,LAST_MOD_OPER_NAME) != null){
             ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_NAME, transCode);
         }
     }

     /**
      * 修改实体对象里面的状态为Invalidate，表明这个订单现在无效
      * @param object 实体类并且实现了Serializable接口的类
      */
     public static void setModelInvalidStatus(Serializable object){
         if(ReflectionUtils.getDeclaredField(object,STATUS) != null){
             ReflectionUtils.invokeSetterMethod(object, STATUS, Constants.DICT_GLOBAL_STATUS_INVALIDATE);
         }
     }
}
