/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-23 下午12:15:47
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
public class CommonMessageHeader {

    private String txnVer = "";                              // 版本号:AN2

    private String txnType = "";                             // 交易类型:AN5

    private String txnClsNo = "";                            // 分类交易序号:AN1

    private String reqBrhCode = "";                         // 请求机构代码:AN15

    private String reqBrhDate = "00000000";                          // 请求交易日期:YYYYMMDD

    private String reqBrhSeq = "";                           // 请求方交易流水号:AN20

    private String rspBrhCode = "";                          // 目的机构代码:AN15

    private String rspBrhDate = "00000000";                          // 目的交易日期:YYYYMMDD

    private String rspBrhSeq = "";                           // 目的方交易流水号:AN20

    private String mediaVal = "";                            // 账号:ANS40

    private String mediaPwd = "";                            // 密码:ANS8

    private String rspCode = "";                             // 应答码:AN2

    /**
     * Property accessor of txnVer
     * @return the txnVer
     */
    public String getTxnVer() {
        return txnVer;
    }
    
    /**
     * @param txnVer the txnVer to set
     */
    public void setTxnVer(String txnVer) {
        this.txnVer = txnVer;
    }

    /**
     * Property accessor of txnType
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }
    
    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    /**
     * Property accessor of txnClsNo
     * @return the txnClsNo
     */
    public String getTxnClsNo() {
        return txnClsNo;
    }
    
    /**
     * @param txnClsNo the txnClsNo to set
     */
    public void setTxnClsNo(String txnClsNo) {
        this.txnClsNo = txnClsNo;
    }
    
    /**
     * Property accessor of reqBrhCode
     * @return the reqBrhCode
     */
    public String getReqBrhCode() {
        return reqBrhCode;
    }
    
    /**
     * @param reqBrhCode the reqBrhCode to set
     */
    public void setReqBrhCode(String reqBrhCode) {
        this.reqBrhCode = reqBrhCode;
    }

    /**
     * Property accessor of reqBrhDate
     * @return the reqBrhDate
     */
    public String getReqBrhDate() {
        return reqBrhDate;
    }
    
    /**
     * @param reqBrhDate the reqBrhDate to set
     */
    public void setReqBrhDate(String reqBrhDate) {
        this.reqBrhDate = reqBrhDate;
    }

    /**
     * Property accessor of reqBrhSeq
     * @return the reqBrhSeq
     */
    public String getReqBrhSeq() {
        return reqBrhSeq;
    }
    
    /**
     * @param reqBrhSeq the reqBrhSeq to set
     */
    public void setReqBrhSeq(String reqBrhSeq) {
        this.reqBrhSeq = reqBrhSeq;
    }
    
    /**
     * Property accessor of rspBrhCode
     * @return the rspBrhCode
     */
    public String getRspBrhCode() {
        return rspBrhCode;
    }
    
    /**
     * @param rspBrhCode the rspBrhCode to set
     */
    public void setRspBrhCode(String rspBrhCode) {
        this.rspBrhCode = rspBrhCode;
    }

    /**
     * Property accessor of rspBrhDate
     * @return the rspBrhDate
     */
    public String getRspBrhDate() {
        return rspBrhDate;
    }
    
    /**
     * @param rspBrhDate the rspBrhDate to set
     */
    public void setRspBrhDate(String rspBrhDate) {
        this.rspBrhDate = rspBrhDate;
    }
    
    /**
     * Property accessor of repBrhSeq
     * @return the repBrhSeq
     */
    public String getRspBrhSeq() {
        return rspBrhSeq;
    }
    
    /**
     * @param repBrhSeq the repBrhSeq to set
     */
    public void setRspBrhSeq(String rspBrhSeq) {
        this.rspBrhSeq = rspBrhSeq;
    }

    /**
     * Property accessor of mediaVal
     * @return the mediaVal
     */
    public String getMediaVal() {
        return mediaVal;
    }
    
    /**
     * @param mediaVal the mediaVal to set
     */
    public void setMediaVal(String mediaVal) {
        this.mediaVal = mediaVal;
    }
    
    /**
     * Property accessor of mediaPwd
     * @return the mediaPwd
     */
    public String getMediaPwd() {
        return mediaPwd;
    }
    
    /**
     * @param mediaPwd the mediaPwd to set
     */
    public void setMediaPwd(String mediaPwd) {
        this.mediaPwd = mediaPwd;
    }
    
    /**
     * Property accessor of rspCode
     * @return the rspCode
     */
    public String getRspCode() {
        return rspCode;
    }
    
    /**
     * @param rspCode the rspCode to set
     */
    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    @Override
    public String toString() {
        return "CommonMessageHeader [txnVer="
                + txnVer
                + ", txnType="
                + txnType
                + ", txnClsNo="
                + txnClsNo
                + ", reqBrhCode="
                + reqBrhCode
                + ", reqBrhDate="
                + reqBrhDate
                + ", reqBrhSeq="
                + reqBrhSeq
                + ", rspBrhCode="
                + rspBrhCode
                + ", rspBrhDate="
                + rspBrhDate
                + ", rspBrhSeq="
                + rspBrhSeq
                + ", mediaVal="
                + mediaVal
                + ", mediaPwd="
                + mediaPwd
                + ", rspCode="
                + rspCode
                + "]";
    }
}
