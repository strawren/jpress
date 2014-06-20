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
 * 模型类，登陆日志，通过标志位来区分系统后台还是商户前台登陆，对应表LOG_LOGIN.
 */
public class LogLogin extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String reqHost;

    private String reqAgent;

    private Date reqDate;

    private String reqUrl;

    private String reqUserName;

    private Long reqUserId;

    /** 后台操作员的信息 */
    private Long reqOperId;

    private String reqOperName;

    private Date logoutDate;

    private String loginResult;

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

    public String getReqHost(){
        return reqHost;
    }

    public void setReqHost(String reqHost){
	entityMap.put("reqHost", reqHost);
        this.reqHost = reqHost;
    }

    public String getReqAgent(){
        return reqAgent;
    }

    public void setReqAgent(String reqAgent){
	entityMap.put("reqAgent", reqAgent);
        this.reqAgent = reqAgent;
    }

    public Date getReqDate(){
        return reqDate;
    }

    public void setReqDate(Date reqDate){
	entityMap.put("reqDate", reqDate);
        this.reqDate = reqDate;
    }

    public String getReqUrl(){
        return reqUrl;
    }

    public void setReqUrl(String reqUrl){
	entityMap.put("reqUrl", reqUrl);
        this.reqUrl = reqUrl;
    }

    public String getReqUserName(){
        return reqUserName;
    }

    public void setReqUserName(String reqUserName){
	entityMap.put("reqUserName", reqUserName);
        this.reqUserName = reqUserName;
    }

    public Long getReqUserId(){
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId){
	entityMap.put("reqUserId", reqUserId);
        this.reqUserId = reqUserId;
    }

    /** 后台操作员的信息 */
    public Long getReqOperId(){
        return reqOperId;
    }

    /** 后台操作员的信息 */
    public void setReqOperId(Long reqOperId){
	entityMap.put("reqOperId", reqOperId);
        this.reqOperId = reqOperId;
    }

    public String getReqOperName(){
        return reqOperName;
    }

    public void setReqOperName(String reqOperName){
	entityMap.put("reqOperName", reqOperName);
        this.reqOperName = reqOperName;
    }

    public Date getLogoutDate(){
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate){
	entityMap.put("logoutDate", logoutDate);
        this.logoutDate = logoutDate;
    }

    public String getLoginResult(){
        return loginResult;
    }

    public void setLoginResult(String loginResult){
	entityMap.put("loginResult", loginResult);
        this.loginResult = loginResult;
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