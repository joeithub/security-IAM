package com.security.common;

import lombok.NoArgsConstructor;

/**
 * @Author: tongq
 * @Date: 2020/4/17 13:23
 * @since：
 */
@NoArgsConstructor
public class ConstantsOauth {
    public static final String APPID = "APPID";
    public static final String SECRET = "SECRET";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String SNS_TOKEN = "SNS_TOKEN";
    public static final String CODE = "CODE";
    public static final String USERID = "USERID";

    public static final String WECHAT = "wechat";
    public static final String OPEN_QR_CONNECT = "https://open.weixin.qq.com/connect/qrconnect";
    public static final String OPEN_URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=OPENID&access_token=ACCESS_TOKEN&lang=zh_CN";
    public static final String OPEN_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    public static final String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String DINGDING = "dingding";
    public static final String DING_QR_CONNECT = "https://oapi.dingtalk.com/connect/qrconnect";
    public static final String DING_URL_AUTHORIZE = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize";
    public static final String DING_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/sns/gettoken?appid=APPID&appsecret=SECRET";
    public static final String DING_SNS_TOKEN_URL = "https://oapi.dingtalk.com/sns/get_sns_token?access_token=ACCESS_TOKEN";
    public static final String DING_GET_USER_INFO = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token=SNS_TOKEN";
    public static final String DING_PERSISTENT_CODE_URL = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token=ACCESS_TOKEN";


    public static final String QYWECHAT = "qywechat";
    public static final String QIYE_QR_CONNECT = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect";
    public static final String QIYE_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=APPID&corpsecret=SECRET";
    public static final String QIYE_GET_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
    public static final String QIYE_GET_USER_INFO_DETAILS = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";

    public static final String FEISHU = "feishu";
    public static final String FEISHU_QR_CONNECT = "https://open.feishu.cn/open-apis/authen/v1/index";
    public static final String FEISHU_ACCESS_TOKEN_URL = "https://open.feishu.cn/open-apis/authen/v1/access_token";

}
