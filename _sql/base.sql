/*
Navicat MySQL Data Transfer

Source Server         :
Source Server Version : 50722
Source Host           :
Source Database       : base

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2022-03-01 20:15:24

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `BLOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_BLOB_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `CALENDAR_NAME` varchar(200) NOT NULL,
    `CALENDAR` blob NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `CRON_EXPRESSION` varchar(120) NOT NULL,
    `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_CRON_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `ENTRY_ID` varchar(95) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `INSTANCE_NAME` varchar(200) NOT NULL,
    `FIRED_TIME` bigint(13) NOT NULL,
    `SCHED_TIME` bigint(13) NOT NULL,
    `PRIORITY` int(11) NOT NULL,
    `STATE` varchar(16) NOT NULL,
    `JOB_NAME` varchar(200) DEFAULT NULL,
    `JOB_GROUP` varchar(200) DEFAULT NULL,
    `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
    KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
    KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
    KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `JOB_NAME` varchar(200) NOT NULL,
    `JOB_GROUP` varchar(200) NOT NULL,
    `DESCRIPTION` varchar(250) DEFAULT NULL,
    `JOB_CLASS_NAME` varchar(250) NOT NULL,
    `IS_DURABLE` varchar(1) NOT NULL,
    `IS_NONCONCURRENT` varchar(1) NOT NULL,
    `IS_UPDATE_DATA` varchar(1) NOT NULL,
    `REQUESTS_RECOVERY` varchar(1) NOT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
    KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `LOCK_NAME` varchar(40) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `INSTANCE_NAME` varchar(200) NOT NULL,
    `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
    `CHECKIN_INTERVAL` bigint(13) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `REPEAT_COUNT` bigint(7) NOT NULL,
    `REPEAT_INTERVAL` bigint(12) NOT NULL,
    `TIMES_TRIGGERED` bigint(10) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `STR_PROP_1` varchar(512) DEFAULT NULL,
    `STR_PROP_2` varchar(512) DEFAULT NULL,
    `STR_PROP_3` varchar(512) DEFAULT NULL,
    `INT_PROP_1` int(11) DEFAULT NULL,
    `INT_PROP_2` int(11) DEFAULT NULL,
    `LONG_PROP_1` bigint(20) DEFAULT NULL,
    `LONG_PROP_2` bigint(20) DEFAULT NULL,
    `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
    `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
    `BOOL_PROP_1` varchar(1) DEFAULT NULL,
    `BOOL_PROP_2` varchar(1) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `JOB_NAME` varchar(200) NOT NULL,
    `JOB_GROUP` varchar(200) NOT NULL,
    `DESCRIPTION` varchar(250) DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PRIORITY` int(11) DEFAULT NULL,
    `TRIGGER_STATE` varchar(16) NOT NULL,
    `TRIGGER_TYPE` varchar(8) NOT NULL,
    `START_TIME` bigint(13) NOT NULL,
    `END_TIME` bigint(13) DEFAULT NULL,
    `CALENDAR_NAME` varchar(200) DEFAULT NULL,
    `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
    KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
    KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    CONSTRAINT `QRTZ_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `SCHEDULE_JOB`
-- ----------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB`;
CREATE TABLE `SCHEDULE_JOB` (
    `JOB_ID` varchar(32) NOT NULL COMMENT '任务id',
    `BEAN_NAME` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
    `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名',
    `PARAMS` varchar(2000) DEFAULT NULL COMMENT '参数',
    `CRON_EXPRESSION` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
    `STATUS` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
    `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of SCHEDULE_JOB
-- ----------------------------
INSERT INTO `SCHEDULE_JOB` VALUES ('7a737ccd0cf55d1447a73ad8d4417c83', 'testTask', 'test', NULL, '0 0/1 * * * ?', 0, 'demo', '2019-07-19 16:54:54');

-- ----------------------------
-- Table structure for `SCHEDULE_JOB_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB_LOG`;
CREATE TABLE `SCHEDULE_JOB_LOG` (
    `LOG_ID` varchar(32) NOT NULL COMMENT '任务日志id',
    `JOB_ID` varchar(32) NOT NULL COMMENT '任务id',
    `BEAN_NAME` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
    `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名',
    `PARAMS` varchar(2000) DEFAULT NULL COMMENT '参数',
    `STATUS` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
    `ERROR` varchar(2000) DEFAULT NULL COMMENT '失败信息',
    `TIMES` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`LOG_ID`),
    KEY `JOB_ID` (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of SCHEDULE_JOB_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_CAPTCHA`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CAPTCHA`;
CREATE TABLE `SYS_CAPTCHA` (
    `UUID` char(36) NOT NULL COMMENT 'uuid',
    `CODE` varchar(6) NOT NULL COMMENT '验证码',
    `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '过期时间',
    PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';

-- ----------------------------
-- Records of SYS_CAPTCHA
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CONFIG`;
CREATE TABLE `SYS_CONFIG` (
    `ID` varchar(32) NOT NULL,
    `PARAM_KEY` varchar(50) DEFAULT NULL COMMENT 'key',
    `PARAM_VALUE` text COMMENT 'value',
    `STATUS` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
    `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `PARAM_KEY` (`PARAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of SYS_CONFIG
-- ----------------------------
INSERT INTO `SYS_CONFIG` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"type\":2,\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuAccessKey\":\"\",\"qiniuSecretKey\":\"\",\"qiniuBucketName\":\"\",\"aliyunDomain\":\"https://platform-wxmall.oss-cn-beijing.aliyuncs.com\",\"aliyunPrefix\":\"image\",\"aliyunEndPoint\":\"oss-cn-beijing.aliyuncs.com\",\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"platform-wxmall\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\",\"diskPath\":\"/usr/local/nginx/html/upload\",\"proxyServer\":\"http://132.232.89.47/upload\"}', '0', '云存储配置信息');
INSERT INTO `SYS_CONFIG` VALUES ('2', 'SMS_CONFIG_KEY', '{\"domain\":\"http://web.cr6868.com/asmx/smsservice.aspx?\",\"name\":\"lipengjun\",\"pwd\":\"\",\"sign\":\"【微同科技】\",\"type\":1}', '0', '短信配置');

-- ----------------------------
-- Table structure for `SYS_DICT`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT`;
CREATE TABLE `SYS_DICT` (
    `ID` varchar(32) NOT NULL,
    `GROUP_ID` varchar(32) DEFAULT NULL COMMENT '所属分组ID',
    `NAME` varchar(100) DEFAULT NULL COMMENT '字典名称',
    `VALUE` varchar(64) DEFAULT NULL COMMENT '字典值',
    `SORT` int(11) DEFAULT NULL COMMENT '排序号',
    `STATUS` int(11) DEFAULT NULL COMMENT '状态码',
    `REMARK` text COMMENT '备注',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of SYS_DICT
-- ----------------------------
INSERT INTO `SYS_DICT` VALUES ('37f73ea6b07c40ab8baec7f58b10e69e', '0b5e3fc9c30a4839a881bef0f85fc8af', '男', '1', '1', '1', null);
INSERT INTO `SYS_DICT` VALUES ('fc982423addd41e3852c70f8396a0c6c', '0b5e3fc9c30a4839a881bef0f85fc8af', '女', '2', '2', '1', null);
INSERT INTO `SYS_DICT` VALUES ('979439be76954bc1852fdf2aeccf3cbc', '0b5e3fc9c30a4839a881bef0f85fc8af', '未知', '0', '3', '1', null);

-- ----------------------------
-- Table structure for `SYS_DICT_GROUP`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT_GROUP`;
CREATE TABLE `SYS_DICT_GROUP` (
    `ID` varchar(32) NOT NULL,
    `CODE` varchar(64) NOT NULL COMMENT '分组编码',
    `NAME` varchar(100) DEFAULT NULL COMMENT '分组名称',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    `REMARK` text COMMENT '备注',
    PRIMARY KEY (`ID`,`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典分组';

-- ----------------------------
-- Records of SYS_DICT_GROUP
-- ----------------------------
INSERT INTO `SYS_DICT_GROUP` VALUES ('0b5e3fc9c30a4839a881bef0f85fc8af', 'SEX', '性别', '2021-01-23 20:16:36', '性别，1：男 2：女 0：未知');

-- ----------------------------
-- Table structure for `SYS_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_LOG`;
CREATE TABLE `SYS_LOG` (
    `ID` varchar(32) NOT NULL,
    `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
    `OPERATION` varchar(50) DEFAULT NULL COMMENT '用户操作',
    `METHOD` varchar(200) DEFAULT NULL COMMENT '请求方法',
    `PARAMS` varchar(5000) DEFAULT NULL COMMENT '请求参数',
    `TIME` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
    `IP` varchar(64) DEFAULT NULL COMMENT 'IP地址',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of SYS_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MENU`;
CREATE TABLE `SYS_MENU` (
    `MENU_ID` VARCHAR(8) NOT NULL,
    `PARENT_ID` VARCHAR(8) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
    `NAME` VARCHAR(50) DEFAULT NULL COMMENT '菜单名称',
    `URL` VARCHAR(200) DEFAULT NULL COMMENT '菜单URL',
    `PERMS` VARCHAR(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    `TYPE` tinyint DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    `ICON` VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
    `ORDER_NUM` INT(11) DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO `SYS_MENU` VALUES ('10', '0', '系统设置', NULL, NULL, 0, 'system', 0);
INSERT INTO `SYS_MENU` VALUES ('1001', '10', '菜单管理', 'sys/menu', 'sys:menu:list,sys:menu:info', 1, 'menu', 1);
INSERT INTO `SYS_MENU` VALUES ('100101', '1001', '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100102', '1001', '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100103', '1001', '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1002', '10', '组织机构', 'sys/org', 'sys:org:list,sys:org:info', 1, 'org', 2);
INSERT INTO `SYS_MENU` VALUES ('100201', '1002', '新增', NULL, 'sys:org:save', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100202', '1002', '修改', NULL, 'sys:org:update', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100203', '1002', '删除', NULL, 'sys:org:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1003', '10', '系统参数', 'sys/config', 'sys:config:list,sys:config:info', 1, 'xitongpeizhi', 3);
INSERT INTO `SYS_MENU` VALUES ('100301', '1003', '新增', NULL, 'sys:config:save', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100302', '1003', '修改', NULL, 'sys:config:update', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100303', '1003', '删除', NULL, 'sys:config:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1004', '10', '字典管理', 'sys/dictgroup', 'sys:dictgroup:list,sys:dictgroup:info,sys:dict:list,sys:dict:info', 1, 'dict', 4);
INSERT INTO `SYS_MENU` VALUES ('100401', '1004', '数据字典新增', NULL, 'sys:dict:save', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100402', '1004', '数据字典修改', NULL, 'sys:dict:update', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100403', '1004', '数据字典删除', NULL, 'sys:dict:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100404', '1004', '数据字典分组新增', NULL, 'sys:dictgroup:save', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100405', '1004', '数据字典分组修改', NULL, 'sys:dictgroup:update', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100406', '1004', '数据字典分组删除', NULL, 'sys:dictgroup:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1005', '10', '文件上传', 'oss/oss', 'sys:oss:list', 1, 'oss', 5);
INSERT INTO `SYS_MENU` VALUES ('100501', '1005', '云存储配置', NULL, 'sys:oss:config', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100502', '1005', '删除', NULL, 'sys:oss:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1006', '10', '短信配置', 'sys/smslog', 'sys:smslog:list', 1, 'duanxin', 6);
INSERT INTO `SYS_MENU` VALUES ('100601', '1006', '修改配置', NULL, 'sys:smslog:config', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100602', '1006', '删除', NULL, 'sys:smslog:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100603', '1006', '发送短信', NULL, 'sys:smslog:send', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1007', '10', '定时任务', 'job/schedule', 'sys:schedule:list,sys:schedule:info', 1, 'job', 7);
INSERT INTO `SYS_MENU` VALUES ('100701', '1007', '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100702', '1007', '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100703', '1007', '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100704', '1007', '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100705', '1007', '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100706', '1007', '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('100707', '1007', '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1008', '10', '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 8);
INSERT INTO `SYS_MENU` VALUES ('11', '0', '权限管理', NULL, NULL, 0, 'quanxian', 1);
INSERT INTO `SYS_MENU` VALUES ('1101', '11', '管理员列表', 'sys/user', 'sys:user:list,sys:user:info', 1, 'admin', 1);
INSERT INTO `SYS_MENU` VALUES ('110101', '1101', '重置密码', NULL, 'sys:user:resetPw', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110102', '1101', '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110103', '1101', '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110104', '1101', '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110105', '1101', '系统监控', null, 'sys:monitor:server', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1102', '11', '角色管理', 'sys/role', 'sys:role:list,sys:role:info', 1, 'role', 2);
INSERT INTO `SYS_MENU` VALUES ('110201', '1102', '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110202', '1102', '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('110203', '1102', '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('12', '0', '开发工具', NULL, NULL, 0, 'dev', 2);
INSERT INTO `SYS_MENU` VALUES ('1201', '12', '令牌管理', 'sys/usertoken', 'sys:usertoken:list', 1, 'zaixian', 1);
INSERT INTO `SYS_MENU` VALUES ('120101', '1201', '删除', NULL, 'sys:usertoken:offline', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1202', '12', '缓存信息', 'sys/redis', 'sys:cache:queryAll', 1, 'redis', 2);
INSERT INTO `SYS_MENU` VALUES ('120201', '1202', '删除', NULL, 'sys:cache:deleteCache', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('1203', '12', 'SQL监控', 'http://localhost:8888/platform-framework/druid/sql.html', NULL, 1, 'sql', 3);
INSERT INTO `SYS_MENU` VALUES ('1204', '12', 'admin接口文档', 'http://localhost:8888/platform-framework/doc.html', NULL, 1, 'interface', 4);
INSERT INTO `SYS_MENU` VALUES ('1205', '12', 'api接口文档', 'http://localhost:8889/platform-framework-api/doc.html', NULL, 1, 'interface', 5);
INSERT INTO `SYS_MENU` VALUES ('1206', '12', '代码生成器', 'gen/generator', 'sys:generator:list', 1, 'code', 6);
INSERT INTO `SYS_MENU` VALUES ('120501', '1205', '生成代码', NULL, 'sys:generator:code', 2, NULL, 0);
INSERT INTO `SYS_MENU` VALUES ('13', '0', '公众号管理', '', '', 0, 'wechat', 3);
INSERT INTO `SYS_MENU` VALUES ('1301', '13', '公众号消息', 'wx/wx-msg', 'wx:wxmsg:list,wx:wxmsg:info,wx:wxuser:list', 1, 'duanxin', 1);
INSERT INTO `SYS_MENU` VALUES ('130101', '1301', '回复', '', 'wx:wxmsg:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130102', '1301', '删除', '', 'wx:wxmsg:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1302', '13', '公众号菜单', 'wx/wx-menu', '', 1, 'leibie', 2);
INSERT INTO `SYS_MENU` VALUES ('130201', '1302', '更新公众号菜单', '', 'wx:menu:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1303', '13', '消息模板', 'wx/msg-template', 'wx:msgtemplate:list,wx:msgtemplate:info', 1, 'tempmsg', 3);
INSERT INTO `SYS_MENU` VALUES ('130301', '1303', '新增', '', 'wx:msgtemplate:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130302', '1303', '修改', '', 'wx:msgtemplate:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130303', '1303', '删除', '', 'wx:msgtemplate:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1304', '13', '消息模版日志', 'wx/template-msg-log', 'wx:templatemsglog:list,wx:templatemsglog:info', 1, 'orders', 4);
INSERT INTO `SYS_MENU` VALUES ('130401', '1304', '删除', '', 'wx:templatemsglog:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1305', '13', '素材管理', 'wx/wx-assets', 'wx:wxassets:list', 1, 'files', 5);
INSERT INTO `SYS_MENU` VALUES ('130501', '1305', '新增修改', '', 'wx:wxassets:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130502', '1305', '删除', '', 'wx:wxassets:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1306', '13', '草稿箱', 'wx/wx-draft', 'wx:draft:listDraft,wx:draft:getDraft', 1, '', 6);
INSERT INTO `SYS_MENU` VALUES ('130601', '1306', '新增', '', 'wx:draft:addDraft', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130602', '1306', '修改', '', 'wx:draft:updateDraft', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130603', '1306', '删除', '', 'wx:draft:delDraft', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130604', '1306', '发布', '', 'wx:freepublish:submit', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1307', '13', '发布记录', 'wx/wx-free-publish', 'wx:freepublish:getPublicationRecords,wx:freepublish:getArticleFromId', 1, '', 7);
INSERT INTO `SYS_MENU` VALUES ('130701', '1307', '删除', '', 'wx:freepublish:deletePush', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1308', '13', '自动回复规则', 'wx/msg-reply-rule', 'wx:msgreplyrule:list,wx:msgreplyrule:info', 1, 'guize', 8);
INSERT INTO `SYS_MENU` VALUES ('130801', '1308', '新增', '', 'wx:msgreplyrule:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130802', '1308', '修改', '', 'wx:msgreplyrule:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130803', '1308', '删除', '', 'wx:msgreplyrule:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1309', '13', '带参二维码', 'wx/wx-qrcode', 'wx:wxqrcode:list,wx:wxqrcode:info', 1, 'qrcode', 9);
INSERT INTO `SYS_MENU` VALUES ('130901', '1309', '新增', '', 'wx:wxqrcode:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('130902', '1309', '删除', '', 'wx:wxqrcode:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('14', '0', '会员管理', '', '', 0, 'admin', 4);
INSERT INTO `SYS_MENU` VALUES ('1401', '14', '用户管理', 'wx/wx-user', 'wx:wxuser:list,wx:wxuser:info', 1, 'vip', 1);
INSERT INTO `SYS_MENU` VALUES ('140101', '1401', '标签操作', '', 'wx:wxuser:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('1402', '14', '会员等级配置', 'mall/userlevel', 'mall:userlevel:list,mall:userlevel:info', 1, 'level', 2);
INSERT INTO `SYS_MENU` VALUES ('140201', '1402', '新增', null, 'mall:userlevel:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('140202', '1402', '修改', null, 'mall:userlevel:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('140203', '1402', '删除', null, 'mall:userlevel:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1403', '14', '会员购物车', 'mall/cart', 'mall:cart:list,mall:cart:info', 1, 'cart', 3);
INSERT INTO `SYS_MENU` VALUES ('140301', '1403', '删除', null, 'mall:cart:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1404', '14', '收货地址', 'mall/address', 'mall:address:list,mall:address:info', 1, 'dangdifill', 4);
INSERT INTO `SYS_MENU` VALUES ('140401', '1404', '删除', null, 'mall:address:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1405', '14', '会员收藏', 'mall/collect', 'mall:collect:list,mall:collect:info', 1, 'collect', 5);
INSERT INTO `SYS_MENU` VALUES ('140501', '1405', '删除', null, 'mall:collect:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1406', '14', '会员反馈', 'mall/feedback', 'mall:feedback:list,mall:feedback:info', 1, 'feedback', 6);
INSERT INTO `SYS_MENU` VALUES ('140601', '1406', '删除', null, 'mall:feedback:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1407', '14', '历史足迹', 'mall/footprint', 'mall:footprint:list,mall:footprint:info', 1, 'footprint', 7);
INSERT INTO `SYS_MENU` VALUES ('140701', '1407', '删除', null, 'mall:footprint:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1408', '14', '会员优惠券', 'mall/usercoupon', 'mall:usercoupon:list,mall:usercoupon:info', 1, 'coupon', 8);
INSERT INTO `SYS_MENU` VALUES ('140801', '1408', '删除', null, 'mall:usercoupon:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1409', '14', '会员搜索历史', 'mall/searchhistory', 'mall:searchhistory:list,mall:searchhistory:info', 1, 'sousuo', 9);
INSERT INTO `SYS_MENU` VALUES ('140901', '1409', '删除', null, 'mall:searchhistory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('15', '0', '商城配置', null, null, 0, 'mall', 5);
INSERT INTO `SYS_MENU` VALUES ('1501', '15', '品牌制造商', 'mall/brand', 'mall:brand:list,mall:brand:info', 1, 'brand', 1);
INSERT INTO `SYS_MENU` VALUES ('150101', '1501', '新增', null, 'mall:brand:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150102', '1501', '修改', null, 'mall:brand:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150103', '1501', '删除', null, 'mall:brand:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1502', '15', '商品分类', 'mall/category', 'mall:category:list,mall:category:info', 1, 'leibie', 2);
INSERT INTO `SYS_MENU` VALUES ('150201', '1502', '新增', null, 'mall:category:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150202', '1502', '修改', null, 'mall:category:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150203', '1502', '删除', null, 'mall:category:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1503', '15', '广告位', 'mall/adposition', 'mall:adposition:list,mall:adposition:info', 1, 'dangdifill', 3);
INSERT INTO `SYS_MENU` VALUES ('150301', '1503', '新增', null, 'mall:adposition:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150302', '1503', '修改', null, 'mall:adposition:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150303', '1503', '删除', null, 'mall:adposition:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1504', '15', '广告管理', 'mall/ad', 'mall:ad:list,mall:ad:info', 1, 'tubiao', 4);
INSERT INTO `SYS_MENU` VALUES ('150401', '1504', '新增', null, 'mall:ad:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150402', '1504', '修改', null, 'mall:ad:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150403', '1504', '删除', null, 'mall:ad:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1505', '15', '专题分类', 'mall/topiccategory', 'mall:topiccategory:list,mall:topiccategory:info', 1, 'zhedie', 5);
INSERT INTO `SYS_MENU` VALUES ('150501', '1505', '新增', null, 'mall:topiccategory:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150502', '1505', '修改', null, 'mall:topiccategory:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150503', '1505', '删除', null, 'mall:topiccategory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1506', '15', '专题管理', 'mall/topic', 'mall:topic:list,mall:topic:info', 1, 'guize', 6);
INSERT INTO `SYS_MENU` VALUES ('150601', '1506', '新增', null, 'mall:topic:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150602', '1506', '修改', null, 'mall:topic:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150603', '1506', '删除', null, 'mall:topic:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1507', '15', '商品参数类型', 'mall/attributecategory', 'mall:attributecategory:list,mall:attributecategory:info', 1, 'zhedie', 7);
INSERT INTO `SYS_MENU` VALUES ('150701', '1507', '新增', null, 'mall:attributecategory:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150702', '1507', '修改', null, 'mall:attributecategory:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150703', '1507', '删除', null, 'mall:attributecategory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1508', '15', '商品参数属性', 'mall/attribute', 'mall:attribute:list,mall:attribute:info', 1, 'guize', 8);
INSERT INTO `SYS_MENU` VALUES ('150801', '1508', '新增', null, 'mall:attribute:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150802', '1508', '修改', null, 'mall:attribute:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150803', '1508', '删除', null, 'mall:attribute:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1509', '15', '关键词热搜', 'mall/keywords', 'mall:keywords:list,mall:keywords:info', 1, 'sousuo', 9);
INSERT INTO `SYS_MENU` VALUES ('150901', '1509', '新增', null, 'mall:keywords:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150902', '1509', '修改', null, 'mall:keywords:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('150903', '1509', '删除', null, 'mall:keywords:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1510', '15', '物流公司', 'mall/shipping', 'mall:shipping:list,mall:shipping:info', 1, 'shipping', 10);
INSERT INTO `SYS_MENU` VALUES ('151001', '1510', '新增', null, 'mall:shipping:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151002', '1510', '修改', null, 'mall:shipping:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151003', '1510', '删除', null, 'mall:shipping:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1511', '15', '金刚区配置', 'mall/channel', 'mall:channel:list,mall:channel:info', 1, 'daohang', 11);
INSERT INTO `SYS_MENU` VALUES ('151101', '1511', '新增', null, 'mall:channel:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151102', '1511', '修改', null, 'mall:channel:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151103', '1511', '删除', null, 'mall:channel:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1512', '15', '规格KEY配置', 'mall/specification', 'mall:specification:list,mall:specification:info', 1, 'zaixian', 12);
INSERT INTO `SYS_MENU` VALUES ('151201', '1512', '新增', null, 'mall:specification:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151202', '1512', '修改', null, 'mall:specification:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('151203', '1512', '删除', null, 'mall:specification:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('16', '0', '推广管理', null, null, 0, 'gift', 6);
INSERT INTO `SYS_MENU` VALUES ('1601', '16', '优惠券管理', 'mall/coupon', 'mall:coupon:list,mall:coupon:info', 1, 'coupons', 1);
INSERT INTO `SYS_MENU` VALUES ('160101', '1601', '新增', null, 'mall:coupon:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('160102', '1601', '修改', null, 'mall:coupon:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('160103', '1601', '删除', null, 'mall:coupon:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('17', '0', '商品管理', null, null, 0, 'goods', 7);
INSERT INTO `SYS_MENU` VALUES ('1701', '17', '商品管理', 'mall/goods', 'mall:goods:list,mall:goods:info', 1, 'coupons', 1);
INSERT INTO `SYS_MENU` VALUES ('170101', '1701', '新增', null, 'mall:goods:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170102', '1701', '修改', null, 'mall:goods:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170103', '1701', '删除', null, 'mall:goods:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1702', '17', '评论管理', 'mall/comment', 'mall:comment:list,mall:comment:info', 1, 'pinglun', 2);
INSERT INTO `SYS_MENU` VALUES ('170201', '1702', '新增', null, 'mall:comment:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170202', '1702', '修改', null, 'mall:comment:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170203', '1702', '删除', null, 'mall:comment:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('18', '0', '订单管理', null, null, 0, 'orders', 8);
INSERT INTO `SYS_MENU` VALUES ('1801', '18', '订单管理', 'mall/order', 'mall:order:list,mall:order:info,mall:ordergoods:list', 1, 'orders', 1);
INSERT INTO `SYS_MENU` VALUES ('180101', '1801', '新增', null, 'mall:order:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180102', '1801', '修改', null, 'mall:order:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180103', '1801', '删除', null, 'mall:order:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('19', '0', '帮助中心', null, null, 0, 'pinglun', 9);
INSERT INTO `SYS_MENU` VALUES ('1901', '19', '问题类型', 'mall/helptype', 'mall:helptype:list,mall:helptype:info', 1, 'zhedie', 1);
INSERT INTO `SYS_MENU` VALUES ('190101', '1901', '新增', null, 'mall:helptype:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190102', '1901', '修改', null, 'mall:helptype:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190103', '1901', '删除', null, 'mall:helptype:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1902', '19', '常见问题', 'mall/helpissue', 'mall:helpissue:list,mall:helpissue:info', 1, 'issue', 1);
INSERT INTO `SYS_MENU` VALUES ('190201', '1902', '新增', null, 'mall:helpissue:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190202', '1902', '修改', null, 'mall:helpissue:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190203', '1902', '删除', null, 'mall:helpissue:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1903', '19', '商品页问答', 'mall/goodsissue', 'mall:goodsissue:list,mall:goodsissue:info', 1, 'issue', 1);
INSERT INTO `SYS_MENU` VALUES ('190301', '1903', '新增', null, 'mall:goodsissue:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190302', '1903', '修改', null, 'mall:goodsissue:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190303', '1903', '删除', null, 'mall:goodsissue:delete', 2, null, 0);

-- ----------------------------
-- Table structure for `SYS_ORG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ORG`;
CREATE TABLE `SYS_ORG` (
    `ORG_NO` varchar(10) NOT NULL COMMENT '机构编码',
    `ORG_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
    `PARENT_NO` varchar(10) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
    `ORG_TYPE` int(11) DEFAULT NULL COMMENT '级别',
    `STATUS` int(11) DEFAULT '1' COMMENT '状态  0：无效   1：有效',
    `SORT` int(11) DEFAULT NULL COMMENT '排序',
    `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
    `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建人所属机构',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`ORG_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of SYS_ORG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_OSS`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_OSS`;
CREATE TABLE `SYS_OSS` (
    `ID` varchar(32) NOT NULL,
    `URL` varchar(200) DEFAULT NULL COMMENT 'URL地址',
    `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
    `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建人所属机构',
    `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of SYS_OSS
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
    `ROLE_ID` varchar(32) NOT NULL,
    `ROLE_NAME` varchar(100) DEFAULT NULL COMMENT '角色名称',
    `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
    `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
    `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建者所属机构',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_ROLE_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_MENU`;
CREATE TABLE `SYS_ROLE_MENU` (
    `ID` varchar(32) NOT NULL,
    `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
    `MENU_ID` varchar(8) DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of SYS_ROLE_MENU
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_ROLE_ORG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_ORG`;
CREATE TABLE `SYS_ROLE_ORG` (
    `ID` varchar(32) NOT NULL,
    `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
    `ORG_NO` varchar(32) DEFAULT NULL COMMENT '部门ID',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与机构对应关系';

-- ----------------------------
-- Records of SYS_ROLE_ORG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_SMS_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_SMS_LOG`;
CREATE TABLE `SYS_SMS_LOG` (
    `ID` VARCHAR(32) NOT NULL COMMENT '主键',
    `TEMPLATE_ID` VARCHAR(32) DEFAULT NULL COMMENT '模板ID',
    `CODE` VARCHAR(32) DEFAULT NULL COMMENT '验证码',
    `MOBILE` TEXT COMMENT '手机号码。多个以英文逗号隔开',
    `STIME` DATETIME DEFAULT NULL COMMENT '发送时间',
    `SIGN` VARCHAR(32) DEFAULT NULL COMMENT '必填参数。用户签名',
    `SEND_STATUS` INT(1) DEFAULT NULL COMMENT '0成功 1失败',
    `SEND_ID` VARCHAR(32) DEFAULT NULL COMMENT '发送编号',
    `SUCCESS_NUM` INT(11) DEFAULT NULL COMMENT '成功提交数',
    `RETURN_MSG` VARCHAR(50) DEFAULT NULL COMMENT '返回消息',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送日志';

-- ----------------------------
-- Records of SYS_SMS_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_USER`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
    `USER_ID` varchar(32) NOT NULL,
    `USER_NAME` varchar(50) NOT NULL COMMENT '用户名',
    `REAL_NAME` varchar(64) NOT NULL,
    `SEX` tinyint(4) NOT NULL,
    `ORG_NO` varchar(32) DEFAULT NULL COMMENT '机构编码',
    `SALT` varchar(20) DEFAULT NULL COMMENT '盐',
    `EMAIL_HOST` varchar(32) DEFAULT NULL COMMENT '邮件服务器地址',
    `EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱',
    `EMAIL_PW` varchar(64) DEFAULT NULL COMMENT '用户邮箱密码',
    `MOBILE` varchar(100) DEFAULT NULL COMMENT '手机号',
    `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
    `PASSWORD` varchar(100) DEFAULT NULL COMMENT '密码',
    `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
    `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建人所属机构',
    `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`USER_ID`),
    UNIQUE KEY `USERNAME` (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
INSERT INTO `SYS_USER` VALUES ('1', 'admin', '李鹏军', '1', '01', 'YzcmCZNvbXocrsz9dm8e', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', '', NULL, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for `SYS_USER_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
    `ID` varchar(32) NOT NULL,
    `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
    `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of SYS_USER_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_USER_TOKEN`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_TOKEN`;
CREATE TABLE `SYS_USER_TOKEN` (
    `USER_ID` varchar(32) NOT NULL,
    `TOKEN` varchar(100) NOT NULL COMMENT 'token',
    `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '过期时间',
    `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`USER_ID`),
    UNIQUE KEY `TOKEN` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of SYS_USER_TOKEN
-- ----------------------------

-- ----------------------------
-- Table structure for WX_MSG
-- ----------------------------
DROP TABLE IF EXISTS `WX_MSG`;
CREATE TABLE `WX_MSG`  (
    `ID` VARCHAR(32) NOT NULL COMMENT '主键',
    `OPENID` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '微信用户ID',
    `IN_OUT` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '消息方向',
    `MSG_TYPE` char(25) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '消息类型',
    `DETAIL` json NULL COMMENT '消息详情',
    `CREATE_TIME` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '微信消息';

-- ----------------------------
-- Table structure for WX_MSG_REPLY_RULE
-- ----------------------------
DROP TABLE IF EXISTS `WX_MSG_REPLY_RULE`;
CREATE TABLE `WX_MSG_REPLY_RULE`  (
    `RULE_ID` VARCHAR(32) NOT NULL,
    `RULE_NAME` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '规则名称',
    `MATCH_VALUE` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '匹配的关键词、事件等',
    `EXACT_MATCH` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否精确匹配',
    `REPLY_TYPE` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '1' COMMENT '回复消息类型',
    `REPLY_CONTENT` varchar(1024) CHARACTER SET utf8 NOT NULL COMMENT '回复消息内容',
    `STATUS` tinyint(1) NOT NULL DEFAULT 1 COMMENT '规则是否有效',
    `DESC` varchar(256) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '备注说明',
    `EFFECT_TIME_START` time(0) NULL DEFAULT '00:00:00' COMMENT '生效起始时间',
    `EFFECT_TIME_END` time(0) NULL DEFAULT '23:59:59' COMMENT '生效结束时间',
    `PRIORITY` int(3) UNSIGNED NULL DEFAULT 0 COMMENT '规则优先级',
    `UPDATE_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`RULE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '自动回复规则';

-- ----------------------------
-- Table structure for wx_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `WX_QR_CODE`;
CREATE TABLE `WX_QR_CODE`  (
    `ID` VARCHAR(32) NOT NULL COMMENT 'ID',
    `IS_TEMP` tinyint(1) NULL DEFAULT NULL COMMENT '是否为临时二维码',
    `SCENE_STR` varchar(64) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '场景值ID',
    `TICKET` varchar(256) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '二维码ticket',
    `URL` varchar(256) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '二维码图片解析后的地址',
    `EXPIRE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '该二维码失效时间',
    `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '该二维码创建时间',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '公众号带参二维码';

-- ----------------------------
-- Table structure for WX_TEMPLATE_MSG_LOG
-- ----------------------------
DROP TABLE IF EXISTS `WX_TEMPLATE_MSG_LOG`;
CREATE TABLE `WX_TEMPLATE_MSG_LOG`  (
    `LOG_ID` VARCHAR(32) NOT NULL COMMENT 'ID',
    `TOUSER` varchar(50) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '用户openid',
    `TEMPLATE_ID` varchar(50) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT 'templateid',
    `DATA` json NULL COMMENT '消息数据',
    `URL` varchar(256) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '消息链接',
    `MINIPROGRAM` json NULL COMMENT '小程序信息',
    `SEND_TIME` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
    `SEND_RESULT` varchar(256) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '发送结果',
    PRIMARY KEY (`LOG_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '微信模版消息发送记录';

-- ----------------------------
-- Table structure for WX_MSG_TEMPLATE
-- ----------------------------
DROP TABLE IF EXISTS `WX_MSG_TEMPLATE`;
CREATE TABLE `WX_MSG_TEMPLATE`  (
    `ID` VARCHAR(32) NOT NULL COMMENT 'ID',
    `TEMPLATE_ID` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '公众号模板ID',
    `NAME` varchar(50) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '模版名称',
    `TITLE` varchar(20) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '标题',
    `CONTENT` text CHARACTER SET utf8 NULL COMMENT '模板内容',
    `DATA` json NULL COMMENT '消息内容',
    `URL` varchar(256) CHARACTER SET utf8 NULL DEFAULT NULL COMMENT '链接',
    `MINIPROGRAM` json NULL COMMENT '小程序信息',
    `STATUS` tinyint(1) UNSIGNED NOT NULL COMMENT '是否有效',
    `UPDATE_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `IDX_NAME`(`NAME`) USING BTREE COMMENT '模板名称',
    INDEX `IDX_STATUS`(`STATUS`) USING BTREE COMMENT '模板状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT = '消息模板';

-- ----------------------------
-- Table structure for WX_USER
-- ----------------------------
DROP TABLE IF EXISTS `WX_USER`;
CREATE TABLE `WX_USER`  (
    `ID` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    `MA_OPENID` varchar(64) DEFAULT NULL COMMENT '微信openId',
    `OPENID` varchar(64) DEFAULT NULL COMMENT '微信openId',
    `PHONE` char(32) DEFAULT NULL COMMENT '手机号',
    `NICKNAME` varchar(128) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '微信昵称',
    `SEX` tinyint(4) DEFAULT NULL COMMENT '性别(0-未知、1-男、2-女)',
    `CITY` varchar(32) DEFAULT NULL COMMENT '城市',
    `PROVINCE` varchar(32) DEFAULT NULL COMMENT '省份',
    `HEAD_IMG_URL` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户头像',
    `SUBSCRIBE_TIME` datetime DEFAULT NULL COMMENT '订阅时间',
    `SUBSCRIBE` tinyint UNSIGNED DEFAULT 1 COMMENT '是否关注',
    `UNION_ID` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户唯一标识',
    `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
    `TAGID_LIST` json DEFAULT NULL COMMENT '标签ID列表',
    `SUBSCRIBE_SCENE` varchar(50) DEFAULT NULL COMMENT '关注场景',
    `QR_SCENE_STR` varchar(64) DEFAULT NULL COMMENT '扫码场景值',
    `ALL_INTEGRAL` int(11) DEFAULT 0 COMMENT '总积分',
    `INTEGRAL` int(11) DEFAULT 0 COMMENT '剩余积分',
    `USER_LEVEL_ID` varchar(32) DEFAULT NULL COMMENT '会员等级ID',
    `REGISTER_TIME` datetime DEFAULT NULL COMMENT '注册时间',
    `REGISTER_IP` varchar(64) DEFAULT NULL COMMENT '注册ip',
    `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
    `LAST_LOGIN_IP` varchar(32) DEFAULT NULL COMMENT '最后登录IP',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UNION_ID` (`UNION_ID`),
    INDEX `IDX_UNIONID`(`UNION_ID`) USING BTREE COMMENT 'unionId'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT = '会员表';

-- ----------------------------
-- Table structure for WX_USER
-- ----------------------------
