/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : platform-shop

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-25 17:08:25
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
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
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
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('PlatformScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('PlatformScheduler', 'TASK_2', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

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
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('PlatformScheduler', 'TASK_1', 'DEFAULT', null, 'com.platform.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720025636F6D2E706C6174666F726D2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000474657374740008706C6174666F726D74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('PlatformScheduler', 'TASK_2', 'DEFAULT', null, 'com.platform.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720025636F6D2E706C6174666F726D2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

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
INSERT INTO `QRTZ_LOCKS` VALUES ('PlatformScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('PlatformScheduler', 'TRIGGER_ACCESS');

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
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('PlatformScheduler', 'Win_PC1508754695596', '1508756226419', '15000');

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
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
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
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
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
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('PlatformScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1507390200000', '-1', '5', 'PAUSED', 'CRON', '1507388787000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720025636F6D2E706C6174666F726D2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000474657374740008706C6174666F726D74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);
INSERT INTO `QRTZ_TRIGGERS` VALUES ('PlatformScheduler', 'TASK_2', 'DEFAULT', 'TASK_2', 'DEFAULT', null, '1507390200000', '-1', '5', 'PAUSED', 'CRON', '1507388787000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720025636F6D2E706C6174666F726D2E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'test', 'platform', '0 0/30 * * * ?', '1', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `schedule_job` VALUES ('2', 'testTask', 'test2', null, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56');

-- ----------------------------
-- Table structure for `schedule_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('3', 'CLOUD_STORAGE_CONFIG_KEY', '{"aliyunAccessKeyId":"1","aliyunAccessKeySecret":"1","aliyunBucketName":"1","aliyunDomain":"http://image.111.com","aliyunEndPoint":"1","aliyunPrefix":"1","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"IXYAaaAwc-ZSuInpEoEJq_CivmcAjWLq1HmpK9dr","qiniuBucketName":"wxmall","qiniuDomain":"http://p9kyr79ne.bkt.clouddn.com","qiniuPrefix":"upload","qiniuSecretKey":"dVlk3dhOAGubYdiCyybE13o5KpjyqGhqh428ufxP","type":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '总部', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '1', '合肥分公司', '1', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '上海分公司', '2', '0');
INSERT INTO `sys_dept` VALUES ('4', '3', '技术部', '0', '0');
INSERT INTO `sys_dept` VALUES ('5', '3', '销售部', '1', '0');
INSERT INTO `sys_dept` VALUES ('6', '0', '测试', '0', '-1');
INSERT INTO `sys_dept` VALUES ('7', '0', 'ceshi', '0', '-1');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=894 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for `sys_macro`
-- ----------------------------
DROP TABLE IF EXISTS `sys_macro`;
CREATE TABLE `sys_macro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(255) DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `value` varchar(2000) DEFAULT NULL COMMENT '值',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态，0：隐藏   1：显示',
  `type` tinyint(20) DEFAULT NULL COMMENT '类型,0:目录，1:参数配置',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `gmt_create` date DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` date DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=gbk COMMENT='通用字典表';

-- ----------------------------
-- Records of sys_macro
-- ----------------------------
INSERT INTO `sys_macro` VALUES ('5', null, '单位', 'goodsUnit', '1', '0', null, null, '2017-08-30', null);
INSERT INTO `sys_macro` VALUES ('6', '5', '个', '个', '1', '1', null, null, '2017-08-30', null);
INSERT INTO `sys_macro` VALUES ('7', '5', '只', '只', '1', '1', '2', null, '2017-10-06', '2017-10-06');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=375 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '11', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员列表', 'sys/user.html', null, '1', 'fa fa-user', '1', '0');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'sys/role.html', null, '1', 'fa fa-user-secret', '2', '0');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu.html', null, '1', 'fa fa-folder-open', '4', '0');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-linux', '9', '0');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'sys/schedule.html', null, '1', 'fa fa-tasks', '5', '0');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '系统参数', 'sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '7', '0');
INSERT INTO `sys_menu` VALUES ('28', '1', '代码生成器', 'sys/generator.html', 'sys:generator:list,sys:generator:code', '1', 'fa fa-rocket', '10', '0');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '8', '0');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'sys/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6', '0');
INSERT INTO `sys_menu` VALUES ('31', '0', '功能测试', null, null, '0', 'fa fa-bug', '11', '0');
INSERT INTO `sys_menu` VALUES ('200', '0', '会员管理', null, null, '0', 'fa fa-user-circle-o', '1', '0');
INSERT INTO `sys_menu` VALUES ('201', '200', '会员管理', 'shop/shopuser.html', null, '1', 'fa fa-user-md', '1', '0');
INSERT INTO `sys_menu` VALUES ('202', '201', '查看', null, 'user:list,user:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('203', '201', '新增', null, 'user:save', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('204', '201', '修改', null, 'user:update', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('205', '201', '删除', null, 'user:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('206', '31', 'iviewDemo', 'test/iviewDemo.html', null, '1', 'fa fa-etsy', '0', '0');
INSERT INTO `sys_menu` VALUES ('207', '200', '会员等级', 'shop/userlevel.html', null, '1', 'fa fa-star-o', '0', '1');
INSERT INTO `sys_menu` VALUES ('208', '207', '查看', null, 'userlevel:list,userlevel:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('209', '207', '新增', null, 'userlevel:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('210', '207', '修改', null, 'userlevel:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('211', '207', '删除', null, 'userlevel:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('212', '200', '收货地址管理', 'shop/address.html', null, '1', 'fa fa-map-marker', '6', '0');
INSERT INTO `sys_menu` VALUES ('213', '212', '查看', null, 'address:list,address:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('214', '212', '新增', null, 'address:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('215', '212', '修改', null, 'address:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('216', '212', '删除', null, 'address:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('222', '0', '商城配置', null, null, '0', 'fa fa-shopping-cart', '2', '0');
INSERT INTO `sys_menu` VALUES ('223', '222', '商品属性种类', 'shop/attributecategory.html', null, '1', 'fa fa-sun-o', '0', '0');
INSERT INTO `sys_menu` VALUES ('224', '223', '查看', null, 'attributecategory:list,attributecategory:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('225', '223', '新增', null, 'attributecategory:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('226', '223', '修改', null, 'attributecategory:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('227', '223', '删除', null, 'attributecategory:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('233', '243', '广告列表', 'shop/ad.html', null, '1', 'fa fa-pencil', '1', '0');
INSERT INTO `sys_menu` VALUES ('234', '233', '查看', null, 'ad:list,ad:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('235', '233', '新增', null, 'ad:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('236', '233', '修改', null, 'ad:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('237', '233', '删除', null, 'ad:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('238', '243', '广告位置', 'shop/adposition.html', null, '1', 'fa fa-map-pin', '0', '0');
INSERT INTO `sys_menu` VALUES ('239', '238', '查看', null, 'adposition:list,adposition:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('240', '238', '新增', null, 'adposition:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('241', '238', '修改', null, 'adposition:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('242', '238', '删除', null, 'adposition:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('243', '0', '推广管理', null, null, '0', 'fa fa-hand-paper-o', '5', '0');
INSERT INTO `sys_menu` VALUES ('244', '243', '优惠劵管理', 'shop/coupon.html', null, '1', 'fa fa-cc-visa', '2', '0');
INSERT INTO `sys_menu` VALUES ('245', '244', '查看', null, 'coupon:list,coupon:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('246', '244', '新增', null, 'coupon:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('247', '244', '修改', null, 'coupon:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('248', '244', '删除', null, 'coupon:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('249', '200', '会员优惠劵', 'shop/usercoupon.html', null, '1', 'fa fa-cc-visa', '2', '0');
INSERT INTO `sys_menu` VALUES ('250', '249', '查看', null, 'usercoupon:list,usercoupon:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('251', '249', '新增', null, 'usercoupon:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('252', '249', '修改', null, 'usercoupon:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('253', '249', '删除', null, 'usercoupon:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('254', '222', '品牌制造商', 'shop/brand.html', null, '1', 'fa fa-registered', '5', '0');
INSERT INTO `sys_menu` VALUES ('255', '254', '查看', null, 'brand:list,brand:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('256', '254', '新增', null, 'brand:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('257', '254', '修改', null, 'brand:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('258', '254', '删除', null, 'brand:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('259', '222', '商品规格', 'shop/specification.html', null, '1', 'fa fa-hand-rock-o', '1', '0');
INSERT INTO `sys_menu` VALUES ('260', '259', '查看', null, 'specification:list,specification:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('261', '259', '新增', null, 'specification:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('262', '259', '修改', null, 'specification:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('263', '259', '删除', null, 'specification:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('264', '200', '会员收藏', 'shop/collect.html', null, '1', 'fa fa-star', '3', '0');
INSERT INTO `sys_menu` VALUES ('265', '264', '查看', null, 'collect:list,collect:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('266', '264', '删除', null, 'collect:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('270', '243', '专题管理', 'shop/topic.html', null, '1', 'fa fa-ticket', '5', '1');
INSERT INTO `sys_menu` VALUES ('271', '270', '查看', null, 'topic:list,topic:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('272', '270', '新增', null, 'topic:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('273', '270', '修改', null, 'topic:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('274', '270', '删除', null, 'topic:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('275', '243', '专题分类', 'shop/topiccategory.html', null, '1', 'fa fa-tint', '4', '1');
INSERT INTO `sys_menu` VALUES ('276', '275', '查看', null, 'topiccategory:list,topiccategory:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('277', '275', '新增', null, 'topiccategory:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('278', '275', '修改', null, 'topiccategory:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('279', '275', '删除', null, 'topiccategory:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('280', '200', '会员足迹', 'shop/footprint.html', null, '1', 'fa fa-history', '6', '0');
INSERT INTO `sys_menu` VALUES ('281', '280', '查看', null, 'footprint:list,footprint:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('282', '280', '删除', null, 'footprint:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('283', '200', '搜索历史', 'shop/searchhistory.html', null, '1', 'fa fa-search', '6', '0');
INSERT INTO `sys_menu` VALUES ('284', '283', '查看', null, 'searchhistory:list,searchhistory:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('285', '283', '删除', null, 'searchhistory:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('286', '200', '购物车', 'shop/cart.html', null, '1', 'fa fa-shopping-cart', '6', '0');
INSERT INTO `sys_menu` VALUES ('287', '286', '查看', null, 'cart:list,cart:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('288', '286', '删除', null, 'cart:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('289', '357', '所有商品', 'shop/goods.html', null, '1', 'fa fa-shopping-bag', '1', '0');
INSERT INTO `sys_menu` VALUES ('290', '289', '查看', null, 'goods:list,goods:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('291', '289', '新增', null, 'goods:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('292', '289', '修改', null, 'goods:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('293', '289', '删除', null, 'goods:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('294', '374', '所有管理', 'shop/order.html', null, '1', 'fa fa-sitemap', '6', '0');
INSERT INTO `sys_menu` VALUES ('295', '294', '查看', null, 'order:list,order:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('296', '294', '发货', null, 'order:sendGoods', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('297', '222', '商品类型', 'shop/category.html', null, '1', 'fa fa-ship', '3', '0');
INSERT INTO `sys_menu` VALUES ('298', '297', '查看', null, 'category:list,category:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('299', '297', '新增', null, 'category:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('300', '297', '修改', null, 'category:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('301', '297', '删除', null, 'category:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('302', '1', '通用字典表', 'sys/macro.html', null, '1', 'fa fa-book', '6', '0');
INSERT INTO `sys_menu` VALUES ('303', '302', '查看', null, 'sys:macro:list,sys:macro:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('304', '302', '新增', null, 'sys:macro:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('305', '302', '修改', null, 'sys:macro:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('306', '302', '删除', null, 'sys:macro:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('307', '222', '渠道管理', 'shop/channel.html', null, '1', 'fa fa-road', '2', '1');
INSERT INTO `sys_menu` VALUES ('308', '307', '查看', null, 'channel:list,channel:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('309', '307', '新增', null, 'channel:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('310', '307', '修改', null, 'channel:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('311', '307', '删除', null, 'channel:delete', '2', null, '6', '0');
# INSERT INTO `sys_menu` VALUES ('312', '0', '微信公众号', null, null, '0', 'fa fa-weixin', '6', '0');
# INSERT INTO `sys_menu` VALUES ('313', '0', '进销存', null, null, '0', 'fa fa-truck', '6', '0');
# INSERT INTO `sys_menu` VALUES ('314', '0', '统计报表', null, null, '0', 'fa fa-line-chart', '7', '0');
INSERT INTO `sys_menu` VALUES ('315', '222', '商品问答', 'shop/goodsissue.html', null, '1', 'fa fa-question-circle-o', '6', '0');
INSERT INTO `sys_menu` VALUES ('316', '315', '查看', null, 'goodsissue:list,goodsissue:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('317', '315', '新增', null, 'goodsissue:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('318', '315', '修改', null, 'goodsissue:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('319', '315', '删除', null, 'goodsissue:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('325', '222', '反馈', 'shop/feedback.html', null, '1', 'fa fa-mail-reply-all', '6', '0');
INSERT INTO `sys_menu` VALUES ('326', '325', '查看', null, 'feedback:list,feedback:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('327', '325', '新增', null, 'feedback:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('328', '325', '修改', null, 'feedback:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('329', '325', '删除', null, 'feedback:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('330', '244', '发放', null, 'coupon:publish', '2', null, '4', '0');
INSERT INTO `sys_menu` VALUES ('331', '222', '关键词', 'shop/keywords.html', null, '1', 'fa fa-underline', '6', '0');
INSERT INTO `sys_menu` VALUES ('332', '331', '查看', null, 'keywords:list,keywords:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('333', '331', '新增', null, 'keywords:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('334', '331', '修改', null, 'keywords:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('335', '331', '删除', null, 'keywords:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('336', '357', '用户评论', 'shop/comment.html', null, '1', 'fa fa-commenting', '6', '0');
INSERT INTO `sys_menu` VALUES ('337', '336', '查看', null, 'comment:list,comment:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('338', '336', '新增', null, 'comment:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('339', '336', '修改', null, 'comment:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('340', '336', '删除', null, 'comment:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('341', '336', '修改状态', null, 'comment:toggleStatus', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('342', '357', '产品设置', 'shop/product.html', null, '1', 'fa fa-paypal', '3', '0');
INSERT INTO `sys_menu` VALUES ('343', '342', '查看', null, 'product:list,product:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('344', '342', '新增', null, 'product:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('345', '342', '修改', null, 'product:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('346', '342', '删除', null, 'product:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('352', '357', '商品规格', 'shop/goodsspecification.html', null, '1', 'fa fa-deafness', '2', '0');
INSERT INTO `sys_menu` VALUES ('353', '352', '查看', null, 'goodsspecification:list,goodsspecification:info', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('354', '352', '新增', null, 'goodsspecification:save', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('355', '352', '修改', null, 'goodsspecification:update', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('356', '352', '删除', null, 'goodsspecification:delete', '2', null, '6', '0');
INSERT INTO `sys_menu` VALUES ('357', '0', '编辑商品', null, null, '0', 'fa fa-edit', '3', '0');
INSERT INTO `sys_menu` VALUES ('358', '357', '商品回收站', 'shop/goodshistory.html', '', '1', 'fa fa-history', '12', '0');
INSERT INTO `sys_menu` VALUES ('359', '358', '恢复', null, 'goods:back', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('360', '294', '确认收货', null, 'order:confirm', '2', null, '0', '0');
# INSERT INTO `sys_menu` VALUES ('366', '0', 'CMS模块', null, null, '0', 'fa fa-leanpub', '6', '0');
INSERT INTO `sys_menu` VALUES ('368', '1', '部门管理', 'sys/dept.html', null, '1', 'fa fa-sitemap', '3', '0');
INSERT INTO `sys_menu` VALUES ('369', '368', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('370', '368', '新增', null, 'sys:dept:save', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('371', '368', '修改', null, 'sys:dept:update', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('372', '368', '删除', null, 'sys:dept:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('373', '368', '选择部门', null, 'sys:dept:select', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('374', '0', '订单管理', null, null, '0', 'fa fa-first-order', '4', '0');
INSERT INTO `sys_menu` VALUES ('375', '0', '短信平台', null, null, '0', 'fa fa-television', '9', '0');
INSERT INTO `sys_menu` VALUES ('376','375', '短信配置', 'sys/smslog.html', 'sys:smslog:list,sys:smslog:info', '1', 'fa fa-envelope-open', '1', '0');
INSERT INTO `sys_menu` VALUES ('377', '1', '地区管理', 'sys/region.html', null, '1', 'fa fa-map-pin', '8', '0');
INSERT INTO `sys_menu` VALUES ('378', '377', '删除', null, 'sys:region:delete', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('379', '377', '修改', '', 'sys:region:update', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('380', '377', '新增', null, 'sys:region:save', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('381', '377', '查看', null, 'sys:region:list,sys:region:info', '2', null, '0', '0');
INSERT INTO `sys_menu` VALUES ('382', '31', 'doc.html', 'doc.html', '', '1', 'fa fa-code', '0', '0');
-- ----------------------------
-- Table structure for `sys_oss`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5', '超级管理员', '超级管理员', '1', '2017-09-18 00:40:15', '1');

-- ----------------------------
-- Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '939961241@qq.com', '15209831990', '1', '1', '2016-11-11 11:11:11', '1');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for `tb_token`
-- ----------------------------
DROP TABLE IF EXISTS `tb_token`;
CREATE TABLE `tb_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

DROP TABLE IF EXISTS sys_region;
create table sys_region
(
  id smallint auto_increment
    primary key,
  parent_id smallint default '0' ,
  name varchar(120) default '' not null,
  type tinyint(1) default '2' not null,
  agency_id smallint default '0'
)
;

create index agency_id
  on sys_region (agency_id)
;

create index parent_id
  on sys_region (parent_id)
;

create index region_type
  on sys_region (type)
;

DROP TABLE IF EXISTS `sys_sms_log`;
CREATE TABLE `sys_sms_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` bigint(20) COMMENT '操作人',
  `mobile` text COMMENT '必填参数。手机号码。多个以英文逗号隔开',
  `template_id` int(11) COMMENT '模板ID',
  `code` int(11) COMMENT '验证码',
  `sign` varchar(32) COMMENT '必填参数。用户签名',
  `send_status` int(11) COMMENT '1成功 0失败',
  `send_id` varchar(32) COMMENT '发送编号',
  `success_num` int(11) COMMENT '成功提交数',
  `return_msg` varchar(50) COMMENT '返回消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO sys_config VALUES ('2', 'SMS_CONFIG_KEY', '{"domain":"http://web.cr6868.com/asmx/smsservice.aspx?","name":"","pwd":"","sign":"","type":1}', 0, '短信配置');
