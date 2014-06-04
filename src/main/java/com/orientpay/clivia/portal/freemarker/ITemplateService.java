package com.orientpay.clivia.portal.freemarker;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;

/**
 * 模版服务
 * @author Administrator
 * @since 2012-11-5
 * @version 1.0.0
 *
 */
public abstract interface ITemplateService {

	public void setTempPath(String tempPath);

	/**
	 * 获得模版
	 * @param name
	 * @return
	 */
	public Template getTemplate(String name);

	/**
	 * 获得模版（字符串格式）
	 * @param name
	 * @return
	 */
	public String getTemplateString(String name);

	/**
	 * 根据模版名称和对应的数据渲染模版
	 * @param name 模版名称
	 * @param data 数据
	 * @return 生成的字符串内容
	 */
	public String buildPage(String name, Map<?,?> data);

	/**
	 * 根据模版名称和对应的数据渲染模版,并生成文件
	 * @param name 模版名称
	 * @param data 数据
	 * @param fullPath 包括文件名的文件路径
	 * @return
	 */
	public Integer buildPage(String name, Map<?,?> data, String fullPath);

	/**
	 * 根据模版名称和对应的数据渲染模版,并生成文件
	 * @param name 模版名称
	 * @param data 数据
	 * @param filePath 文件路径
	 * @param fileName 文件名
	 * @return
	 */
	public Integer buildPage(String name, Map<?,?> data, String filePath, String fileName);

	/**
	 * 根据模版名称和对应的数据渲染模版,并生成文件
	 * @param name 模版名称
	 * @param data 数据
	 * @param file 文件
	 * @return
	 */
	public Integer buildPage(String name, Map<?,?> data, File file);

	/**
     * 根据模版名称和对应的数据渲染模版,并写到流中
     * @param name 模版名称
     * @param data 数据
     * @param file 文件
     * @return
     */
    public Integer buildPage(String name, Map<?,?> data, OutputStream outputStream);

	/**
	 * 根据body生成静态页面
	 * @param env
	 * @param body
	 * @param fullPath : 静态页面的路径 +静态文件名
	 * @return
	 */
	public Integer buildPage(Environment env, TemplateDirectiveBody body, String fullPath);


	public FreeMarkerConfigurer getTempPath();

	/**
	 * 初始化上下文
	 */
	public void initialize();

}
