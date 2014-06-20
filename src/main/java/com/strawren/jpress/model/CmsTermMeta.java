/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by fuhy at 2014-4-2 上午10:02:22
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * fuhy     2014-4-2        Initailized
 */
package com.strawren.jpress.model;

import java.io.Serializable;
import java.util.Date;

import com.strawren.bsp.core.Model;

/**
 * 模型类，目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。
该表不为wordpress固有的，而是新增的。，对应表CMS_TERM_META.
 */
public class CmsTermMeta extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long termId;

    /** 属性类型：term/post
 */
    private String metaType;

    private String key;

    /** 显示的名称 */
    private String name;

    /** 值类型，针对POST才有效，比如文件，数字，日期，文本等 */
    private String valueType;

    /** 值的格式 */
    private String valueFormat;

    /** 如果type为post，则不需要填写此值 */
    private String value;

    /** 排序 */
    private Long showOrder;

    private String miscDesc;

    /** 逻辑删除标志 */
    private String status;

    private Date createTime;

    private Long createOperId;

    private String createOperName;

    private Date lastUpdTime;

    private Long lastUpdOperId;

    private String lastUpdOperName;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
	entityMap.put("id", id);
        this.id = id;
    }

    public Long getTermId(){
        return termId;
    }

    public void setTermId(Long termId){
	entityMap.put("termId", termId);
        this.termId = termId;
    }

    /** 属性类型：term/post
 */
    public String getMetaType(){
        return metaType;
    }

    /** 属性类型：term/post
 */
    public void setMetaType(String metaType){
	entityMap.put("metaType", metaType);
        this.metaType = metaType;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
	entityMap.put("key", key);
        this.key = key;
    }

    /** 显示的名称 */
    public String getName(){
        return name;
    }

    /** 显示的名称 */
    public void setName(String name){
	entityMap.put("name", name);
        this.name = name;
    }

    /** 值类型，针对POST才有效，比如文件，数字，日期，文本等 */
    public String getValueType(){
        return valueType;
    }

    /** 值类型，针对POST才有效，比如文件，数字，日期，文本等 */
    public void setValueType(String valueType){
	entityMap.put("valueType", valueType);
        this.valueType = valueType;
    }

    /** 值的格式 */
    public String getValueFormat(){
        return valueFormat;
    }

    /** 值的格式 */
    public void setValueFormat(String valueFormat){
	entityMap.put("valueFormat", valueFormat);
        this.valueFormat = valueFormat;
    }

    /** 如果type为post，则不需要填写此值 */
    public String getValue(){
        return value;
    }

    /** 如果type为post，则不需要填写此值 */
    public void setValue(String value){
	entityMap.put("value", value);
        this.value = value;
    }


    public Long getShowOrder() {
        return showOrder;
    }


    public void setShowOrder(Long showOrder) {
        entityMap.put("showOrder", showOrder);
        this.showOrder = showOrder;
    }

    public String getMiscDesc(){
        return miscDesc;
    }

    public void setMiscDesc(String miscDesc){
	entityMap.put("miscDesc", miscDesc);
        this.miscDesc = miscDesc;
    }

    /** 逻辑删除标志 */
    public String getStatus(){
        return status;
    }

    /** 逻辑删除标志 */
    public void setStatus(String status){
	entityMap.put("status", status);
        this.status = status;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
	entityMap.put("createTime", createTime);
        this.createTime = createTime;
    }

    public Long getCreateOperId(){
        return createOperId;
    }

    public void setCreateOperId(Long createOperId){
	entityMap.put("createOperId", createOperId);
        this.createOperId = createOperId;
    }

    public String getCreateOperName(){
        return createOperName;
    }

    public void setCreateOperName(String createOperName){
	entityMap.put("createOperName", createOperName);
        this.createOperName = createOperName;
    }

    public Date getLastUpdTime(){
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime){
	entityMap.put("lastUpdTime", lastUpdTime);
        this.lastUpdTime = lastUpdTime;
    }

    public Long getLastUpdOperId(){
        return lastUpdOperId;
    }

    public void setLastUpdOperId(Long lastUpdOperId){
	entityMap.put("lastUpdOperId", lastUpdOperId);
        this.lastUpdOperId = lastUpdOperId;
    }

    public String getLastUpdOperName(){
        return lastUpdOperName;
    }

    public void setLastUpdOperName(String lastUpdOperName){
	entityMap.put("lastUpdOperName", lastUpdOperName);
        this.lastUpdOperName = lastUpdOperName;
    }

    @Override
    public Serializable getPK() {
        return id;
    }

}