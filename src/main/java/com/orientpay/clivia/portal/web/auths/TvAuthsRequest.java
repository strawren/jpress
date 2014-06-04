/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-23 下午12:11:50
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
public class TvAuthsRequest {
	private static final long serialVersionUID = 9063438389524017974L;

    private CommonMessageHeader messageHead = new CommonMessageHeader();

    private CommonMessageFooter messageFoot = new CommonMessageFooter();

    /** 机顶盒MAC地址:length = 40 */
    private String mac = "";

    /** 用户证号:length = 40. */
    private String userNo = "";

    /** 用户证号密码:length = 40. */
    private  String userNoPwd = "";

    /** 应用编号:length = 40. */
    private String productId = "";

    /** 是否需要用户信息:length = 1. */
    private String userInfoFlag = "0";

    /** 备注:length = LLvar. */
    private String desc = "";
    
    public CommonMessageHeader getMessageHeader() {
        return messageHead;
    }

    public void setMessageHeader(CommonMessageHeader messageHead) {
        this.messageHead = messageHead;
    }

    public CommonMessageFooter getMessageFooter() {
        return messageFoot;
    }

    public void setMessageFooter(CommonMessageFooter messageFoot){
        this.messageFoot = messageFoot;
    }

    /** 机顶盒MAC地址:length = 40 */
    public String getMac() {
        return mac;
    }
    /** 机顶盒MAC地址:length = 40 */
    public void setMac(String mac) {
        this.mac = mac;
    }
    /** 用户证号:length = 40 */
    public String getUserNo() {
        return userNo;
    }
    /** 用户证号:length = 40*/
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /** 用户证号密码:length = 40. */
    public String getUserNoPwd() {
        return userNoPwd;
    }

    /** 用户证号密码:length = 40. */
    public void setUserNoPwd(String userNoPwd) {
        this.userNoPwd = userNoPwd;
    }

    /** 应用编号:length = 40. */
    public String getProductId() {
        return productId;
    }

    /** 应用编号:length = 40. */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /** 是否需要用户信息:length = 1. */
    public String getUserInfoFlag() {
        return userInfoFlag;
    }

    /** 是否需要用户信息:length = 1. */
    public void setUserInfoFlag(String userInfoFlag) {
        this.userInfoFlag = userInfoFlag;
    }

    /** 备注:length = LLvar. */
    public String getDesc() {
        return desc;
    }

    /** 备注:length = LLvar. */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStringMessage() {
        return "TvAuthsRequest [messageHead=" + messageHead
        + ", mac=" + mac + ", userNo=" + userNo
        + ", userNoPwd=" + userNoPwd + ", productId=" + productId
        + ", userInfoFlag=" + userInfoFlag + ", desc=" + desc + "]";
    }

    @Override
    public String toString() {
        return "TvAuthsRequest [messageHead=" + messageHead + ", messageFoot="
                + messageFoot + ", mac=" + mac + ", userNo=" + userNo
                + ", userNoPwd=" + userNoPwd + ", productId=" + productId
                + ", userInfoFlag=" + userInfoFlag + ", desc=" + desc + "]";
    }
}
