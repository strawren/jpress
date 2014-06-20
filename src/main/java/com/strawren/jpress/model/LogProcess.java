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
 * 模型类，处理日志，通过日志类型+动作+是否显示给用户
日志类型包括订单、商户，对应表LOG_PROCESS.
 */
public class LogProcess extends Model {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 日志分类 */
    private String logType;

    /** 日志处理结果 */
    private String logResult;

    /** 关联的业务编号 */
    private String referBizId;

    /** 关联的业务内容 */
    private String referBiz;

    private Date processDate;

    /** 系统客户的信息 */
    private Long reqUserId;

    private String reqUserName;

    /** 后台操作员的信息 */
    private Long reqOperId;

    private String reqOperName;

    /** 摘要 */
    private String processSummary;

    /** 详情 */
    private String processDetail;

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

    /** 日志分类 */
    public String getLogType(){
        return logType;
    }

    /** 日志分类 */
    public void setLogType(String logType){
	entityMap.put("logType", logType);
        this.logType = logType;
    }

    /** 日志处理结果 */
    public String getLogResult(){
        return logResult;
    }

    /** 日志处理结果 */
    public void setLogResult(String logResult){
	entityMap.put("logResult", logResult);
        this.logResult = logResult;
    }

    /** 关联的业务编号 */
    public String getReferBizId(){
        return referBizId;
    }

    /** 关联的业务编号 */
    public void setReferBizId(String referBizId){
	entityMap.put("referBizId", referBizId);
        this.referBizId = referBizId;
    }

    /** 关联的业务内容 */
    public String getReferBiz(){
        return referBiz;
    }

    /** 关联的业务内容 */
    public void setReferBiz(String referBiz){
	entityMap.put("referBiz", referBiz);
        this.referBiz = referBiz;
    }

    public Date getProcessDate(){
        return processDate;
    }

    public void setProcessDate(Date processDate){
	entityMap.put("processDate", processDate);
        this.processDate = processDate;
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
    public String getProcessSummary(){
        return processSummary;
    }

    /** 摘要 */
    public void setProcessSummary(String processSummary){
	entityMap.put("processSummary", processSummary);
        this.processSummary = processSummary;
    }

    /** 详情 */
    public String getProcessDetail(){
        return processDetail;
    }

    /** 详情 */
    public void setProcessDetail(String processDetail){
	entityMap.put("processDetail", processDetail);
        this.processDetail = processDetail;
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