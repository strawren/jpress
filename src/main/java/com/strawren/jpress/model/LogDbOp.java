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
 * 模型类，数据库操作日志，通过AOP来实现的，对应表LOG_DB_OP.
 */
public class LogDbOp extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String actionType;

    private String serviceName;

    private String daoName;

    /** 系统客户的信息 */
    private Long reqUserId;

    private String reqUserName;

    /** 后台操作员的信息 */
    private Long reqOperId;

    private String reqOperName;

    /** 摘要 */
    private String beforeData;

    /** 详情 */
    private String afterData;

    private String opSummary;

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

    public String getActionType(){
        return actionType;
    }

    public void setActionType(String actionType){
	entityMap.put("actionType", actionType);
        this.actionType = actionType;
    }

    public String getServiceName(){
        return serviceName;
    }

    public void setServiceName(String serviceName){
	entityMap.put("serviceName", serviceName);
        this.serviceName = serviceName;
    }

    public String getDaoName(){
        return daoName;
    }

    public void setDaoName(String daoName){
	entityMap.put("daoName", daoName);
        this.daoName = daoName;
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

    /** 摘要 */
    public String getBeforeData(){
        return beforeData;
    }

    /** 摘要 */
    public void setBeforeData(String beforeData){
	entityMap.put("beforeData", beforeData);
        this.beforeData = beforeData;
    }

    /** 详情 */
    public String getAfterData(){
        return afterData;
    }

    /** 详情 */
    public void setAfterData(String afterData){
	entityMap.put("afterData", afterData);
        this.afterData = afterData;
    }

    public String getOpSummary(){
        return opSummary;
    }

    public void setOpSummary(String opSummary){
	entityMap.put("opSummary", opSummary);
        this.opSummary = opSummary;
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