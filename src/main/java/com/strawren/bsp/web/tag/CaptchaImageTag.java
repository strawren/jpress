/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-22 下午03:51:12
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-22        Initailized
 */

package com.strawren.bsp.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 验证码生成器
 *
 */
public class CaptchaImageTag extends BaseHandlerTag{
    private static final long serialVersionUID = 4185439127892313279L;
    
    protected  Log log = LogFactory.getLog(getClass());
    
    private boolean haveInput = true;
    
    private String inputWidth;
    
    private String elementName = "span";
    
    private String inputId = "j_check_code";
    
    private String inputName;
    
    private String inputClass = "input";
    
    private String inputSize = "8";
    
    private String inputMaxLength = "4";
    
    private String imgId = "randImage";
    
    private String imgWidth = "90";
    
    private String imgHeight = "30";
    
    private String imgSrc= "/scaptcha";
    
    private String reloadMethod = "javascript:reloadRandImage(rootPath);";
    
    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder("<" + elementName);
    
        sb.append(prepareStyles());
        sb.append(prepareEventHandlers());
        sb.append(">");
        
        // 验证码输入框
        if (haveInput) {
            //sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td");
            sb.append(StringUtils.isBlank(inputWidth) ?"" : " width=\"" + inputWidth + "\"" + ">");
            sb.append("<input id=\"" + inputId + "\" name=\"");
            sb.append((StringUtils.isBlank(inputName)?inputId:inputName) + "\" type=\"text\" size=\"" + inputSize);
            sb.append("\" class=\"" + inputClass + "\" maxlength=\"" + inputMaxLength + "\">");
            //sb.append("</td></tr></table>");
        }
        
        //验证码图片
        String path = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
        if (!imgSrc.startsWith(path)) {
            imgSrc = path + imgSrc;
        }
        sb.append("<a href=\"" + reloadMethod + "\" " + (getTitle() == null ? " " : "title=\"" + getTitle() + "\""));
        sb.append("><img id=\"" + imgId + "\" src=\"" + imgSrc + "\" ");
        sb.append("width=\"" + imgWidth + "\" height=\"" + imgHeight + "\" border=\"0\"></a>");
        
        
        
        
        sb.append("</" + elementName + ">");
        write(sb.toString());
        return super.doStartTag();
    }

    @Override
    public void release(){
        haveInput = true;
        inputWidth = null;
        elementName = "span";
        inputId = "j_check_code";
        inputName = null;
        inputClass = "input";
        inputSize = "8";
        inputMaxLength = "4";
        imgId = "randImage";
        imgWidth = "90";
        imgHeight = "30";
        imgSrc= "/scaptcha";
        reloadMethod = "javascript:reloadRandImage(rootPath);";
        
        super.release();
    }
    
    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputClass() {
        return inputClass;
    }

    public void setInputClass(String inputClass) {
        this.inputClass = inputClass;
    }

    public String getInputSize() {
        return inputSize;
    }

    public void setInputSize(String inputSize) {
        this.inputSize = inputSize;
    }

    public String getInputMaxLength() {
        return inputMaxLength;
    }

    public void setInputMaxLength(String inputMaxLength) {
        this.inputMaxLength = inputMaxLength;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(String imgWidth) {
        this.imgWidth = imgWidth;
    }

    public String getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(String imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getReloadMethod() {
        return reloadMethod;
    }

    public void setReloadMethod(String reloadMethod) {
        this.reloadMethod = reloadMethod;
    }

    public boolean isHaveInput() {
        return haveInput;
    }

    public void setHaveInput(boolean haveInput) {
        this.haveInput = haveInput;
    }

    public String getInputWidth() {
        return inputWidth;
    }

    public void setInputWidth(String inputWidth) {
        this.inputWidth = inputWidth;
    }

    /**
     * Property accessor of elementName
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * @param elementName the elementName to set
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }    
}
