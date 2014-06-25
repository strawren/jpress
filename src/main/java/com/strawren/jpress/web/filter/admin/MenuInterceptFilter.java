package com.strawren.jpress.web.filter.admin;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuInterceptFilter  implements Filter {
	private String menuParamName = "menu";
	private String menuParamKey = "menu";
	
	private final Log log = LogFactory.getLog(getClass());
	
	public void init(FilterConfig arg0) throws ServletException {
		String name = arg0.getInitParameter("name");
		String key = arg0.getInitParameter("key");
		log.debug("config name value ->" + name + ", and key ->" + key);
		
		if(StringUtils.isNotBlank(name)) {
			menuParamName = name;
		}
		if(StringUtils.isNotBlank(key)) {
			menuParamKey = key;
		}
	}

	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			Enumeration enums = request.getParameterNames();
			while(enums.hasMoreElements()) {
				String paramName = String.valueOf(enums.nextElement());
				if(menuParamName.equals(paramName)) {
					String paramVal = request.getParameter(paramName);
					log.info("find menu param in request, val ->" + paramVal);
					request.getSession().setAttribute(menuParamKey, paramVal);
				}
			}
		}
		finally {
			chain.doFilter(req, resp);
		}
	}

	public void destroy() {
		
	}

}
