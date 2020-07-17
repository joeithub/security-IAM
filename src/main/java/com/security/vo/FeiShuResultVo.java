package com.security.vo;

import lombok.Data;

/**
 * @Author: tongq
 * @Date: 2020/7/17 14:40
 * @since：0.0.1
 */
@Data
public class FeiShuResultVo {
    private String code;
    private String msg;
    private FeiShuUserInfoVo data;
}
