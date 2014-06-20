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
 * 模型类，链接，对应表CMS_LINK.
 */
public class CmsLink extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String url;

    private String name;

    private String image;

    private String target;

    /** 提示信息 */
    private String alt;

    private String visible;

    /** 拥有者 */
    private Long ownerId;

    /** 评级 */
    private Integer rating;

    /** XFN关系 */
    private String rel;

    /** XFN备注 */
    private String notes;

    private String rss;

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

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
    	entityMap.put("url", url);
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
	entityMap.put("name", name);
        this.name = name;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
	entityMap.put("image", image);
        this.image = image;
    }

    public String getTarget(){
        return target;
    }

    public void setTarget(String target){
	entityMap.put("target", target);
        this.target = target;
    }

    /** 提示信息 */
    public String getAlt(){
        return alt;
    }

    /** 提示信息 */
    public void setAlt(String alt){
	entityMap.put("alt", alt);
        this.alt = alt;
    }

    public String getVisible(){
        return visible;
    }

    public void setVisible(String visible){
	entityMap.put("visible", visible);
        this.visible = visible;
    }

    /** 拥有者 */
    public Long getOwnerId(){
        return ownerId;
    }

    /** 拥有者 */
    public void setOwnerId(Long ownerId){
	entityMap.put("ownerId", ownerId);
        this.ownerId = ownerId;
    }

    /** 评级 */
    public Integer getRating(){
        return rating;
    }

    /** 评级 */
    public void setRating(Integer rating){
	entityMap.put("rating", rating);
        this.rating = rating;
    }

    /** XFN关系 */
    public String getRel(){
        return rel;
    }

    /** XFN关系 */
    public void setRel(String rel){
	entityMap.put("rel", rel);
        this.rel = rel;
    }

    /** XFN备注 */
    public String getNotes(){
        return notes;
    }

    /** XFN备注 */
    public void setNotes(String notes){
	entityMap.put("notes", notes);
        this.notes = notes;
    }

    public String getRss(){
        return rss;
    }

    public void setRss(String rss){
	entityMap.put("rss", rss);
        this.rss = rss;
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