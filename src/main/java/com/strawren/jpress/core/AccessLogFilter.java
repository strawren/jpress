/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年5月21日 下午4:10:22
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年5月21日        Initailized
 */

package com.strawren.jpress.core;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.LogRequest;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.service.LogRequestService;


/**
 * 记录用户行为Filter
 *
 */
public class AccessLogFilter implements Filter{

    protected final Log logger = LogFactory.getLog(AccessLogFilter.class);

    /**
     * 商品编号key
     */
    protected final String SYS_KEY_ITEM_NO = SystemPropsUtils.getString("sys.key.itemno");

    private final String ACCESS_LOG_LEVEL = "AL";

    private LogRequestService logRequestService;
    private CmsTermService termService;
    private CmsPostService postService;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        logRequestService = context.getBean(LogRequestService.class);
        termService = context.getBean(CmsTermService.class);
        postService = context.getBean(CmsPostService.class);

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        try {
            //记录用户访问行为
            logAccess(req);
        }
        catch (Exception e) {
            logger.info("log user Access fail:"+e.getMessage());
        }

        chain.doFilter(request, response);
    }


    /**
     * 记录用户访问行为
     * @param request
     */
    private void logAccess(HttpServletRequest request) {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        logger.debug("request path:" + path);

        //分类名称
        String termName = null;
        //商品编号
        String itemNo = null;

        //分类ID
        String termIdParam = request.getParameter("termId");
        //商品ID
        String itemIdParam = request.getParameter("postId");

        //如果不是往购物车中添加商品,不记录日志
        if (path.endsWith(".do")&&StringUtils.isBlank(termIdParam)&&StringUtils.isBlank(itemIdParam)) {
            return;
        }

        Long termId = null;
        Long itemId = null;
        try {
        termId = Long.parseLong(termIdParam);
        itemId = Long.parseLong(itemIdParam);
        }
        catch (Exception e) {
            logger.info("parse termId or itemId fail!");
            logger.info("termId:"+termIdParam);
            logger.info("itemId:"+itemIdParam);
        }
        //获取分类名称(取别名作为统计的分类名称)
        CmsTerm term = termService.get(termId);
        if (term!=null) {
            termName = term.getSlug();
        }else {
            termName = "首页";
        }

        //获取商品编号
        CmsPostDTO item = postService.getDetailById(itemId);
        if (item!=null) {
            CmsPostMeta cmsPostMeta = item.getMetaMap().get(SYS_KEY_ITEM_NO);
            if (cmsPostMeta!=null) {
                itemNo = cmsPostMeta.getValue();
            }
        }

        final LogRequest logRequest = new LogRequest();
        //地址
        logRequest.setReqHost(request.getRemoteHost());
        //MAC
        logRequest.setReqAgent(request.getRemoteAddr());
        //访问时间
        Date curDate = new Date();
        logRequest.setReqDate(curDate);
        logRequest.setCreateTime(curDate);
        //商品编号
        logRequest.setMiscDesc(itemNo);
        //商品分类
        logRequest.setReqUrl(termName);
        //日志级别
        logRequest.setLogLevel(ACCESS_LOG_LEVEL);

        logRequestService.save(logRequest);

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        logRequestService = null;
        termService = null;
        postService = null;
    }

}
