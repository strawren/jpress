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
 * 模型类，用户，对应表CMS_USER.
 */
public class CmsUser extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String loginName;

    private String loginPwd;

    private String nickname;

    private String userName;

    private String showName;

    private String userEmail;

    private String userUrl;

    private Date registerDate;

    private String serialKey;

    private String userStatus;

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

    public String getLoginName(){
        return loginName;
    }

    public void setLoginName(String loginName){
	entityMap.put("loginName", loginName);
        this.loginName = loginName;
    }

    public String getLoginPwd(){
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd){
	entityMap.put("loginPwd", loginPwd);
        this.loginPwd = loginPwd;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
	entityMap.put("nickname", nickname);
        this.nickname = nickname;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
	entityMap.put("userName", userName);
        this.userName = userName;
    }

    public String getShowName(){
        return showName;
    }

    public void setShowName(String showName){
	entityMap.put("showName", showName);
        this.showName = showName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserEmail(String userEmail){
	entityMap.put("userEmail", userEmail);
        this.userEmail = userEmail;
    }

    public String getUserUrl(){
        return userUrl;
    }

    public void setUserUrl(String userUrl){
	entityMap.put("userUrl", userUrl);
        this.userUrl = userUrl;
    }

    public Date getRegisterDate(){
        return registerDate;
    }

    public void setRegisterDate(Date registerDate){
	entityMap.put("registerDate", registerDate);
        this.registerDate = registerDate;
    }

    public String getSerialKey(){
        return serialKey;
    }

    public void setSerialKey(String serialKey){
	entityMap.put("serialKey", serialKey);
        this.serialKey = serialKey;
    }

    public String getUserStatus(){
        return userStatus;
    }

    public void setUserStatus(String userStatus){
	entityMap.put("userStatus", userStatus);
        this.userStatus = userStatus;
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