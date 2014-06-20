/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-11-24 下午1:44:35
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-11-24        Initailized
 */

package com.strawren.bsp.web.tag;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.util.HexUtils;


/**
 * 重复提交tag
 *
 */
public class TokenGenTag extends BaseHandlerTag{
    private static final long serialVersionUID = 294665222320461991L;
    
    protected Log log = LogFactory.getLog(getClass());
    
    //重复提交返回的错误页面或者错误处理action
    private String errBackUrl;

   
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext .getRequest();
        String str = generateToken(request);
        request.getSession().setAttribute(Constants.OECS_TOKEN_CHECK_NAME_IN_SESSION, str);
        
        StringBuilder sb = new StringBuilder("<input type=\"hidden\" name=\"" + Constants.OECS_TOKEN_CHECK_NAME+ "\" value=\"" + str + "\" >");
        if(StringUtils.isNotBlank(this.errBackUrl)){
            sb.append("<input type=\"hidden\" name=\"" + Constants.OECS_TOKEN_ERR_BACK_URL_NAME + "\" value=\""  + this.errBackUrl + "\" >");
        }
        
        log.debug("render text : " + sb.toString());
        write(sb.toString());
        
        return super.doStartTag();
    }

    protected String generateToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            byte[] arrayOfByte1 = session.getId().getBytes();
            byte[] arrayOfByte2 = new Long(System.currentTimeMillis()).toString().getBytes();
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(arrayOfByte1);
            localMessageDigest.update(arrayOfByte2);
            return HexUtils.toHex(localMessageDigest.digest());
        }
        catch (NoSuchAlgorithmException e) {
            log.warn(e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public void release() {
        super.release();
        this.errBackUrl = null;
    }
    
    public String getErrBackUrl() {
        return errBackUrl;
    }

    public void setErrBackUrl(String errBackUrl) {
        this.errBackUrl = errBackUrl;
    }

}
