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
   POST_ID              int comment '���POST_IDΪ0,���ʾΪ����',
   AUTHOR_NAME          text comment '�����˵�����',
   AUTHOR_EMAIL         text comment '�����˵�email',
   AUTHOR_URL           text comment '�����˵���վURL',
   AUTHOR_IP            text comment '�����˵�IP',
   COMMENT_DATE         datetime comment '����ʱ��',
   CONTENT              text comment '�����˵�����',
   COMMENT_KARMA        int comment 'δ֪��Ԥ��',
   APPROVED_FLAG        text comment '�Ƿ��Ѿ������',
   AUTHOR_AGENT         text comment '�����˵������',
   COMMENT_TYPE         text comment '��������(pingback/��ͨ)',
   PARENT_ID            int comment '���۵��ϼ��������¼�������0',
   USER_ID              int comment '�������û�ID����һ�����ڣ�',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_COMMENT comment '����';

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_COMMENT_META comment '���۵�Ԫ���ݻ�������';

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
   ALT                  text comment '��ʾ��Ϣ',
   VISIBLE              text,
   OWNER_ID             int comment 'ӵ����',
   RATING               int comment '����',
   REL                  text comment 'XFN��ϵ',
   NOTES                text comment 'XFN��ע',
   RSS                  text,
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_LINK comment '����';

/*==============================================================*/
/* Table: CMS_OPTION                                            */
/*==============================================================*/
create table CMS_OPTION
(
   ID                   int not null auto_increment,
   CODE                 text,
   CATGORY_CODE         text comment '������ţ������������ã���Ҫ����STMP\USER\PWD�ȣ����ڷ��飬���û�з��飬��Ϊ�ռ���',
   NAME                 text,
   VALUE                text,
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_OPTION comment '���ݹ���ƽ̨����������';

/*==============================================================*/
/* Table: CMS_POST                                              */
/*==============================================================*/
create table CMS_POST
(
   ID                   int not null auto_increment,
   OWNER_ID             int comment '����',
   SHOW_OWNER           text comment '��ʾ���û�',
   SLUG                 text,
   POST_DATE            datetime comment '������ʱ��',
   SHOW_DATE            datetime comment '��ʾ��ʱ��',
   CONTENT              text comment '����',
   TITLE                text comment '����',
   EXCERPT              text comment 'ժҪ',
   POST_STATUS          text comment '���ݵ�״̬��publish/auto-draft/inherit',
   COMMENT_STATUS       text comment '���۵�״̬��open��close��',
   PING_STATUS          text comment 'ping��״̬��close��open',
   POST_PWD             text comment '�����Ҫ������ܲ鿴������˴�������',
   TO_PING              text,
   PINGED               text,
   FITLERED             text comment '���˵����ݣ�Ŀǰ������',
   PARENT_ID            int comment '��һ�����ݣ���Ҫ����page��post',
   GUID                 text comment 'Ψһ��url',
   MENU_ORDER           int comment '����ڲ˵���ʾ���˵�������˳��',
   POST_TYPE            text comment '���� post/page/attch/revision/menu/',
   MIME_TYPE            text comment 'MIME����',
   COMMENT_COUNT        int comment '��������',
   SHOW_ORDER           int comment '��ʾ��˳��',
   TOP_FLAG             text comment '�Ƿ������ö�',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_POST comment '���ݣ�����page��revision��post��attachment

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_POST_META comment '���ݵ�����';

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM comment 'Ŀ¼����ǩ
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
   META_TYPE            text comment '�������ͣ�term/post',
   JKEY                 text,
   NAME                 text comment '��ʾ������',
   VALUE_TYPE           text comment 'ֵ���ͣ����POST����Ч�������ļ������֣����ڣ��ı���',
   VALUE_FORMAT         text comment 'ֵ�ĸ�ʽ',
   VALUE                text comment '���typeΪpost������Ҫ��д��ֵ',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_META comment 'Ŀ¼��ӵ�е����ԣ�����������־�Ƿ��౾���Ƿ�������ݵ����ԡ���������ݣ����Ӧ��CMS_POSTMETA�������
                                  ';

/*==============================================================*/
/* Table: CMS_TERM_RELATIONSHIP                                 */
/*==============================================================*/
create table CMS_TERM_RELATIONSHIP
(
   ID                   int not null auto_increment,
   TERM_TAXONOMY_ID     int comment '����ķ���ID',
   OBJECT_ID            int comment '��Ӧ����ID/����ID',
   TERM_ORDER           int comment '����',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_RELATIONSHIP comment '�洢ÿ�����¡����ӺͶ�Ӧ����Ĺ�ϵ

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_TERM_TAXONOMY comment '���෽�����洢ÿ��Ŀ¼����ǩ����Ӧ�ķ���
taxonomy�����෽��(category/post_tag/n';

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_USER comment '�û�';

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
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table CMS_USER_META comment '�û�������';

/*==============================================================*/
/* Table: LOG_DB_OP                                             */
/*==============================================================*/
create table LOG_DB_OP
(
   ID                   int not null auto_increment,
   ACTION_TYPE          text,
   SERVICE_NAME         text,
   DAO_NAME             text,
   REQ_USER_ID          int comment 'ϵͳ�ͻ�����Ϣ',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '��̨����Ա����Ϣ',
   REQ_OPER_NAME        text,
   BEFORE_DATA          text comment 'ժҪ',
   AFTER_DATA           text comment '����',
   OP_SUMMARY           text,
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_DB_OP comment '���ݿ������־��ͨ��AOP��ʵ�ֵ�';

/*==============================================================*/
/* Table: LOG_EXCEPTION                                         */
/*==============================================================*/
create table LOG_EXCEPTION
(
   ID                   int not null auto_increment,
   LOG_LEVEL            text comment '��־�ļ���',
   LOG_TYPE             text comment '��־����',
   LOG_RESULT           text comment '��־������',
   REQ_HOST             text,
   REQ_AGENT            text comment '�û������',
   REQ_PARAM            text comment '�������',
   REQ_URL              text comment '�����ַ',
   REQ_DATE             datetime,
   REQ_USER_ID          int comment 'ϵͳ�ͻ�����Ϣ',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '��̨����Ա����Ϣ',
   REQ_OPER_NAME        text,
   ERR_SUMMARY          text,
   ERR_TRACK            text,
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_EXCEPTION comment '�쳣��־���쳣��Ϣ��ʱ�䣬����������ȣ��Ƿ���Ҫ�˹�����������״̬��';

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
   REQ_OPER_ID          int comment '��̨����Ա����Ϣ',
   REQ_OPER_NAME        text,
   LOGOUT_DATE          datetime,
   LOGIN_RESULT         text,
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_LOGIN comment '��½��־��ͨ����־λ������ϵͳ��̨�����̻�ǰ̨��½';

/*==============================================================*/
/* Table: LOG_PROCESS                                           */
/*==============================================================*/
create table LOG_PROCESS
(
   ID                   int not null auto_increment,
   LOG_TYPE             text comment '��־����',
   LOG_RESULT           text comment '��־������',
   REFER_BIZ_ID         text comment '������ҵ����',
   REFER_BIZ            text comment '������ҵ������',
   PROCESS_DATE         datetime,
   REQ_USER_ID          int comment 'ϵͳ�ͻ�����Ϣ',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '��̨����Ա����Ϣ',
   REQ_OPER_NAME        text,
   PROCESS_SUMMARY      text comment 'ժҪ',
   PROCESS_DETAIL       text comment '����',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
   CREATE_TIME          datetime,
   CREATE_OPER_ID       int,
   CREATE_OPER_NAME     text,
   LAST_UPD_TIME        datetime,
   LAST_UPD_OPER_ID     int,
   LAST_UPD_OPER_NAME   text,
   primary key (ID)
);

alter table LOG_PROCESS comment '������־��ͨ����־����+����+�Ƿ���ʾ���û�
��־���Ͱ����������̻�';

/*==============================================================*/
/* Table: LOG_REQUEST                                           */
/*==============================================================*/
create table LOG_REQUEST
(
   ID                   int not null auto_increment,
   REQ_HOST             text,
   REQ_AGENT            text comment '�û������',
   REQ_PARAM            text comment '�������',
   REQ_URL              text comment '�����ַ',
   REQ_DATE             datetime,
   REQ_USER_ID          int comment 'ϵͳ�ͻ�����Ϣ',
   REQ_USER_NAME        text,
   REQ_OPER_ID          int comment '��̨����Ա����Ϣ',
   REQ_OPER_NAME        text,
   LOG_LEVEL            text comment '��־����',
   MISC_DESC            text,
   STATUS               text comment '�߼�ɾ����־',
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

