package com.strawren.bsp.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.strawren.bsp.util.FileUtils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

@Service
public class TemplateServiceImpl implements ITemplateService{
	private static final Logger log = Logger.getLogger(TemplateServiceImpl.class);
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	private static boolean init = false;

	private static String tempPath;

	public Template getTemplate(String name) {
		if(!init){
			initialize();
		}
		Template tem = null;
		try {
			tem = freemarkerConfig.getConfiguration().getTemplate(name);
		} catch (IOException e) {
			log.error("[getTemplate error, name: "+name+". "+e.getMessage()+"]");
		}
		return tem;
	}

	public String getTemplateString(String name) {
		return getTemplate(name).toString();
	}

	public String buildPage(String name, Map<?, ?> data) {
		StringWriter sw = new StringWriter();
		Template tem = getTemplate(name);
		try {
			tem.process(data, sw);
		} catch (Exception e) {
			log.error("[buildPage error: "+e.getMessage()+"]",e);
		}
		return sw.getBuffer().toString();
	}

	public Integer buildPage(String name, Map<?, ?> data, String fullPath) {
		Writer writer;
		try {
		    FileUtils.createPath(fullPath);
			File file = new File(fullPath);
			if(!file.exists()){
				file.createNewFile();
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),Charset.forName("UTF-8")));
			String str = buildPage(name, data);
			writer.write(str);
			writer.flush();
			writer.close();
			log.debug("buildPage success...");
		} catch (Exception e) {
			log.error("[buildPage error: "+e.getMessage()+"]",e);
		}
		return 0;
	}

	public Integer buildPage(String name, Map<?, ?> data, String filePath,
			String fileName) {

		return null;
	}

	public Integer buildPage(Environment env, TemplateDirectiveBody body, String fullPath) {
			Writer writer;
			try {
				FileUtils.createPath(fullPath);
				File file = new File(fullPath);
				if(!file.exists()){
					file.createNewFile();
				}
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),Charset.forName("UTF-8")));
				try {
					env.setOut(writer);
					body.render(env.getOut());
				}
				catch (Exception e) {
					log.error("[buildPage error: "+e.getMessage()+"]",e);
				}
				writer.flush();
				log.debug("buildPage success...");
			}
			catch (Exception e) {
				log.error("[buildPage error: "+e.getMessage()+"]",e);
			}
			return 0;
		}

	public Integer buildPage(String name, Map<?, ?> data, File file) {
		Writer writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			Template tem = getTemplate(name);
			tem.process(data, writer);
		} catch (Exception e) {
			log.error("[buildPage error: "+e.getMessage()+"]",e);
		}
		return 0;
	}

	//	@Transactional(readOnly = true)
    public void index(String filePath, String tpl, Map<String, Object> data)
            throws IOException, TemplateException {
        File f = new File(filePath);
        File parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Writer out = null;
        try {
            // FileWriter不能指定编码确实是个问题，只能用这个代替了。
            out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            Template template = freemarkerConfig.getConfiguration().getTemplate(tpl);
            template.process(data, out);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

	public FreeMarkerConfigurer getTempPath(){
		return freemarkerConfig;
	}

	public void setTempPath(String tempPath) {
		TemplateServiceImpl.tempPath = tempPath;
	}

	public void initialize() {
		freemarkerConfig.setTemplateLoaderPath(tempPath);
	}

    /* (non-Javadoc)
     * @see com.orientpay.clivia.portal.service.ITemplateService#buildPage(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    public Integer buildPage(String name, Map<?, ?> data, OutputStream outputStream) {
        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            Template tem = getTemplate(name);
            tem.process(data, writer);
        } catch (Exception e) {
            log.error("[buildPage error: "+e.getMessage()+"]",e);
        } finally {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                log.info("[close stream error: "+e.getMessage()+"]",e);
            }
        }
        return 0;
    }

}
