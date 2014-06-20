/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-23 上午11:48:21
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-23        Initailized
 */

package com.strawren.bsp.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

/**
 * 根据分页输出排序标签
 * 
 */
public class OrderRenderTag extends BaseHandlerTag {
    
    private static final long serialVersionUID = 1190150273786407740L;
    // 目标排序字段
    protected String realField = null;
    // 排序字段
    protected String curField = null;
    // 排序方式
    protected String direction = null;
    
    public int doStartTag() throws JspException {
        log.debug("start render...");
        
        if(StringUtils.isNotBlank(curField)){
            StringBuilder sb = new StringBuilder(" orderField=\"" + curField + "\" ");
            if (curField.equals(realField)) {
                sb.append("class=\"" + direction + "\"");
            }
            write(sb.toString());
        }
       
        return super.doStartTag();
    }
    
    @Override
    public void release() {
        realField = null;
        curField = null;
        direction = null;
    }
    
    public String getRealField() {
        return realField;
    }
    
    public void setRealField(String realField) {
        this.realField = realField;
    }
    
    public String getCurField() {
        return curField;
    }
    
    public void setCurField(String curField) {
        this.curField = curField;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
}
