/* 文件名: HttpCode.java
 *
 * 作者: 常官清 (changguanqing@enlink.cn)
 * 描述: 本文件定义了Ajax请求的响应结果代码及说明
 *
 * Copyright @2018 Enlink, All Rights Reserved.
 */
package com.security.common.http;

/**
 * HttpCode - Ajax请求的响应结果代码及说明
 */
public enum HttpCode {
    /**
     * 请求正常返回
     */
    OK("200", "OK"),
    /**
     * 请求异常返回
     */
    ERROR("500", "系统内部错误"),
    /**
     * 1XXX 用户登录与SESSION相关的报错
     */
    ERR_UN_AUTH("1000", "用户未登录或会话过期!"),
    ERR_FORCE_OFFLINE("1001", "该账号被管理员踢下线，请重新登录!"),
    ERR_SESSION_EXPIRATION("1002", "登录会话已过期，请重新登录!"),
    ERR_LOGIN_SOMEWHERE("1003", "该账号在其他地方登录，您已被强制下线!"),
    USER_DISABLED("1004", "账号被锁定，请联系管理员处理！"),
    SESSION_INVALID("1005", "用户SESSION已过期"),
    USER_DELETED("1006", "该账号已被删除！"),
    FORBIDDEN_LOGIN("1007", "该账号禁止登录！"),
    BEYOND_MAX_LOGIN_NUM("1008", "该账号已在其他地方登录！"),
    FORBIDDEN_LOGIN_ADMIN("1009", "您没有管理员权限！"),
    FORBIDDEN_LOGIN_CLIENT("1010", "用户名或密码错误！"),
    LOGIN_EXCEPTION("1011", "登录异常！"),
    USER_SESSION_NOT_MATCH("1012", "用户账号与Session不匹配!"),
    FORBIDDEN_LOGIN_NOT_ADMIN("1013", "管理员身份已失效!"),
    FORBIDDEN_LOGIN_IP_NOT_ALLOWED("1014", "该IP不允许登录管理端!请从指定IP登录"),
    FORBIDDEN_LOGIN_IP_NOT_VALID("1015", "该IP已不允许登录管理端!请从指定IP登录"),
    WECHAT_LOGIN_FAILED("1016", "微信登录失败！"),
	USER_CHANGE_PASSWORD("1017", "密码已修改，请重新登录！"),

    /**
     * 2XXX 正常报错返回
     */
    ERR_PARAMS_INVALID("2000", "参数非法"),
    USERNAME_OR_PASSWORD_INVALID("2001", "用户名或密码错误"),
    USERNAME_NOT_EXISTS("2002", "用户不存在!"),
    CONNECTION_FAILED("2003", "服务器配置信息错误"),
    CODE_INVALID("2004", "验证码错误"),
    NO_PERMISSION("2005", "该用户无权限"),
    AUTH_SERVER_ERROR("2006", "认证服务器异常"),
    USER_NO_MOBILE("2007", "该账号未绑定手机号"),
    MOBILE_NOT_MATCH("2008", "输入的手机号与绑定手机号不一致！"),
    AUTH_TYPE_BINDING_USER_GROUP("2009", "所选认证服务器已绑定用户组!请先删除 "),
    AUTH_TYPE_NOT_BIND_USER_GROUP("2010", "请先给此认证服务器绑定用户组！"),
    AUTH_SERVER_NOT_WORK("2011", "认证服务器无法连接或者配置错误"),
    WECHAT_BIND_FAILED("2012", "微信绑定异常！"),
    WECHAT_CONNECT_FAILED("2013", "微信接口调用错误!请稍后重试"),
    USER_CAN_NOT_MODIFY_PASSWORD("2014", "第三方认证用户，不支持修改密码！请联系管理员!"),
    UNKNOW_HOST("2015", "未知的主机或服务!"),
    INTERNAL_LOGIC_ERROR("2016", "内部逻辑错误！"),
    SESSION_TIMEOUT_ERROR("2017", "会话过期时间不合法"),
    DECRYPT_PASSWORD_ERROR("2018", "密码解密错误！"),
    STRONG_PASS_ERROR("2019", "不符合强密码校验规则！"),
    PASS_HISTORY_NUM_ERROR("2020", "配置错误，历史密码次数在3~10之间!"),

    LICENSE_FILE_IS_NULL_ERROR("2021", "License文件不能为空！"),
    LICENSE_VERIFY_ERROR("2022", "License校验失败！硬件特征码不匹配！"),
    LICENSE_NOT_ACTIVATED("2023", "License未激活！"),
    LICENSE_EXPIRED_ERROR("2024", "3个月试用期已过，请与管理员联系！"),
    LICENSE_ONLINE_USERS_ERROR("2025", "登录在线用户数已经达到最大值，请与管理员联系！"),
    LICENSE_APPS_ERROR("2026", "配置的应用数，超过支持发布的最大应用数: {0}，请与系统提供商联系"),
    LICENSE_EXPIRED_BEFORE_ERROR("2027", "License已激活未生效，请与管理员联系！"),
    ACTIVE_TIME_ERROR("2028", "活跃用户时间不合法"),
	/**不存在用户登录时，提示用户名密码错误但不走容错策略，与真正的用户名密码错误区分开*/
	USERNAME_NOT_EXISTS_NEW("2029", "用户名或密码错误!"),
	LICENSE_CONTENT_VERIFY_ERROR("2030", "License校验失败！License已过期！"),

    /**
     * 3XXX 与网关交互的报错
     */
    SESSION_SYNCHRONIZED_FAILED("3000", "session同步网关失败"),
    NO_MSG_TO_SYNCHRONIZED("3001", "没有消息需要同步网关"),

    /**
     * 4XXX 模块管理异常
     */
    MODULE_NOT_START("4000", "模块{0}未正常启动，请联系管理员！"),
    ;

    private String code;
    private String desc;

    HttpCode(String c, String d) {
        this.code = c;
        this.desc = d;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
