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
 * 模型类，内容：包括page、revision、post、attachment

The core of the WordPress data is the posts. It is stored in the wp_posts table. Also Pages and navigation menu items are stored in this table.，对应表CMS_POST.
 */
public class CmsPost extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 作者 */
    private Long ownerId;

    /** 显示的用户 */
    private String showOwner;

    private String slug;

    /** 发布的时间 */
    private Date postDate;

    /** 显示的时间 */
    private Date showDate;

    /** 内容 */
    private String content;

    /** 标题 */
    private String title;

    /** 摘要 */
    private String excerpt;

    /** 内容的状态：publish/auto-draft/inherit */
    private String postStatus;

    /** 评论的状态（open、close） */
    private String commentStatus;

    /** ping的状态，close、open */
    private String pingStatus;

    /** 如果需要密码才能查看，则填此处的密码 */
    private String postPwd;

    private String toPing;

    private String pinged;

    /** 过滤的内容，目前不启用 */
    private String fitlered;

    /** 上一级内容，主要用在page的post */
    private Long parentId;

    /** 唯一的url */
    private String guid;

    /** 如果在菜单显示，菜单的排列顺序 */
    private Integer menuOrder;

    /** 类型 post/page/attch/revision/menu/ */
    private String postType;

    /** MIME类型 */
    private String mimeType;

    /** 评论总数 */
    private Integer commentCount;

    /** 显示的顺序 */
    private Integer showOrder;

    /** 是否属于置顶 */
    private String topFlag;

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

    /** 作者 */
    public Long getOwnerId(){
        return ownerId;
    }

    /** 作者 */
    public void setOwnerId(Long ownerId){
	entityMap.put("ownerId", ownerId);
        this.ownerId = ownerId;
    }

    /** 显示的用户 */
    public String getShowOwner(){
        return showOwner;
    }

    /** 显示的用户 */
    public void setShowOwner(String showOwner){
	entityMap.put("showOwner", showOwner);
        this.showOwner = showOwner;
    }

    public String getSlug(){
        return slug;
    }

    public void setSlug(String slug){
	entityMap.put("slug", slug);
        this.slug = slug;
    }

    /** 发布的时间 */
    public Date getPostDate(){
        return postDate;
    }

    /** 发布的时间 */
    public void setPostDate(Date postDate){
	entityMap.put("postDate", postDate);
        this.postDate = postDate;
    }

    /** 显示的时间 */
    public Date getShowDate(){
        return showDate;
    }

    /** 显示的时间 */
    public void setShowDate(Date showDate){
	entityMap.put("showDate", showDate);
        this.showDate = showDate;
    }

    /** 内容 */
    public String getContent(){
        return content;
    }

    /** 内容 */
    public void setContent(String content){
	entityMap.put("content", content);
        this.content = content;
    }

    /** 标题 */
    public String getTitle(){
        return title;
    }

    /** 标题 */
    public void setTitle(String title){
	entityMap.put("title", title);
        this.title = title;
    }

    /** 摘要 */
    public String getExcerpt(){
        return excerpt;
    }

    /** 摘要 */
    public void setExcerpt(String excerpt){
	entityMap.put("excerpt", excerpt);
        this.excerpt = excerpt;
    }

    /** 内容的状态：publish/auto-draft/inherit */
    public String getPostStatus(){
        return postStatus;
    }

    /** 内容的状态：publish/auto-draft/inherit */
    public void setPostStatus(String postStatus){
	entityMap.put("postStatus", postStatus);
        this.postStatus = postStatus;
    }

    /** 评论的状态（open、close） */
    public String getCommentStatus(){
        return commentStatus;
    }

    /** 评论的状态（open、close） */
    public void setCommentStatus(String commentStatus){
	entityMap.put("commentStatus", commentStatus);
        this.commentStatus = commentStatus;
    }

    /** ping的状态，close、open */
    public String getPingStatus(){
        return pingStatus;
    }

    /** ping的状态，close、open */
    public void setPingStatus(String pingStatus){
	entityMap.put("pingStatus", pingStatus);
        this.pingStatus = pingStatus;
    }

    /** 如果需要密码才能查看，则填此处的密码 */
    public String getPostPwd(){
        return postPwd;
    }

    /** 如果需要密码才能查看，则填此处的密码 */
    public void setPostPwd(String postPwd){
	entityMap.put("postPwd", postPwd);
        this.postPwd = postPwd;
    }

    public String getToPing(){
        return toPing;
    }

    public void setToPing(String toPing){
	entityMap.put("toPing", toPing);
        this.toPing = toPing;
    }

    public String getPinged(){
        return pinged;
    }

    public void setPinged(String pinged){
	entityMap.put("pinged", pinged);
        this.pinged = pinged;
    }

    /** 过滤的内容，目前不启用 */
    public String getFitlered(){
        return fitlered;
    }

    /** 过滤的内容，目前不启用 */
    public void setFitlered(String fitlered){
	entityMap.put("fitlered", fitlered);
        this.fitlered = fitlered;
    }

    /** 上一级内容，主要用在page的post */
    public Long getParentId(){
        return parentId;
    }

    /** 上一级内容，主要用在page的post */
    public void setParentId(Long parentId){
	entityMap.put("parentId", parentId);
        this.parentId = parentId;
    }

    /** 唯一的url */
    public String getGuid(){
        return guid;
    }

    /** 唯一的url */
    public void setGuid(String guid){
	entityMap.put("guid", guid);
        this.guid = guid;
    }

    /** 如果在菜单显示，菜单的排列顺序 */
    public Integer getMenuOrder(){
        return menuOrder;
    }

    /** 如果在菜单显示，菜单的排列顺序 */
    public void setMenuOrder(Integer menuOrder){
	entityMap.put("menuOrder", menuOrder);
        this.menuOrder = menuOrder;
    }

    /** 类型 post/page/attch/revision/menu/ */
    public String getPostType(){
        return postType;
    }

    /** 类型 post/page/attch/revision/menu/ */
    public void setPostType(String postType){
	entityMap.put("postType", postType);
        this.postType = postType;
    }

    /** MIME类型 */
    public String getMimeType(){
        return mimeType;
    }

    /** MIME类型 */
    public void setMimeType(String mimeType){
	entityMap.put("mimeType", mimeType);
        this.mimeType = mimeType;
    }

    /** 评论总数 */
    public Integer getCommentCount(){
        return commentCount;
    }

    /** 评论总数 */
    public void setCommentCount(Integer commentCount){
	entityMap.put("commentCount", commentCount);
        this.commentCount = commentCount;
    }

    /** 显示的顺序 */
    public Integer getShowOrder(){
        return showOrder;
    }

    /** 显示的顺序 */
    public void setShowOrder(Integer showOrder){
	entityMap.put("showOrder", showOrder);
        this.showOrder = showOrder;
    }

    /** 是否属于置顶 */
    public String getTopFlag(){
        return topFlag;
    }

    /** 是否属于置顶 */
    public void setTopFlag(String topFlag){
	entityMap.put("topFlag", topFlag);
        this.topFlag = topFlag;
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