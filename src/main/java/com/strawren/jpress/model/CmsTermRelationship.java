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
 * 模型类，存储每个文章、链接和对应分类的关系

Posts are associated with categories and tags from the wp_terms table and this association is maintained in the wp_term_relationships table. The association of links to their respective categories are also kept in this table.，对应表CMS_TERM_RELATIONSHIP.
 */
public class CmsTermRelationship extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 分类的方法ID */
    private Long termTaxonomyId;

    /** 对应文章ID/链接ID */
    private Long objectId;

    /** 排序 */
    private Integer termOrder;

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

    /** 分类的方法ID */
    public Long getTermTaxonomyId(){
        return termTaxonomyId;
    }

    /** 分类的方法ID */
    public void setTermTaxonomyId(Long termTaxonomyId){
	entityMap.put("termTaxonomyId", termTaxonomyId);
        this.termTaxonomyId = termTaxonomyId;
    }

    /** 对应文章ID/链接ID */
    public Long getObjectId(){
        return objectId;
    }

    /** 对应文章ID/链接ID */
    public void setObjectId(Long objectId){
	entityMap.put("objectId", objectId);
        this.objectId = objectId;
    }

    /** 排序 */
    public Integer getTermOrder(){
        return termOrder;
    }

    /** 排序 */
    public void setTermOrder(Integer termOrder){
	entityMap.put("termOrder", termOrder);
        this.termOrder = termOrder;
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