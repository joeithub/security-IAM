package com.security.common;

public class Constants {

    private Constants() {
    }

    public final static String OK = "OK";
    public final static String CHECK = "CHECK";
    public final static String INTERNAL_AUTH = "internal-auth";
    public final static String SINGLE_ACCESS = "single-access";
    public final static String AUTHORIZATION = "authorization";
    public final static String ENS_CONTROLLER = "ens";
    public final static String ADMIN_LOGIN = "管理端";
    public final static String CLIENT_LOGIN = "客户端";
    public final static String EMPTY_STRING = "";
	public final static String SECRET = "SECRET";
	public final static String VERSION = "VERSION";
	public final static String USER_SESSION = "USER_SESSION";
	public final static String LOGIN_USER = "LOGIN_USER";
    public final static String LOGIN_USER_ID = "LOGIN_USER_ID";
    public final static String ADMIN_SESSION = "ADMIN_SESSION";
    public final static String INVALID_CODE = "INVALID_CODE";
    public final static String NULL_MSG = "对象（%s） %s 必须为空";
    public final static String NOT_NULL_MSG = "对象（%s） %s 不能为空";
    public final static String NOT_EXISTS = "对象（%s）不存在";
    public final static String IS_EXISTS = "对象（%s）已存在";
    public final static String ILLEGAL_PARAM = "参数（%s）非法";
    public final static String CANNOT_REPEAT = "%s不能重复";
    public final static String ALREADY_EXISTS = "%s已经存在";
    // 超级管理员Id
    public final static String SUPER_ADMIN_ID = "00000000-2019-0918-0000-000000000000";
    // 管理员Id
    public final static String ADMIN_ID = "00000000-2019-0918-0000-admin0000000";
    // 管理员角色Id
    public final static String ADMIN_ROLE_ID = "00000000-2019-0918-0000-admin0role00";
    // 初级管理员角色Id
    public final static String ADMIN_PRIMARY_ROLE_ID = "00000000-2019-0918-0000-adminProle00";
    /**
     * enlink 短信服务器配置 id
     */
    public final static String ENLINK_SMS_CONF_ID = "1d7b9823fbc346c188edcf5247c3c184";
    /**
     * enlink 管理员重置密码服务 id
     */
    public final static String ENLINK_SMS_RESET_ID = "9120705d465911eaa987005056a25a28";
    /**
     * 客户自定义 短信服务器配置 id
     */
    public final static String CUSTOMER_SMS_CONF_ID = "9af45fb460d94504acd45883e4ad0995";
    /**
     * 防暴力破解二次认证
     */
    public final static String PASS_ERROR_SEC_AUTH_ID = "c730261b088343678d9fbdc9a1804577";


    public static final String SESSION_ID_NAME = "ENSADMINSESSIONID";
    public static final String CLIENT_SESSION_ID_NAME = "ENSSESSIONID";
    public static final String AUTH_SMS_KEY = "ensbrain:auth:sms:";
    public final static String ALL = "ALL";
    public final static String AUTH_STRATEGY_SERVICE_PREFIX = "authStrategy";
    /**
     * 管理后台session列表
     */
    private final static String RK_USER_AUTH_CHAIN_KEY = "ensbrain:login:chain";
    private final static String RK_LOGIN_HASH_SESSION = "ensbrain:login:session";
    private static final String STRATEGY_PASS_ERROR_KEY = "ensbrain:strategy:passError";
    private static final String STRATEGY_PASS_ERROR_REMAIN_NUM = "ensbrain:passError:remainNum";
    public final static String WECHAT_LOGIN_SESSION_KEY_PREFIX = "ensbrain:login:wechat:session:";
    public final static String WECHAT_BIND_KEY_PREFIX = "ensbrain:login:wechat:bind:";
    public static final String STRATEGY_PASS_ERROR_KEY_PATTERN = "ensbrain:strategy:passError*";
    /**
     * reids 相关
     */
    public final static String SPRING_SESSION_KEY_PATTERN = "spring:session:sessions*";
    public final static String SPRING_SESSION_KEY_PREFIX = "spring:session:sessions:";
    public final static String SPRING_SESSION_EXPIRE_KEY_PREFIX = "spring:session:sessions:expires:";
    public final static String SESSION_ATTR = "sessionAttr:";
    // Redis中网关连接状态的KEY
    public static final String GATEWAY_CONNECT_STATE = "ensbrain:gateway:connected";
    public static final String GATEWAY_CONNECT_STATE_PATTERN = "ensbrain:gateway:connected*";
    public static final String USER_GROUP_PATH_PREFIX = "ensbrain:usergroup:path:";
    // Redis中网关连接应用的状态的KEY
    public static final String GATEWAY_APP_CONNECT_STATUS = "ensbrain:gateway:app:status";
    // Redis中网关连接中
    public static final String GATEWAY_CONNECTING_STATE = "ensbrain:gateway:connecting";
    // 首次登录用户
    public static final String USER_LOGIN_FIRST_FLAG = "ensbrain:user:first:login";
    //aes加解密的key
    public static final String SECRET_AES_KEY = "ensbrain:secret:aes:key";
    // 在线用户的key
	public static final String ONLINE_USERS_PREFIX = "ensbrain:online_users:";
	// 在线session的key
	public static final String ONLINE_SESSIONS = "ensbrain:online_sessions";
	/**
	 * 在线的管理端用户session集合
	 */
	public final static String ONLINE_ADMIN_SESSIONS = "ensbrian:online_admin_sessions";

    /**
     * 扣分模型
     */
    public final static String SPRING_SESSION_KEY_DEDUCT = "ensbrain:credit:deduct:";

    /**
     * 生成redisDeductGradeKey
     *
     * @param sessionId sessionId
     * @return String
     */
    public static String generateDeductGradeKey(String sessionId) {
        return String.format("%s:%s", SPRING_SESSION_KEY_DEDUCT, sessionId);
    }

    /**
     * 生成aeskey
     *
     * @param sessionId sessionId
     * @return String
     */
    public static String generateSecretAesKey(String sessionId) {
        return String.format("%s:%s", SECRET_AES_KEY, sessionId);
    }

    /**
     * 生成redisSessionKey
     *
     * @param sessionId sessionId
     * @return String
     */
    public static String generateSessionKey(String sessionId) {
        return String.format("%s:%s", RK_LOGIN_HASH_SESSION, sessionId);
    }

    /**
     * 生成redis 密码错误策略的 Key
     *
     * @param username username
     * @return String
     */
    public static String generatePassErrorKey(String username) {
        return String.format("%s:%s", STRATEGY_PASS_ERROR_KEY, username);
    }

    public static String generateRemainNumKey(String username) {
        return String.format("%s:%s", STRATEGY_PASS_ERROR_REMAIN_NUM, username);
    }

    public static String generatePassErrorLockKey(String username) {
        return String.format("%s:%s:lock", STRATEGY_PASS_ERROR_KEY, username);
    }

    public static String generatePassErrorSecAuthKey(String username) {
        return String.format("%s:%s:secAuth", STRATEGY_PASS_ERROR_KEY, username);
    }

    public static String generateAuthChainNodeKey(String userId){
        return String.format("%s:%s", RK_USER_AUTH_CHAIN_KEY, userId);
    }

    /**
     * 配置的key
     */
    public static class ConfKeys {

        private ConfKeys() {
        }
        /**
         * 网关侧
         */
        public final static String GATEWAY_SPA_KEY = "spa.access.key";
        public final static String GATEWAY_SPA_ENABLE = "spa.enable";
    }

    public static class Number {
        private Number() {
        }

        public final static int NUM_0 = 0;
        public final static int NUM_1 = 1;
        public final static int NUM_2 = 2;
        public final static int NUM_8 = 8;
        public final static int NUM_50 = 50;

        public final static int NUM_200 = 200;
        public final static int NUM_400 = 400;
        public final static int NUM_404 = 404;
    }

    /**
     * 数字字符串
     */
    public static class NumberString {

        private NumberString() {
        }
        public final static String NUM_0 = "0";
        public final static String NUM_1 = "1";
        public final static String NUM_2 = "2";
        public final static String NUM_3 = "3";

        public final static String NUM_01 = "01";
        public final static String NUM_02 = "02";
        public final static String NUM_03 = "03";
        public final static String NUM_04 = "04";
        public final static String NUM_05 = "05";
        public final static String NUM_06 = "06";
        public final static String NUM_07 = "07";
        public final static String NUM_08 = "08";
        public final static String NUM_09 = "09";
        public final static String NUM_10 = "10";
        public final static String NUM_11 = "11";
        public final static String NUM_50 = "50";
        public final static String NUM_51 = "51";
        public final static String NUM_52 = "52";
        public final static String NUM_99 = "99";
        public final static String NUM_200 = "200";
    }

    /**
     * 符号字符串
     */
    public static class SymbolString {
        private SymbolString() {
        }

        public final static String COMMA = ",";
        public final static String LEFT_BRACKET = "(";
        public final static String RIGHT_BRACKET = ")";
        public final static String SINGLE_QUOTE = "'";
        public final static String DOT = ".";
        public final static String LEFT_SLASH = "/";
        public final static String SEMICOLON = ";";
    }

    /**
     * 符号字符串
     */
    public static class UploadFileType {
        private UploadFileType() {
        }

        public final static String ICON = "icon";
        public final static String IMPORT = "import";
        public final static String EXPORT = "export";
    }

    /**
     * 用户组相关常量
     */
    public static class UserGroup {
        public final static String ROOT_GROUP_ID = "0"; // 根用户组ID
        public final static String ROOT_GROUP_NAME = "用户组树"; // 根用户组名称
        public final static String DEFAULT_LOCAL_AUTH_GROUP_ID = "1"; // 本地默认用户组ID
        public final static Integer LOCAL_AUTH_TYPE = 0; // 本地认证方式
        public final static Integer THIRD_PARTY_AUTH_TYPE = 1; // 第三方认证方式
    }

    /**
     * 应用分组相关常量
     */
    public static class ServiceGroup {
        public final static String ROOT_GROUP_ID = "0"; // 根用户组ID
        public final static String ROOT_GROUP_NAME = "应用分组树"; // 根用户组名称
    }

    public static class EtcdKey {
        public final static String COMMON_SPA_STATUS_KEY = "##default##default##common##sys.spa.status"; // spa 状态
        public final static String COMMON_SPA_PRE_SHARE_KEY = "##default##default##common##sys.spa.access.key"; // spa 预共享密钥状态
        public final static String COMMON_SYS_PASS_HISTORY_NUM_KEY = "##default##default##common##sys.pass_history_num"; // 系统历史密码校验次数
        public final static String COMMON_SYS_SESSION_ACTIVE_TIME_KEY = "##default##default##common##sys.session.active.time"; // 活跃时间key
    }

    public static class EtcdConstants {
        public static final String SEP = "##";
        public static final String COMMON = "common";
        public static final String DEFAULT_LEASE_NODE = SEP + "default" + SEP + "default" + SEP;
        public static final String REGEX_MATCH = "(.*)" + SEP + "(.*)" + SEP + "(.*)";
        public static final String REGEX_REPLACE = SEP + ".*" + SEP;
    }

    public static class DeviceType {
        public static final String IOS = "iOS";
        public static final String ANDROID = "Android";
        public static final String ENAGENT = "EnAgent";
        public static final String PC = "pc";
    }
}
