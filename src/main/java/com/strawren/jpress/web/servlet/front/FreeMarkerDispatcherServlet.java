/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月21日 下午1:06:29
 * $URL$
 *
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * xieming 2014年4月21日 Initailized
 */

package com.strawren.jpress.web.servlet.front;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.strawren.bsp.template.ITemplateService;
import com.strawren.bsp.util.AssertUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsOption;
import com.strawren.jpress.service.CmsOptionService;

/**
 * 处理所有模板请求
 *
 */
public class FreeMarkerDispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = -7925038877703360128L;

    protected final Log logger = LogFactory.getLog(super.getClass());


    /**
     * 存放在context中后台option对应的key
     */
    protected final String CONTEXT_KEY_OPTIONS = "CONTEXT_KEY_OPTIONS";

    /**
     * 存放在context中后台option转换为Map后对应的key
     */
    protected final String CONTEXT_KEY_OPTIONS_MAP = "CONTEXT_KEY_OPTIONS_MAP";

    /**
     * 模板字符编码
     *
     */
    private String characterEncoding;

    /**
     * 输出contentType
     *
     */
    private String contentType;

    //依赖的bean
    private ITemplateService templateService;
    private CmsOptionService optionService;

    public void init(ServletConfig config) throws ServletException {
        initBean(config);

        initParam(config);

        initOption(config);
    }

    /**
     * 获取后台配置项，缓存到ServletContext中
     * @param config
     */
    private void initOption(ServletConfig config) {
        List<CmsOption> options = optionService.searchAll();
        config.getServletContext().setAttribute(CONTEXT_KEY_OPTIONS, options);
        config.getServletContext().setAttribute(CONTEXT_KEY_OPTIONS_MAP, toMap(options));
    }

    /**
     * @param options
     * @return
     */
    private Map<String, String> toMap(List<CmsOption> options) {
        Map<String, String> optionMap = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(options)) {
            for (CmsOption cmsOption : options) {
                optionMap.put(cmsOption.getCode(), cmsOption.getValue());
            }
        }
        return optionMap;
    }

    /**
     * 获取初始化参数
     * @param config
     */
    private void initParam(ServletConfig config) {
        characterEncoding = config.getInitParameter(characterEncoding);
        contentType = config.getInitParameter(contentType);
    }

    /**
     * 获取Bean
     * @param config
     */
    private void initBean(ServletConfig config) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        templateService = context.getBean(ITemplateService.class);
        optionService = context.getBean(CmsOptionService.class);

        AssertUtils.notNull(templateService, "templateService is null");
        AssertUtils.notNull(optionService, "optionService is null");
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        logger.debug("request path:" + path);

        // 如果请求以".ftl"结尾,返回403
        if (path.toLowerCase().endsWith(".ftl")) {
            response.setStatus(403);
            return;
        }

        if(!File.separator.equals("/")) {
        	path = path.replaceAll("\\/", "\\" + File.separator);
        }
        
        String theme = getThemeName(request);
        // 处理以.html结尾的请求
        String tplPath = File.separator + "themes" + File.separator + theme + path.replace(".html", ".ftl");
        
        //设置编码
        response.setCharacterEncoding (characterEncoding);
        response.setContentType(contentType);
        response.setDateHeader("Expires",0 );
        response.setHeader("Cache-Controll","no-cache");
        response.setHeader("pragma","no-cache");
        logger.debug("tplPath:" + tplPath + " ,theme:" + theme);
        renderFtl(tplPath, request, response);
        return;
    }

    /**
     * 获取当前选用的主题
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getThemeName(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
		Map<String, String> optionMap = (Map<String, String>) servletContext.getAttribute(CONTEXT_KEY_OPTIONS_MAP);
        String theme = optionMap.get(JpressConstants.OPTIONS_KEY_SITE_THEME_CODE);
        if (StringUtils.isBlank(theme)) {
            theme = optionService.getTemplateName();
            optionMap.put(JpressConstants.OPTIONS_KEY_SITE_THEME_CODE, theme);
        }
        return theme;
    }

    /**
     * 渲染freemarker模板
     *
     * @param path
     * @param request
     * @return
     * @throws IOException
     */
    private void renderFtl(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> params = buildParams(request);
        templateService.buildPage(path, params, response.getOutputStream());
    }

    /**
     * 获取request,session,application范围参数,设置到map中
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	private Map<String, Object> buildParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();

        //session范围参数
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            params.put("session_" + key, session.getAttribute(key));
        }

        //request范围参数
        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String key = requestAttributeNames.nextElement();
            params.put(key, request.getAttribute(key));
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            params.put(key, request.getParameter(key));
        }

        //application范围参数
        ServletContext application = session.getServletContext();
        Enumeration<String> applicationAttributeNames = application.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = applicationAttributeNames.nextElement();
            params.put("application_" + key, application.getAttribute(key));
        }

        return params;
    }

}
