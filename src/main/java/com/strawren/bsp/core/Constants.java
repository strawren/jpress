/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-4 下午03:52:49
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-4        Initailized
 */

package com.strawren.bsp.core;


/**
 * 常量定义
 *
 */
public interface Constants {
    /**
     * ? 符号
     */
    public static final String QUESTION_SIGN_SPLIT_NAME =           "?";
    /**
     * @ 符号
     */
    public static final String AT_SIGN_SPLIT_NAME =                 "@";
    /**
     * , 符号
     */
    public static final String COMMA_SIGN_SPLIT_NAME =              ",";
    /**
     * - 符号
     */
    public static final String LINE_SIGN_SPLIT_NAME =               "-";
    /**
     * 下划线
     */
    public static final String UNDERLINE_SIGN_SPLIT_NAME =          "_";
    /**
     * DOT
     */
    public static final String DOT_SIGN_SPLIT_NAME =                ".";
    /**
     * semicolon
     */
    public static final String SEMICOLON_SIGN_SPLIT_NAME =         ";";
    /**
     * COLON
     */
    public static final String COLON_SIGN_SPLIT_NAME =              ":";
    /**
     * Long -1
     */
    public static final long GLOBAL_SYSTEM_OPER_ID =                -1;
    /**
     * 全局数据最长长度：int 1000
     */
    public static final int GLOBAL_DATA_MAX_LENGTH =               1000;

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET_NAME =               "UTF-8";
    /**
     * 默认报文接口编码
     */
    public static final String DEFAULT_PROTOCOL_CHARSET_NAME =      "GBK";
    /**
     * 报文接口类型--WEBSERVICE
     */
    public static final String MSG_INTERFACE_TYPE_WEBSERVICE =      "WEBSERVICE";
    /**
     * 报文接口类型--socket
     */
    public static final String MSG_INTERFACE_TYPE_SOCKET =          "SOCKET";
    /**
     * 报文接口类型--hessian
     */
    public static final String MSG_INTERFACE_TYPE_HESSIAN =         "HESSIAN";

    /**
     * 默认的时间格式
     */
    public static final String DEFUALT_SHOT_TIME_FORMAT =           "yyyy-MM-dd";
    public static final String DEFUALT_LONG_TIME_FORMAT =           "yyyy-MM-dd HH:mm:ss";
    public static final String ORACLE_LONG_TIME_FORMAT =            "yyyy-MM-dd hh24:mi:ss";
    public static final String NO_SPLIT_SHOT_TIME_FORMAT =          "yyyyMMdd";
    public static final String STAMP_TIME_FORMAT =                  "yyyyMMddHHmmss";
    public static final String MONTH_TIME_FORMAT =                  "yyyyMM";

    /**
     * xml文件结尾
     */
    public static final String XML_CONF_NAME_EXT =                  ".xml";
    /**
     * 配置名称
     */
    public static final String EXCLUDE_CONFIG_NAME =                "exclude";
    /**
     * 系统属性文件名
     */
    public static final String SYSTEM_PROPERTY_FILENAME =           "system.properties";
    /**
     * 系统代号属性KEY
     */
    public static final String SYSTEM_PROPERTY_APPCODE_KEY =        "sys.app.code";

    /**
     * cache管理器bean名称
     */
    public static final String CACHE_MANAGER_BEAN_NAME =            "oecsCacheManageService";
    /**
     * AccessDecisionManager
     */
    public static final String ACCESS_DECISION_BEAN_NAME =          "accessDecisionService";
    /**
     * AccessDecisionManager
     */
    public static final String AUDIT_LOG_BEAN_NAME =                "auditLogService";
    /**
     * AccessDecisionManager
     */
    public static final String DICT_CACHE_SERVICE_BEAN_NAME =       "oecsDictCacheService";
    /**
     * TerminologyCacheService
     */
    public static final String TERM_CACHE_SERVICE_BEAN_NAME =       "terminologyCacheService";
    /**
     * DictConvertCacheService
     */
    public static final String DICT_CONVERT_CACHE_SERVICE_BEAN_NAME ="dictConvertCacheService";

    /**
     * PAGEINFO_IN_REQUEST
     */
    public static final String PAGEINFO_IN_REQUEST =                "pageinfo_in_request";
    /**
     * 默认页数 20
     */
    public static final int DEFAULT_PAGE_SIZE =                     20;

    /**
     * 全局数据字典 ：有效
     */
    public final String DICT_GLOBAL_STATUS_VALIDATE =               "V";
    /**
     * 全局数据字典 ：无效
     */
    public final String DICT_GLOBAL_STATUS_INVALIDATE =             "I";

    /**
     * 全局数据字典：是
     */
    public final String DICT_GLOBAL_YESNO_YES =                     "Y";
    /**
     * 全局数据字典：否
     */
    public final String DICT_GLOBAL_YESNO_NO =                      "N";

    /**
     * 全局处理状态P   处理中
     */
    public final String DICT_GLOBAL_RESULT_PROCESSING =             "P";
    /**
     * 全局处理状态F   失败
     */
    public final String DICT_GLOBAL_RESULT_FAIL =                   "F";
    /**
     * 全局处理状态S   成功
     */
    public final String DICT_GLOBAL_RESULT_SUCCESS =                "S";
    /**
     * 全局处理状态U   不能处理
     */
    public final String DICT_GLOBAL_RESULT_UNPROCESS =              "U";

    /**
     * 随机数
     */
    public static final String SPRING_SECURITY_RANDS_IN_SESSION =   "KAPTCHA_SESSION_KEY"; //"j_spring_rands_in_session";
    public static final String SPRING_SECURITY_FORM_VCODE_KEY =     "j_validatecode";
    public static final String SPRING_SECURITY_CHECK_NAME =         "j_spring_security_check";
    /**
     * token
     */
    public static final String OECS_TOKEN_CHECK_NAME_IN_SESSION =   "oecs_token_check_in_session";
    public static final String OECS_TOKEN_CHECK_NAME =              "o_token_check";
    public static final String OECS_TOKEN_ERR_BACK_URL_NAME =       "o_token_err_url";

    /**
     * 首页是否已经做过初始化
     */
    public static final String ACTION_INDEX_INIT_IN_SESSION =       "index_init_in_session";
    /**
     * 当前登录用户
     */
    public static final String CUR_USER_DETAIL_IN_SESSION =         "cur_user_detail_in_session";
    /**
     * 当前登录用户该系统下的菜单树信息
     */
    public static final String CUR_USER_ALL_MENU_IN_SESSION =       "cur_user_all_menu_in_session";
    /**
     * 用户快捷菜单
     */
    public static final String CUR_USER_SHORTCUT_IN_SESSION =       "cur_user_shortcut_in_session";

    /**
     * 当前应用信息信息
     */
    public static final String CUR_APP_INFO_IN_REQUEST =            "cur_app_info_in_request";
    /**
     * 当前用户应用信息
     */
    public static final String CUR_USER_APPS_IN_REQUEST =           "cur_user_apps_in_request";
    /**
     * 当前系统菜单信息
     */
    public static final String CUR_APP_MENUS_IN_REQUEST =           "cur_app_menus_in_request";
    /**
     * 当前菜单树
     */
    public static final String CUR_MENU_TREE_IN_REQUEST =           "cur_menu_tree_in_request";
    /**
     * 用户菜单树FOR RENDER
     */
    public static final String MENU_TREE_FOR_RENDER_IN_REQUEST =    "menu_tree_for_render_in_request";

    /**
     * 是否重新初始化首页参数
     */
    public static final String REINIT_INDEX_PARAMETER_NAME =        "reInit";

    /**
     * 是否重新初始化首页参数
     */
    public static final String FAVORITE_PARAMETER_NAME =            "favorite";

    /**
     * hessian客户端调用者信息in hessian context
     */
    public static final String HESSIAN_CLIENT_INFO_IN_HESSIAN =     "hessian_client_info_in_hessian";

    /**
     * 资源类型    SET_AP_RESOURCE_TYPE 应用
     */
    public static final String SET_AP_RESOURCE_TYPE_APP =           "APP";
    /**
     * 资源类型 菜单
     */
    public static final String SET_AP_RESOURCE_TYPE_MEU =           "MEU";
    /**
     * 资源类型 菜单树
     */
    public static final String SET_AP_RESOURCE_TYPE_MET =           "MET";
    /**
     * 资源类型 按钮
     */
    public static final String SET_AP_RESOURCE_TYPE_BTN =           "BTN";
    /**
     * 资源类型 URL
     */
    public static final String SET_AP_RESOURCE_TYPE_URL =           "URL";
    /**
     * 资源类型 片段
     */
    public static final String SET_AP_RESOURCE_TYPE_FRG =           "FRG";

    /**
     * 授权目标类型 操作员
     */
    public static final String SET_AP_PERMISSION_TARGET_TYPE_OPER = "OPER";
    /**
     * 授权目标类型 角色
     */
    public static final String SET_AP_PERMISSION_TARGET_TYPE_ROLE = "ROLE";

    /**
     * 授权资源类型 资源
     */
    public static final String SET_AP_PERMISSION_RES_TYPE_RES =     "RES";
    /**
     * 授权资源类型 资源组
     */
    public static final String SET_AP_PERMISSION_RES_TYPE_REG =     "REG";

    /**
     * 服务器状态 正常
     */
    public static final String SVR_STATE_NORMAL =                   "NORM";
    /**
     * 服务器状态 停止
     */
    public static final String SVR_STATE_STOP =                     "STOP";
    /**
     * 服务器状态 准备停止
     */
    public static final String SVR_STATE_PRE_STOO =                 "PSTP";
    /**
     * 服务器状态 日切中
     */
    public static final String SVR_STATE_DAUT =                     "DAUT";

    /**
     * 没有可用的接口池
     */
    public static final String SWITCH_NO_ACTIVE_POOL =              "switch.no.active.pool";
    /**
     * 默认异常代号信息
     */
    public static final String DEFAULT_ERR_MSG =                    "sys.unkown.err.msg";

    /**
     * 默认四位的异常代码
     */
    public static final String DEFAULT_ERR_CODE =                   "9999";
    /**
     * 超时异常代码
     */
    public static final String TIMOUT_ERR_CODE =                    "9998";
    /**
     * 默认2位的异常代号
     */
    public static final String DEFAULT_THIRD_PART_ERR_CODE =        "99";
    /**
     * 默认2位的返回代号
     */
    public static final String DEFAULT_RESPONSE_CODE =              "00";

    /**
     * 默认的字段描述
     */
    public static final String DEFAULT_DB_COLUMN_VALUE =            "N/A";

    /**
     * 刷新cache和数据库配置的key参数
     */
    public static final String SYSTEM_REFRESH_PARAM_KEY = "sys.refresh.param.key";

    /**
     * 后台管理系统缓存刷新列表代号
     */
    public static final String SERVERS_CACHE_LIST_CODE = "servers.cache.list";

    /**
     * 全局执行状态 发布
     */
    public final String SET_GC_EXECUTE_STATUS_PUB =             "PUB";
    /**
     * 全局执行状态 草稿
     */
    public final String SET_GC_EXECUTE_STATUS_DRA =             "DRA";
    /**
     * 全局执行状态 作废
     */
    public final String SET_GC_EXECUTE_STATUS_DIS =             "DIS";

    /**
     * 帐单应答错误
     */
    public static final String BILLING_RESPONSE_ERROR = "billing.ocn.response.msg.err";
    /**
     * 第三方超时异常码
     */
    public static final String TIMEOUT_EXCEPTION_CODE = "0010";

    /**
     * 单点登录，判断重复登录缓存KEY
     */
    public static final String SSO_CA_USER_LOGINED_CACHE = "sso_ca_user_logined_";

    /**
     * 单点登录，缓存服务器地址
     */
    public static final String SSO_CA_MEMCACHE_SERVERS_ADDRESS = "sso.memcache.servers";
    
    public static final String JP_GLOBAL_SYS_INFO_APP_CODE_KEY = "appCode";
    public static final String JP_GLOBAL_SYS_INFO_APP_NAME_KEY = "appName";
    public static final String JP_GLOBAL_SYS_INFO_APP_VERSION_KEY = "appVersion";
    public static final String JP_GLOBAL_SYS_INFO_ROOT_PATH_KEY = "rootPath";
    public static final String JP_GLOBAL_SYS_INFO_ADMIN_PATH_KEY = "adminPath";
}
