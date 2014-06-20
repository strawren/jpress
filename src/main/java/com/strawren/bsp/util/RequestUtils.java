/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-8 上午11:46:29
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-8        Initailized
 */

package com.strawren.bsp.util;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.strawren.bsp.core.Constants;


/**
 * request 工具类
 * 
 */
public class RequestUtils {
    
    private static final Log log = LogFactory.getLog(RequestUtils.class);
    
    private static Properties headerMap;
    private static String defaultMobile;
    
    static {
        /*
         * InputStream in = RequestUtils.class.getResourceAsStream(
         * "/cn/com/eaglenetworks/abdf/view/util/mobile_match.properties");
         * headerMap = new Properties(); try{ headerMap.load(in); defaultMobile
         * = headerMap.getProperty("empty"); } catch(IOException e){
         * log.error("ERROR",e); }
         */
        headerMap = new Properties();
        headerMap.put("x-up-calling-line-id", "{0}");
        headerMap.put("x-up-subno", "86{0}_gateway");
        headerMap.put("X-Imsi", "{0}");
        headerMap.put("X-WAP-ClientID", "{0}");
        headerMap.put("X-Network-info", "{2},{0},{1}");
    }
    
    public static boolean isMultipart(HttpServletRequest req) {
        return ((req.getContentType() != null) && (req.getContentType()
                                                      .toLowerCase().startsWith("multipart")));
    }
    
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                return cookies[i];
            }
        }
        return null;
    }
    
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String name,
                                 String value,
                                 int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        String serverName = request.getServerName();
        String domain = getDomainOfServerName(serverName);
        if (domain != null && domain.indexOf('.') != -1) {
            cookie.setDomain('.' + domain);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    public static String getDomainOfServerName(String host) {
        if (isIPAddr(host)) {
            return null;
        }
        
        String[] names = StringUtils.split(host, '.');
        int len = names.length;
        if (len >= 2) {
            return names[len - 2] + '.' + names[len - 1];
        }
        return host;
    }
    
    public static String getHeader(HttpServletRequest req, String name) {
        String value = req.getHeader(name);
        if (value != null)
            return value;
        Enumeration<?> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String n = (String) names.nextElement();
            if (n.equalsIgnoreCase(name)) {
                return req.getHeader(n);
            }
        }
        return null;
    }
    
    public static String getUrlPrefix(HttpServletRequest req) {
        StringBuffer url = new StringBuffer(req.getScheme());
        url.append("://");
        url.append(req.getServerName());
        int port = req.getServerPort();
        if (port != 80) {
            url.append(":");
            url.append(port);
        }
        return url.toString();
    }
    
    public static String getRequestURL(HttpServletRequest req) {
        StringBuffer url = new StringBuffer(req.getRequestURI());
        String param = req.getQueryString();
        if (param != null) {
            url.append('?');
            url.append(param);
        }
        String path = url.toString();
        return path.substring(req.getContextPath().length());
    }
    
    public static void dumpHeaders(PrintStream out, HttpServletRequest req) {
        Enumeration<?> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            out.println(name + "=" + req.getHeader(name));
        }
    }
    
    /**
     * 用于支持WAP
     * 
     * @param req
     * @return
     */
    public static String getRequestMobile(HttpServletRequest req) {
        String mobile = defaultMobile;
        Iterator<?> keys = headerMap.keySet().iterator();
        while (keys.hasNext()) {
            String header = (String) keys.next();
            String value = getHeader(req, header);
            if (value != null) {
                String pattern = (String) headerMap.get(header);
                MessageFormat mf = new MessageFormat(pattern);
                try {
                    Object[] vs = mf.parse(value);
                    mobile = (String) vs[0];
                    if (mobile.startsWith("86")) {
                        mobile = mobile.substring(2);
                    }
                    break;
                } catch (Exception e) {
                    log.warn("error", e);
                    dumpHeaders(req, System.err);
                    continue;
                }
            }
        }
        return mobile;
    }
    
    public static void dumpHeaders(HttpServletRequest req, PrintStream out) {
        Enumeration<?> hds = req.getHeaderNames();
        while (hds.hasMoreElements()) {
            String name = (String) hds.nextElement();
            out.println(name + "=" + req.getHeader(name));
        }
    }
    
    public static boolean support(HttpServletRequest req, String contentType) {
        String accept = getHeader(req, "accept");
        if (accept != null) {
            accept = accept.toLowerCase();
            return accept.indexOf(contentType.toLowerCase()) != -1;
        }
        return false;
    }
    
    public static boolean isMozillaCompatible(HttpServletRequest req) {
        String user_agent = req.getHeader("user-agent");
        return user_agent == null || user_agent.indexOf("Mozilla") != -1;
    }
    
    public static int getParam(HttpServletRequest req,
                               String param,
                               int defaultValue) {
        try {
            String value = req.getParameter(param);
            if (value == null) {
                return defaultValue;
            }
            int idx = value.indexOf('#');
            if (idx != -1) {
                value = value.substring(0, idx);
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.error("error ", e);
        }
        return defaultValue;
    }
    
    public static String getParam(HttpServletRequest req,
                                  String param,
                                  String defaultValue) {
        String value = req.getParameter(param);
        return (StringUtils.isEmpty(value)) ? defaultValue : value;
    }
    
    public static boolean isIPAddr(String addr) {
        if (StringUtils.isEmpty(addr)) {
            return false;
        }
        String[] ips = StringUtils.split(addr, '.');
        if (ips.length != 4) {
            return false;
        }
        
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0
                    && ipa <= 255
                    && ipb >= 0
                    && ipb <= 255
                    && ipc >= 0
                    && ipc <= 255
                    && ipd >= 0
                    && ipd <= 255;
        } catch (Exception e) {
            
        }
        return false;
    }
    
    public static String convert2UTF8(String src){
        if(StringUtils.isBlank(src)){
            return src;
        }
        
        String ret = "";
        try{
            ret = new String(src.getBytes("ISO-8859-1"),Constants.DEFAULT_CHARSET_NAME);
        }
        catch(Exception e){
            log.debug("warn : cannot convert :" + src);
            ret = src;
        }
        return ret;
    }
}
