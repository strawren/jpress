/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/6/23 14:13:39                           */
/*==============================================================*/


drop table if exists CMS_COMMENT;

drop table if exists CMS_COMMENT_META;

drop table if exists CMS_LINK;

drop table if exists CMS_OPTION;

drop table if exists CMS_POST;

drop table if exists CMS_POST_META;

drop index INDEX_TERM_SLUG on CMS_TERM;

drop table if exists CMS_TERM;

drop table if exists CMS_TERM_META;

drop table if exists CMS_TERM_RELATIONSHIP;

drop table if exists CMS_TERM_TAXONOMY;

drop table if exists CMS_USER;

drop table if exists CMS_USER_META;

drop table if exists LOG_DB_OP;

drop table if exists LOG_EXCEPTION;

drop table if exists LOG_LOGIN;

drop table if exists LOG_PROCESS;

drop table if exists LOG_REQUEST;

/*==============================================================*/
/* Table: CMS_COMMENT                                           */
/*==============================================================*/
create table CMS_COMMENT
(
   ID                   int not null auto_increment,
   POST_ID              int comment '如果POST_ID为0,则表示为留言',
   AUTHOR_NAME          text comment '评论人的名称',
   AUTHOR_EMAIL         text comment '评论人的email',
   AUTHOR_URL           text comment '评论人的网站URL',
   AUTHOR_IP            text comment '评论人的IP',
   COMMENT_DATE         datetime comment '评论时间',
   CONTENT              text comment '评论人的内容',
   COMMENT_KARMA        int comment '未知、预留',
   APPROVED_FLAG        text comment '是否已经审核了',
   AUTHOR_AGENT         text comment '评论人的浏览器',
   COMMENT_TYPE         text comment '评论类型(pingback/普通)',
   PARENT_ID            int comment '评论的上级，无上下级的则填0',
   USER_ID              int comment '评论者用户ID（不一定存在）',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_COMMENT comment '评论';

/*==============================================================*/
/* Table: CMS_COMMENT_META                                      */
/*==============================================================*/
create table CMS_COMMENT_META
(
   ID                   int not null auto_increment,
   COMMENT_ID           int,
   JKEY                 text,
   VALUE                text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_COMMENT_META comment '评论的元数据或者属性';

/*==============================================================*/
/* Table: CMS_LINK                                              */
/*==============================================================*/
create table CMS_LINK
(
   ID                   int not null auto_increment,
   URL                  text,
   NAME                 text,
   IMAGE                text,
   TARGET               text,
   ALT                  text comment '提示信息',
   VISIBLE              text,
   OWNER_ID             int comment '拥有者',
   RATING               int comment '评级',
   REL                  text comment 'XFN关系',
   NOTES                text comment 'XFN备注',
   RSS                  text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_LINK comment '链接';

/*==============================================================*/
/* Table: CMS_OPTION                                            */
/*==============================================================*/
create table CMS_OPTION
(
   ID                   int not null auto_increment,
   CODE                 text,
   CATGORY_CODE         text comment '分类代号：比如邮箱配置，需要配置STMP\USER\PWD等，用于分组，如果没有分组，则为空即可',
   NAME                 text,
   VALUE                text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_OPTION comment '内容管理平台的属性配置';

/*==============================================================*/
/* Table: CMS_POST                                              */
/*==============================================================*/
create table CMS_POST
(
   ID                   int not null auto_increment,
   OWNER_ID             int comment '作者',
   SHOW_OWNER           text comment '显示的用户',
   SLUG                 text,
   POST_DATE            datetime comment '发布的时间',
   SHOW_DATE            datetime comment '显示的时间',
   CONTENT              text comment '内容',
   TITLE                text comment '标题',
   EXCERPT              text comment '摘要',
   POST_STATUS          text comment '内容的状态：publish/auto-draft/inherit',
   COMMENT_STATUS       text comment '评论的状态（open、close）',
   PING_STATUS          text comment 'ping的状态，close、open',
   POST_PWD             text comment '如果需要密码才能查看，则填此处的密码',
   TO_PING              text,
   PINGED               text,
   FITLERED             text comment '过滤的内容，目前不启用',
   PARENT_ID            int comment '上一级内容，主要用在page的post',
   GUID                 text comment '唯一的url',
   MENU_ORDER           int comment '如果在菜单显示，菜单的排列顺序',
   POST_TYPE            text comment '类型 post/page/attch/revision/menu/',
   MIME_TYPE            text comment 'MIME类型',
   COMMENT_COUNT        int comment '评论总数',
   SHOW_ORDER           int comment '显示的顺序',
   TOP_FLAG             text comment '是否属于置顶',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_POST comment '内容：包括page、revision、post、attachment

The core of ';

/*==============================================================*/
/* Table: CMS_POST_META                                         */
/*==============================================================*/
create table CMS_POST_META
(
   ID                   int not null auto_increment,
   POST_ID              int,
   TERM_META_ID         int,
   JKEY                 text,
   NAME                 text,
   VALUE                text,
   SHOW_ORDER           int,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_POST_META comment '内容的属性';

/*==============================================================*/
/* Table: CMS_TERM                                              */
/*==============================================================*/
create table CMS_TERM
(
   ID                   int not null auto_increment,
   NAME                 text,
   SLUG                 varchar(200),
   JGROUP               varchar(200),
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM comment '目录、标签
The categories for both posts and links and the ';

/*==============================================================*/
/* Index: INDEX_TERM_SLUG                                       */
/*==============================================================*/
create unique index INDEX_TERM_SLUG on CMS_TERM
(
   SLUG
);

/*==============================================================*/
/* Table: CMS_TERM_META                                         */
/*==============================================================*/
create table CMS_TERM_META
(
   ID                   int not null auto_increment,
   TERM_ID              int,
   META_TYPE            text comment '属性类型：term/post',
   JKEY                 text,
   NAME                 text comment '显示的名称',
   VALUE_TYPE           text comment '值类型，针对POST才有效，比如文件，数字，日期，文本等',
   VALUE_FORMAT         text comment '值的格式',
   VALUE                text comment '如果type为post，则不需要填写此值',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_META comment '目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。
                                  ';

/*==============================================================*/
/* Table: CMS_TERM_RELATIONSHIP                                 */
/*==============================================================*/
create table CMS_TERM_RELATIONSHIP
(
   ID                   int not null auto_increment,
   TERM_TAXONOMY_ID     int comment '分类的方法ID',
   OBJECT_ID            int comment '对应文章ID/链接ID',
   TERM_ORDER           int comment '排序',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_RELATIONSHIP comment '存储每个文章、链接和对应分类的关系

Posts are associated with cat';

/*==============================================================*/
/* Table: CMS_TERM_TAXONOMY                                     */
/*==============================================================*/
create table CMS_TERM_TAXONOMY
(
   ID                   int not null auto_increment,
   TERM_ID              int,
   TAXONOMY             text,
   PARENT_ID            int,
   POST_COUNT           int,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_TAXONOMY comment '分类方法：存储每个目录、标签所对应的分类
taxonomy：分类方法(category/post_tag/n';

/*==============================================================*/
/* Table: CMS_USER                                              */
/*==============================================================*/
create table CMS_USER
(
   ID                   int not null auto_increment,
   LOGIN_NAME           text,
   LOGIN_PWD            text,
   NICKNAME             text,
   USER_NAME            text,
   SHOW_NAME            text,
   USER_EMAIL           text,
   USER_URL             text,
   REGISTER_DATE        datetime,
   SERIAL_KEY           text,
   USER_STATUS          text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_USER comment '用户';

/*==============================================================*/
/* Table: CMS_USER_META                                         */
/*==============================================================*/
create table CMS_USER_META
(
   ID                   int not null auto_increment,
   USER_ID              int,
   JKEY                 text,
   VALUE                text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_USER_META comment '用户的属性';

/*==============================================================*/
/* Table: LOG_DB_OP                                             */
/*==============================================================*/
create table LOG_DB_OP
(
   ID                   int not null auto_increment,
   ACTION_TYPE          text,
   SERVICE_NAME         text,
   DAO_NAME             text,
   REQ_USER_ID          int comment '系统客户的信息',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '后台操作员的信息',
   REQ_OPER_NAME        text,
   BEFORE_DATA          text comment '摘要',
   AFTER_DATA           text comment '详情',
   OP_SUMMARY           text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_DB_OP comment '数据库操作日志，通过AOP来实现的';

/*==============================================================*/
/* Table: LOG_EXCEPTION                                         */
/*==============================================================*/
create table LOG_EXCEPTION
(
   ID                   int not null auto_increment,
   LOG_LEVEL            text comment '日志的级别',
   LOG_TYPE             text comment '日志分类',
   LOG_RESULT           text comment '日志处理结果',
   REQ_HOST             text,
   REQ_AGENT            text comment '用户浏览器',
   REQ_PARAM            text comment '请求参数',
   REQ_URL              text comment '请求地址',
   REQ_DATE             datetime,
   REQ_USER_ID          int comment '系统客户的信息',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '后台操作员的信息',
   REQ_OPER_NAME        text,
   ERR_SUMMARY          text,
   ERR_TRACK            text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_EXCEPTION comment '异常日志，异常信息，时间，关联的组件等：是否需要人工来处理，处理状态等';

/*==============================================================*/
/* Table: LOG_LOGIN                                             */
/*==============================================================*/
create table LOG_LOGIN
(
   ID                   int not null auto_increment,
   REQ_HOST             text,
   REQ_AGENT            text,
   REQ_DATE             datetime,
   REQ_URL              text,
   REQ_USER_NAME        text,
   REQ_USER_ID          int,
   REQ_OPER_ID          int comment '后台操作员的信息',
   REQ_OPER_NAME        text,
   LOGOUT_DATE          datetime,
   LOGIN_RESULT         text,
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_LOGIN comment '登陆日志，通过标志位来区分系统后台还是商户前台登陆';

/*==============================================================*/
/* Table: LOG_PROCESS                                           */
/*==============================================================*/
create table LOG_PROCESS
(
   ID                   int not null auto_increment,
   LOG_TYPE             text comment '日志分类',
   LOG_RESULT           text comment '日志处理结果',
   REFER_BIZ_ID         text comment '关联的业务编号',
   REFER_BIZ            text comment '关联的业务内容',
   PROCESS_DATE         datetime,
   REQ_USER_ID          int comment '系统客户的信息',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '后台操作员的信息',
   REQ_OPER_NAME        text,
   PROCESS_SUMMARY      text comment '摘要',
   PROCESS_DETAIL       text comment '详情',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_PROCESS comment '处理日志，通过日志类型+动作+是否显示给用户
日志类型包括订单、商户';

/*==============================================================*/
/* Table: LOG_REQUEST                                           */
/*==============================================================*/
create table LOG_REQUEST
(
   ID                   int not null auto_increment,
   REQ_HOST             text,
   REQ_AGENT            text comment '用户浏览器',
   REQ_PARAM            text comment '请求参数',
   REQ_URL              text comment '请求地址',
   REQ_DATE             datetime,
   REQ_USER_ID          int comment '系统客户的信息',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '后台操作员的信息',
   REQ_OPER_NAME        text,
   LOG_LEVEL            text comment '日志级别',
   MISC_DESC            text,
   STATUS               text comment '逻辑删除标志',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_COMMENT_META add constraint FK_CMS_COMMENT_META_COMMENT foreign key (COMMENT_ID)
      references CMS_COMMENT (ID) on delete restrict on update restrict;

alter table CMS_POST_META add constraint FK_CMS_POST_META_PK foreign key (POST_ID)
      references CMS_POST (ID) on delete restrict on update restrict;

alter table CMS_TERM_META add constraint FK_CMS_TERM_META_CMS_TERM foreign key (TERM_ID)
      references CMS_TERM (ID) on delete restrict on update restrict;

alter table CMS_TERM_RELATIONSHIP add constraint FK_CMS_TERM_RS_CMS_TERM foreign key (TERM_TAXONOMY_ID)
      references CMS_TERM_TAXONOMY (ID) on delete restrict on update restrict;

alter table CMS_TERM_TAXONOMY add constraint FK_CMS_TERM_TAXONOMY_CMS_TERM foreign key (TERM_ID)
      references CMS_TERM (ID) on delete restrict on update restrict;

alter table CMS_USER_META add constraint FK_CMS_USER_META_CMS_USER foreign key (USER_ID)
      references CMS_USER (ID) on delete restrict on update restrict;

