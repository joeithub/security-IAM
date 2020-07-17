package com.security.cfg.normal;

import com.security.common.ConstantsOauth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tongq
 * @Date: 2020/4/13 19:47
 * @since：
 */
@Configuration
@Data
public class OAuth2Config {
    @Value("${wechat.client.clientId}")
    private String wechatClientId;
    @Value("${wechat.client.clientSecret}")
    private String wechatClientSecret;
//    //微信的二维码生成
//    @Value("${wechat.client.userAuthorizationUri}")
//    private String OPEN_QR_CONNECT ;

    @Value("${dingding.client.clientId}")
    private String dingdingClientId;
    @Value("${dingding.client.clientSecret}")
    private String dingdingClientSecret;
//    @Value("${dingding.client.userAuthorizationUri}")
//    private String DING_QR_CONNECT ;

    @Value("${qywechat.client.clientId}")
    private String qywechatClientId;
    @Value("${qywechat.client.clientSecret}")
    private String qywechatClientSecret;
    @Value("${qywechat.client.agentId}")
    private String qywechatAgentId;

    @Value("${feishu.client.clientId: cli_9e559a12533fd00d}")
    private String feishuClientId;
    @Value("${feishu.client.clientSecret: myg0V8SoVhj7yCnevgJHZfwkptymQO3A}")
    private String feishuClientSecret;

    public Map<String,String> getClientInfo(String oauth){
        HashMap<String, String> map = new HashMap<>();
        switch (oauth){
            case "wechat":
                map.put("clientId",wechatClientId);
                map.put("clientSecret",wechatClientSecret);
                map.put("qrUrl",ConstantsOauth.OPEN_QR_CONNECT);
                break;
            case "dingding":
                map.put("clientId",dingdingClientId);
                map.put("clientSecret",dingdingClientSecret);
                map.put("qrUrl",ConstantsOauth.DING_QR_CONNECT);
                break;
            case "qywechat":
                map.put("clientId",qywechatClientId);
                map.put("clientSecret",qywechatClientSecret);
                map.put("agentId",qywechatAgentId);
                map.put("qrUrl", ConstantsOauth.QIYE_QR_CONNECT);
//                map.put("id",supportId);
                break;
            case "feishu":
                map.put("clientId",feishuClientId);
                map.put("clientSecret",feishuClientSecret);
                map.put("qrUrl",ConstantsOauth.FEISHU_QR_CONNECT);
//                map.put("id",supportId);
                break;
        }
        return map;
    }
}
