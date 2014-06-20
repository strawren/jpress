/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-18 上午09:42:37
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-18        Initailized
 */

package com.strawren.bsp.web.tag;

import java.lang.annotation.Annotation;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 所有TAG的基类
 * 
 */
public abstract class BaseHandlerTag extends BodyTagSupport {
    protected Log log = LogFactory.getLog(getClass());
    
    private static final long serialVersionUID = -6449382767144312974L;
    /**
     * The default Locale for our server.
     */
    protected static final Locale defaultLocale = Locale.getDefault();
    /** Access key character. */
    protected String accesskey = null;
    
    /** Tab index value. */
    protected String tabindex = null;
    
    /**
     * Whether to created indexed names for fields
     */
    protected boolean indexed = false;
    
    /** Mouse click event. */
    private String onclick = null;
    
    /** Mouse double click event. */
    private String ondblclick = null;
    
    /** Mouse over component event. */
    private String onmouseover = null;
    
    /** Mouse exit component event. */
    private String onmouseout = null;
    
    /** Mouse moved over component event. */
    private String onmousemove = null;
    
    /** Mouse pressed on component event. */
    private String onmousedown = null;
    
    /** Mouse released on component event. */
    private String onmouseup = null;
    
    /** Key down in component event. */
    private String onkeydown = null;
    
    /** Key released in component event. */
    private String onkeyup = null;
    
    /** Key down and up together in component event. */
    private String onkeypress = null;
    
    /** Text selected in component event. */
    private String onselect = null;
    
    /** Content changed after component lost focus event. */
    private String onchange = null;
    
    /** Component lost focus event. */
    private String onblur = null;
    
    /** Component has received focus event. */
    private String onfocus = null;
    
    /** Component is disabled. */
    private boolean disabled = false;
    
    /** Component is readonly. */
    private boolean readonly = false;
    
    /** Style attribute associated with component. */
    private String style = null;
    
    /** Named Style class associated with component. */
    private String styleClass = null;
    
    /** Identifier associated with component. */
    private String styleId = null;
    
    /** The alternate text of this element. */
    private String alt = null;
    
    /** The name of the message resources bundle for message lookups. */
    private String bundle = null;
    
    /** The name of the session attribute key for our locale. */
    private String locale = null;
    
    /** The advisory title of this element. */
    private String title = null;
    
    //验证的对象
    private Object chkObject;
    //验证的class名称
    private String chkClazz;
    //验证的字段
    private String chkField; 
    
    /** Sets the accessKey character. */
    public void setAccesskey(String accessKey) {
        this.accesskey = accessKey;
    }
    
    /** Returns the accessKey character. */
    public String getAccesskey() {
        return (this.accesskey);
    }
    
    /** Sets the tabIndex value. */
    public void setTabindex(String tabIndex) {
        this.tabindex = tabIndex;
    }
    
    /** Returns the tabIndex value. */
    public String getTabindex() {
        return (this.tabindex);
    }
    
    // Indexing ability for Iterate
    
    /**
     * Sets the indexed value.
     */
    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }
    
    /**
     * Returns the indexed value.
     */
    public boolean getIndexed() {
        return (this.indexed);
    }
    
    // Mouse Events
    
    /** Sets the onClick event handler. */
    public void setOnclick(String onClick) {
        this.onclick = onClick;
    }
    
    /** Returns the onClick event handler. */
    public String getOnclick() {
        return onclick;
    }
    
    /** Sets the onDblClick event handler. */
    public void setOndblclick(String onDblClick) {
        this.ondblclick = onDblClick;
    }
    
    /** Returns the onDblClick event handler. */
    public String getOndblclick() {
        return ondblclick;
    }
    
    /** Sets the onMouseDown event handler. */
    public void setOnmousedown(String onMouseDown) {
        this.onmousedown = onMouseDown;
    }
    
    /** Returns the onMouseDown event handler. */
    public String getOnmousedown() {
        return onmousedown;
    }
    
    /** Sets the onMouseUp event handler. */
    public void setOnmouseup(String onMouseUp) {
        this.onmouseup = onMouseUp;
    }
    
    /** Returns the onMouseUp event handler. */
    public String getOnmouseup() {
        return onmouseup;
    }
    
    /** Sets the onMouseMove event handler. */
    public void setOnmousemove(String onMouseMove) {
        this.onmousemove = onMouseMove;
    }
    
    /** Returns the onMouseMove event handler. */
    public String getOnmousemove() {
        return onmousemove;
    }
    
    /** Sets the onMouseOver event handler. */
    public void setOnmouseover(String onMouseOver) {
        this.onmouseover = onMouseOver;
    }
    
    /** Returns the onMouseOver event handler. */
    public String getOnmouseover() {
        return onmouseover;
    }
    
    /** Sets the onMouseOut event handler. */
    public void setOnmouseout(String onMouseOut) {
        this.onmouseout = onMouseOut;
    }
    
    /** Returns the onMouseOut event handler. */
    public String getOnmouseout() {
        return onmouseout;
    }
    
    // Keyboard Events
    
    /** Sets the onKeyDown event handler. */
    public void setOnkeydown(String onKeyDown) {
        this.onkeydown = onKeyDown;
    }
    
    /** Returns the onKeyDown event handler. */
    public String getOnkeydown() {
        return onkeydown;
    }
    
    /** Sets the onKeyUp event handler. */
    public void setOnkeyup(String onKeyUp) {
        this.onkeyup = onKeyUp;
    }
    
    /** Returns the onKeyUp event handler. */
    public String getOnkeyup() {
        return onkeyup;
    }
    
    /** Sets the onKeyPress event handler. */
    public void setOnkeypress(String onKeyPress) {
        this.onkeypress = onKeyPress;
    }
    
    /** Returns the onKeyPress event handler. */
    public String getOnkeypress() {
        return onkeypress;
    }
    
    // Text Events
    
    /** Sets the onChange event handler. */
    public void setOnchange(String onChange) {
        this.onchange = onChange;
    }
    
    /** Returns the onChange event handler. */
    public String getOnchange() {
        return onchange;
    }
    
    /** Sets the onSelect event handler. */
    public void setOnselect(String onSelect) {
        this.onselect = onSelect;
    }
    
    /** Returns the onSelect event handler. */
    public String getOnselect() {
        return onselect;
    }
    
    // Focus Events and States
    
    /** Sets the onBlur event handler. */
    public void setOnblur(String onBlur) {
        this.onblur = onBlur;
    }
    
    /** Returns the onBlur event handler. */
    public String getOnblur() {
        return onblur;
    }
    
    /** Sets the onFocus event handler. */
    public void setOnfocus(String onFocus) {
        this.onfocus = onFocus;
    }
    
    /** Returns the onFocus event handler. */
    public String getOnfocus() {
        return onfocus;
    }
    
    /** Sets the disabled event handler. */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    /** Returns the disabled event handler. */
    public boolean getDisabled() {
        return disabled;
    }
    
    /** Sets the readonly event handler. */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    
    /** Returns the readonly event handler. */
    public boolean getReadonly() {
        return readonly;
    }
    
    // CSS Style Support
    
    /** Sets the style attribute. */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /** Returns the style attribute. */
    public String getStyle() {
        return style;
    }
    
    /** Sets the style class attribute. */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /** Returns the style class attribute. */
    public String getStyleClass() {
        return renderStyleClass();
    }
    
    /** Sets the style id attribute. */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }
    
    /** Returns the style id attribute. */
    public String getStyleId() {
        return styleId;
    }
    
    // Other Common Elements
    /** Returns the alternate text attribute. */
    public String getAlt() {
        return alt;
    }
    
    /** Sets the alternate text attribute. */
    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    /** Returns the name of the message resources bundle to use. */
    public String getBundle() {
        return bundle;
    }
    
    /** Sets the name of the message resources bundle to use. */
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
    
    /** Returns the name of the session attribute for our locale. */
    public String getLocale() {
        return locale;
    }
    
    /** Sets the name of the session attribute for our locale. */
    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    /** Returns the advisory title attribute. */
    public String getTitle() {
        return title;
    }
    
    /** Sets the advisory title attribute. */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Property accessor of chkObject
     * @return the chkObject
     */
    public Object getChkObject() {
        return chkObject;
    }

    /**
     * @param chkObject the chkObject to set
     */
    public void setChkObject(Object chkObject) {
        this.chkObject = chkObject;
    }

    /**
     * Property accessor of chkClazz
     * @return the chkClazz
     */
    public String getChkClazz() {
        return chkClazz;
    }

    /**
     * @param chkClazz the chkClazz to set
     */
    public void setChkClazz(String chkClazz) {
        this.chkClazz = chkClazz;
    }

    /**
     * Property accessor of chkField
     * @return the chkField
     */
    public String getChkField() {
        return chkField;
    }

    /**
     * @param chkField the chkField to set
     */
    public void setChkField(String chkField) {
        this.chkField = chkField;
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        
        super.release();
        accesskey = null;
        alt = null;
        bundle = null;
        indexed = false;
        locale = null;
        onclick = null;
        ondblclick = null;
        onmouseover = null;
        onmouseout = null;
        onmousemove = null;
        onmousedown = null;
        onmouseup = null;
        onkeydown = null;
        onkeyup = null;
        onkeypress = null;
        onselect = null;
        onchange = null;
        onblur = null;
        onfocus = null;
        disabled = false;
        readonly = false;
        style = null;
        styleClass = null;
        styleId = null;
        tabindex = null;
        title = null;
        
        chkObject = null;
        chkClazz = null;
        chkField = null; 
    }
    
    // --------------------------------------------------------- Public Methods
    
    /**
     * Prepares the style attributes for inclusion in the component's HTML tag.
     * 
     * @return The prepared String for inclusion in the HTML tag.
     * @exception JspException
     *                if invalid attributes are specified
     */
    protected String prepareStyles(){
        String value = null;
        StringBuffer styles = new StringBuffer();
        if (style != null) {
            styles.append(" style=\"");
            styles.append(getStyle());
            styles.append("\"");
        }
        if (getStyleClass() != null) {
            styles.append(" class=\"");
            styles.append(getStyleClass());
            styles.append("\"");
        }
        if (styleId != null) {
            styles.append(" id=\"");
            styles.append(getStyleId());
            styles.append("\"");
        }
        value = title;
        if (value != null) {
            styles.append(" title=\"");
            styles.append(value);
            styles.append("\"");
        }
        value = alt;
        if (value != null) {
            styles.append(" alt=\"");
            styles.append(value);
            styles.append("\"");
        }
        return styles.toString();
    }
    
    /**
     * Prepares the event handlers for inclusion in the component's HTML tag.
     * 
     * @return The prepared String for inclusion in the HTML tag.
     */
    protected String prepareEventHandlers() {
        StringBuffer handlers = new StringBuffer();
        prepareMouseEvents(handlers);
        prepareKeyEvents(handlers);
        prepareTextEvents(handlers);
        prepareFocusEvents(handlers);
        return handlers.toString();
    }
    
    /**
     * Prepares the mouse event handlers, appending them to the the given
     * StringBuffer.
     * 
     * @param handlers
     *            The StringBuffer that output will be appended to.
     */
    protected void prepareMouseEvents(StringBuffer handlers) {
        if (onclick != null) {
            handlers.append(" onclick=\"");
            handlers.append(getOnclick());
            handlers.append("\"");
        }
        
        if (ondblclick != null) {
            handlers.append(" ondblclick=\"");
            handlers.append(getOndblclick());
            handlers.append("\"");
        }
        
        if (onmouseover != null) {
            handlers.append(" onmouseover=\"");
            handlers.append(getOnmouseover());
            handlers.append("\"");
        }
        
        if (onmouseout != null) {
            handlers.append(" onmouseout=\"");
            handlers.append(getOnmouseout());
            handlers.append("\"");
        }
        
        if (onmousemove != null) {
            handlers.append(" onmousemove=\"");
            handlers.append(getOnmousemove());
            handlers.append("\"");
        }
        
        if (onmousedown != null) {
            handlers.append(" onmousedown=\"");
            handlers.append(getOnmousedown());
            handlers.append("\"");
        }
        
        if (onmouseup != null) {
            handlers.append(" onmouseup=\"");
            handlers.append(getOnmouseup());
            handlers.append("\"");
        }
    }
    
    /**
     * Prepares the keyboard event handlers, appending them to the the given
     * StringBuffer.
     * 
     * @param handlers
     *            The StringBuffer that output will be appended to.
     */
    protected void prepareKeyEvents(StringBuffer handlers) {
        
        if (onkeydown != null) {
            handlers.append(" onkeydown=\"");
            handlers.append(getOnkeydown());
            handlers.append("\"");
        }
        
        if (onkeyup != null) {
            handlers.append(" onkeyup=\"");
            handlers.append(getOnkeyup());
            handlers.append("\"");
        }
        
        if (onkeypress != null) {
            handlers.append(" onkeypress=\"");
            handlers.append(getOnkeypress());
            handlers.append("\"");
        }
    }
    
    /**
     * Prepares the text event handlers, appending them to the the given
     * StringBuffer.
     * 
     * @param handlers
     *            The StringBuffer that output will be appended to.
     */
    protected void prepareTextEvents(StringBuffer handlers) {
        
        if (onselect != null) {
            handlers.append(" onselect=\"");
            handlers.append(getOnselect());
            handlers.append("\"");
        }
        
        if (onchange != null) {
            handlers.append(" onchange=\"");
            handlers.append(getOnchange());
            handlers.append("\"");
        }
    }
    
    /**
     * Prepares the focus event handlers, appending them to the the given
     * StringBuffer.
     * 
     * @param handlers
     *            The StringBuffer that output will be appended to.
     */
    protected void prepareFocusEvents(StringBuffer handlers) {
        if (onblur != null) {
            handlers.append(" onblur=\"");
            handlers.append(getOnblur());
            handlers.append("\"");
        }
        if (onfocus != null) {
            handlers.append(" onfocus=\"");
            handlers.append(getOnfocus());
            handlers.append("\"");
        }
        if (disabled) {
            handlers.append(" disabled=\"disabled\"");
        }
        if (readonly) {
            handlers.append(" readonly=\"readonly\"");
        }
    }
    
    protected String renderStyleClass(){
        //如果需要验证
        if(StringUtils.isNotBlank(getChkField()) && (getChkObject() != null || StringUtils.isNotBlank(getChkClazz()))){
            StringBuilder styleSb = new StringBuilder();
            if(StringUtils.isNotBlank(styleClass)){
                styleSb.append(styleClass);
            }
            
            //输出class
            return styleSb.toString();
        }
        else{
            return styleClass;
        }
    }
    
    //遍历所有的validate annotation
    protected boolean renderStyle(Annotation annotation){
        
        return false;
    }
    
    protected void write(String text) {
        JspWriter writer = pageContext.getOut();
        try {
            if (text!=null) writer.print(text);
        } 
        catch (Exception e) {
            log.error("ERROR",e);
        }
    }
}
