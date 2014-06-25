-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.37


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema jpress
--

CREATE DATABASE IF NOT EXISTS jpress;
USE jpress;

--
-- Definition of table `cms_comment`
--

DROP TABLE IF EXISTS `cms_comment`;
CREATE TABLE `cms_comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POST_ID` int(11) DEFAULT NULL COMMENT '如果POST_ID为0,则表示为留言',
  `AUTHOR_NAME` text COMMENT '评论人的名称',
  `AUTHOR_EMAIL` text COMMENT '评论人的email',
  `AUTHOR_URL` text COMMENT '评论人的网站URL',
  `AUTHOR_IP` text COMMENT '评论人的IP',
  `COMMENT_DATE` datetime DEFAULT NULL COMMENT '评论时间',
  `CONTENT` text COMMENT '评论人的内容',
  `COMMENT_KARMA` int(11) DEFAULT NULL COMMENT '未知、预留',
  `APPROVED_FLAG` text COMMENT '是否已经审核了',
  `AUTHOR_AGENT` text COMMENT '评论人的浏览器',
  `COMMENT_TYPE` text COMMENT '评论类型(pingback/普通)',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '评论的上级，无上下级的则填0',
  `USER_ID` int(11) DEFAULT NULL COMMENT '评论者用户ID（不一定存在）',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论';

--
-- Dumping data for table `cms_comment`
--

/*!40000 ALTER TABLE `cms_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_comment` ENABLE KEYS */;


--
-- Definition of table `cms_comment_meta`
--

DROP TABLE IF EXISTS `cms_comment_meta`;
CREATE TABLE `cms_comment_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMMENT_ID` int(11) DEFAULT NULL,
  `JKEY` text,
  `VALUE` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_COMMENT_META_COMMENT` (`COMMENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='评论的元数据或者属性';

--
-- Dumping data for table `cms_comment_meta`
--

/*!40000 ALTER TABLE `cms_comment_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_comment_meta` ENABLE KEYS */;


--
-- Definition of table `cms_link`
--

DROP TABLE IF EXISTS `cms_link`;
CREATE TABLE `cms_link` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `URL` text,
  `NAME` text,
  `IMAGE` text,
  `TARGET` text,
  `ALT` text COMMENT '提示信息',
  `VISIBLE` text,
  `OWNER_ID` int(11) DEFAULT NULL COMMENT '拥有者',
  `RATING` int(11) DEFAULT NULL COMMENT '评级',
  `REL` text COMMENT 'XFN关系',
  `NOTES` text COMMENT 'XFN备注',
  `RSS` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='链接';

--
-- Dumping data for table `cms_link`
--

/*!40000 ALTER TABLE `cms_link` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_link` ENABLE KEYS */;


--
-- Definition of table `cms_option`
--

DROP TABLE IF EXISTS `cms_option`;
CREATE TABLE `cms_option` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` text,
  `CATGORY_CODE` text COMMENT '分类代号：比如邮箱配置，需要配置STMPUSERPWD等，用于分组，如果没有分组，则为空即可',
  `NAME` text,
  `VALUE` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容管理平台的属性配置';

--
-- Dumping data for table `cms_option`
--

/*!40000 ALTER TABLE `cms_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_option` ENABLE KEYS */;


--
-- Definition of table `cms_post`
--

DROP TABLE IF EXISTS `cms_post`;
CREATE TABLE `cms_post` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OWNER_ID` int(11) DEFAULT NULL COMMENT '作者',
  `SHOW_OWNER` text COMMENT '显示的用户',
  `SLUG` text,
  `POST_DATE` datetime DEFAULT NULL COMMENT '发布的时间',
  `SHOW_DATE` datetime DEFAULT NULL COMMENT '显示的时间',
  `CONTENT` text COMMENT '内容',
  `TITLE` text COMMENT '标题',
  `EXCERPT` text COMMENT '摘要',
  `POST_STATUS` text COMMENT '内容的状态：publish/auto-draft/inherit',
  `COMMENT_STATUS` text COMMENT '评论的状态（open、close）',
  `PING_STATUS` text COMMENT 'ping的状态，close、open',
  `POST_PWD` text COMMENT '如果需要密码才能查看，则填此处的密码',
  `TO_PING` text,
  `PINGED` text,
  `FITLERED` text COMMENT '过滤的内容，目前不启用',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '上一级内容，主要用在page的post',
  `GUID` text COMMENT '唯一的url',
  `MENU_ORDER` int(11) DEFAULT NULL COMMENT '如果在菜单显示，菜单的排列顺序',
  `POST_TYPE` text COMMENT '类型 post/page/attch/revision/menu/',
  `MIME_TYPE` text COMMENT 'MIME类型',
  `COMMENT_COUNT` int(11) DEFAULT NULL COMMENT '评论总数',
  `SHOW_ORDER` int(11) DEFAULT NULL COMMENT '显示的顺序',
  `TOP_FLAG` text COMMENT '是否属于置顶',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容：包括page、revision、post、attachment\r\n\r\nThe core of ';

--
-- Dumping data for table `cms_post`
--

/*!40000 ALTER TABLE `cms_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_post` ENABLE KEYS */;


--
-- Definition of table `cms_post_meta`
--

DROP TABLE IF EXISTS `cms_post_meta`;
CREATE TABLE `cms_post_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POST_ID` int(11) DEFAULT NULL,
  `TERM_META_ID` int(11) DEFAULT NULL,
  `JKEY` text,
  `NAME` text,
  `VALUE` text,
  `SHOW_ORDER` int(11) DEFAULT NULL,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_POST_META_PK` (`POST_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='内容的属性';

--
-- Dumping data for table `cms_post_meta`
--

/*!40000 ALTER TABLE `cms_post_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_post_meta` ENABLE KEYS */;


--
-- Definition of table `cms_term`
--

DROP TABLE IF EXISTS `cms_term`;
CREATE TABLE `cms_term` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` text,
  `SLUG` varchar(200) DEFAULT NULL,
  `JGROUP` varchar(200) DEFAULT NULL,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_TERM_SLUG` (`SLUG`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='目录、标签\r\nThe categories for both posts and links and the ';

--
-- Dumping data for table `cms_term`
--

/*!40000 ALTER TABLE `cms_term` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_term` ENABLE KEYS */;


--
-- Definition of table `cms_term_meta`
--

DROP TABLE IF EXISTS `cms_term_meta`;
CREATE TABLE `cms_term_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TERM_ID` int(11) DEFAULT NULL,
  `META_TYPE` text COMMENT '属性类型：term/post',
  `JKEY` text,
  `NAME` text COMMENT '显示的名称',
  `VALUE_TYPE` text COMMENT '值类型，针对POST才有效，比如文件，数字，日期，文本等',
  `VALUE_FORMAT` text COMMENT '值的格式',
  `VALUE` text COMMENT '如果type为post，则不需要填写此值',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_TERM_META_CMS_TERM` (`TERM_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。\r\n';

--
-- Dumping data for table `cms_term_meta`
--

/*!40000 ALTER TABLE `cms_term_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_term_meta` ENABLE KEYS */;


--
-- Definition of table `cms_term_relationship`
--

DROP TABLE IF EXISTS `cms_term_relationship`;
CREATE TABLE `cms_term_relationship` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TERM_TAXONOMY_ID` int(11) DEFAULT NULL COMMENT '分类的方法ID',
  `OBJECT_ID` int(11) DEFAULT NULL COMMENT '对应文章ID/链接ID',
  `TERM_ORDER` int(11) DEFAULT NULL COMMENT '排序',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_TERM_RS_CMS_TERM` (`TERM_TAXONOMY_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='存储每个文章、链接和对应分类的关系\r\n\r\nPosts are associated with cat';

--
-- Dumping data for table `cms_term_relationship`
--

/*!40000 ALTER TABLE `cms_term_relationship` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_term_relationship` ENABLE KEYS */;


--
-- Definition of table `cms_term_taxonomy`
--

DROP TABLE IF EXISTS `cms_term_taxonomy`;
CREATE TABLE `cms_term_taxonomy` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TERM_ID` int(11) DEFAULT NULL,
  `TAXONOMY` text,
  `PARENT_ID` int(11) DEFAULT NULL,
  `POST_COUNT` int(11) DEFAULT NULL,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_TERM_TAXONOMY_CMS_TERM` (`TERM_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分类方法：存储每个目录、标签所对应的分类\r\ntaxonomy：分类方法(category/post_tag/n';

--
-- Dumping data for table `cms_term_taxonomy`
--

/*!40000 ALTER TABLE `cms_term_taxonomy` DISABLE KEYS */;
/*!40000 ALTER TABLE `cms_term_taxonomy` ENABLE KEYS */;


--
-- Definition of table `cms_user`
--

DROP TABLE IF EXISTS `cms_user`;
CREATE TABLE `cms_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` text,
  `LOGIN_PWD` text,
  `NICKNAME` text,
  `USER_NAME` text,
  `SHOW_NAME` text,
  `USER_EMAIL` text,
  `USER_URL` text,
  `REGISTER_DATE` datetime DEFAULT NULL,
  `SERIAL_KEY` text,
  `USER_STATUS` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户';

--
-- Dumping data for table `cms_user`
--

/*!40000 ALTER TABLE `cms_user` DISABLE KEYS */;
INSERT INTO `cms_user` (`ID`,`LOGIN_NAME`,`LOGIN_PWD`,`NICKNAME`,`USER_NAME`,`SHOW_NAME`,`USER_EMAIL`,`USER_URL`,`REGISTER_DATE`,`SERIAL_KEY`,`USER_STATUS`,`MISC_DESC`,`STATUS`,`CREATE_TIME`,`CREATE_OPER_ID`,`CREATE_OPER_NAME`,`LAST_UPD_TIME`,`LAST_UPD_OPER_ID`,`LAST_UPD_OPER_NAME`) VALUES 
 (1,'admin','7A57A5A743894A0E','admin','admin','admin','webmaster@jpress.cn','http://www.jpress.cn','2016-06-23 00:00:00','NA','V','NA','V','2014-06-23 00:00:00',-1,'sys','2014-06-23 00:00:00',-1,'sys'),
 (2,'jpress','E9DB9C60C3E8AA94','admin','zhou',NULL,'webmaster@jpress.com',NULL,NULL,NULL,'V',NULL,'I','2014-06-23 20:52:11',1,'admin','2014-06-23 22:45:55',1,'admin'),
 (3,'jpress2','E9DB9C60C3E8AA94','admin','zhou','admin','webmaster@jpress.comc',NULL,'2014-06-23 20:58:56','NA','V',NULL,'I','2014-06-23 20:58:56',1,'admin','2014-06-23 22:44:22',1,'admin'),
 (4,'jpress222','E9DB9C60C3E8AA94','admin','zhou','admin','webmaster@jpress.cn22',NULL,'2014-06-23 21:02:03','NA','V',NULL,'I','2014-06-23 21:02:03',1,'admin','2014-06-23 22:44:10',1,'admin'),
 (5,'王胜利','E9DB9C60C3E8AA94','admin','zhou','admin','webmaster@jpress.cn333',NULL,'2014-06-23 21:21:20','NA','V',NULL,'I','2014-06-23 21:21:20',1,'admin','2014-06-23 22:05:26',1,'admin'),
 (6,'王胜利333','E9DB9C60C3E8AA94','adfadsf','fdasf','admin','3333@eee.cc',NULL,'2014-06-23 21:42:16','NA','V',NULL,'I','2014-06-23 21:42:16',1,'admin','2014-06-23 21:42:48',1,'admin'),
 (7,'admindfads','E9DB9C60C3E8AA94','adsfasdfasdf','fadsf','admin','fadsfads@www.cn',NULL,'2014-06-23 21:42:32','NA','V',NULL,'I','2014-06-23 21:42:32',1,'admin','2014-06-23 22:05:26',1,'admin'),
 (8,'jpress2222','E9DB9C60C3E8AA94','dfadfa','dfa','admin','23432@rre.mm',NULL,'2014-06-23 22:14:31','NA','V',NULL,'I','2014-06-23 22:14:31',1,'admin','2014-06-23 22:25:01',1,'admin'),
 (9,'jpress','E9DB9C60C3E8AA94','admin','zhou','admin','webmaster@strawren.com',NULL,'2014-06-25 19:29:28','NA','V',NULL,'V','2014-06-25 19:29:28',1,'admin','2014-06-25 19:29:28',1,'admin'),
 (10,'jpress222','E9DB9C60C3E8AA94','admin','zhou','admin','webmaster@jpress.cn22',NULL,'2014-06-25 19:59:15','NA','V',NULL,'I','2014-06-25 19:59:15',1,'admin','2014-06-25 20:45:13',1,'admin'),
 (11,'admin33','E9DB9C60C3E8AA94','adfadsf','333','admin','3333@eee.cc',NULL,'2014-06-25 19:59:30','NA','V',NULL,'I','2014-06-25 19:59:30',1,'admin','2014-06-25 20:19:04',1,'admin'),
 (12,'王胜利','E9DB9C60C3E8AA94','admin','王者归来','admin','webmaster@jpress.cn2',NULL,'2014-06-25 20:45:39','NA','V',NULL,'I','2014-06-25 20:45:39',1,'admin','2014-06-25 21:48:53',1,'admin'),
 (13,'admin444','E9DB9C60C3E8AA94','adfadsf','zhou','admin','3333@eee.cc333',NULL,'2014-06-25 21:49:30','NA','V',NULL,'I','2014-06-25 21:49:30',1,'admin','2014-06-25 22:05:34',1,'admin');
/*!40000 ALTER TABLE `cms_user` ENABLE KEYS */;


--
-- Definition of table `cms_user_meta`
--

DROP TABLE IF EXISTS `cms_user_meta`;
CREATE TABLE `cms_user_meta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  `JKEY` text,
  `VALUE` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`),
  KEY `FK_CMS_USER_META_CMS_USER` (`USER_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户的属性';

--
-- Dumping data for table `cms_user_meta`
--

/*!40000 ALTER TABLE `cms_user_meta` DISABLE KEYS */;
INSERT INTO `cms_user_meta` (`ID`,`USER_ID`,`JKEY`,`VALUE`,`MISC_DESC`,`STATUS`,`CREATE_TIME`,`CREATE_OPER_ID`,`CREATE_OPER_NAME`,`LAST_UPD_TIME`,`LAST_UPD_OPER_ID`,`LAST_UPD_OPER_NAME`) VALUES 
 (1,2,'userLevel','普通用户',NULL,'V','2014-06-23 20:52:11',1,'admin','2014-06-23 20:52:11',1,'admin'),
 (2,3,'userLevel','普通用户',NULL,'V','2014-06-23 20:58:56',1,'admin','2014-06-23 20:58:56',1,'admin'),
 (3,4,'userLevel','普通用户',NULL,'V','2014-06-23 21:02:03',1,'admin','2014-06-23 21:02:03',1,'admin'),
 (4,5,'userLevel','普通用户',NULL,'V','2014-06-23 21:21:20',1,'admin','2014-06-23 21:21:20',1,'admin'),
 (5,6,'userLevel','普通用户',NULL,'V','2014-06-23 21:42:16',1,'admin','2014-06-23 21:42:16',1,'admin'),
 (6,7,'userLevel','普通用户',NULL,'V','2014-06-23 21:42:32',1,'admin','2014-06-23 21:42:32',1,'admin'),
 (7,8,'userLevel','普通用户',NULL,'V','2014-06-23 22:14:31',1,'admin','2014-06-23 22:14:31',1,'admin'),
 (8,9,'userLevel','普通用户',NULL,'V','2014-06-25 19:29:28',1,'admin','2014-06-25 19:29:28',1,'admin'),
 (9,10,'userLevel','普通用户',NULL,'V','2014-06-25 19:59:15',1,'admin','2014-06-25 19:59:15',1,'admin'),
 (10,11,'userLevel','普通用户',NULL,'V','2014-06-25 19:59:30',1,'admin','2014-06-25 19:59:30',1,'admin'),
 (11,12,'userLevel','普通用户',NULL,'V','2014-06-25 20:45:39',1,'admin','2014-06-25 20:45:39',1,'admin'),
 (12,13,'userLevel','普通用户',NULL,'V','2014-06-25 21:49:30',1,'admin','2014-06-25 21:49:30',1,'admin');
/*!40000 ALTER TABLE `cms_user_meta` ENABLE KEYS */;


--
-- Definition of table `log_db_op`
--

DROP TABLE IF EXISTS `log_db_op`;
CREATE TABLE `log_db_op` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION_TYPE` text,
  `SERVICE_NAME` text,
  `DAO_NAME` text,
  `REQ_USER_ID` int(11) DEFAULT NULL COMMENT '系统客户的信息',
  `REQ_USER_NAME` text,
  `REQ_OPER_ID` int(11) DEFAULT NULL COMMENT '后台操作员的信息',
  `REQ_OPER_NAME` text,
  `BEFORE_DATA` text COMMENT '摘要',
  `AFTER_DATA` text COMMENT '详情',
  `OP_SUMMARY` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='数据库操作日志，通过AOP来实现的';

--
-- Dumping data for table `log_db_op`
--

/*!40000 ALTER TABLE `log_db_op` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_db_op` ENABLE KEYS */;


--
-- Definition of table `log_exception`
--

DROP TABLE IF EXISTS `log_exception`;
CREATE TABLE `log_exception` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOG_LEVEL` text COMMENT '日志的级别',
  `LOG_TYPE` text COMMENT '日志分类',
  `LOG_RESULT` text COMMENT '日志处理结果',
  `REQ_HOST` text,
  `REQ_AGENT` text COMMENT '用户浏览器',
  `REQ_PARAM` text COMMENT '请求参数',
  `REQ_URL` text COMMENT '请求地址',
  `REQ_DATE` datetime DEFAULT NULL,
  `REQ_USER_ID` int(11) DEFAULT NULL COMMENT '系统客户的信息',
  `REQ_USER_NAME` text,
  `REQ_OPER_ID` int(11) DEFAULT NULL COMMENT '后台操作员的信息',
  `REQ_OPER_NAME` text,
  `ERR_SUMMARY` text,
  `ERR_TRACK` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='异常日志，异常信息，时间，关联的组件等：是否需要人工来处理，处理状态等';

--
-- Dumping data for table `log_exception`
--

/*!40000 ALTER TABLE `log_exception` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_exception` ENABLE KEYS */;


--
-- Definition of table `log_login`
--

DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQ_HOST` text,
  `REQ_AGENT` text,
  `REQ_DATE` datetime DEFAULT NULL,
  `REQ_URL` text,
  `REQ_USER_NAME` text,
  `REQ_USER_ID` int(11) DEFAULT NULL,
  `REQ_OPER_ID` int(11) DEFAULT NULL COMMENT '后台操作员的信息',
  `REQ_OPER_NAME` text,
  `LOGOUT_DATE` datetime DEFAULT NULL,
  `LOGIN_RESULT` text,
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='登陆日志，通过标志位来区分系统后台还是商户前台登陆';

--
-- Dumping data for table `log_login`
--

/*!40000 ALTER TABLE `log_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_login` ENABLE KEYS */;


--
-- Definition of table `log_process`
--

DROP TABLE IF EXISTS `log_process`;
CREATE TABLE `log_process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOG_TYPE` text COMMENT '日志分类',
  `LOG_RESULT` text COMMENT '日志处理结果',
  `REFER_BIZ_ID` text COMMENT '关联的业务编号',
  `REFER_BIZ` text COMMENT '关联的业务内容',
  `PROCESS_DATE` datetime DEFAULT NULL,
  `REQ_USER_ID` int(11) DEFAULT NULL COMMENT '系统客户的信息',
  `REQ_USER_NAME` text,
  `REQ_OPER_ID` int(11) DEFAULT NULL COMMENT '后台操作员的信息',
  `REQ_OPER_NAME` text,
  `PROCESS_SUMMARY` text COMMENT '摘要',
  `PROCESS_DETAIL` text COMMENT '详情',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='处理日志，通过日志类型+动作+是否显示给用户\r\n日志类型包括订单、商户';

--
-- Dumping data for table `log_process`
--

/*!40000 ALTER TABLE `log_process` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_process` ENABLE KEYS */;


--
-- Definition of table `log_request`
--

DROP TABLE IF EXISTS `log_request`;
CREATE TABLE `log_request` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQ_HOST` text,
  `REQ_AGENT` text COMMENT '用户浏览器',
  `REQ_PARAM` text COMMENT '请求参数',
  `REQ_URL` text COMMENT '请求地址',
  `REQ_DATE` datetime DEFAULT NULL,
  `REQ_USER_ID` int(11) DEFAULT NULL COMMENT '系统客户的信息',
  `REQ_USER_NAME` text,
  `REQ_OPER_ID` int(11) DEFAULT NULL COMMENT '后台操作员的信息',
  `REQ_OPER_NAME` text,
  `LOG_LEVEL` text COMMENT '日志级别',
  `MISC_DESC` text,
  `STATUS` text COMMENT '逻辑删除标志',
  `CREATE_TIME` datetime DEFAULT NULL,
  `CREATE_OPER_ID` int(11) DEFAULT NULL,
  `CREATE_OPER_NAME` text,
  `LAST_UPD_TIME` datetime DEFAULT NULL,
  `LAST_UPD_OPER_ID` int(11) DEFAULT NULL,
  `LAST_UPD_OPER_NAME` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `log_request`
--

/*!40000 ALTER TABLE `log_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_request` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
