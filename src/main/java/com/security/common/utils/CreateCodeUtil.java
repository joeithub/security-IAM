package com.security.common.utils;

/**
 * @Author: tongq
 * @Date: 2020/3/12 19:50
 * @since：
 */
public class CreateCodeUtil {
    public static int getCode(){
        return (int) Math.ceil(Math.random() * 90000 + 1000);
    }
}
