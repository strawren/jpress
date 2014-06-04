/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-25 上午10:47:02
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2013-8-25        Initailized
 */

package com.orientpay.clivia.portal.web.auths;

import java.util.Date;

import com.orientpay.oecs.util.crypto.MsgCryptoUtils;


/**
 * TODO Add class descriptions
 *
 */
public class AuthsTest {
    public static final String TVAUTHS_URL = "http://192.168.0.219:8080/tvauths/auths.html";
    public static final String TVAUTHS_BRH = "8888888800000000";
    public static final String TVAUTHS_MAC = "8888888800000000";
    public static final String TVAUTHS_TRANS_CODE = "00Q00010";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // String stbMac = "00:05:5D:A0:8F:60";
        String stbMac = "98BC5714FFB0";
        String appCode = "TvPay";// TvPay
        TvAuthsRequest request = createTvAuthsRequest(stbMac, appCode);
        String json = JsonUtils.objectToJson(request);

        debug("send to boss3 :" + json);
        String jsonRes = HttpUtils.doJsonPost(TVAUTHS_URL, json);
        debug("jsonRes -->" + jsonRes);
        if (jsonRes == null || "".equals(jsonRes.trim())) {
            throw new IllegalStateException("can not get msg from boss3");
        }
        TvAuthsResponse res = (TvAuthsResponse) JsonUtils.jsonToObject(jsonRes,
                TvAuthsResponse.class);
        debug("get user from boss3 :" + res);
    }

    protected static TvAuthsRequest createTvAuthsRequest(String stbMac,
            String appCode) {
        TvAuthsRequest request = new TvAuthsRequest();

        CommonMessageHeader header = new CommonMessageHeader();
        CommonMessageFooter footer = new CommonMessageFooter();
        request.setMessageFooter(footer);
        request.setMessageHeader(header);

        request.setDesc("N/A");
        request.setMac(stbMac);
        request.setProductId(appCode);
        request.setUserInfoFlag("0");
        request.setUserNo("");
        request.setUserNoPwd("");

        header.setMediaPwd("");
        header.setMediaVal("");
        header.setReqBrhCode(TVAUTHS_BRH);
        header.setReqBrhDate("20130825");
        header.setReqBrhSeq(String.valueOf(new Date().getTime()));
        header.setRspBrhCode("888888888888888");
        header.setRspBrhDate("00000000");
        header.setReqBrhSeq("0000000000000000000");
        header.setRspCode("00");
        header.setTxnClsNo(TVAUTHS_TRANS_CODE.substring(7, 8));
        header.setTxnType(TVAUTHS_TRANS_CODE.substring(2, 7));
        header.setTxnVer(TVAUTHS_TRANS_CODE.substring(0, 2));

        // 加签
        byte[] msg = null;
        try {
            msg = request.getStringMessage().getBytes("GBK");
        } catch (Exception e) {
            msg = request.getStringMessage().getBytes();
        }
        String dataMac = MsgCryptoUtils.produceStringMac(msg, TVAUTHS_MAC);
        footer.setFooter(dataMac);

        return request;
    }

    protected static void debug(String msg) {
        System.out.println(msg);
    }
}
