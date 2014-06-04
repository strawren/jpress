/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-23 下午12:12:05
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2013-8-23        Initailized
 */

package com.orientpay.clivia.portal.web.auths;

/**
 * TODO Add class descriptions
 * 
 */
public class TvAuthsResponse {
	private static final long serialVersionUID = 1L;

	private CommonMessageHeader messageHead = new CommonMessageHeader();
	private CommonMessageFooter messageFoot = new CommonMessageFooter();

	/** 结果代码:length = 4 */
	private String returnCode = "";

	/** 结果描述:LLvar */
	private String returnMsg = "";

	/** 验证结果:AN1. */
	private String isPass = "";

	/** 用户服务ID（用户证号）:length = 40. */
	private String userNo = "";

	/** 用户姓名:length = 20 */
	private String userName = "";

	/** 证件类型:length = 2 */
	private String certiType = "";

	/** 证件号码:length = 40 */
	private String certiNo = "";

	/** 详细状态:length = 40) */
	private String state = "";

	/** 邮政编码:length = 7 */
	private String postCode = "";

	/** E-MAIL:length = 40). */
	private String email = "";

	/** 备注信息:LLvar */
	private String remark = "";

	/** 联系人电话1:length = 20 */
	private String telephone1 = "";

	/** 联系人电话2:length = 20 */
	private String telephone2 = "";

	/** 联系人手机1:length = 11 */
	private String mobile1 = "";

	/** 联系人手机2:length = 11 */
	private String mobile2 = "";

	/** 客户类型:length = 30 */
	private String custType = "";

	/** 安装地址:AN2. */
	private String insAddress = "";

	/** 用户类别:length = 30 */
	private String userType = "";

	public CommonMessageHeader getMessageHeader() {
		return messageHead;
	}

	public void setMessageHeader(CommonMessageHeader messageHead) {
		this.messageHead = messageHead;
	}

	public CommonMessageFooter getMessageFooter() {
		return messageFoot;
	}

	public void setMessageFooter(CommonMessageFooter messageFoot) {
		this.messageFoot = messageFoot;
	}

	/** 结果代码:length = 4 */
	public String getReturnCode() {
		return returnCode;
	}

	/** 结果代码:length = 4 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	/** 结果描述:LLvar */
	public String getReturnMsg() {
		return returnMsg;
	}

	/** 结果描述:LLvar */
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	/** 验证结果:AN2. */
	public String getIsPass() {
		return isPass;
	}

	/** 验证结果:AN2. */
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	/** 用户服务ID（用户证号）:length = 40. */
	public String getUserNo() {
		return userNo;
	}

	/** 用户服务ID（用户证号）:length = 40. */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 用户姓名:length = 20 */
	public String getUserName() {
		return userName;
	}

	/** 用户姓名:length = 20 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** 证件类型:length = 40 */
	public String getCertiType() {
		return certiType;
	}

	/** 证件类型:length = 40 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/** 证件号码:length = 40 */
	public String getCertiNo() {
		return certiNo;
	}

	/** 证件号码:length = 40 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/** 详细状态:length = 40) */
	public String getState() {
		return state;
	}

	/** 详细状态:length = 40) */
	public void setState(String state) {
		this.state = state;
	}

	/** 邮政编码:length = 7 */
	public String getPostCode() {
		return postCode;
	}

	/** 邮政编码:length = 7 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/** E-MAIL:length = 40). */
	public String getEmail() {
		return email;
	}

	/** E-MAIL:length = 40). */
	public void setEmail(String email) {
		this.email = email;
	}

	/** 备注信息:LLvar */
	public String getRemark() {
		return remark;
	}

	/** 备注信息:LLvar */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 联系人电话1:length = 20 */
	public String getTelephone1() {
		return telephone1;
	}

	/** 联系人电话1:length = 20 */
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	/** 联系人电话2:length = 20 */
	public String getTelephone2() {
		return telephone2;
	}

	/** 联系人电话2:length = 20 */
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	/** 联系人手机1:length = 11 */
	public String getMobile1() {
		return mobile1;
	}

	/** 联系人手机1:length = 11 */
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	/** 联系人手机2:length = 11 */
	public String getMobile2() {
		return mobile2;
	}

	/** 联系人手机2:length = 11 */
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	/** 客户类型:length = 30 */
	public String getCustType() {
		return custType;
	}

	/** 客户类型:length = 30 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/** 安装地址:AN2. */
	public String getInsAddress() {
		return insAddress;
	}

	/** 安装地址:AN2. */
	public void setInsAddress(String insAddress) {
		this.insAddress = insAddress;
	}

	/** 用户类别:length = 30 */
	public String getUserType() {
		return userType;
	}

	/** 用户类别:length = 30 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStringMessage() {
		return "TvAuthsResponse [messageHead=" + messageHead + ", returnCode="
				+ returnCode + ", returnMsg=" + returnMsg + ", isPass="
				+ isPass + ", userNo=" + userNo + ", userName=" + userName
				+ ", certiType=" + certiType + ", certiNo=" + certiNo + ", state="
				+ state + ", postCode=" + postCode + ", email=" + email
				+ ", remark=" + remark + ", telephone1=" + telephone1
				+ ", telephone2=" + telephone2 + ", mobile1=" + mobile1
				+ ", mobile2=" + mobile2 + ", custType=" + custType
				+ ", insAddress=" + insAddress + ", userType=" + userType + "]";
	}

	@Override
	public String toString() {
		return "TvAuthsResponse [messageHead=" + messageHead + ", messageFoot="
				+ messageFoot + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + ", isPass=" + isPass + ", userNo=" + userNo
				+ ", userName=" + userName + ", certiype=" + certiType
				+ ", certiNo=" + certiNo + ", state=" + state + ", postCode="
				+ postCode + ", email=" + email + ", remark=" + remark
				+ ", telephone1=" + telephone1 + ", telephone2=" + telephone2
				+ ", mobile1=" + mobile1 + ", mobile2=" + mobile2
				+ ", custType=" + custType + ", insAddress=" + insAddress
				+ ", userType=" + userType + "]";
	}
}
