package com.strawren.jpress.core;

public interface JpressConstants {
    //系统appcode
    public static final String SYSTEM_APP_CODE = "sys.app.code";
    //获取机顶盒mac参数
    public static final String GET_STB_MAC_PARAM = "mac";
    //机顶盒mac在session中
    public static final String MAC_IN_SESSION_ATTR = "mac_in_session";
    
    //认证系统url
    public static final String TV_AUTHS_URL = "tv.auths.url";
    //请求认证系统时的加签mac
    public static final String TV_AUTHS_MAC_KEY = "tv.auths.mac.key";
    //用户信息
    public static final String TV_AUTHS_USER_INFO = "tv.auths.user.info";
    
    //润有下单请求url
    public static final String ORDER_INTENTION_REQ_URL = "order.intention.req.url";
    //润有下单appid
    public static final String ORDER_INTENTION_APPID = "order.intention.appid";
    //润有下单MD5加签appsecurity
    public static final String ORDER_INTENTION_APP_SECURITY = "order.intention.appsecurity";
    
    //视频请求url
    public static final String VOD_REQ_BAMS_URL = "vod.req.bams.url";
    
    //系统是否打开测试mac：0关闭，1打开
    public static final String SYSTEM_OPEN_TEST_MAC = "system.open.test.mac";

	//OPTION系统的参数
	//系统核心
	//是否需要进行审核标志，值为true/false
	public static final String OPTIONS_KEY_SYS_CORE_POST_CHK = "sys_core_post_chk";

	//用户核心
	//用户的角色 admin/super super具有审核功能
	public static final String OPTIONS_KEY_USER_CORE_ROLE = "user_core_role";

	//随机码放到session中的key
	public static final String RANDOM_CODE_KEY = "random_validate_code_key";

	//站点核心
	//站点的URL
	public static final String OPTIONS_KEY_SITE_CORE_URL = "site_core_url";
	//站点的名称
	public static final String OPTIONS_KEY_SITE_CORE_NAME = "site_core_name";
	//站点描述
	public static final String OPTIONS_KEY_SITE_CORE_DESC = "site_core_desc";
	//站点管理员的邮箱，用于忘记密码找回等
	public static final String OPTIONS_KEY_SITE_CORE_EMAIL = "site_core_email";
	//站点主题CODE
	public static final String OPTIONS_KEY_SITE_THEME_CODE = "site_core_theme";
	//用户级别KEY
    public static final String OPTIONS_KEY_SITE_USER_LEVEL = "site_user_level";
    //默认用户级别
    public static final String USER_DEFAULT_LEVEL = "普通用户";

	//模板根目录（各主题的上一级）
	public static final String OPTIONS_KEY_SITE_TEMPLATE_URL = "site.template.url";

	//数据字典
	//文章状态
	public static final String DICT_POST_STATUS_PUBLISH = "pub";    //发布
	public static final String DICT_POST_STATUS_INHERIT = "inh";    //继承，用于版本管理
	public static final String DICT_POST_STATUS_DRAFT =   "drt";    //草稿
	public static final String DICT_POST_STATUS_WAITING = "wai";    //待审核
	//文章类型
	public static final String DICT_POST_TYPE_POST =   		"post";    //文章
	public static final String DICT_POST_TYPE_PAGE =   		"page";    //页面
	public static final String DICT_POST_TYPE_REVISION =   	"revn";    //版本
	public static final String DICT_POST_TYPE_ATTACHMENT =  "atth";    //附件
	public static final String DICT_POST_TYPE_MENU_NAV =   	"mnav";    //菜单导航
	public static final String DICT_POST_TYPE_LINK =        "link";    //链接
	//通用状态
	public static final String DICT_GLOBAL_STATUS_INVLIDATE =   "I";    //无效
	public static final String DICT_GLOBAL_STATUS_VALIDATE =  	"V";    //有效
	//通用状态
	public static final String DICT_GLOBAL_BIZ_STATUS_OPEN =   "open";    //开放
	public static final String DICT_GLOBAL_BIZ_STATUS_CLOSE =  "clse";    //关闭
	//目录分类方法
	public static final String DICT_TERM_TAXONOMY_CATEGORY =   "cat";    //栏目
	public static final String DICT_TERM_TAXONOMY_NAV_MENU =   "nav";    //导航
	public static final String DICT_TERM_TAXONOMY_LINK =   	   "lnk";    //链接
	public static final String DICT_TERM_TAXONOMY_TAG =   	   "tag";    //TAG
	public static final String DICT_TERM_TAXONOMY_COMMONMETA =       "meta";    //TAG

	//父目录为空显示文本
	public static final String NO_PARENT_TEXT = "无父级";

	//分类目录属性类型
	public static final String METE_TYPE_TERM = "term";            //分类目录
	public static final String METE_TYPE_POST = "post";            //POST

	//菜单指向类型的key
	public static final String MENU_POINT_TYPE_KEY =    "target_menu_object_type";
	//菜单指向的类型
    public static final String MENU_PONIT_TYPE_TERM =   "term";      //term类型
    public static final String MENU_PONIT_TYPE_LINK =   "link";      //link类型
    public static final String MENU_PONIT_TYPE_PAGE =   "page";      //page类型
    public static final String MENU_PONIT_TYPE_TAG  =   "tag";       //tag类型

	//POST META的key，菜单目标对象id
	public static final String POST_META_TAR_MENU_OBJ_ID =     "target_menu_object_id";
	//POST META的key，菜单父对象id(菜单的父id，逻辑父子)
	public static final String POST_META_TAR_MENU_PARENT_ID =  "target_menu_item_parent_id";
	//POST META的key，post访问的url(比如link类post的url)
	public static final String POST_META_POST_VISIT_URL =      "post_visit_url";

	//定义系统Key-特色图片
	public static final String KEY_PRCTURE_FEATURE_PERFIX = "sys_feature_prcture";

	//定义系统Key-特色图片(激活)
    public static final String KEY_PRCTURE_FEATURE_ACTIVE = "sys_feature_prcture_active";

	//特色图片name
	public static final String PRCTURE_FEATURE_TEXT = "商品图片路径";

	//公用属性的分类目录别名
	public static final String SYS_COMMON_META_TERM_SLUG = "sys_common_term_slug";

	public static final String SYS_POST_IMAGE_PATH_KEY = "sys_item_image_path";


}
