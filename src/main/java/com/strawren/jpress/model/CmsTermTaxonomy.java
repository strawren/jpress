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
 * 模型类，分类方法：存储每个目录、标签所对应的分类
taxonomy：分类方法(category/post_tag/nav_menu/link_category)

This table describes the taxonomy (category, link, or tag) for the entries in the wp_terms table.，对应表CMS_TERM_TAXONOMY.
 */
public class CmsTermTaxonomy extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long termId;

    private String taxonomy;

    private Long parentId;

    private Integer postCount;

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

    public String getTaxonomy(){
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy){
	entityMap.put("taxonomy", taxonomy);
        this.taxonomy = taxonomy;
    }

    public Long getParentId(){
        return parentId;
    }

    public void setParentId(Long parentId){
	entityMap.put("parentId", parentId);
        this.parentId = parentId;
    }

    public Integer getPostCount(){
        return postCount;
    }

    public void setPostCount(Integer postCount){
	entityMap.put("postCount", postCount);
        this.postCount = postCount;
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