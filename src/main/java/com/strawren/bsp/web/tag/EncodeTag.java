/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-10-13 下午2:53:39
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-10-13        Initailized
 */

package com.strawren.bsp.web.tag;

import java.net.URLEncoder;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.strawren.bsp.core.Constants;


/**
 * URL 编码工具Tag
 *
 */
public class EncodeTag extends BaseHandlerTag {
    private static final long serialVersionUID = 7151802162691310136L;
    
    private String value;
    private String charset;
    
    public int doStartTag() throws JspException {
        log.debug("start render...");
        
        if(StringUtils.isBlank(value)){
            log.info("value is null ,cannot render");
            return super.doStartTag();
        }
        
        try{
            if(StringUtils.isBlank(charset)){
                write(URLEncoder.encode(value, Constants.DEFAULT_CHARSET_NAME));
            }
            else{
                write(URLEncoder.encode(value, charset));
            }
        }
        catch(Exception e){
            log.info("INFO", e);
        }
        
        return super.doStartTag();
    }
    
    @Override
    public void release() {
        value = null;
        charset = null;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getCharset() {
        return value;
    }
    
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
    public static void main(String [] args) throws Exception{
        System.out.println(URLEncoder.encode("中文", "utf-8"));
    }
}
