/**
 * Copyright : http://www.orientpay.com , 2007-2012
 * Project : portal
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

package com.orientpay.clivia.portal.web.filter;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orientpay.clivia.component.constants.CliviaConstants;
import com.orientpay.clivia.component.dto.CmsPostDTO;
import com.orientpay.clivia.component.model.CmsPostMeta;
import com.orientpay.clivia.component.model.CmsTerm;
import com.orientpay.clivia.component.model.LogRequest;
import com.orientpay.clivia.component.service.CmsPostService;
import com.orientpay.clivia.component.service.CmsTermService;
import com.orientpay.clivia.component.service.LogRequestService;
import com.orientpay.clivia.portal.web.auths.HttpTvAuthsUtils;
import com.orientpay.clivia.portal.web.auths.TvAuthsResponse;
import com.orientpay.oecs.util.misc.SystemPropertyUtils;


/**
 * 记录用户行为Filter
 *
 */
public class AccessFilter implements Filter{

    protected final Log logger = LogFactory.getLog(AccessFilter.class);

    /**
     * 商品编号key
     */
    protected final String SYS_KEY_ITEM_NO = SystemPropertyUtils.getString("sys.key.itemno");

    /**
     * json类型转换
     *
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

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
            //获取MAC和用户信息,并保存到session中
            setMacAndUserInfo(req);

            //记录用户访问行为
            logAccess(req);
        }
        catch (Exception e) {
            logger.info("log user Access fail:"+e.getMessage());
        }

        chain.doFilter(request, response);
    }

    /**
     * 获取MAC和用户信息,并保存到session中
     * @param request
     */
    private void setMacAndUserInfo(HttpServletRequest request) {
        //获取mac
        HttpSession session = request.getSession();
        session.removeAttribute(CliviaConstants.MAC_IN_SESSION_ATTR);
        Object macObj = session.getAttribute(CliviaConstants.MAC_IN_SESSION_ATTR);
        if(macObj == null){
            String mac = request.getParameter("mac");
            mac = "00196821530A";  //测试环境测试mac
            if(StringUtils.isNotBlank(mac)){
                logger.debug("current mac is: " + mac);
                session.setAttribute(CliviaConstants.MAC_IN_SESSION_ATTR, mac);

                //发送认证
                TvAuthsResponse authsResponse = HttpTvAuthsUtils.tvAuths(SystemPropertyUtils.getString(CliviaConstants.TV_AUTHS_URL), SystemPropertyUtils.getString(CliviaConstants.SYSTEM_APP_CODE), SystemPropertyUtils.getString(CliviaConstants.TV_AUTHS_MAC_KEY), mac, "0");
                if(authsResponse != null && "0000".equals(authsResponse.getReturnCode())){
                    logger.debug("userName: [" + authsResponse.getUserName() + "]");
                    session.setAttribute(CliviaConstants.TV_AUTHS_USER_INFO, authsResponse);
                }
            }
        }
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

        //MAC地址
        String mac = null;
        //用户信息
        TvAuthsResponse user = null;
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


        HttpSession session = request.getSession();
        //获取MAC
        Object macObject = session.getAttribute(CliviaConstants.MAC_IN_SESSION_ATTR);
        if (macObject!=null) {
            mac = (String) macObject;
        }
        //获取用户信息
        Object userObject = session.getAttribute(CliviaConstants.TV_AUTHS_USER_INFO);
        if (userObject!=null) {
            user = (TvAuthsResponse) userObject;
        }

        final LogRequest logRequest = new LogRequest();
        if (user!=null) {
            //用户名
            logRequest.setReqUserName(user.getUserName());
            //地址
            logRequest.setReqHost(user.getInsAddress());
        }

        //MAC
        logRequest.setReqAgent(mac);
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

        if (user!=null) {
            try {
                    //将用户信息转为json字符串保存到ReqParam中
                    String userJson = objectMapper.writeValueAsString(user);
                    logRequest.setReqParam(userJson);
            }
            catch (IOException e) {
                 logger.error("convert userInfo to json fail");
                 logger.error(e.getMessage());
            }
        }

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
