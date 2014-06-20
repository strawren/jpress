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
 * 模型类，内容管理平台的属性配置，对应表CMS_OPTION.
 */
public class CmsOption extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    /** 分类代号：比如邮箱配置，需要配置STMP\USER\PWD等，用于分组，如果没有分组，则为空即可 */
    private String catgoryCode;

    private String name;

    private String value;

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

    public String getCode(){
        return code;
    }

    public void setCode(String code){
	entityMap.put("code", code);
        this.code = code;
    }

    /** 分类代号：比如邮箱配置，需要配置STMP\USER\PWD等，用于分组，如果没有分组，则为空即可 */
    public String getCatgoryCode(){
        return catgoryCode;
    }

    /** 分类代号：比如邮箱配置，需要配置STMP\USER\PWD等，用于分组，如果没有分组，则为空即可 */
    public void setCatgoryCode(String catgoryCode){
	entityMap.put("catgoryCode", catgoryCode);
        this.catgoryCode = catgoryCode;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
	entityMap.put("name", name);
        this.name = name;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
	entityMap.put("value", value);
        this.value = value;
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