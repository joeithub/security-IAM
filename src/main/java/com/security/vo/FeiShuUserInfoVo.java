package com.security.vo;

import lombok.Data;

/**
 * @Author: tongq
 * @Date: 2020/7/17 14:41
 * @since：0.0.1
 */
@Data
public class FeiShuUserInfoVo {
//    private  String qrType = "04";
    private  String access_token;
    private  String avatar_url;
    private  String	avatar_thumb;
    private  String	avatar_middle;
    private  String	avatar_big;
    private  String	expires_in;
    private  String	name;
    private  String	en_name;
    private  String	open_id;
    private  String	tenant_key;
    private  String	refresh_expires_in;
    private  String	refresh_token;
    private  String	token_type;
}
