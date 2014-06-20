/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-9-13 下午4:47:42
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-9-13        Initailized
 */

package com.strawren.bsp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.util.SpringContextUtils;
import com.strawren.bsp.util.SystemPropsUtils;


/**
 * 系统加载监听器
 *
 */
public class ContextLoaderListener implements ServletContextListener {
    protected Log log = LogFactory.getLog(getClass());
    
    public void contextInitialized(ServletContextEvent arg0) {
        log.debug("contextInitialized begin...");
        
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
        
        log.debug("init springContextUtils...");
        SpringContextUtils.init(ctx);
        initGlobalInfo(arg0.getServletContext());
        log.debug("contextInitialized end");
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        log.debug("contextDestroyed !");
    }
    
    /**
     * 初始化全局信息
     * @param servletContext
     */
    protected void initGlobalInfo(ServletContext servletContext) {
    	//全局的上下文路径
    	String rootPath = servletContext.getContextPath();
    	servletContext.setAttribute(Constants.JP_GLOBAL_SYS_INFO_ROOT_PATH_KEY, rootPath);
    	//后台管理路径
    	String adminPath = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_ADMIN_PATH_KEY);
    	servletContext.setAttribute(Constants.JP_GLOBAL_SYS_INFO_ADMIN_PATH_KEY, adminPath);
    	
    	//系统代号
    	String appCode = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_APP_CODE_KEY);
    	servletContext.setAttribute(Constants.JP_GLOBAL_SYS_INFO_APP_CODE_KEY, appCode);
    	//系统名称
    	String appName = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_APP_NAME_KEY);
    	servletContext.setAttribute(Constants.JP_GLOBAL_SYS_INFO_APP_NAME_KEY, appName);
    	//系统版本
    	String appVersion = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_APP_VERSION_KEY);
    	servletContext.setAttribute(Constants.JP_GLOBAL_SYS_INFO_APP_VERSION_KEY, appVersion);
    }
}
