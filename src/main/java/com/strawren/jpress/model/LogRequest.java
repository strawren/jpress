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
 * 模型类，对应表LOG_REQUEST.
 */
public class LogRequest extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String reqHost;

    /** 用户浏览器 */
    private String reqAgent;

    /** 请求参数 */
    private String reqParam;

    /** 请求地址 */
    private String reqUrl;

    private Date reqDate;

    /** 系统客户的信息 */
    private Long reqUserId;

    private String reqUserName;

    /** 后台操作员的信息 */
    private Long reqOperId;

    private String reqOperName;

    /** 日志级别 */
    private String logLevel;

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

    /** 用户浏览器 */
    public String getReqAgent(){
        return reqAgent;
    }

    /** 用户浏览器 */
    public void setReqAgent(String reqAgent){
	entityMap.put("reqAgent", reqAgent);
        this.reqAgent = reqAgent;
    }

    /** 请求参数 */
    public String getReqParam(){
        return reqParam;
    }

    /** 请求参数 */
    public void setReqParam(String reqParam){
	entityMap.put("reqParam", reqParam);
        this.reqParam = reqParam;
    }

    /** 请求地址 */
    public String getReqUrl(){
        return reqUrl;
    }

    /** 请求地址 */
    public void setReqUrl(String reqUrl){
	entityMap.put("reqUrl", reqUrl);
        this.reqUrl = reqUrl;
    }

    public Date getReqDate(){
        return reqDate;
    }

    public void setReqDate(Date reqDate){
	entityMap.put("reqDate", reqDate);
        this.reqDate = reqDate;
    }

    /** 系统客户的信息 */
    public Long getReqUserId(){
        return reqUserId;
    }

    /** 系统客户的信息 */
    public void setReqUserId(Long reqUserId){
	entityMap.put("reqUserId", reqUserId);
        this.reqUserId = reqUserId;
    }

    public String getReqUserName(){
        return reqUserName;
    }

    public void setReqUserName(String reqUserName){
	entityMap.put("reqUserName", reqUserName);
        this.reqUserName = reqUserName;
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

    /** 日志级别 */
    public String getLogLevel(){
        return logLevel;
    }

    /** 日志级别 */
    public void setLogLevel(String logLevel){
	entityMap.put("logLevel", logLevel);
        this.logLevel = logLevel;
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