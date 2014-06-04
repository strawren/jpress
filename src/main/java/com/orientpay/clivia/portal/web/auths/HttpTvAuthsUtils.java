package com.orientpay.clivia.portal.web.auths;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orientpay.oecs.util.crypto.MsgCryptoUtils;

/**
 * 封装认证接口的APT客户端
 * @author wangcm
 *
 */
public class HttpTvAuthsUtils {
    protected static Log log = LogFactory.getLog(HttpTvAuthsUtils.class);
    //服务端地址URL
//    public static final String TVAUTHS_URL = "http://192.168.0.219:8080/tvauths/auths.html";
    public static final String TVAUTHS_URL = "http://192.168.200.238:8001/tvauths/auths.html";


    //目的机构号
    public static final String RSP_BRH_CODE = "888888888888888";
    //目的交易日期
    public static final String RSP_BRH_DATE = "00000000";
    //mackey加签使用
    public static final String MAC_KEY = "8888888800000000";
    //BOSS三期认证系统交易码
    public static final String TVAUTHS_TRANS_CODE = "00M00010";//
    //机顶盒mac地址
//    public static final String STBMAC = "98BC57150054";
    //请求机构代码 代表东方亿付
    public static final String BRH_CODE = "999990299999999";//999990299999999

    public static String macKey = "8888888800000000";//B9E9E3E634B9FBA7 加签必须使用B9E9E3E634B9FBA7数据库中已经配置
    public static String stbMac = "00196821530A";//87BA02854F8D674A
    public static String userInfoFlag = "0";
    public static String appCode = "bizportal_tv";//便民付费

    public static void main(String[] args) {
        TvAuthsResponse resp = tvAuths(TVAUTHS_URL, appCode, macKey, stbMac, userInfoFlag);
        System.out.println("response:"+resp.toString());
    }

    /**
     * 发往服务端系统认证
     * @param url 远程服务器端地址
     * @param appCode 用用appcode 例如：TvPay
     * @param macKey  加签请求
     * @param stbMac 机顶盒mac地址
     * @param userInfoFlag 是否需要用户信息 0需要 1不需要
     */
    public static TvAuthsResponse tvAuths(String url,String appCode,String macKey,String stbMac,String userInfoFlag){
//        String appCode = "tvauths";//TvPay
        //组装请求报文
        TvAuthsRequest request = createTvAuthsRequest(stbMac, appCode,macKey,userInfoFlag);
        String json = JsonUtils.objectToJson(request);

        debug("send to boss3 :" + json);
        String jsonRes = HttpUtils.doJsonPost(url, json);
        debug("jsonRes -->" + jsonRes);
        if(jsonRes == null || "".equals(jsonRes.trim())) {
            throw new IllegalStateException("can not get msg from boss3");
        }
        TvAuthsResponse res = (TvAuthsResponse)JsonUtils.jsonToObject(jsonRes, TvAuthsResponse.class);
        debug("get user from boss3 :" + res);
        return res;

    }
    /**
     * 组装请求报文
     * @param stbMac 机顶盒mac地址
     * @param bthCode 请求机构代码对应一个应用
     * @param mackey 加签
     * @return userInfoFlag 是否需要用户信息 0需要 1不需要
     */
    protected static TvAuthsRequest createTvAuthsRequest(String stbMac, String appCode,String mackey,String userInfoFlag) {
        TvAuthsRequest request = new TvAuthsRequest();

        CommonMessageHeader header = new CommonMessageHeader();
        CommonMessageFooter footer = new CommonMessageFooter();
        request.setMessageFooter(footer);
        request.setMessageHeader(header);

        request.setDesc("N/A");
        request.setMac(stbMac);//机顶盒mac地址
        request.setProductId(appCode);
        request.setUserInfoFlag(userInfoFlag);//是否需要用户信息
        request.setUserNo("");
        request.setUserNoPwd("");

        header.setMediaPwd("");
        header.setMediaVal("");
        header.setReqBrhCode(BRH_CODE);//请求机构代码
        header.setReqBrhDate(DateTimeUtils.dateToStringFormat(DateTimeUtils.getCurrentDate(), DateTimeUtils.SHOT_DATE_FORMAT));
        header.setReqBrhSeq(String.valueOf(new Date().getTime()));//请求方交易流水
        header.setRspBrhSeq("");
        header.setRspBrhCode(RSP_BRH_CODE);//目的机构代码
        header.setRspBrhDate(RSP_BRH_DATE);
        header.setRspCode("00");
        header.setTxnClsNo(TVAUTHS_TRANS_CODE.substring(7, 8));
        header.setTxnType(TVAUTHS_TRANS_CODE.substring(2, 7));
        header.setTxnVer(TVAUTHS_TRANS_CODE.substring(0, 2));

        //加签
        byte [] msg = null;
        try {
            msg = request.getStringMessage().getBytes("GBK");
        }
        catch(Exception e) {
            msg = request.getStringMessage().getBytes();
        }
        String dataMac = MsgCryptoUtils.produceStringMac(msg, mackey);
        footer.setFooter(dataMac);
        return request;
    }

    protected static void debug(String msg) {
        log.debug(msg);
    }

}
