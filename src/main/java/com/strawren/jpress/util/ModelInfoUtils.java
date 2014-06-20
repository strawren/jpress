package com.strawren.jpress.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.bsp.util.ReflectionUtils;
import com.strawren.jpress.model.CmsUser;


/**
 * model更新的辅助类，用于修改实体对象里面各个字段的信息
 *
 */
public abstract class ModelInfoUtils {


    /**
     * 创建时间
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 创建人id
     */
    public static final String CREATE_OPER_ID = "createOperId";

    /**
     * 创建人姓名
     */
    public static final String CREATE_OPER_NAME = "createOperName";

    /**最后修改时间
     */
    public static final String LAST_MOD_TIME = "lastUpdTime";

    /**
     * 最好修改人id
     */
    public static final String LAST_MOD_OPER_ID = "lastUpdOperId";

    /**
     * 最好修改人姓名
     */
    public static final String LAST_MOD_OPER_NAME = "lastUpdOperName";


    /**
     * 创建
     * @param object
     * @param user
     */
    public static void createModelInfoBySys(Serializable object)
    {
        //获取用户名
        String userName = getCurUserName();

        if(ReflectionUtils.getDeclaredField(object, CREATE_TIME) != null)
            ReflectionUtils.invokeSetterMethod(object, CREATE_TIME, DateTimeUtils.getCurrentDate());

        if(ReflectionUtils.getDeclaredField(object, CREATE_OPER_ID) != null)
            ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_ID, Long.valueOf(-1L));

        if(ReflectionUtils.getDeclaredField(object, CREATE_OPER_NAME) != null)
            ReflectionUtils.invokeSetterMethod(object, CREATE_OPER_NAME, userName);

        if(ReflectionUtils.getDeclaredField(object, LAST_MOD_TIME) != null)
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_TIME, DateTimeUtils.getCurrentDate());

        if(ReflectionUtils.getDeclaredField(object, LAST_MOD_OPER_ID) != null)
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_ID, Long.valueOf(-1L));

        if(ReflectionUtils.getDeclaredField(object, LAST_MOD_OPER_NAME) != null)
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_NAME, userName);

    }

    /**
     * 获取当前登录的用户名
     * @return
     */
    private static String getCurUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        String userName = currentUser.getPrincipal().toString();
        return userName;
    }


    /**
     * 获取当前登录的用户id
     * @return
     */
    public static Long getCurUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        CmsUser cmsUser = (CmsUser)session.getAttribute("user");
        if (cmsUser != null) {
            return cmsUser.getId();
        }
        return null;
    }

    /**
     * 修改
     * @param object
     * @param user
     */
    public static void updateModelInfoBySys(Serializable object)
    {
      //获取用户名
        String userName = getCurUserName();

        if(ReflectionUtils.getDeclaredField(object, LAST_MOD_OPER_ID) != null)
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_ID, Long.valueOf(-1L));

        if(ReflectionUtils.getDeclaredField(object, LAST_MOD_OPER_NAME) != null)
            ReflectionUtils.invokeSetterMethod(object, LAST_MOD_OPER_NAME, userName);

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

}
