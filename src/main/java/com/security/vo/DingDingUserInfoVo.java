package com.security.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: tongq
 * @Date: 2020/4/17 15:59
 * @since：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DingDingUserInfoVo {
     private String errcode;
     private String errmsg;
     private String unionid;
     private String openid;
     private String persistent_code;
     private String sns_token;
     private String expires_in;
     private DingUserInfo user_info;

    @Data
    public static class DingUserInfo {
        private String nick;
        private String unionid;
        private String dingId;
        private String openid;
    }
}
