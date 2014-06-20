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
 * 模型类，评论，对应表CMS_COMMENT.
 */
public class CmsComment extends Model {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 如果POST_ID为0,则表示为留言 */
    private Long postId;

    /** 评论人的名称 */
    private String authorName;

    /** 评论人的email */
    private String authorEmail;

    /** 评论人的网站URL */
    private String authorUrl;

    /** 评论人的IP */
    private String authorIp;

    /** 评论时间 */
    private Date commentDate;

    /** 评论人的内容 */
    private String content;

    /** 未知、预留 */
    private Long commentKarma;

    /** 是否已经审核了 */
    private String approvedFlag;

    /** 评论人的浏览器 */
    private String authorAgent;

    /** 评论类型(pingback/普通) */
    private String commentType;

    /** 评论的上级，无上下级的则填0 */
    private Long parentId;

    /** 评论者用户ID（不一定存在） */
    private Long userId;

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

    /** 如果POST_ID为0,则表示为留言 */
    public Long getPostId(){
        return postId;
    }

    /** 如果POST_ID为0,则表示为留言 */
    public void setPostId(Long postId){
	entityMap.put("postId", postId);
        this.postId = postId;
    }

    /** 评论人的名称 */
    public String getAuthorName(){
        return authorName;
    }

    /** 评论人的名称 */
    public void setAuthorName(String authorName){
	entityMap.put("authorName", authorName);
        this.authorName = authorName;
    }

    /** 评论人的email */
    public String getAuthorEmail(){
        return authorEmail;
    }

    /** 评论人的email */
    public void setAuthorEmail(String authorEmail){
	entityMap.put("authorEmail", authorEmail);
        this.authorEmail = authorEmail;
    }

    /** 评论人的网站URL */
    public String getAuthorUrl(){
        return authorUrl;
    }

    /** 评论人的网站URL */
    public void setAuthorUrl(String authorUrl){
	entityMap.put("authorUrl", authorUrl);
        this.authorUrl = authorUrl;
    }

    /** 评论人的IP */
    public String getAuthorIp(){
        return authorIp;
    }

    /** 评论人的IP */
    public void setAuthorIp(String authorIp){
	entityMap.put("authorIp", authorIp);
        this.authorIp = authorIp;
    }

    /** 评论时间 */
    public Date getCommentDate(){
        return commentDate;
    }

    /** 评论时间 */
    public void setCommentDate(Date commentDate){
	entityMap.put("commentDate", commentDate);
        this.commentDate = commentDate;
    }

    /** 评论人的内容 */
    public String getContent(){
        return content;
    }

    /** 评论人的内容 */
    public void setContent(String content){
	entityMap.put("content", content);
        this.content = content;
    }

    /** 未知、预留 */
    public Long getCommentKarma(){
        return commentKarma;
    }

    /** 未知、预留 */
    public void setCommentKarma(Long commentKarma){
	entityMap.put("commentKarma", commentKarma);
        this.commentKarma = commentKarma;
    }

    /** 是否已经审核了 */
    public String getApprovedFlag(){
        return approvedFlag;
    }

    /** 是否已经审核了 */
    public void setApprovedFlag(String approvedFlag){
	entityMap.put("approvedFlag", approvedFlag);
        this.approvedFlag = approvedFlag;
    }

    /** 评论人的浏览器 */
    public String getAuthorAgent(){
        return authorAgent;
    }

    /** 评论人的浏览器 */
    public void setAuthorAgent(String authorAgent){
	entityMap.put("authorAgent", authorAgent);
        this.authorAgent = authorAgent;
    }

    /** 评论类型(pingback/普通) */
    public String getCommentType(){
        return commentType;
    }

    /** 评论类型(pingback/普通) */
    public void setCommentType(String commentType){
	entityMap.put("commentType", commentType);
        this.commentType = commentType;
    }

    /** 评论的上级，无上下级的则填0 */
    public Long getParentId(){
        return parentId;
    }

    /** 评论的上级，无上下级的则填0 */
    public void setParentId(Long parentId){
	entityMap.put("parentId", parentId);
        this.parentId = parentId;
    }

    /** 评论者用户ID（不一定存在） */
    public Long getUserId(){
        return userId;
    }

    /** 评论者用户ID（不一定存在） */
    public void setUserId(Long userId){
	entityMap.put("userId", userId);
        this.userId = userId;
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