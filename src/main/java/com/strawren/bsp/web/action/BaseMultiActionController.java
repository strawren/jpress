/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-4 下午02:22:03
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-4        Initailized
 */

package com.strawren.bsp.web.action;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.bsp.util.SystemPropsUtils;

/**
 * 
 * Action的基类
 *
 * 在spring mvc的框架里，最终负责处理请求的是MultiActionController中的invokeNamedMethod，它负责
 * 封装参数转换成对象以及调用Action的具体方法
 * 
 */
public class BaseMultiActionController extends MultiActionController {
    protected final  Log log = LogFactory.getLog(getClass());
    protected static final String MODEL_ID =    "id";
    protected static final String MODEL_IDS =   "ids";
    protected static final String ERRORS_NAME = "errors";
    protected static final String APP_ID =      "appId";
    
    protected static final String RESPONSE_STATUS_CODE =        "statusCode";
    protected static final String RESPONSE_MESSAGE =            "message";
    protected static final String STATUS_CODE_OK =              "200";
    protected static final String STATUS_CODE_ERR =             "300";
    
    protected String adminPath = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_ADMIN_PATH_KEY);
    
    protected String getModelId(HttpServletRequest request) {
        return request.getParameter(MODEL_ID);
    }

    protected String getMutilModelId(HttpServletRequest request) {
        return request.getParameter(MODEL_IDS);
    }
    
    protected String getAppId(HttpServletRequest request) {
        return request.getParameter(APP_ID);
    }
    
    protected String getText(String msgKey) {
        Object[] args = null;
        return getText(msgKey, args);
    }

    protected String getText(String msgKey, String arg) {
        return getText(msgKey, new Object[] { arg });
    }

    protected String getText(String msgKey, Object[] args) {
        Locale locale = null;
        return getMessageSourceAccessor().getMessage(msgKey, args, locale);
    }

   /* @Override
    protected void bind(HttpServletRequest request, Object command) throws Exception {
        logger.debug("Binding request parameters onto MultiActionController command");
        setValidator();
        ServletRequestDataBinder binder = createBinder(request, command);
        BindException errors = new BindException(binder.getBindingResult());
        binder.bind(request);
        if (this.getValidators() != null) {
            for (int i = 0; i < this.getValidators().length; i++) {
                if (this.getValidators()[i].supports(command.getClass())) {
                    ValidationUtils.invokeValidator(this.getValidators()[i],command, errors);
                }
            }
        }
        request.setAttribute(ERRORS_NAME, errors);
    }

    @Override
    protected ServletRequestDataBinder createBinder(HttpServletRequest request,Object command) throws Exception {
        String commandName = command.getClass().getSimpleName();
        commandName = commandName.substring(0,1).toLowerCase()+commandName.substring(1);
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command,commandName);
        initBinder(request, binder);
        return binder;   
    }*/

    /*@Override
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        log.debug("initBinder(HttpServletRequest request, ServletRequestDataBinder binder) begin ...");
        binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateShortFormat = new SimpleDateFormat(AbdfConstants.DEFUALT_SHOT_TIME_FORMAT);
        dateShortFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateShortFormat, true));
        SimpleDateFormat dateLongFormat = new SimpleDateFormat(AbdfConstants.DEFUALT_LONG_TIME_FORMAT);
        dateLongFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateLongFormat, true));
    }*/
    
    @InitBinder 
    //http://sillycat.javaeye.com/blog/563979
    public void initBinder(WebDataBinder binder) { 
       //日期转化的，包括长时间和短时间格式的
       binder.registerCustomEditor(Date.class, new PropertyEditorSupport(true){
           private final boolean allowEmpty = true;
           
           @Override
           public void setAsText(String text) throws IllegalArgumentException {
               if (this.allowEmpty && StringUtils.isBlank(text)) {
                   setValue(null);
                   return;
               }
               else if (StringUtils.isBlank(text)) {
                   throw new IllegalArgumentException("Could not parse date: it is null");
               }
               
               //不带横线的日期
               if(text.indexOf(Constants.LINE_SIGN_SPLIT_NAME) == -1 && text.length() == 8){
                   setValue(DateTimeUtils.stringFormatToDate(text, Constants.NO_SPLIT_SHOT_TIME_FORMAT));
                   return;
               }
               //常日期
               if(text.length() > 10){
                   setValue(DateTimeUtils.stringFormatToDate(text, Constants.DEFUALT_LONG_TIME_FORMAT));
                   return;
               }
               //正常日期
               else{
                   setValue(DateTimeUtils.stringFormatToDate(text, Constants.DEFUALT_SHOT_TIME_FORMAT));
               }
           }

           /**
            * Format the Date as String, using the specified DateFormat.
            */
           @Override
           public String getAsText() {
               Date value = (Date) getValue();
               String ret =DateTimeUtils.dateToStringFormat(value, Constants.DEFUALT_LONG_TIME_FORMAT);
               if(ret != null && ret.endsWith("00:00:00")){
                   ret =DateTimeUtils.dateToStringFormat(value, Constants.DEFUALT_SHOT_TIME_FORMAT);

               }
               return ret;
           }
       }); 
       
       binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true)); 
       binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, null, true)); 
       binder.registerCustomEditor(int.class, null, new CustomNumberEditor(Integer.class, null , true)); 
       binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true)); 
       binder.registerCustomEditor(long.class, null, new CustomNumberEditor(Long.class, null, true)); 
       binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true)); 
       binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true)); 
       binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
    } 
    
    public BindException handleErrors(HttpServletRequest request,HttpServletResponse response) throws Exception {
        BindException errors = (BindException) request.getAttribute(ERRORS_NAME);
        if (errors.hasErrors()) {
            log.debug("handleErrors hasErrors!!!");
            return errors;
        } else {
            return null;
        }
    }
    
    //包装异常信息
    protected void wrapModelAndView(ModelAndView view,Exception e){
        if(e != null){
            log.warn("WARN", e);
            view.addObject(RESPONSE_STATUS_CODE, STATUS_CODE_ERR);
            view.addObject(RESPONSE_MESSAGE, e.getMessage());
        }
    }
    
    //得到术语的转义输出
    public String getTermValue(String code, String defaultValue, String param, String split){
        log.debug("begin get term value...");
        
        if(StringUtils.isBlank(defaultValue)){
            defaultValue = code;
        }
        
        log.debug("code :" + code + ", value : " + defaultValue);
        return defaultValue;
    }
    
    public String getTermValue(String code, String defaultValue, String param){
        return getTermValue(code, defaultValue, param, null);
    }
    
    public String getTermValue(String code, String defaultValue){
        return getTermValue(code, defaultValue, null, null);
    }
    
    public String getTermValue(String code){
        return getTermValue(code, code, null, null);
    }
}
