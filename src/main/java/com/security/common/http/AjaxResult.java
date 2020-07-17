/* 文件名: AjaxResult.java
 *
 * 作者: 常官清 (changguanqing@enlink.cn)
 * 描述: 本文件定义了Ajax请求的响应格式，及成功和失败时的默认方法。
 *
 * Copyright @2018 Enlink, All Rights Reserved.
 */
package com.security.common.http;


import com.security.common.utils.GsonUtils;
import com.security.common.utils.StringCommonUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AjaxResult - Ajax请求响应结果的格式
 */
@Data
public class AjaxResult {
    private String code;
    private String messages;
    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(String code, String messages, Object data) {
        this.code = code;
        this.messages = messages;
        this.data = data;
    }

    public AjaxResult(HttpCode httpCode, Object data) {
        this.code = httpCode.code();
        this.messages = httpCode.desc();
        this.data = data;
    }

    /**
     * ok - Http请求正常执行时，默认的返回结果
     *
     * @param {Object} data
     * @return {AjaxResult} 返回对象本身
     */
    public static AjaxResult ok() {
        return new AjaxResult(HttpCode.OK.code(), HttpCode.OK.desc(), null);
    }

    /**
     * ok - Http请求正常执行时，默认的返回结果
     *
     * @param {Object} data
     * @return {AjaxResult} 返回对象本身
     */
    public static AjaxResult ok(Object data) {
        return new AjaxResult(HttpCode.OK.code(), HttpCode.OK.desc(), data);
    }

    /**
     * errorOf - Http请求异常时，默认的返回结果
     *
     * @param {HttpCode} httpCode
     * @param {String}   messages
     * @return {AjaxResult} 返回对象本身
     */
    public static AjaxResult errorOf(String messages) {
        return new AjaxResult(HttpCode.ERROR.code(),
                StringCommonUtils.isNullOrEmpty(messages) ? HttpCode.ERROR.desc() : messages, null);
    }

    public static AjaxResult logicError(String messages) {
        return new AjaxResult(HttpCode.INTERNAL_LOGIC_ERROR.code(),
                StringCommonUtils.isNullOrEmpty(messages) ? HttpCode.INTERNAL_LOGIC_ERROR.desc() : messages, null);
    }

    public <T> T dataToBean(Class<T> tClass) {
        if (Objects.nonNull(getData())) {
            return GsonUtils.convertToBean(GsonUtils.convertToString(getData()), tClass);
        }
        return null;
    }

    public <T> Map<String, T> dataToMap() {
        if (Objects.nonNull(getData())) {
            return GsonUtils.jsonToMaps(GsonUtils.convertToString(getData()));
        }
        return null;
    }

    public <T> List<T> dataToList(Class<T> tClass) {
        if (Objects.nonNull(getData())) {
            return GsonUtils.jsonToList(GsonUtils.convertToString(getData()), tClass);
        }
        return null;
    }

    public <T> List<Map<String, T>> dataToListMap(Class<T> tClass) {
        if (Objects.nonNull(getData())) {
            return GsonUtils.jsonToListMaps(GsonUtils.convertToString(getData()));
        }
        return null;
    }

}
