package com.orientpay.clivia.portal.freemarker;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Freemarker异常处理
 * @author SUNNY 
 * @since 2012-11-05
 * @version 1.0.0
 *
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler{
	private static final Logger log = Logger.getLogger(FreemarkerExceptionHandler.class);

	public void handleTemplateException(TemplateException exc,
			Environment env, Writer out) throws TemplateException {
		try {
			out.write("[Freemarker Error: "+exc.getMessage()+"]");
			log.warn("[Freemarker Error: "+exc.getMessage()+"]");
		} catch (IOException e) {
			log.warn(e.getMessage());
			throw new TemplateException("Failed to print error message. Cause: "+e,env);
		}
	}

}
