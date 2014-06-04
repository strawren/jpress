/**
 * Copyright : http://www.orientpay.com , 2007-2012
 * Project : portal
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

package com.orientpay.clivia.portal.web.servlet;

import com.orientpay.clivia.component.constants.CliviaConstants;
import com.orientpay.clivia.component.model.CmsOption;
import com.orientpay.clivia.component.service.CmsOptionService;
import com.orientpay.clivia.portal.freemarker.ITemplateService;
import com.orientpay.oecs.util.lang.AssertUtils;
import com.orientpay.oecs.util.misc.SystemPropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理所有模板请求
 *
 */
public class FreeMarkerDispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = -7925038877703360128L;

    protected final Log logger = LogFactory.getLog(super.getClass());

    /**
     * 模板路径
     */
    protected final String CMS_TEMPLATE_DIR = SystemPropertyUtils.getString("clivia.portal.tpl.dir");

    /**
     * 模板Web路径
     */
    protected final String CMS_TEMPLATE_URL = SystemPropertyUtils.getString("clivia.portal.tplPath");

    /**
     * admin 项目对应的web访问路径
     */
    protected final String CMS_ADMIN_SITE_URL = SystemPropertyUtils.getString("clivia.admin.site.url");

    /**
     * 商品编号key
     */
    protected final String SYS_KEY_ITEM_NO = SystemPropertyUtils.getString("sys.key.itemno");

    /**
     * 媒体文件访问路径
     *
     */
    protected final String ADMIN_MEDIA_DIR = SystemPropertyUtils.getString("sys.media.url");

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

        String template = getTemplateName(request);

        // 处理以.html结尾的请求
        String tplPath = File.separator+"themes" + File.separator + template
                + path.replace(".html", ".ftl");

        request.setAttribute("themeUrl", CMS_TEMPLATE_URL+template);
        request.setAttribute("mediaUrl", ADMIN_MEDIA_DIR);
        
        //获取bams视频访问的url及session中的mac
        String bamsVodUrl = SystemPropertyUtils.getString(CliviaConstants.VOD_REQ_BAMS_URL);
        HttpSession session = request.getSession();
        Object macObject = session.getAttribute(CliviaConstants.MAC_IN_SESSION_ATTR);
        request.setAttribute("bamsVodUrl", bamsVodUrl);
        request.setAttribute("mac", macObject != null ? macObject.toString() : "");
        logger.debug("bamsVodUrl: " + bamsVodUrl + ", mac: " + macObject + ", zone_code: " + request.getParameter("zone_code"));

        //设置编码
        response.setCharacterEncoding (characterEncoding);
        response.setContentType(contentType);
        response.setDateHeader("Expires",0 );
        response.setHeader("Cache-Controll","no-cache");
        response.setHeader("pragma","no-cache");
        logger.debug("tplPath:" + tplPath + " ,themeUrl:" + CMS_TEMPLATE_URL+template);
        renderFtl(tplPath, request, response);
        return;
    }

    /**
     * 获取当前选用的主题
     * @return
     */
    private String getTemplateName(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        Map<String, String> optionMap = (Map<String, String>) servletContext.getAttribute(CONTEXT_KEY_OPTIONS_MAP);
        String theme = optionMap.get(CliviaConstants.OPTIONS_KEY_SITE_THEME_CODE);
        if (StringUtils.isBlank(theme)) {
            theme = optionService.getTemplateName();
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
    private Map<String, Object> buildParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();

        //session范围参数
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            params.put("session_"+key, session.getAttribute(key));
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
            params.put("application_"+key, application.getAttribute(key));
        }

        return params;
    }

}
