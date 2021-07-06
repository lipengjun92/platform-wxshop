/*
 Navicat Premium Data Transfer

 Source Server         : 140.143.236.168
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : 140.143.236.168:3307
 Source Schema         : jflow

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : 65001

 Date: 02/07/2021 14:30:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo_dingdan
-- ----------------------------
DROP TABLE IF EXISTS `demo_dingdan`;
CREATE TABLE `demo_dingdan`  (
  `KHBH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户编号',
  `LiaoHao` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '料号',
  `DanJia` float(20, 2) NULL DEFAULT NULL COMMENT '单价',
  `SL` int(255) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`KHBH`, `LiaoHao`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of demo_dingdan
-- ----------------------------
INSERT INTO `demo_dingdan` VALUES ('001', '001', 12.34, 10);

-- ----------------------------
-- Table structure for frm_ctrlmodel
-- ----------------------------
DROP TABLE IF EXISTS `frm_ctrlmodel`;
CREATE TABLE `frm_ctrlmodel`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FrmID` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `CtrlObj` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制权限',
  `IsEnableAll` int(11) NULL DEFAULT NULL COMMENT '任何人都可以',
  `IsEnableStation` int(11) NULL DEFAULT NULL COMMENT '按照岗位计算',
  `IsEnableDept` int(11) NULL DEFAULT NULL COMMENT '按照绑定的部门计算',
  `IsEnableUser` int(11) NULL DEFAULT NULL COMMENT '按照绑定的人员计算',
  `IsEnableMyDept` int(11) NULL DEFAULT NULL COMMENT '按照本部门计算',
  `IDOfUsers` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的人员ID',
  `IDOfStations` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的岗位ID',
  `IDOfDepts` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的部门ID',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '控制模型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_ctrlmodeldtl
-- ----------------------------
DROP TABLE IF EXISTS `frm_ctrlmodeldtl`;
CREATE TABLE `frm_ctrlmodeldtl`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FrmID` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `CtrlObj` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制权限',
  `OrgType` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织类型',
  `IDs` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IDs',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '控制模型表Dtl' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_deptcreate
-- ----------------------------
DROP TABLE IF EXISTS `frm_deptcreate`;
CREATE TABLE `frm_deptcreate`  (
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单 - 主键',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可以创建部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FrmID`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_empcreate
-- ----------------------------
DROP TABLE IF EXISTS `frm_empcreate`;
CREATE TABLE `frm_empcreate`  (
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FrmID`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据可创建的人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_generbill
-- ----------------------------
DROP TABLE IF EXISTS `frm_generbill`;
CREATE TABLE `frm_generbill`  (
  `WorkID` int(11) NOT NULL COMMENT 'WorkID - 主键',
  `FK_FrmTree` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据类别',
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据ID',
  `FrmName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据名称',
  `BillNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编号',
  `Title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `BillSta` int(11) NULL DEFAULT NULL COMMENT '状态(简),枚举类型:0 运行中;1 已完成;2 其他;',
  `BillState` int(11) NULL DEFAULT NULL COMMENT '单据状态,枚举类型:0 空白;1 草稿;2 编辑中;100 归档;',
  `Starter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `StarterName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `Sender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  `SendDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据活动时间',
  `NDStep` int(11) NULL DEFAULT NULL COMMENT '步骤',
  `NDStepName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '步骤名称',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `DeptName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `PRI` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `SDTOfNode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点应完成时间',
  `SDTOfFlow` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据应完成时间',
  `PFrmID` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父单据编号',
  `PWorkID` int(11) NULL DEFAULT NULL COMMENT '父单据ID',
  `TSpan` int(11) NULL DEFAULT NULL COMMENT '时间段,枚举类型:0 本周;1 上周;2 上上周;3 更早;',
  `AtPara` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数(单据运行设置临时存储的参数)',
  `Emps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参与人',
  `GUID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `FK_NY` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`WorkID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据控制表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_method
-- ----------------------------
DROP TABLE IF EXISTS `frm_method`;
CREATE TABLE `frm_method`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FrmID` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `MethodID` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法ID',
  `MethodName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `WarningMsg` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能执行警告信息',
  `RefMethodType` int(11) NULL DEFAULT NULL COMMENT '方法类型,枚举类型:0 功能;1 模态窗口打开;2 新窗口打开;3 右侧窗口打开;4 实体集合的功能;',
  `IsMyBillToolBar` int(11) NULL DEFAULT NULL COMMENT '是否显示在MyBill.htm工具栏上',
  `IsMyBillToolExt` int(11) NULL DEFAULT NULL COMMENT '是否显示在MyBill.htm工具栏右边的更多按钮里',
  `IsSearchBar` int(11) NULL DEFAULT NULL COMMENT '是否显示在Search.htm工具栏上(用于批处理)',
  `PopHeight` int(11) NULL DEFAULT NULL COMMENT '弹窗高度',
  `PopWidth` int(11) NULL DEFAULT NULL COMMENT '弹窗宽度',
  `MsgSuccess` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成功提示信息',
  `MsgErr` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败提示信息',
  `WhatAreYouTodo` int(11) NULL DEFAULT NULL COMMENT '执行完毕后干啥？,枚举类型:0 关闭提示窗口;1 关闭提示窗口并刷新;2 转入到Search.htm页面上去;',
  `Idx` int(11) NULL DEFAULT NULL COMMENT 'Idx',
  `ShowModel` int(11) NULL DEFAULT NULL COMMENT '显示方式,枚举类型:0 按钮;1 超链接;',
  `MethodDocTypeOfFunc` int(11) NULL DEFAULT NULL COMMENT '内容类型,枚举类型:0 SQL;1 URL;2 JavaScript;3 业务单元;',
  `MethodDoc_Url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '连接URL',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单方法' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_stationcreate
-- ----------------------------
DROP TABLE IF EXISTS `frm_stationcreate`;
CREATE TABLE `frm_stationcreate`  (
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可以创建岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FrmID`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_stationdept
-- ----------------------------
DROP TABLE IF EXISTS `frm_stationdept`;
CREATE TABLE `frm_stationdept`  (
  `FK_Frm` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单据编号 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FK_Frm`, `FK_Station`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据岗位部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for frm_track
-- ----------------------------
DROP TABLE IF EXISTS `frm_track`;
CREATE TABLE `frm_track`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FrmID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `FrmName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单名称(可以为空)',
  `ActionType` int(11) NULL DEFAULT NULL COMMENT '类型',
  `ActionTypeText` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(名称)',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT '工作ID/OID',
  `Msg` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息',
  `Rec` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人',
  `RecName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期时间',
  `DeptNo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `DeptName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `FlowNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程ID',
  `FlowName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `NodeID` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `NodeName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单轨迹表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_app
-- ----------------------------
DROP TABLE IF EXISTS `gpm_app`;
CREATE TABLE `gpm_app`  (
  `No` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `AppModel` int(11) NULL DEFAULT NULL COMMENT '应用类型,枚举类型:0 BS系统;1 CS系统;',
  `Name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '名称',
  `FK_AppSort` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别,外键:对应物理表:GPM_AppSort,表描述:系统类别',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '启用?',
  `UrlExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '默认连接',
  `SubUrl` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '第二连接',
  `UidControl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名控件',
  `PwdControl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码控件',
  `ActionType` int(11) NULL DEFAULT NULL COMMENT '提交类型,枚举类型:0 GET;1 POST;',
  `SSOType` int(11) NULL DEFAULT NULL COMMENT '登录方式,枚举类型:0 SID验证;1 连接;2 表单提交;3 不传值;',
  `OpenWay` int(11) NULL DEFAULT NULL COMMENT '打开方式,枚举类型:0 新窗口;1 本窗口;2 覆盖新窗口;',
  `RefMenuNo` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联菜单编号',
  `AppRemark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ICON',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFileExt',
  `WebPath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WebPath',
  `MyFileH` int(11) NULL DEFAULT NULL COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT NULL COMMENT 'MyFileW',
  `MyFileSize` float NULL DEFAULT NULL COMMENT 'MyFileSize',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_appsort
-- ----------------------------
DROP TABLE IF EXISTS `gpm_appsort`;
CREATE TABLE `gpm_appsort`  (
  `No` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `RefMenuNo` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联的菜单编号',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统类别' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_bar
-- ----------------------------
DROP TABLE IF EXISTS `gpm_bar`;
CREATE TABLE `gpm_bar`  (
  `No` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '名称',
  `Title` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标题',
  `OpenWay` int(11) NULL DEFAULT NULL COMMENT '打开方式,枚举类型:0 新窗口;1 本窗口;2 覆盖新窗口;',
  `IsLine` int(11) NULL DEFAULT NULL COMMENT '是否独占一行',
  `MoreUrl` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '更多标签Url',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '信息块' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_baremp
-- ----------------------------
DROP TABLE IF EXISTS `gpm_baremp`;
CREATE TABLE `gpm_baremp`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Bar` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息块编号',
  `FK_Emp` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `Title` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标题',
  `IsShow` int(11) NULL DEFAULT NULL COMMENT '是否显示',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员信息块' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_empapp
-- ----------------------------
DROP TABLE IF EXISTS `gpm_empapp`;
CREATE TABLE `gpm_empapp`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Emp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `FK_App` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统',
  `Name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '系统-名称',
  `UrlExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '连接',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFileExt',
  `WebPath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WebPath',
  `MyFileH` int(11) NULL DEFAULT NULL COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT NULL COMMENT 'MyFileW',
  `MyFileSize` float NULL DEFAULT NULL COMMENT 'MyFileSize',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员与系统权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_empmenu
-- ----------------------------
DROP TABLE IF EXISTS `gpm_empmenu`;
CREATE TABLE `gpm_empmenu`  (
  `FK_Menu` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单功能,主外键:对应物理表:Port_Emp,表描述:用户',
  `FK_App` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统编号',
  `IsChecked` int(11) NULL DEFAULT NULL COMMENT '是否选中',
  PRIMARY KEY (`FK_Menu`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员菜单对应' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_group
-- ----------------------------
DROP TABLE IF EXISTS `gpm_group`;
CREATE TABLE `gpm_group`  (
  `No` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限组' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_groupdept
-- ----------------------------
DROP TABLE IF EXISTS `gpm_groupdept`;
CREATE TABLE `gpm_groupdept`  (
  `FK_Group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限组 - 主键',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FK_Group`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限组部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_groupemp
-- ----------------------------
DROP TABLE IF EXISTS `gpm_groupemp`;
CREATE TABLE `gpm_groupemp`  (
  `FK_Group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限组 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FK_Group`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限组人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_groupmenu
-- ----------------------------
DROP TABLE IF EXISTS `gpm_groupmenu`;
CREATE TABLE `gpm_groupmenu`  (
  `FK_Group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限组 - 主键',
  `FK_Menu` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单 - 主键',
  `IsChecked` int(11) NULL DEFAULT NULL COMMENT '是否选中',
  PRIMARY KEY (`FK_Group`, `FK_Menu`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限组菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_groupstation
-- ----------------------------
DROP TABLE IF EXISTS `gpm_groupstation`;
CREATE TABLE `gpm_groupstation`  (
  `FK_Group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限组 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Group`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限组岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_grouptype
-- ----------------------------
DROP TABLE IF EXISTS `gpm_grouptype`;
CREATE TABLE `gpm_grouptype`  (
  `No` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组类型' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_menu
-- ----------------------------
DROP TABLE IF EXISTS `gpm_menu`;
CREATE TABLE `gpm_menu`  (
  `No` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能编号 - 主键',
  `ParentNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点,外键:对应物理表:GPM_Menu,表描述:系统菜单',
  `Name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `MenuType` int(11) NULL DEFAULT NULL COMMENT '菜单类型,枚举类型:0 系统根目录;1 系统类别;2 系统;3 目录;4 功能/界面;5 功能控制点;',
  `FK_App` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统,外键:对应物理表:GPM_App,表描述:系统',
  `OpenWay` int(11) NULL DEFAULT NULL COMMENT '打开方式,枚举类型:0 新窗口;1 本窗口;2 覆盖新窗口;',
  `UrlExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'PC端连接',
  `MobileUrlExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '移动端连接',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用?',
  `Icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Icon',
  `MenuCtrlWay` int(11) NULL DEFAULT NULL COMMENT '控制方式,枚举类型:0 按照设置的控制;1 任何人都可以使用;2 Admin用户可以使用;',
  `Flag` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标记',
  `Tag1` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag1',
  `Tag2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `Tag3` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag3',
  `WebPath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图标',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'MyFileExt',
  `MyFileH` int(11) NULL DEFAULT 0 COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT 0 COMMENT 'MyFileW',
  `MyFileSize` float(11, 2) NULL DEFAULT 0.00 COMMENT 'MyFileSize',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_persetting
-- ----------------------------
DROP TABLE IF EXISTS `gpm_persetting`;
CREATE TABLE `gpm_persetting`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Emp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员',
  `FK_App` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统',
  `UserNo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UserNo',
  `UserPass` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UserPass',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '个人设置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gpm_stationmenu
-- ----------------------------
DROP TABLE IF EXISTS `gpm_stationmenu`;
CREATE TABLE `gpm_stationmenu`  (
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  `FK_Menu` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单 - 主键',
  `IsChecked` int(11) NULL DEFAULT NULL COMMENT '是否选中',
  PRIMARY KEY (`FK_Station`, `FK_Menu`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for nd1rpt
-- ----------------------------
DROP TABLE IF EXISTS `nd1rpt`;
CREATE TABLE `nd1rpt`  (
  `BMJLSP_Note` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核意见',
  `BMJLSP_Checker` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `BMJLSP_RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核日期',
  `Title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `ShenQingRen` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `ShenQingRiJi` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请日期',
  `ShenQingRenBuMen` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人部门',
  `OID` int(11) NOT NULL DEFAULT 0,
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `FID` int(11) NULL DEFAULT 0 COMMENT 'FID',
  `CDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动时间',
  `Rec` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `Emps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参与者',
  `MyNum` int(11) NULL DEFAULT 1 COMMENT '个数',
  `QingJiaRiQiCong` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假日期从',
  `Dao` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到',
  `QingJiaTianShu` float NULL DEFAULT NULL COMMENT '请假天数',
  `QingJiaYuanYin` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假原因',
  `PNodeID` int(11) NULL DEFAULT 0 COMMENT '父流程启动的节点',
  `PWorkID` int(11) NULL DEFAULT 0 COMMENT '父流程WorkID',
  `FlowDaySpan` decimal(20, 4) NULL DEFAULT NULL COMMENT '跨度(天)',
  `FlowEndNode` int(11) NULL DEFAULT 0 COMMENT '结束节点',
  `FlowEnderRDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `FlowStartRDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起时间',
  `GUID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `PEmp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调起子流程的人员',
  `PrjNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目编号',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参数',
  `BillNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编号',
  `PFlowNo` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父流程流程编号',
  `PrjName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `FlowEmps` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参与人',
  `FlowNote` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程信息',
  `WFSta` int(11) NULL DEFAULT 0 COMMENT '状态',
  `WFState` int(11) NULL DEFAULT 0 COMMENT '流程状态',
  `FlowEnder` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束人',
  `FlowStarter` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员部门',
  `FK_NY` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年月',
  `ZJLSP_Note` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '审核意见',
  `ZJLSP_Checker` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `ZJLSP_RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核日期',
  `JobSchedule` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '流程进度图',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for nd1track
-- ----------------------------
DROP TABLE IF EXISTS `nd1track`;
CREATE TABLE `nd1track`  (
  `MyPK` int(11) NOT NULL DEFAULT 0 COMMENT 'MyPK',
  `ActionType` int(11) NULL DEFAULT 0 COMMENT '类型',
  `ActionTypeText` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(名称)',
  `FID` int(11) NULL DEFAULT 0 COMMENT '流程ID',
  `WorkID` int(11) NULL DEFAULT 0 COMMENT '工作ID',
  `NDFrom` int(11) NULL DEFAULT 0 COMMENT '从节点',
  `NDFromT` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从节点(名称)',
  `NDTo` int(11) NULL DEFAULT 0 COMMENT '到节点',
  `NDToT` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到节点(名称)',
  `EmpFrom` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从人员',
  `EmpFromT` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从人员(名称)',
  `EmpTo` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到人员',
  `EmpToT` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到人员(名称)',
  `RDT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日期',
  `WorkTimeSpan` float NULL DEFAULT NULL COMMENT '时间跨度(天)',
  `Msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '消息',
  `NodeData` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '节点数据(日志信息)',
  `Tag` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `Exer` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行人',
  `FrmDB` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for port_dept_old
-- ----------------------------
DROP TABLE IF EXISTS `port_dept_old`;
CREATE TABLE `port_dept_old`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `ParentNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点编号',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属组织',
  `Leader` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门领导',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `DeptType` int(11) NULL DEFAULT NULL COMMENT '部门状态',
  `NameOfPath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门路径',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_dept_old
-- ----------------------------
INSERT INTO `port_dept_old` VALUES ('100', '集团总部', '0', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1001', '集团市场部', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1002', '集团研发部', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1003', '集团服务部', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1004', '集团财务部', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1005', '集团人力资源部', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1060', '南方分公司', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1061', '市场部', '1060', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1062', '财务部', '1060', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1063', '销售部', '1060', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1070', '北方分公司', '100', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1071', '市场部', '1070', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1072', '财务部', '1070', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1073', '销售部', '1070', NULL, NULL, 0, 1, '');
INSERT INTO `port_dept_old` VALUES ('1099', '外来单位', '100', NULL, NULL, 0, 1, '');

-- ----------------------------
-- Table structure for port_deptemp
-- ----------------------------
DROP TABLE IF EXISTS `port_deptemp`;
CREATE TABLE `port_deptemp`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员,外键:对应物理表:Port_Emp,表描述:用户',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编码',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门人员信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_deptemp
-- ----------------------------
INSERT INTO `port_deptemp` VALUES ('1001_zhanghaicheng', '1001', 'zhanghaicheng', NULL);
INSERT INTO `port_deptemp` VALUES ('1001_zhangyifan', '1001', 'zhangyifan', NULL);
INSERT INTO `port_deptemp` VALUES ('1001_zhoushengyu', '1001', 'zhoushengyu', NULL);
INSERT INTO `port_deptemp` VALUES ('1002_qifenglin', '1002', 'qifenglin', NULL);
INSERT INTO `port_deptemp` VALUES ('1002_zhoutianjiao', '1002', 'zhoutianjiao', NULL);
INSERT INTO `port_deptemp` VALUES ('1003_fuhui', '1003', 'fuhui', NULL);
INSERT INTO `port_deptemp` VALUES ('1003_guoxiangbin', '1003', 'guoxiangbin', NULL);
INSERT INTO `port_deptemp` VALUES ('1004_guobaogeng', '1004', 'guobaogeng', NULL);
INSERT INTO `port_deptemp` VALUES ('1004_yangyilei', '1004', 'yangyilei', NULL);
INSERT INTO `port_deptemp` VALUES ('1005_liping', '1005', 'liping', NULL);
INSERT INTO `port_deptemp` VALUES ('1005_liyan', '1005', 'liyan', NULL);
INSERT INTO `port_deptemp` VALUES ('100_zhoupeng', '100', 'zhoupeng', NULL);

-- ----------------------------
-- Table structure for port_deptempstation
-- ----------------------------
DROP TABLE IF EXISTS `port_deptempstation`;
CREATE TABLE `port_deptempstation`  (
  `MyPK` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `FK_Station` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位',
  `FK_Emp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编码',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门岗位人员对应' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_deptempstation
-- ----------------------------
INSERT INTO `port_deptempstation` VALUES ('1001_zhanghaicheng_02', '1001', '02', 'zhanghaicheng', NULL);
INSERT INTO `port_deptempstation` VALUES ('1001_zhangyifan_07', '1001', '07', 'zhangyifan', NULL);
INSERT INTO `port_deptempstation` VALUES ('1001_zhoushengyu_07', '1001', '07', 'zhoushengyu', NULL);
INSERT INTO `port_deptempstation` VALUES ('1002_qifenglin_03', '1002', '03', 'qifenglin', NULL);
INSERT INTO `port_deptempstation` VALUES ('1002_zhoutianjiao_08', '1002', '08', 'zhoutianjiao', NULL);
INSERT INTO `port_deptempstation` VALUES ('1003_fuhui_09', '1003', '09', 'fuhui', NULL);
INSERT INTO `port_deptempstation` VALUES ('1003_guoxiangbin_04', '1003', '04', 'guoxiangbin', NULL);
INSERT INTO `port_deptempstation` VALUES ('1004_guobaogeng_10', '1004', '10', 'guobaogeng', NULL);
INSERT INTO `port_deptempstation` VALUES ('1004_yangyilei_05', '1004', '05', 'yangyilei', NULL);
INSERT INTO `port_deptempstation` VALUES ('1005_liping_06', '1005', '06', 'liping', NULL);
INSERT INTO `port_deptempstation` VALUES ('1005_liyan_11', '1005', '11', 'liyan', '');
INSERT INTO `port_deptempstation` VALUES ('100_zhoupeng_01', '100', '01', 'zhoupeng', NULL);

-- ----------------------------
-- Table structure for port_deptstation
-- ----------------------------
DROP TABLE IF EXISTS `port_deptstation`;
CREATE TABLE `port_deptstation`  (
  `FK_Dept` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Dept`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门岗位对应' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_deptstation
-- ----------------------------
INSERT INTO `port_deptstation` VALUES ('100', '01');
INSERT INTO `port_deptstation` VALUES ('1001', '07');
INSERT INTO `port_deptstation` VALUES ('1002', '08');
INSERT INTO `port_deptstation` VALUES ('1003', '09');
INSERT INTO `port_deptstation` VALUES ('1004', '10');
INSERT INTO `port_deptstation` VALUES ('1005', '11');

-- ----------------------------
-- Table structure for port_emp_old
-- ----------------------------
DROP TABLE IF EXISTS `port_emp_old`;
CREATE TABLE `port_emp_old`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Pass` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门,外键:对应物理表:Port_Dept,表描述:部门',
  `SID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SID',
  `Tel` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `Email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `UserType` int(11) NULL DEFAULT NULL COMMENT '用户状态',
  `PinYin` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编号',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '序号',
  `SignType` int(11) NULL DEFAULT NULL COMMENT '签字类型,枚举类型:0 不签名;1 图片签名;2 电子签名;',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_emp_old
-- ----------------------------
INSERT INTO `port_emp_old` VALUES ('admin', 'admin', '123', '100', 'f642464364f44afe974d77c07681e6dc', '0531-82374939', 'zhoupeng@ccflow.org', 1, ',admin,admin,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('fuhui', '福惠', '123', '1003', '951a203fe828407f8d534107ac7edcb6', '0531-82374939', 'fuhui@ccflow.org', 1, ',fuhui,fh,fh/jtfwb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('guobaogeng', '郭宝庚', '123', '1004', '', '0531-82374939', 'guobaogeng@ccflow.org', 1, ',guobaogeng,gbg,gbg/jtcwb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('guoxiangbin', '郭祥斌', '123', '1003', '', '0531-82374939', 'guoxiangbin@ccflow.org', 1, ',guoxiangbin,gxb,gxb/jtfwb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('liping', '李萍', '123', '1005', '', '0531-82374939', 'liping@ccflow.org', 1, ',liping,lp,lp/jtrlzyb,', '', 2, 0);
INSERT INTO `port_emp_old` VALUES ('liyan', '李言', '123', '1005', '', '0531-82374939', 'liyan@ccflow.org', 1, ',liyan,ly,ly/jtrlzyb,', '', 1, 0);
INSERT INTO `port_emp_old` VALUES ('qifenglin', '祁凤林', '123', '1002', '', '0531-82374939', 'qifenglin@ccflow.org', 1, ',qifenglin,qfl,qfl/jtyfb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('yangyilei', '杨依雷', '123', '1004', '', '0531-82374939', 'yangyilei@ccflow.org', 1, ',yangyilei,yyl,yyl/jtcwb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('zhanghaicheng', '张海成', '123', '1001', '', '0531-82374939', 'zhanghaicheng@ccflow.org', 1, ',zhanghaicheng,zhc,zhc/jtscb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('zhangyifan', '张一帆', '123', '1001', '', '0531-82374939', 'zhangyifan@ccflow.org', 1, ',zhangyifan,zyf,zyf/jtscb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('zhoupeng', '周朋', '123', '100', '25e79d5b42b64cc4bdb2dd28f86d5ed8', '0531-82374939', 'zhoupeng@ccflow.org', 1, ',zhoupeng,zp,zp/jtzb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('zhoushengyu', '周升雨', '123', '1001', '06de868632c543a3a038e3f30df5baca', '0531-82374939', 'zhoushengyu@ccflow.org', 1, ',zhoushengyu,zsy,zsy/jtscb,', '', 0, 0);
INSERT INTO `port_emp_old` VALUES ('zhoutianjiao', '周天娇', '123', '1002', 'bd086ef875334006aa92d0f172ce7eba', '0531-82374939', 'zhoutianjiao@ccflow.org', 1, ',zhoutianjiao,ztj,ztj/jtyfb,', '', 0, 0);

-- ----------------------------
-- Table structure for port_org
-- ----------------------------
DROP TABLE IF EXISTS `port_org`;
CREATE TABLE `port_org`  (
  `No` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号(与部门编号相同) - 主键',
  `Name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `ParentNo` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级组织编号',
  `ParentName` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级组织名称',
  `Adminer` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主要管理员(创始人)',
  `AdminerName` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '独立组织' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for port_orgadminer
-- ----------------------------
DROP TABLE IF EXISTS `port_orgadminer`;
CREATE TABLE `port_orgadminer`  (
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`OrgNo`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织管理员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for port_station_old
-- ----------------------------
DROP TABLE IF EXISTS `port_station_old`;
CREATE TABLE `port_station_old`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FK_StationType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型,外键:对应物理表:Port_StationType,表描述:岗位类型',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属组织',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_station_old
-- ----------------------------
INSERT INTO `port_station_old` VALUES ('01', '总经理', '1', '0');
INSERT INTO `port_station_old` VALUES ('02', '市场部经理', '2', '0');
INSERT INTO `port_station_old` VALUES ('03', '研发部经理', '2', '0');
INSERT INTO `port_station_old` VALUES ('04', '客服部经理', '2', '0');
INSERT INTO `port_station_old` VALUES ('05', '财务部经理', '2', '0');
INSERT INTO `port_station_old` VALUES ('06', '人力资源部经理', '2', '0');
INSERT INTO `port_station_old` VALUES ('07', '销售人员岗', '3', '0');
INSERT INTO `port_station_old` VALUES ('08', '程序员岗', '3', '0');
INSERT INTO `port_station_old` VALUES ('09', '技术服务岗', '3', '0');
INSERT INTO `port_station_old` VALUES ('10', '出纳岗', '3', '0');
INSERT INTO `port_station_old` VALUES ('11', '人力资源助理岗', '3', '0');
INSERT INTO `port_station_old` VALUES ('12', '外来人员岗', '3', '0');

-- ----------------------------
-- Table structure for port_stationtype
-- ----------------------------
DROP TABLE IF EXISTS `port_stationtype`;
CREATE TABLE `port_stationtype`  (
  `No` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编号',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位类型' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of port_stationtype
-- ----------------------------
INSERT INTO `port_stationtype` VALUES ('1', '高层', 0, NULL);
INSERT INTO `port_stationtype` VALUES ('2', '中层', 0, NULL);
INSERT INTO `port_stationtype` VALUES ('3', '基层', 0, NULL);

-- ----------------------------
-- Table structure for port_team
-- ----------------------------
DROP TABLE IF EXISTS `port_team`;
CREATE TABLE `port_team`  (
  `No` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FK_TeamType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型,外键:对应物理表:Port_TeamType,表描述:用户组类型',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for port_teamemp
-- ----------------------------
DROP TABLE IF EXISTS `port_teamemp`;
CREATE TABLE `port_teamemp`  (
  `FK_Team` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户组 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FK_Team`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for port_teamtype
-- ----------------------------
DROP TABLE IF EXISTS `port_teamtype`;
CREATE TABLE `port_teamtype`  (
  `No` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户组类型' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pub_nd
-- ----------------------------
DROP TABLE IF EXISTS `pub_nd`;
CREATE TABLE `pub_nd`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '年度' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pub_ny
-- ----------------------------
DROP TABLE IF EXISTS `pub_ny`;
CREATE TABLE `pub_ny`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '名称',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '月份' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pub_yf
-- ----------------------------
DROP TABLE IF EXISTS `pub_yf`;
CREATE TABLE `pub_yf`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '月份' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for scheduled_task
-- ----------------------------
DROP TABLE IF EXISTS `scheduled_task`;
CREATE TABLE `scheduled_task`  (
  `id` int(11) NULL DEFAULT NULL,
  `Taskkey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TaskDesc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TaskCron` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `InitStartFlag` int(11) NULL DEFAULT NULL,
  `DataSource` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ProNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UsrNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_docfile
-- ----------------------------
DROP TABLE IF EXISTS `sys_docfile`;
CREATE TABLE `sys_docfile`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FileName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FileSize` int(11) NULL DEFAULT NULL COMMENT '大小',
  `FileType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `D1` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'D1',
  `D2` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'D2',
  `D3` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'D3',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '备注字段文件管理者' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_encfg
-- ----------------------------
DROP TABLE IF EXISTS `sys_encfg`;
CREATE TABLE `sys_encfg`  (
  `No` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实体名称 - 主键',
  `UIRowStyleGlo` int(11) NULL DEFAULT NULL COMMENT '表格数据行风格(应用全局),枚举类型:0 无风格;1 交替风格;2 鼠标移动;3 交替并鼠标移动;',
  `IsEnableDouclickGlo` int(11) NULL DEFAULT NULL COMMENT '是否启动双击打开(应用全局)?',
  `IsEnableFocusField` int(11) NULL DEFAULT NULL COMMENT '是否启用焦点字段?',
  `FocusField` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '焦点字段',
  `IsEnableRefFunc` int(11) NULL DEFAULT NULL COMMENT '是否启用相关功能列?',
  `IsEnableOpenICON` int(11) NULL DEFAULT NULL COMMENT '是否启用打开图标?',
  `MoveToShowWay` int(11) NULL DEFAULT NULL COMMENT '移动到显示方式,枚举类型:0 不显示;1 下拉列表0;2 平铺;',
  `KeyLabel` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字Label',
  `PageSize` int(11) NULL DEFAULT NULL COMMENT '页面显示的条数',
  `FontSize` int(11) NULL DEFAULT NULL COMMENT '页面字体大小',
  `EditerType` int(11) NULL DEFAULT NULL COMMENT '大块文本编辑器,枚举类型:0 无编辑器;1 Sina编辑器0;2 FKEditer;3 KindEditor;4 百度的UEditor;',
  `GroupTitle` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组标签',
  `SearchUrlOpenType` int(11) NULL DEFAULT NULL COMMENT '双击行打开内容,枚举类型:0 En.htm 实体与实体相关功能编辑器;1 EnOnly.htm 实体编辑器;2 /CCForm/FrmGener.htm 傻瓜表单解析器;3 /CCForm/FrmGener.htm 自由表单解析器;9 自定义URL;',
  `UrlExt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要打开的Url',
  `OpenModel` int(11) NULL DEFAULT NULL COMMENT '打开方式,枚举类型:0 弹窗;1 新窗口打开;',
  `IsRefreshParentPage` int(11) NULL DEFAULT NULL COMMENT '关闭后是否刷新本页面',
  `WinCardW` int(11) NULL DEFAULT NULL COMMENT '窗体宽度',
  `WinCardH` int(11) NULL DEFAULT NULL COMMENT '高度',
  `OrderBy` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询排序字段',
  `IsDeSc` int(11) NULL DEFAULT NULL COMMENT '是否降序排序?',
  `FJSavePath` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加保存路径',
  `FJWebPath` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件Web路径',
  `Datan` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段数据分析方式',
  `UI` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UI设置',
  `ColorSet` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色设置',
  `FieldSet` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段求和/平均设置',
  `ForamtFunc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段格式化函数',
  `BtnsShowLeft` int(11) NULL DEFAULT NULL COMMENT '按钮显示到左边?',
  `IsImp` int(11) NULL DEFAULT NULL COMMENT '是否显示导入?',
  `ImpFuncUrl` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入功能Url',
  `IsGroup` int(11) NULL DEFAULT NULL COMMENT '是否显示分析按钮（在查询工具栏里）?',
  `IsEnableLazyload` int(11) NULL DEFAULT NULL COMMENT '是否启用懒加载？（对树结构实体有效）?',
  `BtnLab1` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义按钮标签1',
  `BtnJS1` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Url/Javasccript',
  `BtnLab2` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义按钮标签2',
  `BtnJS2` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Url/Javasccript',
  `AtPara` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实体配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_encfg
-- ----------------------------
INSERT INTO `sys_encfg` VALUES ('BP.CCBill.FrmBill', 0, 1, 0, NULL, 0, 0, 0, NULL, 10, 14, 0, '@No=基础信息,单据基础配置信息.@BtnNewLable=单据按钮权限,用于控制每个功能按钮启用规则.@BtnImpExcel=列表按钮,列表按钮控制@Designer=设计者,流程开发设计者信息', 0, NULL, 0, 1, 1000, 600, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.CCBill.FrmDict', 0, 1, 0, NULL, 0, 0, 0, NULL, 10, 14, 0, '@No=基础信息,单据基础配置信息.@BtnNewLable=单据按钮权限,用于控制每个功能按钮启用规则.@BtnImpExcel=列表按钮,列表按钮控制@Designer=设计者,流程开发设计者信息', 0, NULL, 0, 1, 1000, 600, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.Frm.FrmBill', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基础信息,单据基础配置信息.@BtnNewLable=单据按钮权限,用于控制每个功能按钮启用规则.@BtnImpExcel=列表按钮,列表按钮控制@Designer=设计者,流程开发设计者信息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.Frm.FrmDict', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基础信息,单据基础配置信息.@BtnNewLable=单据按钮权限,用于控制每个功能按钮启用规则.@BtnImpExcel=列表按钮,列表按钮控制@Designer=设计者,流程开发设计者信息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.Sys.EnCfg', 0, 1, 0, NULL, 0, 0, 0, NULL, 10, 14, 0, '@No=基础信息,基础信息权限信息.@BtnsShowLeft=工具栏按钮@SearchUrlOpenType=双击弹窗@', 0, NULL, 0, 1, 1000, 600, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.Sys.FrmUI.FrmAttachmentExt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@MyPK=基础信息,附件的基本配置.\n@DeleteWay=权限控制,控制附件的下载与上传权限.@IsRowLock=WebOffice属性,设置与公文有关系的属性配置.\n@IsToHeLiuHZ=流程相关,控制节点附件的分合流.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.FlowExt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基础信息,基础信息权限信息.@IsBatchStart=数据&表单,数据导入导出.@IsFrmEnable=轨迹@DesignerNo=设计者,流程开发设计者信息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.FlowSheet', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基本配置@FlowRunWay=启动方式,配置工作流程如何自动发起，该选项要与流程服务一起工作才有效.@StartLimitRole=启动限制规则@StartGuideWay=发起前置导航@CFlowWay=延续流程@DTSWay=流程数据与业务数据同步@PStarter=轨迹查看权限', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.FrmNodeComponent', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@NodeID=审核组件,适用于sdk表单审核组件与ccform上的审核组件属性设置.@SFLab=父子流程组件,在该节点上配置与显示父子流程.@FrmThreadLab=子线程组件,对合流节点有效，用于配置与现实子线程运行的情况。@FrmTrackLab=轨迹组件,用于显示流程运行的轨迹图.@FTCLab=流转自定义,在每个节点上自己控制节点的处理人.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.MapDataExt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基本属性@Designer=设计者信息', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.MapDtlExt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基础信息,基础信息权限信息.@IsExp=数据导入导出,数据导入导出.@IsEnableLink=超链接,显示在从表的右边.@IsCopyNDData=流程相关,与流程相关的配置非流程可以忽略.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.MapFrmFool', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@No=基础属性,基础属性.@Designer=设计者信息,设计者的单位信息，人员信息，可以上传到表单云.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.NodeExt', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@NodeID=基本配置@SendLab=按钮权限,控制工作节点可操作按钮.@RunModel=运行模式,分合流,父子流程@AutoJumpRole0=跳转,自动跳转规则当遇到该节点时如何让其自动的执行下一步.', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_encfg` VALUES ('BP.WF.Template.NodeSheet', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '@NodeID=基本配置@FormType=表单@FWCSta=审核组件,适用于sdk表单审核组件与ccform上的审核组件属性设置.@SFSta=父子流程,对启动，查看父子流程的控件设置.@SendLab=按钮权限,控制工作节点可操作按钮.@RunModel=运行模式,分合流,父子流程@AutoJumpRole0=跳转,自动跳转规则当遇到该节点时如何让其自动的执行下一步.@MPhone_WorkModel=移动,与手机平板电脑相关的应用设置.@OfficeOpen=公文按钮,只有当该节点是公文流程时候有效', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_enum
-- ----------------------------
DROP TABLE IF EXISTS `sys_enum`;
CREATE TABLE `sys_enum`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Lab` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Lab',
  `EnumKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'EnumKey',
  `IntKey` int(11) NULL DEFAULT NULL COMMENT 'Val',
  `Lang` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '枚举数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_enum
-- ----------------------------
INSERT INTO `sys_enum` VALUES ('ActionType_CH_0', 'GET', 'ActionType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ActionType_CH_1', 'POST', 'ActionType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertType_CH_0', '短信', 'AlertType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertType_CH_1', '邮件', 'AlertType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertType_CH_2', '邮件与短信', 'AlertType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertType_CH_3', '系统(内部)消息', 'AlertType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_0', '不接收', 'AlertWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_1', '短信', 'AlertWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_2', '邮件', 'AlertWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_3', '内部消息', 'AlertWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_4', 'QQ消息', 'AlertWay', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_5', 'RTX消息', 'AlertWay', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AlertWay_CH_6', 'MSN消息', 'AlertWay', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AppModel_CH_0', 'BS系统', 'AppModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AppModel_CH_1', 'CS系统', 'AppModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AppType_CH_0', '外部Url连接', 'AppType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AppType_CH_1', '本地可执行文件', 'AppType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_0', 'PK-主键', 'AthCtrlWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_1', 'FID-干流程ID', 'AthCtrlWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_2', 'PWorkID-父流程ID', 'AthCtrlWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_3', '仅能查看自己上传的附件', 'AthCtrlWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_4', 'WorkID-按照WorkID计算(对流程节点表单有效)', 'AthCtrlWay', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_5', 'P2WorkID', 'AthCtrlWay', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthCtrlWay_CH_6', 'P3WorkID', 'AthCtrlWay', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthSaveWay_CH_0', '保存到web服务器', 'AthSaveWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthSaveWay_CH_1', '保存到数据库', 'AthSaveWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthSaveWay_CH_2', 'ftp服务器', 'AthSaveWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthUploadWay_CH_0', '继承模式', 'AthUploadWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AthUploadWay_CH_1', '协作模式', 'AthUploadWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AuthorWay_CH_0', '不授权', 'AuthorWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AuthorWay_CH_1', '全部流程授权', 'AuthorWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('AuthorWay_CH_2', '指定流程授权', 'AuthorWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BackCopyRole_CH_0', '不反填', 'BackCopyRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BackCopyRole_CH_1', '字段自动匹配', 'BackCopyRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BackCopyRole_CH_2', '按照设置的格式', 'BackCopyRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BackCopyRole_CH_3', '混合模式', 'BackCopyRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillFrmType_CH_0', '傻瓜表单', 'BillFrmType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillFrmType_CH_1', '自由表单', 'BillFrmType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillFrmType_CH_8', '开发者表单', 'BillFrmType', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillOpenModel_CH_0', '下载本地', 'BillOpenModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillOpenModel_CH_1', '在线WebOffice打开', 'BillOpenModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillState_CH_0', '空白', 'BillState', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillState_CH_1', '草稿', 'BillState', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillState_CH_100', '归档', 'BillState', 100, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillState_CH_2', '编辑中', 'BillState', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillSta_CH_0', '运行中', 'BillSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillSta_CH_1', '已完成', 'BillSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BillSta_CH_2', '其他', 'BillSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BtnNewModel_CH_0', '表格模式', 'BtnNewModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BtnNewModel_CH_1', '卡片模式', 'BtnNewModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('BtnNewModel_CH_2', '不可用', 'BtnNewModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CancelRole_CH_0', '上一步可以撤销', 'CancelRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CancelRole_CH_1', '不能撤销', 'CancelRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CancelRole_CH_2', '上一步与开始节点可以撤销', 'CancelRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CancelRole_CH_3', '指定的节点可以撤销', 'CancelRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_0', '不能抄送', 'CCRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_1', '手工抄送', 'CCRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_2', '自动抄送', 'CCRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_3', '手工与自动', 'CCRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_4', '按表单SysCCEmps字段计算', 'CCRole', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCRole_CH_5', '在发送前打开抄送窗口', 'CCRole', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_0', '仅按岗位计算', 'CCStaWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_1', '按岗位智能计算(当前节点)', 'CCStaWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_2', '按岗位智能计算(发送到节点)', 'CCStaWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_3', '按岗位与部门的交集', 'CCStaWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_4', '按直线上级部门找岗位下的人员(当前节点)', 'CCStaWay', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCStaWay_CH_5', '按直线上级部门找岗位下的人员(接受节点)', 'CCStaWay', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCWriteTo_CH_0', '写入抄送列表', 'CCWriteTo', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCWriteTo_CH_1', '写入待办', 'CCWriteTo', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CCWriteTo_CH_2', '写入待办与抄送列表', 'CCWriteTo', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertRole_CH_0', '不提示', 'CHAlertRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertRole_CH_1', '每天1次', 'CHAlertRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertRole_CH_2', '每天2次', 'CHAlertRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertWay_CH_0', '邮件', 'CHAlertWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertWay_CH_1', '短信', 'CHAlertWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHAlertWay_CH_2', 'CCIM即时通讯', 'CHAlertWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ChartType_CH_0', '几何图形', 'ChartType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ChartType_CH_1', '肖像图片', 'ChartType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHRole_CH_0', '禁用', 'CHRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHRole_CH_1', '启用', 'CHRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHRole_CH_2', '只读', 'CHRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHRole_CH_3', '启用并可以调整流程应完成时间', 'CHRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHSta_CH_0', '及时完成', 'CHSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHSta_CH_1', '按期完成', 'CHSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHSta_CH_2', '逾期完成', 'CHSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CHSta_CH_3', '超期完成', 'CHSta', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CodeStruct_CH_0', '普通的编码表(具有No,Name)', 'CodeStruct', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CodeStruct_CH_1', '树结构(具有No,Name,ParentNo)', 'CodeStruct', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CodeStruct_CH_2', '行政机构编码表(编码以两位编号标识级次树形关系)', 'CodeStruct', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrDT_CH_0', '跨0个单元格', 'ColSpanAttrDT', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrDT_CH_1', '跨1个单元格', 'ColSpanAttrDT', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrDT_CH_2', '跨2个单元格', 'ColSpanAttrDT', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrDT_CH_3', '跨3个单元格', 'ColSpanAttrDT', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrDT_CH_4', '跨4个单元格', 'ColSpanAttrDT', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrString_CH_1', '跨1个单元格', 'ColSpanAttrString', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrString_CH_2', '跨2个单元格', 'ColSpanAttrString', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrString_CH_3', '跨3个单元格', 'ColSpanAttrString', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpanAttrString_CH_4', '跨4个单元格', 'ColSpanAttrString', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpan_CH_0', '跨0个单元格', 'ColSpan', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpan_CH_1', '跨1个单元格', 'ColSpan', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpan_CH_2', '跨2个单元格', 'ColSpan', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpan_CH_3', '跨3个单元格', 'ColSpan', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ColSpan_CH_4', '跨4个单元格', 'ColSpan', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CondModel_CH_0', '由连接线条件控制', 'CondModel', 0, 'CH', NULL);
INSERT INTO `sys_enum` VALUES ('CondModel_CH_1', '按照用户选择计算', 'CondModel', 1, 'CH', NULL);
INSERT INTO `sys_enum` VALUES ('CondModel_CH_2', '发送按钮旁下拉框选择', 'CondModel', 2, 'CH', NULL);
INSERT INTO `sys_enum` VALUES ('CtrlWay_CH_0', '单个', 'CtrlWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CtrlWay_CH_1', '多个', 'CtrlWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('CtrlWay_CH_2', '指定', 'CtrlWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DataStoreModel_CH_0', '数据轨迹模式', 'DataStoreModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DataStoreModel_CH_1', '数据合并模式', 'DataStoreModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_0', '应用系统主数据库(默认)', 'DBSrcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_1', 'SQLServer数据库', 'DBSrcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_100', 'WebService数据源', 'DBSrcType', 100, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_2', 'Oracle数据库', 'DBSrcType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_3', 'MySQL数据库', 'DBSrcType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_4', 'Informix数据库', 'DBSrcType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DBSrcType_CH_50', 'Dubbo服务', 'DBSrcType', 50, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DefValType_CH_0', '默认值为空', 'DefValType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DefValType_CH_1', '按照设置的默认值设置', 'DefValType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DelEnable_CH_0', '不能删除', 'DelEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DelEnable_CH_1', '逻辑删除', 'DelEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DelEnable_CH_2', '记录日志方式删除', 'DelEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DelEnable_CH_3', '彻底删除', 'DelEnable', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DelEnable_CH_4', '让用户决定删除方式', 'DelEnable', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DeleteWay_CH_0', '不能删除', 'DeleteWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DeleteWay_CH_1', '删除所有', 'DeleteWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DeleteWay_CH_2', '只能删除自己上传的', 'DeleteWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DocType_CH_0', '正式公文', 'DocType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DocType_CH_1', '便函', 'DocType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Draft_CH_0', '无(不设草稿)', 'Draft', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Draft_CH_1', '保存到待办', 'Draft', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Draft_CH_2', '保存到草稿箱', 'Draft', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlAddRecModel_CH_0', '按设置的数量初始化空白行', 'DtlAddRecModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlAddRecModel_CH_1', '用按钮增加空白行', 'DtlAddRecModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlOpenType_CH_0', '操作员', 'DtlOpenType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlOpenType_CH_1', 'WorkID-流程ID', 'DtlOpenType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlOpenType_CH_2', 'FID-干流程ID', 'DtlOpenType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlOpenType_CH_3', 'PWorkID-父流程WorkID', 'DtlOpenType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlSaveModel_CH_0', '自动存盘(失去焦点自动存盘)', 'DtlSaveModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlSaveModel_CH_1', '手动存盘(保存按钮触发存盘)', 'DtlSaveModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DtlVer_CH_0', '2017传统版', 'DtlVer', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSearchWay_CH_0', '不启用', 'DTSearchWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSearchWay_CH_1', '按日期', 'DTSearchWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSearchWay_CH_2', '按日期时间', 'DTSearchWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSWay_CH_0', '不考核', 'DTSWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSWay_CH_1', '按照时效考核', 'DTSWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('DTSWay_CH_2', '按照工作量考核', 'DTSWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditerType_CH_0', '无编辑器', 'EditerType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditerType_CH_1', 'Sina编辑器0', 'EditerType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditerType_CH_2', 'FKEditer', 'EditerType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditerType_CH_3', 'KindEditor', 'EditerType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditerType_CH_4', '百度的UEditor', 'EditerType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditModel_CH_0', '表格模式', 'EditModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditModel_CH_1', '傻瓜表单', 'EditModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EditModel_CH_2', '自由表单', 'EditModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityEditModel_CH_0', '表格', 'EntityEditModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityEditModel_CH_1', '行编辑', 'EntityEditModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityShowModel_CH_0', '表格', 'EntityShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityShowModel_CH_1', '树干模式', 'EntityShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityType_CH_0', '独立表单', 'EntityType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityType_CH_1', '单据', 'EntityType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityType_CH_2', '编号名称实体', 'EntityType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EntityType_CH_3', '树结构实体', 'EntityType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EnumUIContralType_CH_1', '下拉框', 'EnumUIContralType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EnumUIContralType_CH_2', '复选框', 'EnumUIContralType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EnumUIContralType_CH_3', '单选按钮', 'EnumUIContralType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EventType_CH_0', '禁用', 'EventType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EventType_CH_1', '执行URL', 'EventType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('EventType_CH_2', '执行CCFromRef.js', 'EventType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ExcType_CH_0', '超链接', 'ExcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ExcType_CH_1', '函数', 'ExcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ExpType_CH_3', '按照SQL计算', 'ExpType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ExpType_CH_4', '按照参数计算', 'ExpType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FileType_CH_0', '普通附件', 'FileType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FileType_CH_1', '图片文件', 'FileType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FJOpen_CH_0', '关闭附件', 'FJOpen', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FJOpen_CH_1', '操作员', 'FJOpen', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FJOpen_CH_2', '工作ID', 'FJOpen', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FJOpen_CH_3', '流程ID', 'FJOpen', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowAppType_CH_0', '业务流程', 'FlowAppType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowAppType_CH_1', '工程类(项目组流程)', 'FlowAppType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowAppType_CH_2', '公文流程(VSTO)', 'FlowAppType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowDeleteRole_CH_0', '超级管理员可以删除', 'FlowDeleteRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowDeleteRole_CH_1', '分级管理员可以删除', 'FlowDeleteRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowDeleteRole_CH_2', '发起人可以删除', 'FlowDeleteRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowDeleteRole_CH_3', '节点启动删除按钮的操作员', 'FlowDeleteRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmModel_CH_0', '完整版-2019年更早版本', 'FlowFrmModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmModel_CH_1', '绑定表单库的表单', 'FlowFrmModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmModel_CH_2', '表单树模式', 'FlowFrmModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmModel_CH_3', '自定义(嵌入)表单', 'FlowFrmModel', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmModel_CH_4', 'SDK表单', 'FlowFrmModel', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmType_CH_0', '完整版-2019年更早版本', 'FlowFrmType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmType_CH_1', '开发者表单', 'FlowFrmType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmType_CH_2', '傻瓜表单', 'FlowFrmType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmType_CH_3', '自定义(嵌入)表单', 'FlowFrmType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowFrmType_CH_4', 'SDK表单', 'FlowFrmType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowRunWay_CH_0', '手工启动', 'FlowRunWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowRunWay_CH_1', '指定人员按时启动', 'FlowRunWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowRunWay_CH_2', '数据集按时启动', 'FlowRunWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FlowRunWay_CH_3', '触发式启动', 'FlowRunWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FLRole_CH_0', '按接受人', 'FLRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FLRole_CH_1', '按部门', 'FLRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FLRole_CH_2', '按岗位', 'FLRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmShowType_CH_0', '普通方式', 'FrmShowType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmShowType_CH_1', '页签方式', 'FrmShowType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmSln_CH_0', '默认方案', 'FrmSln', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmSln_CH_1', '只读方案', 'FrmSln', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmSln_CH_2', '自定义方案', 'FrmSln', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmThreadSta_CH_0', '禁用', 'FrmThreadSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmThreadSta_CH_1', '启用', 'FrmThreadSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmTrackSta_CH_0', '禁用', 'FrmTrackSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmTrackSta_CH_1', '显示轨迹图', 'FrmTrackSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmTrackSta_CH_2', '显示轨迹表', 'FrmTrackSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_0', '傻瓜表单', 'FrmType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_1', '自由表单', 'FrmType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_11', '累加表单', 'FrmType', 11, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_3', '嵌入式表单', 'FrmType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_4', 'Word表单', 'FrmType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_5', '在线编辑模式Excel表单', 'FrmType', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_6', 'VSTO模式Excel表单', 'FrmType', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_7', '实体类组件', 'FrmType', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmType_CH_8', '开发者表单', 'FrmType', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmUrlShowWay_CH_0', '不显示', 'FrmUrlShowWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmUrlShowWay_CH_1', '自动大小', 'FrmUrlShowWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmUrlShowWay_CH_2', '指定大小', 'FrmUrlShowWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FrmUrlShowWay_CH_3', '新窗口', 'FrmUrlShowWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FTCSta_CH_0', '禁用', 'FTCSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FTCSta_CH_1', '只读', 'FTCSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FTCSta_CH_2', '可设置人员', 'FTCSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FTCWorkModel_CH_0', '简洁模式', 'FTCWorkModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FTCWorkModel_CH_1', '高级模式', 'FTCWorkModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCAth_CH_0', '不启用', 'FWCAth', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCAth_CH_1', '多附件', 'FWCAth', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCAth_CH_2', '单附件(暂不支持)', 'FWCAth', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCAth_CH_3', '图片附件(暂不支持)', 'FWCAth', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCMsgShow_CH_0', '都显示', 'FWCMsgShow', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCMsgShow_CH_1', '仅显示自己的意见', 'FWCMsgShow', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCOrderModel_CH_0', '按审批时间先后排序', 'FWCOrderModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCOrderModel_CH_1', '按照接受人员列表先后顺序(官职大小)', 'FWCOrderModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCShowModel_CH_0', '表格方式', 'FWCShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCShowModel_CH_1', '自由模式', 'FWCShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCSta_CH_0', '禁用', 'FWCSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCSta_CH_1', '启用', 'FWCSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCSta_CH_2', '只读', 'FWCSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCType_CH_0', '审核组件', 'FWCType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCType_CH_1', '日志组件', 'FWCType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCType_CH_2', '周报组件', 'FWCType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCType_CH_3', '月报组件', 'FWCType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCVer_CH_0', '1个节点1个人保留1个意见', 'FWCVer', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('FWCVer_CH_1', '保留节点历史意见(默认)', 'FWCVer', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('GuestFlowRole_CH_0', '不参与', 'GuestFlowRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('GuestFlowRole_CH_1', '开始节点参与', 'GuestFlowRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('GuestFlowRole_CH_2', '中间节点参与', 'GuestFlowRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HelpRole_CH_0', '禁用', 'HelpRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HelpRole_CH_1', '启用', 'HelpRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HelpRole_CH_2', '强制提示', 'HelpRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HelpRole_CH_3', '选择性提示', 'HelpRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianLeaderRole_CH_0', '只有一个组长', 'HuiQianLeaderRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianLeaderRole_CH_1', '最后一个组长发送', 'HuiQianLeaderRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianLeaderRole_CH_2', '任意组长发送', 'HuiQianLeaderRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianRole_CH_0', '不启用', 'HuiQianRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianRole_CH_1', '协作模式', 'HuiQianRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HuiQianRole_CH_4', '组长模式', 'HuiQianRole', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HungUpWay_CH_0', '无限挂起', 'HungUpWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HungUpWay_CH_1', '按指定的时间解除挂起并通知我自己', 'HungUpWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('HungUpWay_CH_2', '按指定的时间解除挂起并通知所有人', 'HungUpWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ImgSrcType_CH_0', '本地', 'ImgSrcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ImgSrcType_CH_1', 'URL', 'ImgSrcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ImpModel_CH_0', '不导入', 'ImpModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ImpModel_CH_1', '按配置模式导入', 'ImpModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ImpModel_CH_2', '按照xls文件模版导入', 'ImpModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('InvokeTime_CH_0', '发送时', 'InvokeTime', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('InvokeTime_CH_1', '工作到达时', 'InvokeTime', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSLSubFlowOver_CH_0', '不处理', 'IsAutoSendSLSubFlowOver', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSLSubFlowOver_CH_1', '让同级子流程自动运行下一步', 'IsAutoSendSLSubFlowOver', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSLSubFlowOver_CH_2', '结束同级子流程', 'IsAutoSendSLSubFlowOver', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSubFlowOver_CH_0', '不处理', 'IsAutoSendSubFlowOver', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSubFlowOver_CH_1', '让父流程自动运行下一步', 'IsAutoSendSubFlowOver', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsAutoSendSubFlowOver_CH_2', '结束父流程', 'IsAutoSendSubFlowOver', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsEnableFWC_CH_0', '禁用', 'IsEnableFWC', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsEnableFWC_CH_1', '启用', 'IsEnableFWC', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsEnableFWC_CH_2', '只读', 'IsEnableFWC', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSigan_CH_0', '无', 'IsSigan', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSigan_CH_1', '图片签名', 'IsSigan', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSigan_CH_2', '山东CA', 'IsSigan', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSigan_CH_3', '广东CA', 'IsSigan', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSigan_CH_4', '图片盖章', 'IsSigan', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_0', 'yyyy-MM-dd', 'IsSupperText', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_1', 'yyyy-MM-dd HH:mm', 'IsSupperText', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_2', 'yyyy-MM-dd HH:mm:ss', 'IsSupperText', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_3', 'yyyy-MM', 'IsSupperText', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_4', 'HH:mm', 'IsSupperText', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_5', 'HH:mm:ss', 'IsSupperText', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('IsSupperText_CH_6', 'MM-dd', 'IsSupperText', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JMCD_CH_0', '一般', 'JMCD', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JMCD_CH_1', '保密', 'JMCD', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JMCD_CH_2', '秘密', 'JMCD', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JMCD_CH_3', '机密', 'JMCD', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JumpWay_CH_0', '不能跳转', 'JumpWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JumpWay_CH_1', '只能向后跳转', 'JumpWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JumpWay_CH_2', '只能向前跳转', 'JumpWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JumpWay_CH_3', '任意节点跳转', 'JumpWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('JumpWay_CH_4', '按指定规则跳转', 'JumpWay', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('LGType_CH_0', '普通', 'LGType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('LGType_CH_1', '枚举', 'LGType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('LGType_CH_2', '外键', 'LGType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('LGType_CH_3', '打开系统页面', 'LGType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ListShowModel_CH_0', '表格', 'ListShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ListShowModel_CH_1', '卡片', 'ListShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuCtrlWay_CH_0', '按照设置的控制', 'MenuCtrlWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuCtrlWay_CH_1', '任何人都可以使用', 'MenuCtrlWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuCtrlWay_CH_2', 'Admin用户可以使用', 'MenuCtrlWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_0', '系统根目录', 'MenuType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_1', '系统类别', 'MenuType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_2', '系统', 'MenuType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_3', '目录', 'MenuType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_4', '功能/界面', 'MenuType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MenuType_CH_5', '功能控制点', 'MenuType', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MethodDocTypeOfFunc_CH_0', 'SQL', 'MethodDocTypeOfFunc', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MethodDocTypeOfFunc_CH_1', 'URL', 'MethodDocTypeOfFunc', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MethodDocTypeOfFunc_CH_2', 'JavaScript', 'MethodDocTypeOfFunc', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MethodDocTypeOfFunc_CH_3', '业务单元', 'MethodDocTypeOfFunc', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MobileShowModel_CH_0', '新页面显示模式', 'MobileShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MobileShowModel_CH_1', '列表模式', 'MobileShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Model_CH_0', '普通', 'Model', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Model_CH_1', '固定行', 'Model', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MoveToShowWay_CH_0', '不显示', 'MoveToShowWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MoveToShowWay_CH_1', '下拉列表0', 'MoveToShowWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MoveToShowWay_CH_2', '平铺', 'MoveToShowWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MsgCtrl_CH_0', '不发送', 'MsgCtrl', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MsgCtrl_CH_1', '按设置的下一步接受人自动发送（默认）', 'MsgCtrl', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MsgCtrl_CH_2', '由本节点表单系统字段(IsSendEmail,IsSendSMS)来决定', 'MsgCtrl', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MsgCtrl_CH_3', '由SDK开发者参数(IsSendEmail,IsSendSMS)来决定', 'MsgCtrl', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_1', '字符串String', 'MyDataType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_2', '整数类型Int', 'MyDataType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_3', '浮点类型AppFloat', 'MyDataType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_4', '判断类型Boolean', 'MyDataType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_5', '双精度类型Double', 'MyDataType', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_6', '日期型Date', 'MyDataType', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_7', '时间类型Datetime', 'MyDataType', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDataType_CH_8', '金额类型AppMoney', 'MyDataType', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDeptRole_CH_0', '仅部门领导可以查看', 'MyDeptRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDeptRole_CH_1', '部门下所有的人都可以查看', 'MyDeptRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('MyDeptRole_CH_2', '本部门里指定岗位的人可以查看', 'MyDeptRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoGenerModel_CH_0', '自定义', 'NoGenerModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoGenerModel_CH_1', '流水号', 'NoGenerModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoGenerModel_CH_2', '标签的全拼', 'NoGenerModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoGenerModel_CH_3', '标签的简拼', 'NoGenerModel', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoGenerModel_CH_4', '按GUID生成', 'NoGenerModel', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoteEnable_CH_0', '禁用', 'NoteEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoteEnable_CH_1', '启用', 'NoteEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('NoteEnable_CH_2', '只读', 'NoteEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeBtnEnable_CH_0', '不可用', 'OfficeBtnEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeBtnEnable_CH_1', '可编辑', 'OfficeBtnEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeBtnEnable_CH_2', '不可编辑', 'OfficeBtnEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeBtnLocal_CH_0', '工具栏上', 'OfficeBtnLocal', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeBtnLocal_CH_1', '表单标签(divID=GovDocFile)', 'OfficeBtnLocal', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeFileType_CH_0', 'word文件', 'OfficeFileType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OfficeFileType_CH_1', 'WPS文件', 'OfficeFileType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OpenModel_CH_0', '弹窗', 'OpenModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OpenModel_CH_1', '新窗口打开', 'OpenModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OpenWay_CH_0', '新窗口', 'OpenWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OpenWay_CH_1', '本窗口', 'OpenWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('OpenWay_CH_2', '覆盖新窗口', 'OpenWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PicUploadType_CH_0', '拍照上传或者相册上传', 'PicUploadType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PicUploadType_CH_1', '只能拍照上传', 'PicUploadType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PopValFormat_CH_0', 'No(仅编号)', 'PopValFormat', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PopValFormat_CH_1', 'Name(仅名称)', 'PopValFormat', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PopValFormat_CH_2', 'No,Name(编号与名称,比如zhangsan,张三;lisi,李四;)', 'PopValFormat', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PowerCtrlType_CH_0', '岗位', 'PowerCtrlType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PowerCtrlType_CH_1', '人员', 'PowerCtrlType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRIEnable_CH_0', '不启用', 'PRIEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRIEnable_CH_1', '只读', 'PRIEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRIEnable_CH_2', '编辑', 'PRIEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PrintPDFModle_CH_0', '全部打印', 'PrintPDFModle', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PrintPDFModle_CH_1', '单个表单打印(针对树形表单)', 'PrintPDFModle', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRI_CH_0', '低', 'PRI', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRI_CH_1', '中', 'PRI', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PRI_CH_2', '高', 'PRI', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_0', '按照指定节点的工作人员', 'PushWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_1', '按照指定的工作人员', 'PushWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_2', '按照指定的工作岗位', 'PushWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_3', '按照指定的部门', 'PushWay', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_4', '按照指定的SQL', 'PushWay', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('PushWay_CH_5', '按照系统指定的字段', 'PushWay', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('QRModel_CH_0', '不生成', 'QRModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('QRModel_CH_1', '生成二维码', 'QRModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RBShowModel_CH_0', '竖向', 'RBShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RBShowModel_CH_3', '横向', 'RBShowModel', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadReceipts_CH_0', '不回执', 'ReadReceipts', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadReceipts_CH_1', '自动回执', 'ReadReceipts', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadReceipts_CH_2', '由上一节点表单字段决定', 'ReadReceipts', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadReceipts_CH_3', '由SDK开发者参数决定', 'ReadReceipts', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadRole_CH_0', '不控制', 'ReadRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadRole_CH_1', '未阅读阻止发送', 'ReadRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReadRole_CH_2', '未阅读做记录', 'ReadRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefBillRole_CH_0', '不启用', 'RefBillRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefBillRole_CH_1', '非必须选择关联单据', 'RefBillRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefBillRole_CH_2', '必须选择关联单据', 'RefBillRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodTypeLink_CH_0', '模态窗口打开', 'RefMethodTypeLink', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodTypeLink_CH_1', '新窗口打开', 'RefMethodTypeLink', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodTypeLink_CH_2', '右侧窗口打开', 'RefMethodTypeLink', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodTypeLink_CH_4', '转到新页面', 'RefMethodTypeLink', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodType_CH_0', '功能', 'RefMethodType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodType_CH_1', '模态窗口打开', 'RefMethodType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodType_CH_2', '新窗口打开', 'RefMethodType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodType_CH_3', '右侧窗口打开', 'RefMethodType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RefMethodType_CH_4', '实体集合的功能', 'RefMethodType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnOneNodeRole_CH_0', '不启用', 'ReturnOneNodeRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnOneNodeRole_CH_1', '按照[退回信息填写字段]作为退回意见直接退回', 'ReturnOneNodeRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnOneNodeRole_CH_2', '按照[审核组件]填写的信息作为退回意见直接退回', 'ReturnOneNodeRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnRole_CH_0', '不能退回', 'ReturnRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnRole_CH_1', '只能退回上一个节点', 'ReturnRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnRole_CH_2', '可退回以前任意节点', 'ReturnRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnRole_CH_3', '可退回指定的节点', 'ReturnRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnRole_CH_4', '由流程图设计的退回路线决定', 'ReturnRole', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnSendModel_CH_0', '从退回节点正常执行', 'ReturnSendModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnSendModel_CH_1', '直接发送到当前节点', 'ReturnSendModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ReturnSendModel_CH_2', '直接发送到当前节点的下一个节点', 'ReturnSendModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenModel_CH_0', '新窗口打开', 'RowOpenModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenModel_CH_1', '弹出窗口打开,关闭后刷新列表', 'RowOpenModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenModel_CH_2', '弹出窗口打开,关闭后不刷新列表', 'RowOpenModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenMode_CH_0', '新窗口打开', 'RowOpenMode', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenMode_CH_1', '在本窗口打开', 'RowOpenMode', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenMode_CH_2', '弹出窗口打开,关闭后不刷新列表', 'RowOpenMode', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowOpenMode_CH_3', '弹出窗口打开,关闭后刷新列表', 'RowOpenMode', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpanAttrString_CH_1', '跨1个行', 'RowSpanAttrString', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpanAttrString_CH_2', '跨2行', 'RowSpanAttrString', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpanAttrString_CH_3', '跨3行', 'RowSpanAttrString', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpan_CH_1', '跨1个行', 'RowSpan', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpan_CH_2', '跨2行', 'RowSpan', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('RowSpan_CH_3', '跨3行', 'RowSpan', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SaveModel_CH_0', '仅节点表', 'SaveModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SaveModel_CH_1', '节点表与Rpt表', 'SaveModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SearchUrlOpenType_CH_0', 'En.htm 实体与实体相关功能编辑器', 'SearchUrlOpenType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SearchUrlOpenType_CH_1', 'EnOnly.htm 实体编辑器', 'SearchUrlOpenType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SearchUrlOpenType_CH_2', '/CCForm/FrmGener.htm 傻瓜表单解析器', 'SearchUrlOpenType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SearchUrlOpenType_CH_3', '/CCForm/FrmGener.htm 自由表单解析器', 'SearchUrlOpenType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SearchUrlOpenType_CH_9', '自定义URL', 'SearchUrlOpenType', 9, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectAccepterEnable_CH_0', '不启用', 'SelectAccepterEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectAccepterEnable_CH_1', '单独启用', 'SelectAccepterEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectAccepterEnable_CH_2', '在发送前打开', 'SelectAccepterEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectAccepterEnable_CH_3', '转入新页面', 'SelectAccepterEnable', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_0', '按岗位', 'SelectorModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_1', '按部门', 'SelectorModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_2', '按人员', 'SelectorModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_3', '按SQL', 'SelectorModel', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_4', '按SQL模版计算', 'SelectorModel', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_5', '使用通用人员选择器', 'SelectorModel', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_6', '部门与岗位的交集', 'SelectorModel', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_7', '自定义Url', 'SelectorModel', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_8', '使用通用部门岗位人员选择器', 'SelectorModel', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SelectorModel_CH_9', '按岗位智能计算(操作员所在部门)', 'SelectorModel', 9, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SendModel_CH_0', '给当前人员设置开始节点待办', 'SendModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SendModel_CH_1', '发送到下一个节点', 'SendModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFOpenType_CH_0', '工作查看器', 'SFOpenType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFOpenType_CH_1', '傻瓜表单轨迹查看器', 'SFOpenType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFShowCtrl_CH_0', '可以看所有的子流程', 'SFShowCtrl', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFShowCtrl_CH_1', '仅仅可以看自己发起的子流程', 'SFShowCtrl', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFShowModel_CH_0', '表格方式', 'SFShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFShowModel_CH_1', '自由模式', 'SFShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFSta_CH_0', '禁用', 'SFSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFSta_CH_1', '启用', 'SFSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFSta_CH_2', '只读', 'SFSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFTGSH_CH_0', '通过', 'SFTGSH', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SFTGSH_CH_1', '不通过', 'SFTGSH', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SharingType_CH_0', '共享', 'SharingType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SharingType_CH_1', '私有', 'SharingType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowModel_CH_0', '按钮', 'ShowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowModel_CH_1', '超链接', 'ShowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowType_CH_0', '显示', 'ShowType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowType_CH_1', 'PC折叠', 'ShowType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowType_CH_2', '隐藏', 'ShowType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowWhere_CH_0', '树形表单', 'ShowWhere', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowWhere_CH_1', '工具栏', 'ShowWhere', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ShowWhere_CH_2', '抄送工具栏', 'ShowWhere', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SigantureEnabel_CH_0', '不签名', 'SigantureEnabel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SigantureEnabel_CH_1', '图片签名', 'SigantureEnabel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SigantureEnabel_CH_2', '写字板', 'SigantureEnabel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SignType_CH_0', '不签名', 'SignType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SignType_CH_1', '图片签名', 'SignType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SignType_CH_2', '电子签名', 'SignType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_0', '方向条件', 'SQLType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_1', '接受人规则', 'SQLType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_2', '下拉框数据过滤', 'SQLType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_3', '级联下拉框', 'SQLType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_4', 'PopVal开窗返回值', 'SQLType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SQLType_CH_5', '人员选择器人员选择范围', 'SQLType', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_0', '本地的类', 'SrcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_1', '创建表', 'SrcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_2', '表或视图', 'SrcType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_3', 'SQL查询表', 'SrcType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_4', 'WebServices', 'SrcType', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_5', '微服务Handler外部数据源', 'SrcType', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_6', 'JavaScript外部数据源', 'SrcType', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SrcType_CH_7', '系统字典表', 'SrcType', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SSOType_CH_0', 'SID验证', 'SSOType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SSOType_CH_1', '连接', 'SSOType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SSOType_CH_2', '表单提交', 'SSOType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SSOType_CH_3', '不传值', 'SSOType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowModel_CH_0', '下级子流程', 'SubFlowModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowModel_CH_1', '同级子流程', 'SubFlowModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowStartWay_CH_0', '不启动', 'SubFlowStartWay', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowStartWay_CH_1', '指定的字段启动', 'SubFlowStartWay', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowStartWay_CH_2', '按明细表启动', 'SubFlowStartWay', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowSta_CH_0', '禁用', 'SubFlowSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowSta_CH_1', '启用', 'SubFlowSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowSta_CH_2', '只读', 'SubFlowSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowType_CH_0', '手动启动子流程', 'SubFlowType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowType_CH_1', '触发启动子流程', 'SubFlowType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubFlowType_CH_2', '延续子流程', 'SubFlowType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubThreadType_CH_0', '同表单', 'SubThreadType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('SubThreadType_CH_1', '异表单', 'SubThreadType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TableCol_CH_0', '4列', 'TableCol', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TableCol_CH_1', '6列', 'TableCol', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TableCol_CH_2', '上下模式3列', 'TableCol', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TabType_CH_0', '本地表或视图', 'TabType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TabType_CH_1', '通过一个SQL确定的一个外部数据源', 'TabType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TabType_CH_2', '通过WebServices获得的一个数据源', 'TabType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Target_CH_0', '新窗口', 'Target', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Target_CH_1', '本窗口', 'Target', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('Target_CH_2', '父窗口', 'Target', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TaskSta_CH_0', '未开始', 'TaskSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TaskSta_CH_1', '进行中', 'TaskSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TaskSta_CH_2', '完成', 'TaskSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TaskSta_CH_3', '推迟', 'TaskSta', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TemplateFileModel_CH_0', 'rtf模版', 'TemplateFileModel', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TemplateFileModel_CH_1', 'vsto模式的word模版', 'TemplateFileModel', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TemplateFileModel_CH_2', 'vsto模式的excel模版', 'TemplateFileModel', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TextColSpan_CH_1', '跨1个单元格', 'TextColSpan', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TextColSpan_CH_2', '跨2个单元格', 'TextColSpan', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TextColSpan_CH_3', '跨3个单元格', 'TextColSpan', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TextColSpan_CH_4', '跨4个单元格', 'TextColSpan', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ThreadKillRole_CH_0', '不能删除', 'ThreadKillRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ThreadKillRole_CH_1', '手工删除', 'ThreadKillRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ThreadKillRole_CH_2', '自动删除', 'ThreadKillRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ToobarExcType_CH_0', '超链接', 'ToobarExcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('ToobarExcType_CH_1', '函数', 'ToobarExcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TSpan_CH_0', '本周', 'TSpan', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TSpan_CH_1', '上周', 'TSpan', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TSpan_CH_2', '上上周', 'TSpan', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('TSpan_CH_3', '更早', 'TSpan', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIContralType_CH_1', '下拉框', 'UIContralType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIContralType_CH_2', '复选框', 'UIContralType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIContralType_CH_3', '单选按钮', 'UIContralType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIRowStyleGlo_CH_0', '无风格', 'UIRowStyleGlo', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIRowStyleGlo_CH_1', '交替风格', 'UIRowStyleGlo', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIRowStyleGlo_CH_2', '鼠标移动', 'UIRowStyleGlo', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UIRowStyleGlo_CH_3', '交替并鼠标移动', 'UIRowStyleGlo', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileCheck_CH_0', '不控制', 'UploadFileCheck', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileCheck_CH_1', '上传附件个数不能为0', 'UploadFileCheck', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileCheck_CH_2', '每个类别下面的个数不能为0', 'UploadFileCheck', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileNumCheck_CH_0', '不用校验', 'UploadFileNumCheck', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileNumCheck_CH_1', '不能为空', 'UploadFileNumCheck', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadFileNumCheck_CH_2', '每个类别下不能为空', 'UploadFileNumCheck', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadType_CH_0', '单个', 'UploadType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadType_CH_1', '多个', 'UploadType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UploadType_CH_2', '指定', 'UploadType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UrlSrcType_CH_0', '自定义', 'UrlSrcType', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UrlSrcType_CH_1', '地图', 'UrlSrcType', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UrlSrcType_CH_2', '流程轨迹表', 'UrlSrcType', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('UrlSrcType_CH_3', '流程轨迹图', 'UrlSrcType', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WebOfficeEnable_CH_0', '不启用', 'WebOfficeEnable', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WebOfficeEnable_CH_1', '按钮方式', 'WebOfficeEnable', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WebOfficeEnable_CH_2', '标签页置后方式', 'WebOfficeEnable', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WebOfficeEnable_CH_3', '标签页置前方式', 'WebOfficeEnable', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_10', '批处理', 'WFStateApp', 10, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_2', '运行中', 'WFStateApp', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_3', '已完成', 'WFStateApp', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_4', '挂起', 'WFStateApp', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_5', '退回', 'WFStateApp', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_6', '转发', 'WFStateApp', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_7', '删除', 'WFStateApp', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_8', '加签', 'WFStateApp', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFStateApp_CH_9', '冻结', 'WFStateApp', 9, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_0', '空白', 'WFState', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_1', '草稿', 'WFState', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_10', '批处理', 'WFState', 10, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_11', '加签回复状态', 'WFState', 11, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_2', '运行中', 'WFState', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_3', '已完成', 'WFState', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_4', '挂起', 'WFState', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_5', '退回', 'WFState', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_6', '转发', 'WFState', 6, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_7', '删除', 'WFState', 7, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_8', '加签', 'WFState', 8, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFState_CH_9', '冻结', 'WFState', 9, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFSta_CH_0', '运行中', 'WFSta', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFSta_CH_1', '已完成', 'WFSta', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WFSta_CH_2', '其他', 'WFSta', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhatAreYouTodo_CH_0', '关闭提示窗口', 'WhatAreYouTodo', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhatAreYouTodo_CH_1', '关闭提示窗口并刷新', 'WhatAreYouTodo', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhatAreYouTodo_CH_2', '转入到Search.htm页面上去', 'WhatAreYouTodo', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhenOverSize_CH_0', '不处理', 'WhenOverSize', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhenOverSize_CH_1', '向下顺增行', 'WhenOverSize', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhenOverSize_CH_2', '次页显示', 'WhenOverSize', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoExeIt_CH_0', '操作员执行', 'WhoExeIt', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoExeIt_CH_1', '机器执行', 'WhoExeIt', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoExeIt_CH_2', '混合执行', 'WhoExeIt', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_0', 'WorkID是主键', 'WhoIsPK', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_1', 'FID是主键(干流程的WorkID)', 'WhoIsPK', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_2', '父流程ID是主键', 'WhoIsPK', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_3', '延续流程ID是主键', 'WhoIsPK', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_4', 'P2WorkID是主键', 'WhoIsPK', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('WhoIsPK_CH_5', 'P3WorkID是主键', 'WhoIsPK', 5, 'CH', '');
INSERT INTO `sys_enum` VALUES ('YBFlowReturnRole_CH_0', '不能退回', 'YBFlowReturnRole', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('YBFlowReturnRole_CH_1', '退回到父流程的开始节点', 'YBFlowReturnRole', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('YBFlowReturnRole_CH_2', '退回到父流程的任何节点', 'YBFlowReturnRole', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('YBFlowReturnRole_CH_3', '退回父流程的启动节点', 'YBFlowReturnRole', 3, 'CH', '');
INSERT INTO `sys_enum` VALUES ('YBFlowReturnRole_CH_4', '可退回到指定的节点', 'YBFlowReturnRole', 4, 'CH', '');
INSERT INTO `sys_enum` VALUES ('傻瓜表单显示方式_CH_0', '4列', '傻瓜表单显示方式', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('傻瓜表单显示方式_CH_1', '6列', '傻瓜表单显示方式', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('傻瓜表单显示方式_CH_2', '上下模式3列', '傻瓜表单显示方式', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('显示方式_CH_0', '4列', '显示方式', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('显示方式_CH_1', '6列', '显示方式', 1, 'CH', '');
INSERT INTO `sys_enum` VALUES ('显示方式_CH_2', '上下模式3列', '显示方式', 2, 'CH', '');
INSERT INTO `sys_enum` VALUES ('表单展示方式_CH_0', '普通方式', '表单展示方式', 0, 'CH', '');
INSERT INTO `sys_enum` VALUES ('表单展示方式_CH_1', '页签方式', '表单展示方式', 1, 'CH', '');

-- ----------------------------
-- Table structure for sys_enummain
-- ----------------------------
DROP TABLE IF EXISTS `sys_enummain`;
CREATE TABLE `sys_enummain`  (
  `No` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `CfgVal` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置信息',
  `Lang` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言',
  `EnumKey` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'EnumKey',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '枚举' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_enummain
-- ----------------------------
INSERT INTO `sys_enummain` VALUES ('SFTGSH', '是否通过审核', '@0=通过@1=不通过', 'CH', '', '');

-- ----------------------------
-- Table structure for sys_enver
-- ----------------------------
DROP TABLE IF EXISTS `sys_enver`;
CREATE TABLE `sys_enver`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体类',
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体名',
  `PKValue` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键值',
  `EVer` int(11) NULL DEFAULT NULL COMMENT '版本号',
  `Rec` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实体版本号' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_enverdtl
-- ----------------------------
DROP TABLE IF EXISTS `sys_enverdtl`;
CREATE TABLE `sys_enverdtl`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `EnName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体名',
  `EnVerPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本主表PK',
  `AttrKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `AttrName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `OldVal` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旧值',
  `NewVal` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新值',
  `EnVer` int(11) NULL DEFAULT NULL COMMENT '版本号(日期)',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日期',
  `Rec` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '实体修改明细' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_filemanager
-- ----------------------------
DROP TABLE IF EXISTS `sys_filemanager`;
CREATE TABLE `sys_filemanager`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `AttrFileName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定名称',
  `AttrFileNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定编号',
  `EnName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联的表',
  `RefVal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键值',
  `WebPath` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Web路径',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFileExt',
  `MyFileH` int(11) NULL DEFAULT NULL COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT NULL COMMENT 'MyFileW',
  `MyFileSize` float NULL DEFAULT NULL COMMENT 'MyFileSize',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传时间',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `Doc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件管理者' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_formtree
-- ----------------------------
DROP TABLE IF EXISTS `sys_formtree`;
CREATE TABLE `sys_formtree`  (
  `No` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `ParentNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点No',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  `Idx` int(11) NULL DEFAULT NULL COMMENT 'Idx',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单树' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_formtree
-- ----------------------------
INSERT INTO `sys_formtree` VALUES ('1', '表单树', '0', '', 0);
INSERT INTO `sys_formtree` VALUES ('110', '流程独立表单', '1', '', 0);
INSERT INTO `sys_formtree` VALUES ('111', '常用信息管理', '1', '', 0);
INSERT INTO `sys_formtree` VALUES ('112', '常用单据', '1', '', 0);

-- ----------------------------
-- Table structure for sys_frmattachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmattachment`;
CREATE TABLE `sys_frmattachment`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `NoOfObj` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件标识',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点控制(对sln有效)',
  `AthRunModel` int(11) NULL DEFAULT NULL COMMENT '运行模式,枚举类型:0 流水模式;1 固定模式;2 自定义页面;',
  `AthSaveWay` int(11) NULL DEFAULT NULL COMMENT '保存方式,枚举类型:0 保存到web服务器;1 保存到数据库;2 ftp服务器;',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称',
  `Exts` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件格式',
  `NumOfUpload` int(11) NULL DEFAULT NULL COMMENT '最小上传数量',
  `TopNumOfUpload` int(11) NULL DEFAULT NULL COMMENT '最大上传数量',
  `FileMaxSize` int(11) NULL DEFAULT NULL COMMENT '附件最大限制(KB)',
  `UploadFileNumCheck` int(11) NULL DEFAULT NULL COMMENT '上传校验方式,枚举类型:0 不用校验;1 不能为空;2 每个类别下不能为空;',
  `Sort` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `W` float NULL DEFAULT NULL COMMENT '宽度',
  `H` float NULL DEFAULT NULL COMMENT '高度',
  `IsUpload` int(11) NULL DEFAULT NULL COMMENT '是否可以上传',
  `IsVisable` int(11) NULL DEFAULT NULL COMMENT '是否显示附件分组',
  `FileType` int(11) NULL DEFAULT NULL COMMENT '附件类型,枚举类型:0 普通附件;1 图片文件;',
  `ReadRole` int(11) NULL DEFAULT NULL COMMENT '阅读规则,枚举类型:0 不控制;1 未阅读阻止发送;2 未阅读做记录;',
  `PicUploadType` int(11) NULL DEFAULT NULL COMMENT '图片附件上传方式,枚举类型:0 拍照上传或者相册上传;1 只能拍照上传;',
  `DeleteWay` int(11) NULL DEFAULT NULL COMMENT '附件删除规则,枚举类型:0 不能删除;1 删除所有;2 只能删除自己上传的;',
  `IsDownload` int(11) NULL DEFAULT NULL COMMENT '是否可以下载',
  `IsOrder` int(11) NULL DEFAULT NULL COMMENT '是否可以排序',
  `IsAutoSize` int(11) NULL DEFAULT NULL COMMENT '自动控制大小',
  `IsNote` int(11) NULL DEFAULT NULL COMMENT '是否增加备注',
  `IsExpCol` int(11) NULL DEFAULT NULL COMMENT '是否启用扩展列',
  `IsShowTitle` int(11) NULL DEFAULT NULL COMMENT '是否显示标题列',
  `UploadType` int(11) NULL DEFAULT NULL COMMENT '上传类型,枚举类型:0 单个;1 多个;2 指定;',
  `CtrlWay` int(11) NULL DEFAULT NULL COMMENT '控制呈现控制方式,枚举类型:0 单个;1 多个;2 指定;',
  `AthUploadWay` int(11) NULL DEFAULT NULL COMMENT '控制上传控制方式,枚举类型:0 继承模式;1 协作模式;',
  `DataRefNoOfObj` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应附件标识',
  `AtPara` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT 'GroupID',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `IsTurn2Html` int(11) NULL DEFAULT NULL COMMENT '是否转换成html(方便手机浏览)',
  `IsToHeLiuHZ` int(11) NULL DEFAULT NULL COMMENT '该附件是否要汇总到合流节点上去？(对子线程节点有效)',
  `IsHeLiuHuiZong` int(11) NULL DEFAULT NULL COMMENT '是否是合流节点的汇总附件组件？(对合流节点有效)',
  `IsIdx` int(11) NULL DEFAULT 0 COMMENT '是否可以排序',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmattachmentdb
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmattachmentdb`;
CREATE TABLE `sys_frmattachmentdb`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_MapData',
  `FK_FrmAttachment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件主键',
  `NoOfObj` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件标识',
  `RefPKVal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体主键',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `NodeID` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `Sort` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `FileFullName` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `FileName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FileExts` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展',
  `FileSize` float NULL DEFAULT NULL COMMENT '文件大小',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人',
  `RecName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人名字',
  `FK_Dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在部门',
  `FK_DeptName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在部门名称',
  `MyNote` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `IsRowLock` int(11) NULL DEFAULT NULL COMMENT '是否锁定行',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '排序',
  `UploadGUID` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传GUID',
  `AtPara` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件数据存储' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmbtn
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmbtn`;
CREATE TABLE `sys_frmbtn`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `IsView` int(11) NULL DEFAULT NULL COMMENT '是否可见',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否起用',
  `UAC` int(11) NULL DEFAULT NULL COMMENT '控制类型',
  `UACContext` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '控制内容',
  `EventType` int(11) NULL DEFAULT NULL COMMENT '事件类型,枚举类型:0 禁用;1 执行URL;2 执行CCFromRef.js;',
  `EventContext` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '事件内容',
  `MsgOK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行成功提示',
  `MsgErr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行失败提示',
  `BtnID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮ID',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT '所在分组',
  `GroupIDText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在分组',
  `Lab` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '按钮' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmeledb
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmeledb`;
CREATE TABLE `sys_frmeledb`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_MapData',
  `EleID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'EleID',
  `RefPKVal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'RefPKVal',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `Tag1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag1',
  `Tag2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `Tag3` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag3',
  `Tag4` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag4',
  `Tag5` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag5',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单元素扩展DB' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmevent
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmevent`;
CREATE TABLE `sys_frmevent`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `EventSource` int(11) NULL DEFAULT NULL COMMENT '事件类型',
  `FK_Event` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件标记',
  `RefFlowNo` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联的流程编号',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID(包含Dtl表)',
  `FK_Flow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `EventDoType` int(11) NULL DEFAULT NULL COMMENT '事件执行类型',
  `DoDoc` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行内容',
  `MsgOK` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成功执行提示',
  `MsgError` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常信息提示',
  `MsgCtrl` int(11) NULL DEFAULT NULL COMMENT '消息发送控制,枚举类型:0 不发送;1 按设置的下一步接受人自动发送（默认）;2 由本节点表单系统字段(IsSendEmail,IsSendSMS)来决定;3 由SDK开发者参数(IsSendEmail,IsSendSMS)来决定;',
  `MailEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用邮件发送？(如果启用就要设置邮件模版，支持ccflow表达式。)',
  `MailTitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件标题模版',
  `MailDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '邮件内容模版',
  `SMSEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用短信发送？(如果启用就要设置短信模版，支持ccflow表达式。)',
  `SMSDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '短信内容模版',
  `MobilePushEnable` int(11) NULL DEFAULT NULL COMMENT '是否推送到手机、pad端。',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '外部自定义事件(表单,从表,流程,节点)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmimg
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmimg`;
CREATE TABLE `sys_frmimg`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_MapData',
  `KeyOfEn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应字段',
  `ImgAppType` int(11) NULL DEFAULT NULL COMMENT '应用类型',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `H` float NULL DEFAULT NULL COMMENT 'H',
  `W` float NULL DEFAULT NULL COMMENT 'W',
  `ImgURL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ImgURL',
  `ImgPath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ImgPath',
  `LinkURL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'LinkURL',
  `LinkTarget` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'LinkTarget',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `Tag0` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `ImgSrcType` int(11) NULL DEFAULT NULL COMMENT '图片来源0=本地,1=URL',
  `IsEdit` int(11) NULL DEFAULT NULL COMMENT '是否可以编辑',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `EnPK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `ColSpan` int(11) NULL DEFAULT NULL COMMENT '单元格数量',
  `TextColSpan` int(11) NULL DEFAULT NULL COMMENT '文本单元格数量',
  `RowSpan` int(11) NULL DEFAULT NULL COMMENT '行数',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT '显示的分组',
  `GroupIDText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的分组',
  `UIWidth` int(11) NULL DEFAULT 0 COMMENT '宽度',
  `UIHeight` int(11) NULL DEFAULT 0 COMMENT '高度',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmimgath
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmimgath`;
CREATE TABLE `sys_frmimgath`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `CtrlID` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控件ID',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `H` float NULL DEFAULT NULL COMMENT 'H',
  `W` float NULL DEFAULT NULL COMMENT 'W',
  `IsEdit` int(11) NULL DEFAULT NULL COMMENT '是否可编辑',
  `IsRequired` int(11) NULL DEFAULT NULL COMMENT '是否必填项',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT '显示的分组',
  `GroupIDText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的分组',
  `ColSpan` int(11) NULL DEFAULT NULL COMMENT '单元格数量',
  `TextColSpan` int(11) NULL DEFAULT NULL COMMENT '文本单元格数量,枚举类型:1 跨1个单元格;2 跨2个单元格;3 跨3个单元格;4 跨4个单元格;',
  `RowSpan` int(11) NULL DEFAULT NULL COMMENT '行数,枚举类型:1 跨1个行;2 跨2行;3 跨3行;',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片附件' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmimgathdb
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmimgathdb`;
CREATE TABLE `sys_frmimgathdb`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `FK_FrmImgAth` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片附件编号',
  `RefPKVal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体主键',
  `FileFullName` varchar(700) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件全路径',
  `FileName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FileExts` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展名',
  `FileSize` float NULL DEFAULT NULL COMMENT '文件大小',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人',
  `RecName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人名字',
  `MyNote` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '剪切图片附件数据存储' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmlab
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmlab`;
CREATE TABLE `sys_frmlab`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_MapData',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `FontSize` int(11) NULL DEFAULT NULL COMMENT '字体大小',
  `FontColor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `FontName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字体名称',
  `FontStyle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字体风格',
  `FontWeight` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字体宽度',
  `IsBold` int(11) NULL DEFAULT NULL COMMENT '是否粗体',
  `IsItalic` int(11) NULL DEFAULT NULL COMMENT '是否斜体',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `Lab` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_frmlab
-- ----------------------------
INSERT INTO `sys_frmlab` VALUES ('0919e593eb46450080e6727cf676243c', 'ND103', 605, 490, 14, '#FF000000', 'Portable User Interface', 'Normal', '', 0, 0, '', 'Made&nbsp;by&nbsp;ccform');
INSERT INTO `sys_frmlab` VALUES ('39bced685577470fbf2d91fc36ee3c85', 'ND104', 605, 490, 14, '#FF000000', 'Portable User Interface', 'Normal', '', 0, 0, '', 'Made&nbsp;by&nbsp;ccform');
INSERT INTO `sys_frmlab` VALUES ('88dc64a353774378b43f58abd7bc1d8a', 'ND101', 605, 490, 14, '#FF000000', 'Portable User Interface', 'Normal', '', 0, 0, '', 'Made&nbsp;by&nbsp;ccform');
INSERT INTO `sys_frmlab` VALUES ('8efaa6789a2642e59dd222f736a273f9', 'ND104', 79.67, 4.27, 23, '#FF000000', 'Portable User Interface', 'Normal', 'normal', 0, 0, '', '填写xxxx申请单');
INSERT INTO `sys_frmlab` VALUES ('98ace65d625e4607b56915265df75fa2', 'ND102', 605, 490, 14, '#FF000000', 'Portable User Interface', 'Normal', '', 0, 0, '', 'Made&nbsp;by&nbsp;ccform');
INSERT INTO `sys_frmlab` VALUES ('a78d5985d334464198d7bb046f1505e1', 'ND103', 79.67, 4.27, 23, '#FF000000', 'Portable User Interface', 'Normal', 'normal', 0, 0, '', '填写xxxx申请单');
INSERT INTO `sys_frmlab` VALUES ('d08e73b08a62409ca1fd519441a62152', 'ND102', 79.67, 4.27, 23, '#FF000000', 'Portable User Interface', 'Normal', 'normal', 0, 0, '', '填写xxxx申请单');
INSERT INTO `sys_frmlab` VALUES ('ef8280b8542c4780bdbaedd0ff07e665', 'ND101', 79.67, 4.27, 23, '#FF000000', 'Portable User Interface', 'Normal', 'normal', 0, 0, '', '填写xxxx申请单');

-- ----------------------------
-- Table structure for sys_frmline
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmline`;
CREATE TABLE `sys_frmline`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主表',
  `X1` float NULL DEFAULT NULL COMMENT 'X1',
  `Y1` float NULL DEFAULT NULL COMMENT 'Y1',
  `X2` float NULL DEFAULT NULL COMMENT 'X2',
  `Y2` float NULL DEFAULT NULL COMMENT 'Y2',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `BorderWidth` float NULL DEFAULT NULL COMMENT '宽度',
  `BorderColor` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '初始的GUID',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '线' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_frmline
-- ----------------------------
INSERT INTO `sys_frmline` VALUES ('00f1a17d0289457588ac38ad5be8a7bd', 'ND101', 81.82, 40, 81.82, 480.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('0bf2be205fa84088b6bf94c53638c831', 'ND103', 81.82, 40, 81.82, 480.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('1cd1f42c16304ccd9d638e1d542176f0', 'ND103', 83.36, 40.91, 717.91, 40.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('1cda0d258e1340caa298117cb3b04610', 'ND102', 83.36, 40.91, 717.91, 40.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('24bf0aa9b23443b78cfb56e80e84502b', 'ND104', 719.09, 40, 719.09, 482.73, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('594fae41a88a44b88b88392761b2e7da', 'ND104', 83.36, 120.91, 717.91, 120.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('612c992626ab42cc8eba27a65fe96da6', 'ND103', 81.55, 80, 718.82, 80, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('61d37f13059744fe94ffb077a1c039ea', 'ND104', 81.55, 80, 718.82, 80, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('62edfd0b08a94d89848247276d531667', 'ND102', 719.09, 40, 719.09, 482.73, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('6cd8be9874f3463093a1f2b26b8b63ad', 'ND102', 81.82, 481.82, 720, 481.82, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('6e92cf58bcfb4c489166710828fc4c4c', 'ND104', 81.82, 40, 81.82, 480.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('975683fd64ea41fbab17dc05376b56f1', 'ND104', 83.36, 40.91, 717.91, 40.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('9c711b06ec9a4c77be5989025cc2d439', 'ND101', 81.55, 80, 718.82, 80, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('a21d314b29894c86bcf8f97088a6cf0e', 'ND102', 81.82, 40, 81.82, 480.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('a69934b5162f4edc84ba5f2f8289b5e0', 'ND101', 81.82, 481.82, 720, 481.82, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('b38a86d3603540e799ba8965709650ca', 'ND103', 83.36, 120.91, 717.91, 120.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('be050c3b3e54402690c95c99329f84f8', 'ND102', 83.36, 120.91, 717.91, 120.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('c3b530bfd158412698be62fe777a64b8', 'ND104', 81.82, 481.82, 720, 481.82, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('c78f4ba4b4b1485383c964f9257c0717', 'ND103', 719.09, 40, 719.09, 482.73, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('ce31ed0e934446ca9d142ffaec98a89f', 'ND102', 81.55, 80, 718.82, 80, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('daab0a4013cd40b18121cf1e6ac17c74', 'ND101', 719.09, 40, 719.09, 482.73, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('e0d5d70ca05e4785b91473da2acdbc69', 'ND101', 83.36, 120.91, 717.91, 120.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('ed54ed7863fe4947ab66068fb16090f3', 'ND101', 83.36, 40.91, 717.91, 40.91, 9, 9, 2, 'Black', '');
INSERT INTO `sys_frmline` VALUES ('faf5dd67cf874051b8ad41867bb29737', 'ND103', 81.82, 481.82, 720, 481.82, 9, 9, 2, 'Black', '');

-- ----------------------------
-- Table structure for sys_frmlink
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmlink`;
CREATE TABLE `sys_frmlink`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `URLExt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `Target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接目标(_blank,_parent,_self)',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `FontSize` int(11) NULL DEFAULT NULL COMMENT 'FontSize',
  `FontColor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FontColor',
  `FontName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FontName',
  `FontStyle` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FontStyle',
  `IsBold` int(11) NULL DEFAULT NULL COMMENT 'IsBold',
  `IsItalic` int(11) NULL DEFAULT NULL COMMENT 'IsItalic',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT '显示的分组',
  `GroupIDText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的分组',
  `Lab` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '超连接' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmrb
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmrb`;
CREATE TABLE `sys_frmrb`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `KeyOfEn` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `EnumKey` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '枚举值',
  `Lab` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `IntKey` int(11) NULL DEFAULT NULL COMMENT 'IntKey',
  `UIIsEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `Script` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '要执行的脚本',
  `FieldsCfg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置信息@FieldName=Sta',
  `SetVal` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置的值',
  `Tip` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选择后提示的信息',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `AtPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单选框' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_frmsln
-- ----------------------------
DROP TABLE IF EXISTS `sys_frmsln`;
CREATE TABLE `sys_frmsln`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `KeyOfEn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `EleType` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `UIIsEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用',
  `UIVisible` int(11) NULL DEFAULT NULL COMMENT '是否可见',
  `IsSigan` int(11) NULL DEFAULT NULL COMMENT '是否签名',
  `IsNotNull` int(11) NULL DEFAULT NULL COMMENT '是否为空',
  `RegularExp` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正则表达式',
  `IsWriteToFlowTable` int(11) NULL DEFAULT NULL COMMENT '是否写入流程表',
  `IsWriteToGenerWorkFlow` int(11) NULL DEFAULT NULL COMMENT '是否写入流程注册表',
  `DefVal` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单字段方案' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_glovar
-- ----------------------------
DROP TABLE IF EXISTS `sys_glovar`;
CREATE TABLE `sys_glovar`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '键 - 主键',
  `Name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Val` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '值',
  `GroupKey` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组值',
  `Note` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全局变量' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_glovar
-- ----------------------------
INSERT INTO `sys_glovar` VALUES ('0', '选择系统约定默认值', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@FK_ND', '当前年度', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@FK_YF', '当前月份', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@OrgName', '登录人员组织名称', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@OrgNo', '登录人员组织', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@WebUser.FK_Dept', '登陆人员部门编号', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@WebUser.FK_DeptFullName', '登陆人员部门全称', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@WebUser.FK_DeptName', '登陆人员部门名称', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@WebUser.Name', '登陆人员名称', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@WebUser.No', '登陆人员账号', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@yyyy年MM月dd日', '当前日期(yyyy年MM月dd日)', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@yyyy年MM月dd日HH时mm分', '当前日期(yyyy年MM月dd日HH时mm分)', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@yy年MM月dd日', '当前日期(yy年MM月dd日)', NULL, 'DefVal', NULL, NULL);
INSERT INTO `sys_glovar` VALUES ('@yy年MM月dd日HH时mm分', '当前日期(yy年MM月dd日HH时mm分)', NULL, 'DefVal', NULL, NULL);

-- ----------------------------
-- Table structure for sys_groupenstemplate
-- ----------------------------
DROP TABLE IF EXISTS `sys_groupenstemplate`;
CREATE TABLE `sys_groupenstemplate`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `EnName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表称',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报表名',
  `EnsName` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报表类名',
  `OperateCol` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作属性',
  `Attrs` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运算属性',
  `Rec` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表模板' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_groupfield
-- ----------------------------
DROP TABLE IF EXISTS `sys_groupfield`;
CREATE TABLE `sys_groupfield`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `Lab` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `FrmID` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `CtrlType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控件类型',
  `CtrlID` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控件ID',
  `IsZDPC` int(11) NULL DEFAULT NULL COMMENT '是否折叠(PC)',
  `IsZDMobile` int(11) NULL DEFAULT NULL COMMENT '是否折叠(Mobile)',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `AtPara` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `ShowType` int(11) NULL DEFAULT 0 COMMENT '分组显示模式',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '傻瓜表单分组' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_groupfield
-- ----------------------------
INSERT INTO `sys_groupfield` VALUES (100, '填写请假申请单', 'ND101', '', '', 0, 0, 1, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (101, '流程信息', 'ND1Rpt', '', '', 0, 0, 1, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (102, '填写请假申请单', 'ND102', '', '', 0, 0, 1, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (103, '部门经理审批', 'ND102', '', '', 0, 0, 2, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (104, '填写请假申请单', 'ND103', '', '', 0, 0, 1, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (105, '部门经理审批', 'ND103', '', '', 0, 0, 2, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (106, '总经理审批', 'ND103', '', '', 0, 0, 3, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (107, '填写请假申请单', 'ND104', '', '', 0, 0, 1, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (108, '部门经理审批', 'ND104', '', '', 0, 0, 2, '', '', 0);
INSERT INTO `sys_groupfield` VALUES (109, '总经理审批', 'ND104', '', '', 0, 0, 3, '', '', 0);

-- ----------------------------
-- Table structure for sys_langue
-- ----------------------------
DROP TABLE IF EXISTS `sys_langue`;
CREATE TABLE `sys_langue`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Langue` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '语言ID',
  `Model` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `ModelKey` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块实例',
  `Sort` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `SortKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别PK',
  `Val` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '语言值',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '语言定义' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_mapattr
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapattr`;
CREATE TABLE `sys_mapattr`  (
  `MyPK` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `KeyOfEn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `DefVal` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '默认值表达式',
  `DefValType` int(11) NULL DEFAULT NULL COMMENT '默认值选择方式,枚举类型:0 默认值为空;1 按照设置的默认值设置;',
  `UIContralType` int(11) NULL DEFAULT NULL COMMENT '控件',
  `MyDataType` int(11) NULL DEFAULT NULL COMMENT '数据类型,枚举类型:1 字符串String;2 整数类型Int;3 浮点类型AppFloat;4 判断类型Boolean;5 双精度类型Double;6 日期型Date;7 时间类型Datetime;8 金额类型AppMoney;',
  `LGType` int(11) NULL DEFAULT NULL COMMENT '类型,枚举类型:0 普通;1 枚举;2 外键;3 打开系统页面;',
  `UIWidth` float NULL DEFAULT NULL COMMENT '宽度',
  `UIHeight` float NULL DEFAULT NULL COMMENT '高度',
  `MinLen` int(11) NULL DEFAULT NULL COMMENT '最小长度',
  `MaxLen` int(11) NULL DEFAULT NULL COMMENT '最大长度',
  `UIBindKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键SFTable',
  `UIRefKey` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的Key',
  `UIRefKeyText` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的Text',
  `ExtIsSum` int(11) NULL DEFAULT NULL COMMENT '是否显示合计(对从表有效)',
  `UIVisible` int(11) NULL DEFAULT NULL COMMENT '是否可见？',
  `UIIsEnable` int(11) NULL DEFAULT NULL COMMENT '是否可编辑？',
  `UIIsLine` int(11) NULL DEFAULT NULL COMMENT '是否单独栏显示',
  `UIIsInput` int(11) NULL DEFAULT NULL COMMENT '是否必填项？',
  `IsSecret` int(11) NULL DEFAULT NULL COMMENT '是否保密？',
  `IsRichText` int(11) NULL DEFAULT NULL COMMENT '是否富文本？',
  `IsSupperText` int(11) NULL DEFAULT NULL COMMENT '是否大块文本？(是否该字段存放的超长字节字段)',
  `FontSize` int(11) NULL DEFAULT NULL COMMENT '字体大小',
  `IsSigan` int(11) NULL DEFAULT NULL COMMENT '签名模式,枚举类型:0 无;1 图片签名;2 山东CA;3 广东CA;4 图片盖章;',
  `X` float NULL DEFAULT NULL COMMENT 'X',
  `Y` float NULL DEFAULT NULL COMMENT 'Y',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `EditType` int(11) NULL DEFAULT NULL COMMENT '编辑类型',
  `Tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识',
  `Tag1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接目标(_blank,_parent,_self)',
  `Tag2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `Tag3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识3',
  `Tip` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '激活提示',
  `ColSpan` int(11) NULL DEFAULT NULL COMMENT 'TextBox单元格数量,枚举类型:0 跨0个单元格;1 跨1个单元格;2 跨2个单元格;3 跨3个单元格;4 跨4个单元格;',
  `TextColSpan` int(11) NULL DEFAULT NULL COMMENT 'Label单元格数量,枚举类型:1 跨1个单元格;2 跨2个单元格;3 跨3个单元格;4 跨4个单元格;',
  `RowSpan` int(11) NULL DEFAULT NULL COMMENT '行数',
  `GroupID` int(11) NULL DEFAULT NULL COMMENT '显示的分组',
  `IsEnableInAPP` int(11) NULL DEFAULT NULL COMMENT '是否在移动端中显示',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `CSS` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义样式',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  `GroupIDText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的分组',
  `CSSText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义样式',
  `DefValText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值（选中）',
  `RBShowModel` int(11) NULL DEFAULT NULL COMMENT '单选按钮的展现方式,枚举类型:0 竖向;3 横向;',
  `ExtDefVal` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统默认值',
  `ExtDefValText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统默认值',
  `CSSCtrl` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'CSSCtrl自定义样式',
  `ICON` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'ICON',
  `CSSLabel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'CSSLabel标签样式',
  `CSSLabelText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '标签样式',
  `CSSCtrlText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '控件样式',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程进度图' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_mapattr
-- ----------------------------
INSERT INTO `sys_mapattr` VALUES ('ND101_CDT', 'ND101', 'CDT', '发起时间', '@RDT', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 100, 1, 1001, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_Dao', 'ND101', 'Dao', '到', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1008, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_Emps', 'ND101', 'Emps', 'Emps', '', 0, 0, 1, 0, 100, 23, 0, 8000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1003, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_FID', 'ND101', 'FID', 'FID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1000, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_FK_Dept', 'ND101', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'BP.Port.Depts', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1004, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_FK_NY', 'ND101', 'FK_NY', '年月', '', 0, 0, 1, 0, 100, 23, 0, 7, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1005, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_JobSchedule', 'ND101', 'JobSchedule', '流程进度图', '', 1, 50, 1, 0, 0, 100, 0, 300, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 100, 1, 1011, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_OID', 'ND101', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 100, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_QingJiaRiQiCong', 'ND101', 'QingJiaRiQiCong', '请假日期从', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1007, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_QingJiaTianShu', 'ND101', 'QingJiaTianShu', '请假天数', '10002', 0, 0, 3, 0, 100, 23, 0, 50, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1009, '0', '@IsSum=0@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_QingJiaYuanYin', 'ND101', 'QingJiaYuanYin', '请假原因', '', 0, 0, 1, 0, 100, 123, 0, 50, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 100, 1, 1010, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_RDT', 'ND101', 'RDT', '更新时间', '@RDT', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 100, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_Rec', 'ND101', 'Rec', '发起人', '@WebUser.No', 0, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1002, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_ShenQingRen', 'ND101', 'ShenQingRen', '申请人', '@WebUser.Name', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_ShenQingRenBuMen', 'ND101', 'ShenQingRenBuMen', '申请人部门', '@WebUser.FK_DeptName', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 3, 1, 1, 100, 1, 3, '0', '@FontSize=12@IsRichText=0@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_ShenQingRiJi', 'ND101', 'ShenQingRiJi', '申请日期', '@RDT', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 2, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND101_Title', 'ND101', 'Title', '标题', '', 0, 0, 1, 0, 251, 23, 0, 200, '', '', '', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, -1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_BMJLSP_Checker', 'ND102', 'BMJLSP_Checker', '审核人', '@WebUser.No', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_BMJLSP_Note', 'ND102', 'BMJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 103, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_BMJLSP_RDT', 'ND102', 'BMJLSP_RDT', '审核日期', '@RDT', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_CDT', 'ND102', 'CDT', '发起时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 103, 1, 1001, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_Dao', 'ND102', 'Dao', '到', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 1008, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_Emps', 'ND102', 'Emps', 'Emps', '', 0, 0, 1, 0, 100, 23, 0, 8000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, 1003, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_FID', 'ND102', 'FID', 'FID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, 1000, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_FK_Dept', 'ND102', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'BP.Port.Depts', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, 1004, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_FK_NY', 'ND102', 'FK_NY', '年月', '', 0, 0, 1, 0, 100, 23, 0, 7, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, 1005, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_OID', 'ND102', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 103, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_QingJiaRiQiCong', 'ND102', 'QingJiaRiQiCong', '请假日期从', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 1007, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_QingJiaTianShu', 'ND102', 'QingJiaTianShu', '请假天数', '10002', 0, 0, 3, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 1009, '0', '@IsSum=0@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_QingJiaYuanYin', 'ND102', 'QingJiaYuanYin', '请假原因', '', 0, 0, 1, 0, 100, 123, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 103, 1, 1010, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_RDT', 'ND102', 'RDT', '更新时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 103, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_Rec', 'ND102', 'Rec', '发起人', '', 0, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, 1002, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_ShenQingRen', 'ND102', 'ShenQingRen', '申请人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 1, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_ShenQingRenBuMen', 'ND102', 'ShenQingRenBuMen', '申请人部门', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 3, 1, 1, 103, 1, 3, '0', '@FontSize=12@IsRichText=0@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_ShenQingRiJi', 'ND102', 'ShenQingRiJi', '申请日期', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 2, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND102_Title', 'ND102', 'Title', '标题', '', 0, 0, 1, 0, 251, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 103, 1, -1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_BMJLSP_Checker', 'ND103', 'BMJLSP_Checker', '审核人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_BMJLSP_Note', 'ND103', 'BMJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 106, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_BMJLSP_RDT', 'ND103', 'BMJLSP_RDT', '审核日期', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_CDT', 'ND103', 'CDT', '发起时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 106, 1, 1001, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_Dao', 'ND103', 'Dao', '到', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 1008, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_Emps', 'ND103', 'Emps', 'Emps', '', 0, 0, 1, 0, 100, 23, 0, 8000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, 1003, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_FID', 'ND103', 'FID', 'FID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, 1000, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_FK_Dept', 'ND103', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'BP.Port.Depts', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, 1004, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_FK_NY', 'ND103', 'FK_NY', '年月', '', 0, 0, 1, 0, 100, 23, 0, 7, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, 1005, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_OID', 'ND103', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 106, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_QingJiaRiQiCong', 'ND103', 'QingJiaRiQiCong', '请假日期从', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 1007, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_QingJiaTianShu', 'ND103', 'QingJiaTianShu', '请假天数', '10002', 0, 0, 3, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 1009, '0', '@IsSum=0@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_QingJiaYuanYin', 'ND103', 'QingJiaYuanYin', '请假原因', '', 0, 0, 1, 0, 100, 123, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 106, 1, 1010, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_RDT', 'ND103', 'RDT', '更新时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 106, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_Rec', 'ND103', 'Rec', '发起人', '', 0, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, 1002, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ShenQingRen', 'ND103', 'ShenQingRen', '申请人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 1, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ShenQingRenBuMen', 'ND103', 'ShenQingRenBuMen', '申请人部门', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 3, 1, 1, 106, 1, 3, '0', '@FontSize=12@IsRichText=0@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ShenQingRiJi', 'ND103', 'ShenQingRiJi', '申请日期', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 2, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_Title', 'ND103', 'Title', '标题', '', 0, 0, 1, 0, 251, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 106, 1, -1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ZJLSP_Checker', 'ND103', 'ZJLSP_Checker', '审核人', '@WebUser.No', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ZJLSP_Note', 'ND103', 'ZJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 106, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND103_ZJLSP_RDT', 'ND103', 'ZJLSP_RDT', '审核日期', '@RDT', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 106, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_BMJLSP_Checker', 'ND104', 'BMJLSP_Checker', '审核人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_BMJLSP_Note', 'ND104', 'BMJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 109, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_BMJLSP_RDT', 'ND104', 'BMJLSP_RDT', '审核日期', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_CDT', 'ND104', 'CDT', '发起时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 109, 1, 1001, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_Dao', 'ND104', 'Dao', '到', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 1008, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_Emps', 'ND104', 'Emps', 'Emps', '', 0, 0, 1, 0, 100, 23, 0, 8000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, 1003, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_FID', 'ND104', 'FID', 'FID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, 1000, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_FK_Dept', 'ND104', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'BP.Port.Depts', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, 1004, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_FK_NY', 'ND104', 'FK_NY', '年月', '', 0, 0, 1, 0, 100, 23, 0, 7, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, 1005, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_OID', 'ND104', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 109, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_QingJiaRiQiCong', 'ND104', 'QingJiaRiQiCong', '请假日期从', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 1007, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_QingJiaTianShu', 'ND104', 'QingJiaTianShu', '请假天数', '10002', 0, 0, 3, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 1009, '0', '@IsSum=0@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_QingJiaYuanYin', 'ND104', 'QingJiaYuanYin', '请假原因', '', 0, 0, 1, 0, 100, 123, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 109, 1, 1010, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_RDT', 'ND104', 'RDT', '更新时间', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 109, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_Rec', 'ND104', 'Rec', '发起人', '', 0, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, 1002, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ShenQingRen', 'ND104', 'ShenQingRen', '申请人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 1, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ShenQingRenBuMen', 'ND104', 'ShenQingRenBuMen', '申请人部门', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 3, 1, 1, 109, 1, 3, '0', '@FontSize=12@IsRichText=0@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ShenQingRiJi', 'ND104', 'ShenQingRiJi', '申请日期', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 2, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_Title', 'ND104', 'Title', '标题', '', 0, 0, 1, 0, 251, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 109, 1, -1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ZJLSP_Checker', 'ND104', 'ZJLSP_Checker', '审核人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ZJLSP_Note', 'ND104', 'ZJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 109, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND104_ZJLSP_RDT', 'ND104', 'ZJLSP_RDT', '审核日期', '', 0, 0, 7, 0, 145, 23, 0, 20, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 109, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FK_Dept', 'ND1RptMyJoin', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'bp.port.Depts', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1004, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FK_NY', 'ND1RptMyJoin', 'FK_NY', '年月', '', 0, 1, 1, 2, 100, 23, 0, 7, 'bp.pub.NYs', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1005, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowDaySpan', 'ND1RptMyJoin', 'FlowDaySpan', '跨度(天)', '', 1, 0, 8, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowEmps', 'ND1RptMyJoin', 'FlowEmps', '参与人', '', 1, 0, 1, 0, 100, 23, 0, 1000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowEnder', 'ND1RptMyJoin', 'FlowEnder', '结束人', '', 1, 1, 1, 2, 100, 23, 0, 20, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowEnderRDT', 'ND1RptMyJoin', 'FlowEnderRDT', '结束时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowEndNode', 'ND1RptMyJoin', 'FlowEndNode', '结束节点', '0', 1, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowStarter', 'ND1RptMyJoin', 'FlowStarter', '发起人', '', 1, 1, 1, 2, 100, 23, 0, 20, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_FlowStartRDT', 'ND1RptMyJoin', 'FlowStartRDT', '发起时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_OID', 'ND1RptMyJoin', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 100, 1, 999, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_Title', 'ND1RptMyJoin', 'Title', '标题', '', 0, 0, 1, 0, 120, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, -1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_WFSta', 'ND1RptMyJoin', 'WFSta', '状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFSta', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMyJoin_WFState', 'ND1RptMyJoin', 'WFState', '流程状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFState', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FK_Dept', 'ND1RptMy', 'FK_Dept', '操作员部门', '', 0, 1, 1, 2, 100, 23, 0, 100, 'bp.port.Depts', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1004, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FK_NY', 'ND1RptMy', 'FK_NY', '年月', '', 0, 1, 1, 2, 100, 23, 0, 7, 'bp.pub.NYs', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1005, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowDaySpan', 'ND1RptMy', 'FlowDaySpan', '跨度(天)', '', 1, 0, 8, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowEmps', 'ND1RptMy', 'FlowEmps', '参与人', '', 1, 0, 1, 0, 100, 23, 0, 1000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowEnder', 'ND1RptMy', 'FlowEnder', '结束人', '', 1, 1, 1, 2, 100, 23, 0, 100, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowEnderRDT', 'ND1RptMy', 'FlowEnderRDT', '结束时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowEndNode', 'ND1RptMy', 'FlowEndNode', '结束节点', '0', 1, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowStarter', 'ND1RptMy', 'FlowStarter', '发起人', '', 1, 1, 1, 2, 100, 23, 0, 100, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_FlowStartRDT', 'ND1RptMy', 'FlowStartRDT', '发起时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_OID', 'ND1RptMy', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 100, 1, 999, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_Title', 'ND1RptMy', 'Title', '标题', '', 0, 0, 1, 0, 120, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_WFSta', 'ND1RptMy', 'WFSta', '状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFSta', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1RptMy_WFState', 'ND1RptMy', 'WFState', '流程状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFState', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_AtPara', 'ND1Rpt', 'AtPara', '参数', '', 1, 0, 1, 0, 100, 23, 0, 4000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_BillNo', 'ND1Rpt', 'BillNo', '单据编号', '', 1, 0, 1, 0, 100, 23, 0, 100, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_BMJLSP_Checker', 'ND1Rpt', 'BMJLSP_Checker', '审核人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 1, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_BMJLSP_Note', 'ND1Rpt', 'BMJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 1, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_BMJLSP_RDT', 'ND1Rpt', 'BMJLSP_RDT', '审核日期', '', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 1, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_CDT', 'ND1Rpt', 'CDT', '活动时间', '', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 100, 1, 1001, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_Dao', 'ND1Rpt', 'Dao', '到', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1008, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_Emps', 'ND1Rpt', 'Emps', '参与者', '', 0, 0, 1, 0, 100, 23, 0, 8000, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1003, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FID', 'ND1Rpt', 'FID', 'FID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1000, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FK_Dept', 'ND1Rpt', 'FK_Dept', '操作员部门', '', 0, 0, 1, 0, 100, 23, 0, 100, 'BP.Port.Depts', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1004, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FK_NY', 'ND1Rpt', 'FK_NY', '年月', '', 0, 0, 1, 0, 100, 23, 0, 7, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, 1005, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowDaySpan', 'ND1Rpt', 'FlowDaySpan', '跨度(天)', '', 1, 0, 8, 0, 100, 23, 0, 300, '', '', '', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowEmps', 'ND1Rpt', 'FlowEmps', '参与人', '', 1, 0, 1, 0, 100, 23, 0, 1000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowEnder', 'ND1Rpt', 'FlowEnder', '结束人', '', 1, 1, 1, 2, 100, 23, 0, 20, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowEnderRDT', 'ND1Rpt', 'FlowEnderRDT', '结束时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowEndNode', 'ND1Rpt', 'FlowEndNode', '结束节点', '0', 1, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowNote', 'ND1Rpt', 'FlowNote', '流程信息', '', 1, 0, 1, 0, 100, 23, 0, 500, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowStarter', 'ND1Rpt', 'FlowStarter', '发起人', '', 1, 1, 1, 2, 100, 23, 0, 20, 'bp.port.Emps', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_FlowStartRDT', 'ND1Rpt', 'FlowStartRDT', '发起时间', '', 1, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_GUID', 'ND1Rpt', 'GUID', 'GUID', '', 1, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_OID', 'ND1Rpt', 'OID', 'OID', '0', 0, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 2, '', '', '', '', '', 1, 1, 1, 100, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PEmp', 'ND1Rpt', 'PEmp', '调起子流程的人员', '', 1, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PFlowNo', 'ND1Rpt', 'PFlowNo', '父流程流程编号', '', 1, 0, 1, 0, 100, 23, 0, 3, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PNodeID', 'ND1Rpt', 'PNodeID', '父流程启动的节点', '0', 1, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PrjName', 'ND1Rpt', 'PrjName', '项目名称', '', 1, 0, 1, 0, 100, 23, 0, 100, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PrjNo', 'ND1Rpt', 'PrjNo', '项目编号', '', 1, 0, 1, 0, 100, 23, 0, 100, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -100, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_PWorkID', 'ND1Rpt', 'PWorkID', '父流程WorkID', '0', 1, 0, 2, 0, 100, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -101, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_QingJiaRiQiCong', 'ND1Rpt', 'QingJiaRiQiCong', '请假日期从', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1007, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_QingJiaTianShu', 'ND1Rpt', 'QingJiaTianShu', '请假天数', '10002', 0, 0, 3, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1009, '0', '@IsSum=0@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_QingJiaYuanYin', 'ND1Rpt', 'QingJiaYuanYin', '请假原因', '', 0, 0, 1, 0, 100, 123, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 100, 1, 1010, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_RDT', 'ND1Rpt', 'RDT', '更新时间', '', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 1, '1', '', '', '', '', 1, 1, 1, 100, 1, 999, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_Rec', 'ND1Rpt', 'Rec', '发起人', '', 0, 0, 1, 0, 100, 23, 0, 32, '', '', '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, 1002, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ShenQingRen', 'ND1Rpt', 'ShenQingRen', '申请人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 1, '0', '@IsRichText=0@FontSize=12@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ShenQingRenBuMen', 'ND1Rpt', 'ShenQingRenBuMen', '申请人部门', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 3, 1, 1, 100, 1, 3, '0', '@FontSize=12@IsRichText=0@IsSupperText=0', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ShenQingRiJi', 'ND1Rpt', 'ShenQingRiJi', '申请日期', '', 0, 0, 6, 0, 125, 23, 0, 10, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 100, 1, 2, '0', '@FontSize=12', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_Title', 'ND1Rpt', 'Title', '标题', '', 0, 0, 1, 0, 251, 23, 0, 200, '', '', '', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 171.2, 68.4, '', 1, '', '', '', '', '', 1, 1, 1, 100, 1, -1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_WFSta', 'ND1Rpt', 'WFSta', '状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFSta', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_WFState', 'ND1Rpt', 'WFState', '流程状态', '', 1, 1, 2, 1, 100, 23, 0, 1000, 'WFState', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, '', 1, '', '', '', '', '', 1, 1, 1, 101, 1, -1, '0', '', NULL, NULL, NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ZJLSP_Checker', 'ND1Rpt', 'ZJLSP_Checker', '审核人', '', 0, 0, 1, 0, 100, 23, 0, 50, '', '', '', 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 2, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ZJLSP_Note', 'ND1Rpt', 'ZJLSP_Note', '审核意见', '', 0, 0, 1, 0, 100, 69, 0, 4000, '', '', '', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 5, 5, '', 0, '', '', '', '', '', 4, 1, 1, 103, 1, 1, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_mapattr` VALUES ('ND1Rpt_ZJLSP_RDT', 'ND1Rpt', 'ZJLSP_RDT', '审核日期', '', 0, 0, 7, 0, 145, 23, 0, 300, '', '', '', 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 5, 5, '', 0, '', '', '', '', '', 1, 1, 1, 103, 1, 3, '0', '', '0', '0', '0', 0, '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for sys_mapdata
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapdata`;
CREATE TABLE `sys_mapdata`  (
  `No` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'null - 主键',
  `FrmType` int(11) NULL DEFAULT NULL COMMENT '表单类型',
  `PTable` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物理表',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `FK_FormTree` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单树',
  `TableCol` int(11) NULL DEFAULT NULL COMMENT '表单显示列数,枚举类型:0 4列;1 6列;2 上下模式3列;',
  `RowOpenModel` int(11) NULL DEFAULT NULL COMMENT '行记录打开模式,枚举类型:0 新窗口打开;1 弹出窗口打开,关闭后刷新列表;2 弹出窗口打开,关闭后不刷新列表;',
  `EntityType` int(11) NULL DEFAULT NULL COMMENT '业务类型,枚举类型:0 独立表单;1 单据;2 编号名称实体;3 树结构实体;',
  `BillNoFormat` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体编号规则',
  `TitleRole` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题生成规则',
  `SortColumns` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序字段',
  `ColorSet` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色设置',
  `FieldSet` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段求和求平均设置',
  `BtnNewLable` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新建',
  `BtnNewModel` int(11) NULL DEFAULT NULL COMMENT '新建模式,枚举类型:0 表格模式;1 卡片模式;2 不可用;',
  `BtnSaveLable` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存',
  `BtnSubmitLable` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交',
  `BtnDelLable` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除',
  `BtnSearchLabel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列表',
  `BtnGroupLabel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分析',
  `BtnGroupEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnPrintHtml` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印Html',
  `BtnPrintHtmlEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnPrintPDF` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印PDF',
  `BtnPrintPDFEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnPrintRTF` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印RTF',
  `BtnPrintRTFEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnPrintCCWord` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印CCWord',
  `BtnPrintCCWordEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnExpZip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导出zip文件',
  `BtnExpZipEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnRefBill` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联单据',
  `RefBillRole` int(11) NULL DEFAULT NULL COMMENT '关联单据工作模式,枚举类型:0 不启用;1 非必须选择关联单据;2 必须选择关联单据;',
  `RefBill` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联单据ID',
  `BtnImpExcel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入Excel文件',
  `BtnImpExcelEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `BtnExpExcel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导出Excel文件',
  `BtnExpExcelEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用？',
  `Designer` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设计者',
  `DesignerContact` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `DesignerUnit` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `Ver` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `Note` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `Tag0` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag0',
  `Tag1` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Tag1',
  `Tag2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  `PopHeight` int(11) NULL DEFAULT NULL COMMENT '弹窗高度',
  `PopWidth` int(11) NULL DEFAULT NULL COMMENT '弹窗宽度',
  `EntityEditModel` int(11) NULL DEFAULT NULL COMMENT '编辑模式',
  `EntityShowModel` int(11) NULL DEFAULT NULL COMMENT '展示模式,枚举类型:0 表格;1 树干模式;',
  `FormEventEntity` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件实体',
  `EnPK` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体主键',
  `PTableModel` int(11) NULL DEFAULT NULL COMMENT '表存储模式',
  `URL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Url',
  `Dtls` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从表',
  `FrmW` int(11) NULL DEFAULT NULL COMMENT '系统表单宽度',
  `FrmH` int(11) NULL DEFAULT NULL COMMENT '系统表单高度',
  `Tag` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag',
  `FK_FrmSort` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单类别',
  `FrmShowType` int(11) NULL DEFAULT NULL COMMENT '表单展示方式,枚举类型:0 普通方式;1 页签方式;',
  `AppType` int(11) NULL DEFAULT NULL COMMENT '应用类型',
  `DBSrc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源,外键:对应物理表:Sys_SFDBSrc,表描述:数据源',
  `BodyAttr` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单Body属性',
  `FlowCtrls` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程控件',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  `IsTemplate` int(11) NULL DEFAULT NULL COMMENT '是否是表单模版',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '独立表单属性:FK_Flow',
  `RightViewWay` int(11) NULL DEFAULT NULL COMMENT '报表查看权限控制方式',
  `RightViewTag` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '报表查看权限控制Tag',
  `RightDeptWay` int(11) NULL DEFAULT NULL COMMENT '部门数据查看控制方式',
  `RightDeptTag` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '部门数据查看控制Tag',
  `DBURL` int(11) NULL DEFAULT NULL COMMENT 'DBURL',
  `TemplaterVer` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模版编号',
  `DBSave` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Word数据文件存储',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单模版',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFileExt',
  `WebPath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WebPath',
  `MyFileH` int(11) NULL DEFAULT NULL COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT NULL COMMENT 'MyFileW',
  `MyFileSize` float(11, 2) NULL DEFAULT NULL COMMENT 'MyFileSize',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统表单' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_mapdata
-- ----------------------------
INSERT INTO `sys_mapdata` VALUES ('ND101', 0, 'ND1Rpt', '填写请假申请单', '', 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, 1, NULL, 1, '', '', '', '', '2021-07-01 18:13:01', '', 100, NULL, NULL, NULL, '@FrmImgs_AutoNum=-1@MapFrames_AutoNum=-1@FrmAttachments_AutoNum=-1@FrmImgAths_AutoNum=-1@FrmRBs_AutoNum=-1@MapDtls_AutoNum=0@MapExts_AutoNum=-1@FrmEvents_AutoNum=-1@IsHaveCA=0', 500, 760, 0, 0, '', '', 0, ',/WF/Comm/Handler.ashx', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', 0, '', 0, NULL, 0, NULL, 0, '', '', '', '', '', '', 0, 0, 0.00);
INSERT INTO `sys_mapdata` VALUES ('ND102', 0, 'ND1Rpt', '部门经理审批', '', 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, 1, NULL, 1, '', '', '', '', '2021-07-01 18:13:01', '', 100, NULL, NULL, NULL, '@FrmImgs_AutoNum=0@MapFrames_AutoNum=0@FrmAttachments_AutoNum=0@FrmImgAths_AutoNum=0@FrmRBs_AutoNum=0@MapDtls_AutoNum=0@MapExts_AutoNum=0@FrmEvents_AutoNum=0@IsHaveCA=0', 500, 760, 0, 0, '', '', 0, ',/WF/Comm/Handler.ashx', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', 0, '', 0, NULL, 0, NULL, 0, '', '', '', '', '', '', 0, 0, 0.00);
INSERT INTO `sys_mapdata` VALUES ('ND103', 0, 'ND1Rpt', '总经理审批', '', 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, 1, NULL, 1, '', '', '', '', '2021-07-01 18:13:02', '', 100, NULL, NULL, NULL, '@FrmImgs_AutoNum=-1@MapFrames_AutoNum=-1@FrmAttachments_AutoNum=-1@FrmImgAths_AutoNum=-1@FrmRBs_AutoNum=-1@MapDtls_AutoNum=0@MapExts_AutoNum=-1@FrmEvents_AutoNum=-1@IsHaveCA=0', 500, 760, 0, 0, '', '', 0, ',/WF/Comm/Handler.ashx', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', 0, '', 0, NULL, 0, NULL, 0, '', '', '', '', '', '', 0, 0, 0.00);
INSERT INTO `sys_mapdata` VALUES ('ND104', 0, 'ND1Rpt', '反馈给申请人', '', 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, 1, NULL, 1, '', '', '', '', '2021-07-01 18:13:03', '', 100, NULL, NULL, NULL, '@FrmAttachments_AutoNum=-1@MapDtls_AutoNum=0@MapExts_AutoNum=-1@FrmImgAths_AutoNum=-1@IsHaveCA=0@FrmEvents_AutoNum=-1', 500, 760, 0, 0, '', '', 0, ',/WF/Comm/Handler.ashx', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', 0, '', 0, NULL, 0, NULL, 0, '', '', '', '', '', '', 0, 0, 0.00);
INSERT INTO `sys_mapdata` VALUES ('ND1Rpt', 1, 'ND1Rpt', '请假流程-经典表单-演示', '', 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, 1, NULL, 1, '', '', '', '', '2020-12-11 11:09:04', '', 100, NULL, NULL, NULL, '@MapDtls_AutoNum=0@IsHaveCA=0', 500, 760, 0, 0, '', '', 0, '', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', 0, '', 0, NULL, 0, NULL, 0, '', '', '', '', '', '', 0, 0, 0.00);
INSERT INTO `sys_mapdata` VALUES ('ND1RptMy', 1, 'ND1Rpt', '我发起的流程', '', 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '', '', '2020-11-30 11:12:00', '', 100, NULL, NULL, NULL, '@RptIsSearchKey=1@RptDTSearchWay=0@RptDTSearchKey=@MapDtls_AutoNum=0', NULL, NULL, NULL, NULL, '', '', 0, '', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_mapdata` VALUES ('ND1RptMyJoin', 1, 'ND1Rpt', '我审批的流程', '', 0, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '', '', '2021-04-28 17:24:20', '', 100, NULL, NULL, NULL, '@RptIsSearchKey=1@RptDTSearchWay=0@RptDTSearchKey=@MapDtls_AutoNum=0', NULL, NULL, NULL, NULL, '', '', 0, '', '', 900, 1200, '', '', 0, 0, 'local', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_mapdtl
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapdtl`;
CREATE TABLE `sys_mapdtl`  (
  `No` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `Alias` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `PTable` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储表',
  `GroupField` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组字段',
  `RefPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联的主键',
  `FEBD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类实体类',
  `Model` int(11) NULL DEFAULT NULL COMMENT '工作模式,枚举类型:0 普通;1 固定行;',
  `DtlVer` int(11) NULL DEFAULT NULL COMMENT '使用版本,枚举类型:0 2017传统版;',
  `RowsOfList` int(11) NULL DEFAULT NULL COMMENT '初始化行数',
  `IsEnableGroupField` int(11) NULL DEFAULT NULL COMMENT '是否启用分组字段',
  `IsShowSum` int(11) NULL DEFAULT NULL COMMENT '是否显示合计？',
  `IsShowIdx` int(11) NULL DEFAULT NULL COMMENT '是否显示序号？',
  `IsCopyNDData` int(11) NULL DEFAULT NULL COMMENT '是否允许copy节点数据',
  `IsHLDtl` int(11) NULL DEFAULT NULL COMMENT '是否是合流汇总',
  `IsReadonly` int(11) NULL DEFAULT NULL COMMENT '是否只读？',
  `IsShowTitle` int(11) NULL DEFAULT NULL COMMENT '是否显示标题？',
  `IsView` int(11) NULL DEFAULT NULL COMMENT '是否可见？',
  `IsInsert` int(11) NULL DEFAULT NULL COMMENT '是否可以插入行？',
  `IsDelete` int(11) NULL DEFAULT NULL COMMENT '是否可以删除行？',
  `IsUpdate` int(11) NULL DEFAULT NULL COMMENT '是否可以更新？',
  `IsEnablePass` int(11) NULL DEFAULT NULL COMMENT '是否启用通过审核功能?',
  `IsEnableAthM` int(11) NULL DEFAULT NULL COMMENT '是否启用多附件',
  `IsEnableM2M` int(11) NULL DEFAULT NULL COMMENT '是否启用M2M',
  `IsEnableM2MM` int(11) NULL DEFAULT NULL COMMENT '是否启用M2M',
  `WhenOverSize` int(11) NULL DEFAULT NULL COMMENT '超出行数,枚举类型:0 不处理;1 向下顺增行;2 次页显示;',
  `DtlOpenType` int(11) NULL DEFAULT NULL COMMENT '数据开放类型,枚举类型:0 操作员;1 WorkID-流程ID;2 FID-干流程ID;3 PWorkID-父流程WorkID;',
  `ListShowModel` int(11) NULL DEFAULT NULL COMMENT '列表数据显示格式,枚举类型:0 表格;1 卡片;',
  `EditModel` int(11) NULL DEFAULT NULL COMMENT '编辑数据方式,枚举类型:0 表格模式;1 傻瓜表单;2 自由表单;',
  `MobileShowModel` int(11) NULL DEFAULT NULL COMMENT '移动端数据显示方式,枚举类型:0 新页面显示模式;1 列表模式;',
  `MobileShowField` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '移动端列表显示字段',
  `X` float NULL DEFAULT NULL COMMENT '距左',
  `Y` float NULL DEFAULT NULL COMMENT '距上',
  `H` float NULL DEFAULT NULL COMMENT '高度',
  `W` float NULL DEFAULT NULL COMMENT '宽度',
  `FrmW` float NULL DEFAULT NULL COMMENT '表单宽度',
  `FrmH` float NULL DEFAULT NULL COMMENT '表单高度',
  `IsEnableLink` int(11) NULL DEFAULT NULL COMMENT '是否启用超链接',
  `LinkLabel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超连接标签',
  `LinkTarget` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接目标',
  `LinkUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接URL',
  `FilterSQLExp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过滤数据SQL表达式',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点(用户独立表单权限控制)',
  `ShowCols` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的列',
  `IsExp` int(11) NULL DEFAULT NULL COMMENT '是否可以导入？',
  `ImpModel` int(11) NULL DEFAULT NULL COMMENT '导入方式,枚举类型:0 不导入;1 按配置模式导入;2 按照xls文件模版导入;',
  `ImpSQLSearch` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '查询SQL(SQL里必须包含@Key关键字.)',
  `ImpSQLInit` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '初始化SQL(初始化表格的时候的SQL数据,可以为空)',
  `ImpSQLFullOneRow` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '数据填充一行数据的SQL(必须包含@Key关键字,为选择的主键)',
  `ImpSQLNames` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列的中文名称',
  `IsImp` int(11) NULL DEFAULT NULL COMMENT '是否可以导出？',
  `ColAutoExp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列自动计算表达式',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `AtPara` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `SubThreadWorker` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子线程处理人字段',
  `SubThreadWorkerText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子线程处理人字段',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '明细' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_mapext
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapext`;
CREATE TABLE `sys_mapext`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主表',
  `ExtType` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `DoWay` int(11) NULL DEFAULT NULL COMMENT '执行方式',
  `AttrOfOper` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的Attr',
  `AttrsOfActive` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活的字段',
  `Doc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `Tag` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag',
  `Tag1` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag1',
  `Tag2` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `Tag3` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag3',
  `Tag4` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag4',
  `Tag5` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag5',
  `H` int(11) NULL DEFAULT NULL COMMENT '高度',
  `W` int(11) NULL DEFAULT NULL COMMENT '宽度',
  `DBType` int(11) NULL DEFAULT NULL COMMENT '数据类型',
  `FK_DBSrc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源',
  `PRI` int(11) NULL DEFAULT NULL COMMENT 'PRI/顺序号',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参数',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '业务逻辑' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_mapframe
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapframe`;
CREATE TABLE `sys_mapframe`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `URL` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `FrameURL` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `UrlSrcType` int(11) NULL DEFAULT NULL COMMENT 'URL来源,枚举类型:0 自定义;1 地图;2 流程轨迹表;3 流程轨迹图;',
  `Y` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Y',
  `X` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'x',
  `W` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宽度',
  `H` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高度',
  `IsAutoSize` int(11) NULL DEFAULT NULL COMMENT '是否自动设置大小',
  `EleType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `GUID` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '框架' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_rptdept
-- ----------------------------
DROP TABLE IF EXISTS `sys_rptdept`;
CREATE TABLE `sys_rptdept`  (
  `FK_Rpt` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表 - 主键',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FK_Rpt`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表部门对应信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_rptemp
-- ----------------------------
DROP TABLE IF EXISTS `sys_rptemp`;
CREATE TABLE `sys_rptemp`  (
  `FK_Rpt` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FK_Rpt`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表人员对应信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_rptstation
-- ----------------------------
DROP TABLE IF EXISTS `sys_rptstation`;
CREATE TABLE `sys_rptstation`  (
  `FK_Rpt` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Rpt`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表岗位对应信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_rpttemplate
-- ----------------------------
DROP TABLE IF EXISTS `sys_rpttemplate`;
CREATE TABLE `sys_rpttemplate`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `EnsName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类名',
  `FK_Emp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `D1` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'D1',
  `D2` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'D2',
  `D3` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'D3',
  `AlObjs` varchar(90) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要分析的对象',
  `Height` int(11) NULL DEFAULT NULL COMMENT 'Height',
  `Width` int(11) NULL DEFAULT NULL COMMENT 'Width',
  `IsSumBig` int(11) NULL DEFAULT NULL COMMENT '是否显示大合计',
  `IsSumLittle` int(11) NULL DEFAULT NULL COMMENT '是否显示小合计',
  `IsSumRight` int(11) NULL DEFAULT NULL COMMENT '是否显示右合计',
  `PercentModel` int(11) NULL DEFAULT NULL COMMENT '比率显示方式',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报表模板' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_serial
-- ----------------------------
DROP TABLE IF EXISTS `sys_serial`;
CREATE TABLE `sys_serial`  (
  `CfgKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CfgKey - 主键',
  `IntVal` int(11) NULL DEFAULT NULL COMMENT '属性',
  PRIMARY KEY (`CfgKey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '序列号' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_serial
-- ----------------------------
INSERT INTO `sys_serial` VALUES ('bp.wf.template.FlowSort', 102);
INSERT INTO `sys_serial` VALUES ('OID', 209);
INSERT INTO `sys_serial` VALUES ('UpdataCCFlowVer', 1125105518);
INSERT INTO `sys_serial` VALUES ('Ver', 20210422);
INSERT INTO `sys_serial` VALUES ('WorkID', 122);

-- ----------------------------
-- Table structure for sys_sfdbsrc
-- ----------------------------
DROP TABLE IF EXISTS `sys_sfdbsrc`;
CREATE TABLE `sys_sfdbsrc`  (
  `No` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源编号(必须是英文) - 主键',
  `Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `DBSrcType` int(11) NULL DEFAULT NULL COMMENT '数据源类型,枚举类型:0 应用系统主数据库(默认);1 SQLServer数据库;2 Oracle数据库;3 MySQL数据库;4 Informix数据库;50 Dubbo服务;100 WebService数据源;',
  `UserID` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库登录用户ID',
  `Password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库登录用户密码',
  `IP` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址/数据库实例名',
  `DBName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库名称/Oracle保持为空',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据源' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_sfdbsrc
-- ----------------------------
INSERT INTO `sys_sfdbsrc` VALUES ('local', '本机数据源(默认)', 0, '', '', '', '');

-- ----------------------------
-- Table structure for sys_sftable
-- ----------------------------
DROP TABLE IF EXISTS `sys_sftable`;
CREATE TABLE `sys_sftable`  (
  `No` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表英文名称 - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表中文名称',
  `SrcType` int(11) NULL DEFAULT NULL COMMENT '数据表类型,枚举类型:0 本地的类;1 创建表;2 表或视图;3 SQL查询表;4 WebServices;5 微服务Handler外部数据源;6 JavaScript外部数据源;7 系统字典表;',
  `CodeStruct` int(11) NULL DEFAULT NULL COMMENT '字典表类型,枚举类型:0 普通的编码表(具有No,Name);1 树结构(具有No,Name,ParentNo);2 行政机构编码表(编码以两位编号标识级次树形关系);',
  `RootVal` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '根节点值',
  `FK_Val` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认创建的字段名',
  `TableDesc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表描述',
  `DefVal` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `NoGenerModel` int(11) NULL DEFAULT NULL COMMENT '编号生成规则,枚举类型:0 自定义;1 流水号;2 标签的全拼;3 标签的简拼;4 按GUID生成;',
  `FK_SFDBSrc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源,外键:对应物理表:Sys_SFDBSrc,表描述:数据源',
  `SrcTable` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表/视图',
  `ColumnValue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的值(编号列/默认为No)',
  `ColumnText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示的文字(名称列默认为Name)',
  `ParentValue` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级值(父级列)',
  `SelectStatement` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '查询语句',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加入日期',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_sftabledtl
-- ----------------------------
DROP TABLE IF EXISTS `sys_sftabledtl`;
CREATE TABLE `sys_sftabledtl`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_SFTable` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键表ID',
  `BH` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'BH',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `ParentNo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点ID',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Sender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人(可以为空)',
  `SendTo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送给(可以为空)',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写入时间',
  `Mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号(可以为空)',
  `MobileSta` int(11) NULL DEFAULT NULL COMMENT '消息状态',
  `MobileInfo` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短信信息',
  `Email` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email(可以为空)',
  `EmailSta` int(11) NULL DEFAULT NULL COMMENT 'EmaiSta消息状态',
  `EmailTitle` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `EmailDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `SendDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送时间',
  `IsRead` int(11) NULL DEFAULT NULL COMMENT '是否读取?',
  `IsAlert` int(11) NULL DEFAULT NULL COMMENT '是否提示?',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `MsgFlag` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息标记(用于防止发送重复)',
  `MsgType` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型(CC抄送,Todolist待办,Return退回,Etc其他消息...)',
  `AtPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_sms
-- ----------------------------
INSERT INTO `sys_sms` VALUES ('038dcd5083354ff9a45591975004cf55', 'admin', 'zhoushengyu', '2020-11-27 16:18', '', 0, '有新工作{集团总部-admin,admin在2020-11-24 10:00发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_100_201_1f172b3f79ce4e5db47b714e7232218d .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-24 10:00发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-24 10:00发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_100_201_1f172b3f79ce4e5db47b714e7232218d .', '', 0, 0, 100, 'WKAlt201_100', 'SendSuccess', '@FK_Flow=002@WorkID=100@NodeID=201@FK_Node=201@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_100_201_1f172b3f79ce4e5db47b714e7232218d@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('0692c0cc7be74684a1839e000851f449', 'admin', 'zhoushengyu', '2020-11-27 16:21', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_110_101_6398363ac2964f5fbd87bf2da15481af .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:21发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_110_101_6398363ac2964f5fbd87bf2da15481af .', '', 0, 0, 110, 'WKAlt101_110', 'SendSuccess', '@FK_Flow=001@WorkID=110@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_110_101_6398363ac2964f5fbd87bf2da15481af@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('12015414108041aab622ba5e77b65f16', 'zhoushengyu', 'zhoupeng', '2020-12-10 08:38', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:25发起.}需要您处理, 发送人:zhoushengyu, 周升雨,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_116_102_5ccdc340ca3b459e88e1c97714091242 .', 'zhoupeng@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:25发起.},发送人zhoushengyu,周升雨', '有新工作{集团总部-admin,admin在2020-11-27 16:25发起.}需要您处理, 发送人:zhoushengyu, 周升雨,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_116_102_5ccdc340ca3b459e88e1c97714091242 .', '', 0, 0, 116, 'WKAlt102_116', 'SendSuccess', '@FK_Flow=001@WorkID=116@NodeID=102@FK_Node=102@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_116_102_5ccdc340ca3b459e88e1c97714091242@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('46c10f2b59e3486981d6ee1ae3710718', 'admin', 'zhoushengyu', '2020-11-27 16:21', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:20发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_109_101_79d44055bcd74f77ae2aa05171cf8506 .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:20发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:20发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_109_101_79d44055bcd74f77ae2aa05171cf8506 .', '', 0, 0, 109, 'WKAlt101_109', 'SendSuccess', '@FK_Flow=001@WorkID=109@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_109_101_79d44055bcd74f77ae2aa05171cf8506@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('482a77f79b47488b93da910a4afccadb', 'admin', 'zhoushengyu', '2020-11-27 16:23', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:22发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_113_101_2f03d3fb3092417bbd0e74ed7173157a .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:22发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:22发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_113_101_2f03d3fb3092417bbd0e74ed7173157a .', '', 0, 0, 113, 'WKAlt101_113', 'SendSuccess', '@FK_Flow=001@WorkID=113@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_113_101_2f03d3fb3092417bbd0e74ed7173157a@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('59dbbb61282f4f0d8f48ce0b51946800', 'admin', 'admin', '2021-07-01 16:21', '', 0, '有新工作{总部-admin,admin在2021-07-01 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=admin_120_101_d375c52bf2894faeacc4122e5f498140 .', 'zhoupeng@ccflow.org', 0, '新工作{总部-admin,admin在2021-07-01 16:21发起.},发送人admin,admin', '有新工作{总部-admin,admin在2021-07-01 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=admin_120_101_d375c52bf2894faeacc4122e5f498140 .', '', 0, 0, 120, 'WKAlt101_120', 'SendSuccess', '@FK_Flow=001@WorkID=120@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=admin_120_101_d375c52bf2894faeacc4122e5f498140@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('7772d8157be14a6983a43adcd261da0b', 'admin', 'zhoushengyu', '2020-11-27 16:22', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:22发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_112_101_bcf58b02c984467684ee5f2b2f0492ac .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:22发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:22发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_112_101_bcf58b02c984467684ee5f2b2f0492ac .', '', 0, 0, 112, 'WKAlt101_112', 'SendSuccess', '@FK_Flow=001@WorkID=112@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_112_101_bcf58b02c984467684ee5f2b2f0492ac@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('77f0a7b937584e6095ba3208431888d6', 'admin', 'zhoushengyu', '2020-11-27 16:24', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:23发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_114_101_3ce4a00bba8147b28cc70b0ba4414218 .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:23发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:23发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_114_101_3ce4a00bba8147b28cc70b0ba4414218 .', '', 0, 0, 114, 'WKAlt101_114', 'SendSuccess', '@FK_Flow=001@WorkID=114@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_114_101_3ce4a00bba8147b28cc70b0ba4414218@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('8715971de01a41c39a7afb18ceaa87a7', 'admin', 'zhoushengyu', '2020-11-27 16:22', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_111_101_772add67bb48450daa77b7cbffbd722d .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:21发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:21发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_111_101_772add67bb48450daa77b7cbffbd722d .', '', 0, 0, 111, 'WKAlt101_111', 'SendSuccess', '@FK_Flow=001@WorkID=111@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_111_101_772add67bb48450daa77b7cbffbd722d@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('8b176e29def341acb5b9045f32eca42c', 'zhoushengyu', 'zhoupeng', '2020-12-11 13:44', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:16发起.}需要您处理, 发送人:zhoushengyu, 周升雨,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_101_102_9369a1f2069b433dbfe896035fecae6e .', 'zhoupeng@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:16发起.},发送人zhoushengyu,周升雨', '有新工作{集团总部-admin,admin在2020-11-27 16:16发起.}需要您处理, 发送人:zhoushengyu, 周升雨,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_101_102_9369a1f2069b433dbfe896035fecae6e .', '', 0, 0, 101, 'WKAlt102_101', 'SendSuccess', '@FK_Flow=001@WorkID=101@NodeID=102@FK_Node=102@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoupeng_101_102_9369a1f2069b433dbfe896035fecae6e@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('943a93aed50d45b39a6315d9db73c588', 'admin', 'zhoushengyu', '2020-11-27 16:24', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:24发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_115_101_cb0005dbfd6341f0bf96e819bc864287 .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:24发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:24发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_115_101_cb0005dbfd6341f0bf96e819bc864287 .', '', 0, 0, 115, 'WKAlt101_115', 'SendSuccess', '@FK_Flow=001@WorkID=115@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_115_101_cb0005dbfd6341f0bf96e819bc864287@PushModel=Email');
INSERT INTO `sys_sms` VALUES ('f06ef809b87545a5999bbb7f049dbe12', 'admin', 'zhoushengyu', '2020-11-27 16:20', '', 0, '有新工作{集团总部-admin,admin在2020-11-27 16:20发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_108_101_a2ef207657fc41ff80bcf07eca4feac4 .', 'zhoushengyu@ccflow.org', 0, '新工作{集团总部-admin,admin在2020-11-27 16:20发起.},发送人admin,admin', '有新工作{集团总部-admin,admin在2020-11-27 16:20发起.}需要您处理, 发送人:admin, admin,打开http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_108_101_a2ef207657fc41ff80bcf07eca4feac4 .', '', 0, 0, 108, 'WKAlt101_108', 'SendSuccess', '@FK_Flow=001@WorkID=108@NodeID=101@FK_Node=101@OpenUrl=http://localhost:8080/jflow-web/WF/Do.htm?DoType=OF&SID=zhoushengyu_108_101_a2ef207657fc41ff80bcf07eca4feac4@PushModel=Email');

-- ----------------------------
-- Table structure for sys_userlogt
-- ----------------------------
DROP TABLE IF EXISTS `sys_userlogt`;
CREATE TABLE `sys_userlogt`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Emp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户',
  `IP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `LogFlag` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识',
  `Docs` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `RDT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_userlogt
-- ----------------------------
INSERT INTO `sys_userlogt` VALUES ('2d7e87d536334321968597a7c07cc1cb', 'admin', '', 'StartFlow', '发起流程', '2021-07-01 16:21');

-- ----------------------------
-- Table structure for sys_userregedit
-- ----------------------------
DROP TABLE IF EXISTS `sys_userregedit`;
CREATE TABLE `sys_userregedit`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `EnsName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体类名称',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `Attrs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '属性s',
  `FK_MapData` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体',
  `AttrKey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点对应字段',
  `LB` int(11) NULL DEFAULT NULL COMMENT '类别',
  `CurValue` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文本',
  `CfgKey` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '键',
  `Vals` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `GenerSQL` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GenerSQL',
  `Paras` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Paras',
  `NumKey` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分析的Key',
  `OrderBy` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrderBy',
  `OrderWay` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrderWay',
  `SearchKey` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SearchKey',
  `MVals` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MVals',
  `IsPic` int(11) NULL DEFAULT NULL COMMENT '是否图片',
  `DTFrom` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询时间从',
  `DTTo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  `ContrastKey` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型CYY',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '常用语' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_userregedit
-- ----------------------------
INSERT INTO `sys_userregedit` VALUES ('adminbp.wf.data.MyStartFlow_SearchAttrs', NULL, 'admin', NULL, NULL, NULL, NULL, NULL, 'bp.wf.data.MyStartFlow_SearchAttrs', '', '', '', '', '', '', '', '', 0, '', '', '', NULL);
INSERT INTO `sys_userregedit` VALUES ('adminND1RptMyJoin_SearchAttrs', NULL, 'admin', NULL, NULL, NULL, NULL, NULL, 'ND1RptMyJoin_SearchAttrs', '', '', '', '', '', '', '', '', 0, '', '', '@SelectFields=,Title,FlowStarter,FlowStartRDT,FK_Dept,WFState,@Count=11', NULL);
INSERT INTO `sys_userregedit` VALUES ('admin_BP.App.Port.Emps_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=1', NULL);
INSERT INTO `sys_userregedit` VALUES ('admin_BP.WF.Data.Delays_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=0', NULL);
INSERT INTO `sys_userregedit` VALUES ('admin_BP.WF.Data.MyJoinFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=15', NULL);
INSERT INTO `sys_userregedit` VALUES ('admin_BP.WF.Data.MyStartFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=15', NULL);
INSERT INTO `sys_userregedit` VALUES ('fuhuiND1RptMy_SearchAttrs', NULL, 'fuhui', NULL, NULL, NULL, NULL, NULL, 'ND1RptMy_SearchAttrs', '', '', '', '', '', '', '', '', 0, '', '', '', NULL);
INSERT INTO `sys_userregedit` VALUES ('zhoupeng_BP.WF.Data.MyStartFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=0', NULL);
INSERT INTO `sys_userregedit` VALUES ('zhoushengyu_BP.WF.Data.Delays_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=0', NULL);
INSERT INTO `sys_userregedit` VALUES ('zhoushengyu_BP.WF.Data.MyDeptFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=1', NULL);
INSERT INTO `sys_userregedit` VALUES ('zhoushengyu_BP.WF.Data.MyJoinFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=5', NULL);
INSERT INTO `sys_userregedit` VALUES ('zhoushengyu_BP.WF.Data.MyStartFlows_SearchAttrs', NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', '', '', '', '', '', '', '', 0, '', '', '@RecCount=0', NULL);

-- ----------------------------
-- Table structure for testindex
-- ----------------------------
DROP TABLE IF EXISTS `testindex`;
CREATE TABLE `testindex`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auto_id`(`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testindex
-- ----------------------------
INSERT INTO `testindex` VALUES (1, '1');
INSERT INTO `testindex` VALUES (2, '2');

-- ----------------------------
-- Table structure for testindex_copy1
-- ----------------------------
DROP TABLE IF EXISTS `testindex_copy1`;
CREATE TABLE `testindex_copy1`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auto_id`(`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testindex_copy1
-- ----------------------------
INSERT INTO `testindex_copy1` VALUES (1, '1');
INSERT INTO `testindex_copy1` VALUES (2, '2');

-- ----------------------------
-- Table structure for wf_accepterrole
-- ----------------------------
DROP TABLE IF EXISTS `wf_accepterrole`;
CREATE TABLE `wf_accepterrole`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `FK_Node` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点',
  `FK_Mode` int(11) NULL DEFAULT NULL COMMENT '模式类型',
  `Tag0` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag0',
  `Tag1` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag1',
  `Tag2` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `Tag3` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag3',
  `Tag4` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag4',
  `Tag5` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag5',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接受人规则' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_athunreadlog
-- ----------------------------
DROP TABLE IF EXISTS `wf_athunreadlog`;
CREATE TABLE `wf_athunreadlog`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门,外键:对应物理表:Port_Dept,表描述:部门',
  `Title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `FlowStarter` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `FlowStartRDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起时间',
  `FK_Flow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程,外键:对应物理表:WF_Flow,表描述:流程',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `NodeName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `FK_Emp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员',
  `FK_EmpDept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员部门',
  `FK_EmpDeptName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员名称',
  `BeiZhu` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `SendDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件未读日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_auth
-- ----------------------------
DROP TABLE IF EXISTS `wf_auth`;
CREATE TABLE `wf_auth`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Auther` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权人',
  `AuthType` int(11) NULL DEFAULT NULL COMMENT '类型(0=全部流程1=指定流程)',
  `EmpNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '委托给人员编号',
  `EmpName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '委托给人员名称',
  `FlowNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FlowName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `TakeBackDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取回日期',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  `AutherToEmpNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权给谁?',
  `AutherToEmpName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权给谁?',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_bill
-- ----------------------------
DROP TABLE IF EXISTS `wf_bill`;
CREATE TABLE `wf_bill`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT '工作ID',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `FK_BillType` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据类型',
  `Title` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `FK_Starter` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `StartDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起时间',
  `Url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Url',
  `FullPath` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FullPath',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印人,外键:对应物理表:Port_Emp,表描述:用户',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印时间',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属部门,外键:对应物理表:Port_Dept,表描述:部门',
  `FK_NY` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属年月',
  `Emps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Emps',
  `FK_Node` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点',
  `FK_Bill` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_Bill',
  `MyNum` int(11) NULL DEFAULT NULL COMMENT '个数',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_billtemplate
-- ----------------------------
DROP TABLE IF EXISTS `wf_billtemplate`;
CREATE TABLE `wf_billtemplate`  (
  `No` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'No - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `TempFilePath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板路径',
  `NodeID` int(11) NULL DEFAULT NULL COMMENT 'NodeID',
  `FK_MapData` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单编号',
  `BillFileType` int(11) NULL DEFAULT NULL COMMENT '生成的文件类型,枚举类型:0 Word;1 PDF;2 Excel(未完成);3 Html(未完成);',
  `BillOpenModel` int(11) NULL DEFAULT NULL COMMENT '生成的文件打开方式,枚举类型:0 下载本地;1 在线WebOffice打开;',
  `QRModel` int(11) NULL DEFAULT NULL COMMENT '二维码生成方式,枚举类型:0 不生成;1 生成二维码;',
  `TemplateFileModel` int(11) NULL DEFAULT NULL COMMENT '模版模式,枚举类型:0 rtf模版;1 vsto模式的word模版;2 vsto模式的excel模版;',
  `Idx` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Idx',
  `MyFrmID` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单编号',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单据模板' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_ccdept
-- ----------------------------
DROP TABLE IF EXISTS `wf_ccdept`;
CREATE TABLE `wf_ccdept`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点,主外键:对应物理表:WF_Node,表描述:节点',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FK_Node`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抄送部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_ccemp
-- ----------------------------
DROP TABLE IF EXISTS `wf_ccemp`;
CREATE TABLE `wf_ccemp`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FK_Node`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抄送人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_cclist
-- ----------------------------
DROP TABLE IF EXISTS `wf_cclist`;
CREATE TABLE `wf_cclist`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `Doc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `Sta` int(11) NULL DEFAULT NULL COMMENT '状态',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FlowName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `NodeName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT '工作ID',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送人员',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送日期',
  `CCTo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送给',
  `CCToName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送给(人员名称)',
  `CCToDept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送到部门',
  `CCToDeptName` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送给部门名称',
  `CCToOrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送到组织',
  `CCToOrgName` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送给组织名称',
  `CDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打开时间',
  `ReadDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阅读时间',
  `PFlowNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父流程编号',
  `PWorkID` int(11) NULL DEFAULT NULL COMMENT '父流程WorkID',
  `InEmpWorks` int(11) NULL DEFAULT NULL COMMENT '是否加入待办列表',
  `Domain` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Domain',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抄送列表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_ccstation
-- ----------------------------
DROP TABLE IF EXISTS `wf_ccstation`;
CREATE TABLE `wf_ccstation`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点,主外键:对应物理表:WF_Node,表描述:节点',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Node`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '抄送岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_ch
-- ----------------------------
DROP TABLE IF EXISTS `wf_ch`;
CREATE TABLE `wf_ch`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT '工作ID',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `Title` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `FK_Flow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `FK_FlowT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `FK_NodeT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `Sender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `SenderT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人名称',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人',
  `FK_EmpT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当事人名称',
  `GroupEmps` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '相关当事人',
  `GroupEmpsNames` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '相关当事人名称',
  `GroupEmpsNum` int(11) NULL DEFAULT NULL COMMENT '相关当事人数量',
  `DTFrom` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务下达时间',
  `DTTo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务处理时间',
  `SDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应完成日期',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属部门',
  `FK_DeptT` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `FK_NY` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隶属月份',
  `DTSWay` int(11) NULL DEFAULT NULL COMMENT '考核方式,枚举类型:0 不考核;1 按照时效考核;2 按照工作量考核;',
  `TimeLimit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规定限期',
  `OverMinutes` float NULL DEFAULT NULL COMMENT '逾期分钟',
  `UseDays` float NULL DEFAULT NULL COMMENT '实际使用天',
  `OverDays` float NULL DEFAULT NULL COMMENT '逾期天',
  `CHSta` int(11) NULL DEFAULT NULL COMMENT '状态',
  `WeekNum` int(11) NULL DEFAULT NULL COMMENT '第几周',
  `Points` float NULL DEFAULT NULL COMMENT '总扣分',
  `MyNum` int(11) NULL DEFAULT NULL COMMENT '个数',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '时效考核' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_cheval
-- ----------------------------
DROP TABLE IF EXISTS `wf_cheval`;
CREATE TABLE `wf_cheval`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FlowName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT '工作ID',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '评价节点',
  `NodeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停留节点',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价人',
  `RecName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价人名称',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价日期',
  `EvalEmpNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被考核的人员编号',
  `EvalEmpName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被考核的人员名称',
  `EvalCent` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价分值',
  `EvalNote` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `FK_Dept` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `DeptName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `FK_NY` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工作质量评价' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_chnode
-- ----------------------------
DROP TABLE IF EXISTS `wf_chnode`;
CREATE TABLE `wf_chnode`  (
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `NodeName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `FK_Emp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人',
  `FK_EmpT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人名称',
  `StartDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划开始时间',
  `EndDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划结束时间',
  `GT` int(11) NULL DEFAULT NULL COMMENT '工天',
  `Scale` float NULL DEFAULT NULL COMMENT '阶段占比',
  `TotalScale` float NULL DEFAULT NULL COMMENT '总进度',
  `ChanZhi` float NULL DEFAULT NULL COMMENT '产值',
  `AtPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `MyPK` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'MyPK - 主键',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点时限' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_cond
-- ----------------------------
DROP TABLE IF EXISTS `wf_cond`;
CREATE TABLE `wf_cond`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `RefFlowNo` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `CondType` int(11) NULL DEFAULT NULL COMMENT '条件类型',
  `DataFrom` int(11) NULL DEFAULT NULL COMMENT '条件数据来源0表单,1岗位(对方向条件有效)',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `SubFlowNo` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程编号',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID(对方向条件有效)',
  `ToNodeID` int(11) NULL DEFAULT NULL COMMENT 'ToNodeID（对方向条件有效）',
  `FK_Attr` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性',
  `AttrKey` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性键',
  `AttrName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中文名称',
  `FK_Operator` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运算符号',
  `OperatorValue` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '要运算的值',
  `OperatorValueT` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '要运算的值T',
  `Note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `AtPara` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '条件' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_deptflowsearch
-- ----------------------------
DROP TABLE IF EXISTS `wf_deptflowsearch`;
CREATE TABLE `wf_deptflowsearch`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Emp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程部门数据查询权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_direction
-- ----------------------------
DROP TABLE IF EXISTS `wf_direction`;
CREATE TABLE `wf_direction`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `Node` int(11) NULL DEFAULT NULL COMMENT '从节点',
  `ToNode` int(11) NULL DEFAULT NULL COMMENT '到节点',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '计算优先级顺序',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点方向信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_direction
-- ----------------------------
INSERT INTO `wf_direction` VALUES ('001_101_102', '001', 101, 102, 0);
INSERT INTO `wf_direction` VALUES ('001_102_103', '001', 102, 103, 0);
INSERT INTO `wf_direction` VALUES ('001_103_104', '001', 103, 104, 0);

-- ----------------------------
-- Table structure for wf_directionstation
-- ----------------------------
DROP TABLE IF EXISTS `wf_directionstation`;
CREATE TABLE `wf_directionstation`  (
  `FK_Direction` int(11) NOT NULL COMMENT '节点 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Direction`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_doctemplate
-- ----------------------------
DROP TABLE IF EXISTS `wf_doctemplate`;
CREATE TABLE `wf_doctemplate`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'No - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FilePath` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板路径',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `FK_Flow` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公文模板' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_emp
-- ----------------------------
DROP TABLE IF EXISTS `wf_emp`;
CREATE TABLE `wf_emp`  (
  `No` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'No - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `UseSta` int(11) NULL DEFAULT NULL COMMENT '用户状态0禁用,1正常.',
  `Tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tel',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_Dept',
  `Email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email',
  `AlertWay` int(11) NULL DEFAULT NULL COMMENT '收听方式,枚举类型:0 不接收;1 短信;2 邮件;3 内部消息;4 QQ消息;5 RTX消息;6 MSN消息;',
  `Author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权人',
  `AuthorDate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权日期',
  `AuthorWay` int(11) NULL DEFAULT NULL COMMENT '授权方式',
  `AuthorToDate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权到日期',
  `AuthorFlows` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '可以执行的授权流程',
  `Stas` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位s',
  `Depts` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Deptss',
  `Msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Msg',
  `Style` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Style',
  `OrgNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  `SPass` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片签名密码',
  `Idx` int(11) NULL DEFAULT NULL COMMENT 'Idx',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  `StartFlows` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '可以发起的流程',
  `Token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作员' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_emp
-- ----------------------------
INSERT INTO `wf_emp` VALUES ('admin', 'admin', 1, '', '', 'zhoupeng@ccflow.org', 3, '', '', 0, '', '', '', '', '@流程已经走到最后一个节点，流程成功结束。null@流程已经结束@', '', '', '', 0, '@SID=37da58742507445bbad6145b63d4e267', '{\"Start\":[{\"No\":\"001\",\"Name\":\"请假流程-经典表单-演示\",\"IsBatchStart\":0,\"FK_FlowSort\":\"100\",\"FK_FlowSortText\":\"日常办公类\",\"Domain\":\"\",\"IsStartInMobile\":1,\"Idx\":0}],\"Sort\":[{\"No\":\"100\",\"Name\":\"日常办公类\",\"Domain\":\"\"}]}', 'aa03a254e84047f6bbfc776e3d75eeea');
INSERT INTO `wf_emp` VALUES ('fuhui', '福惠', 1, NULL, NULL, 'fuhui@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('guobaogeng', '郭宝庚', 1, NULL, NULL, 'guobaogeng@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('guoxiangbin', '郭祥斌', 1, NULL, NULL, 'guoxiangbin@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('liping', '李萍', 1, NULL, NULL, 'liping@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('liyan', '李言', 1, NULL, NULL, 'liyan@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('qifenglin', '祁凤林', 1, NULL, NULL, 'qifenglin@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('yangyilei', '杨依雷', 1, NULL, NULL, 'yangyilei@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('zhanghaicheng', '张海成', 1, NULL, NULL, 'zhanghaicheng@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('zhangyifan', '张一帆', 1, NULL, NULL, 'zhangyifan@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('zhoupeng', '周朋', 1, NULL, NULL, 'zhoupeng@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, '@当前工作[总经理审批]已经完成@发送给如下1位处理人(admin,admin).@<a href=\'./WorkOpt/UnSend.htm?DoType=UnSend&UserNo=zhoupeng&SID=aae88c87bbce4787a9c0e69e6589c295&WorkID=117&FK_Flow=001\' ><img src=\'http://192.168.20.162:8090//jflow-web/WF/Img/Action/UnSend.png\' border=0 />撤销本次发送</a> @下一步工作<font color=blue>[反馈给申请人]</font>成功启动.@已向:{admin}发送提醒信息.', NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('zhoushengyu', '周升雨', 1, NULL, NULL, 'zhoushengyu@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, '@当前工作[部门经理审批]已经完成@发送给如下1位处理人(zhoupeng,周朋).@<a href=\'./WorkOpt/UnSend.htm?DoType=UnSend&UserNo=zhoushengyu&SID=c00a9ce9c2c944c98135ba786c272ad1&WorkID=101&FK_Flow=001\' ><img src=\'http://192.168.20.162:8090//jflow-web/WF/Img/Action/UnSend.png\' border=0 />撤销本次发送</a> @下一步工作<font color=blue>[总经理审批]</font>成功启动.@已向:{周朋}发送提醒信息.', NULL, NULL, NULL, 0, NULL, '', NULL);
INSERT INTO `wf_emp` VALUES ('zhoutianjiao', '周天娇', 1, NULL, NULL, 'zhoutianjiao@ccflow.org', 3, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '', NULL);

-- ----------------------------
-- Table structure for wf_findworkerrole
-- ----------------------------
DROP TABLE IF EXISTS `wf_findworkerrole`;
CREATE TABLE `wf_findworkerrole`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `SortVal0` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortVal0',
  `SortText0` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortText0',
  `SortVal1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortVal1',
  `SortText1` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortText1',
  `SortVal2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortText2',
  `SortText2` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortText2',
  `SortVal3` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortVal3',
  `SortText3` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SortText3',
  `TagVal0` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagVal0',
  `TagVal1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagVal1',
  `TagVal2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagVal2',
  `TagVal3` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagVal3',
  `TagText0` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagText0',
  `TagText1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagText1',
  `TagText2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagText2',
  `TagText3` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TagText3',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用',
  `Idx` int(11) NULL DEFAULT NULL COMMENT 'IDX',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '找人规则' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_flow
-- ----------------------------
DROP TABLE IF EXISTS `wf_flow`;
CREATE TABLE `wf_flow`  (
  `No` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `FK_FlowSort` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程类别,外键:对应物理表:WF_FlowSort,表描述:流程类别',
  `Name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `FlowMark` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程标记',
  `FlowEventEntity` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程事件实体',
  `TitleRole` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题生成规则',
  `IsCanStart` int(11) NULL DEFAULT NULL COMMENT '可以独立启动否？(独立启动的流程可以显示在发起流程列表里)',
  `IsFullSA` int(11) NULL DEFAULT NULL COMMENT '是否自动计算未来的处理人？',
  `FlowAppType` int(11) NULL DEFAULT NULL COMMENT '流程应用类型,枚举类型:0 业务流程;1 工程类(项目组流程);2 公文流程(VSTO);',
  `Draft` int(11) NULL DEFAULT NULL COMMENT '草稿规则,枚举类型:0 无(不设草稿);1 保存到待办;2 保存到草稿箱;',
  `FlowDeleteRole` int(11) NULL DEFAULT NULL COMMENT '流程实例删除规则,枚举类型:0 超级管理员可以删除;1 分级管理员可以删除;2 发起人可以删除;3 节点启动删除按钮的操作员;',
  `IsToParentNextNode` int(11) NULL DEFAULT NULL COMMENT '子流程结束时，让父流程自动运行到下一步',
  `HelpUrl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帮助文档',
  `SysType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统类型',
  `Tester` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起测试人',
  `NodeAppType` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型枚举(可为Null)',
  `NodeAppTypeText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型枚举(可为Null)',
  `ChartType` int(11) NULL DEFAULT NULL COMMENT '节点图形类型,枚举类型:0 几何图形;1 肖像图片;',
  `HostRun` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行主机(IP+端口)',
  `IsBatchStart` int(11) NULL DEFAULT NULL COMMENT '是否可以批量发起流程？(如果是就要设置发起的需要填写的字段,多个用逗号分开)',
  `BatchStartFields` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起字段s',
  `HistoryFields` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '历史查看字段',
  `IsResetData` int(11) NULL DEFAULT NULL COMMENT '是否启用开始节点数据重置按钮？已经取消)',
  `IsLoadPriData` int(11) NULL DEFAULT NULL COMMENT '是否自动装载上一笔数据？',
  `IsDBTemplate` int(11) NULL DEFAULT NULL COMMENT '是否启用数据模版？',
  `IsStartInMobile` int(11) NULL DEFAULT NULL COMMENT '是否可以在手机里启用？(如果发起表单特别复杂就不要在手机里启用了)',
  `IsMD5` int(11) NULL DEFAULT NULL COMMENT '是否是数据加密流程(MD5数据加密防篡改)',
  `DataStoreModel` int(11) NULL DEFAULT NULL COMMENT '流程数据存储模式,枚举类型:0 数据轨迹模式;1 数据合并模式;',
  `PTable` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程数据存储表',
  `FlowNoteExp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注的表达式',
  `BillNoFormat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编号格式',
  `BuessFields` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键业务字段',
  `FlowFrmType` int(11) NULL DEFAULT NULL COMMENT '流程全局表单类型,枚举类型:0 完整版-2019年更早版本;1 开发者表单;2 傻瓜表单;3 自定义(嵌入)表单;4 SDK表单;',
  `FrmUrl` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单Url',
  `IsFrmEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示表单',
  `IsTruckEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示轨迹图',
  `IsTimeBaseEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示时间轴',
  `IsTableEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示时间表',
  `IsOPEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示操作',
  `DesignerNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设计者编号',
  `DesignerName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设计者名称',
  `DesignTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `FlowRunWay` int(11) NULL DEFAULT NULL COMMENT '运行方式',
  `RunObj` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '运行内容',
  `Note` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程描述',
  `RunSQL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程结束执行后执行的SQL',
  `NumOfBill` int(11) NULL DEFAULT NULL COMMENT '是否有单据',
  `NumOfDtl` int(11) NULL DEFAULT NULL COMMENT 'NumOfDtl',
  `AvgDay` double NULL DEFAULT NULL COMMENT '平均运行用天',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '显示顺序号(在发起列表中)',
  `SDTOfFlowRole` int(11) NULL DEFAULT NULL COMMENT '流程计划完成日期计算规则',
  `SDTOfFlowRoleSQL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程计划完成日期计算规则SQL',
  `Paras` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `DRCtrlType` int(11) NULL DEFAULT NULL COMMENT '部门查询权限控制方式',
  `StartLimitRole` int(11) NULL DEFAULT NULL COMMENT '启动限制规则',
  `StartLimitPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则内容',
  `StartLimitAlert` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制提示',
  `StartLimitWhen` int(11) NULL DEFAULT NULL COMMENT '提示时间',
  `StartGuideWay` int(11) NULL DEFAULT NULL COMMENT '前置导航方式',
  `StartGuideLink` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '右侧的连接',
  `StartGuideLab` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接标签',
  `StartGuidePara1` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数1',
  `StartGuidePara2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数2',
  `StartGuidePara3` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数3',
  `IsAutoSendSubFlowOver` int(11) NULL DEFAULT NULL COMMENT '(当前节点为子流程时)是否检查所有子流程完成后父流程自动发送',
  `Ver` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  `AtPara` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `DataDTSWay` int(11) NULL DEFAULT NULL COMMENT '同步方式',
  `DTSTime` int(11) NULL DEFAULT NULL COMMENT '执行同步时间点',
  `DTSFields` varchar(900) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要同步的字段s,中间用逗号分开.',
  `MyDeptRole` int(11) NULL DEFAULT NULL COMMENT '本部门发起的流程,枚举类型:0 仅部门领导可以查看;1 部门下所有的人都可以查看;2 本部门里指定岗位的人可以查看;',
  `PStarter` int(11) NULL DEFAULT NULL COMMENT '发起人可看(必选)',
  `PWorker` int(11) NULL DEFAULT NULL COMMENT '参与人可看(必选)',
  `PCCer` int(11) NULL DEFAULT NULL COMMENT '被抄送人可看(必选)',
  `PMyDept` int(11) NULL DEFAULT NULL COMMENT '本部门人可看',
  `PPMyDept` int(11) NULL DEFAULT NULL COMMENT '直属上级部门可看(比如:我是)',
  `PPDept` int(11) NULL DEFAULT NULL COMMENT '上级部门可看',
  `PSameDept` int(11) NULL DEFAULT NULL COMMENT '平级部门可看',
  `PSpecDept` int(11) NULL DEFAULT NULL COMMENT '指定部门可看',
  `PSpecDeptExt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `PSpecSta` int(11) NULL DEFAULT NULL COMMENT '指定的岗位可看',
  `PSpecStaExt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位编号',
  `PSpecGroup` int(11) NULL DEFAULT NULL COMMENT '指定的权限组可看',
  `PSpecGroupExt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限组',
  `PSpecEmp` int(11) NULL DEFAULT NULL COMMENT '指定的人员可看',
  `PSpecEmpExt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定的人员编号',
  `IsGuestFlow` int(11) NULL DEFAULT NULL COMMENT '是否外部用户参与流程(非组织结构人员参与的流程)',
  `FlowFrmModel` int(11) NULL DEFAULT 0 COMMENT '流程表单类型',
  `GuestFlowRole` int(11) NULL DEFAULT 0 COMMENT '是否是客户参与流程？',
  `DTSDBSrc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '数据源',
  `DTSBTable` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务表名',
  `DTSBTablePK` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '业务表主键',
  `DTSSpecNodes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '同步字段',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程模版主表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_flow
-- ----------------------------
INSERT INTO `wf_flow` VALUES ('001', '100', '请假流程-经典表单-演示', '', '', '', 1, 0, 0, 0, 0, 0, NULL, '', NULL, NULL, NULL, 1, '', 0, '', '', 0, 0, 0, 1, 0, 1, '', '', '', NULL, 0, '', 1, 1, 1, 1, 1, NULL, NULL, NULL, 0, '', '', '', 0, 0, 0, 0, 0, '', '@StartNodeX=200@StartNodeY=50@EndNodeX=200@EndNodeY=350', 0, 0, '', '', 0, 0, '', '', '', '', '', 0, '2021-07-01 18:13:02', '', '@FrmEventsNum=0@FrmEvents_AutoNum=0@PushMsgs_AutoNum=0@Conds_AutoNum=0', 0, 0, '', 0, 1, 1, 1, 1, 1, 1, 1, 1, '', 1, '', 1, '', 1, '', 0, 0, 0, '', '', '', NULL);

-- ----------------------------
-- Table structure for wf_floworg
-- ----------------------------
DROP TABLE IF EXISTS `wf_floworg`;
CREATE TABLE `wf_floworg`  (
  `FlowNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程 - 主键',
  `OrgNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '到组织,主外键:对应物理表:Port_Org,表描述:独立组织',
  PRIMARY KEY (`FlowNo`, `OrgNo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程对应组织' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_flowsort
-- ----------------------------
DROP TABLE IF EXISTS `wf_flowsort`;
CREATE TABLE `wf_flowsort`  (
  `No` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `ParentNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点No',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `OrgNo` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编号(0为系统组织)',
  `Domain` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '域/系统编号',
  `Idx` int(11) NULL DEFAULT NULL COMMENT 'Idx',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程类别' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_flowsort
-- ----------------------------
INSERT INTO `wf_flowsort` VALUES ('1', '0', '流程树', '0', '', 0);
INSERT INTO `wf_flowsort` VALUES ('100', '1', '日常办公类', '0', '', 0);
INSERT INTO `wf_flowsort` VALUES ('101', '1', '财务类', '0', '', 0);
INSERT INTO `wf_flowsort` VALUES ('102', '1', '人力资源类', '0', '', 0);

-- ----------------------------
-- Table structure for wf_frmnode
-- ----------------------------
DROP TABLE IF EXISTS `wf_frmnode`;
CREATE TABLE `wf_frmnode`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点编号',
  `FK_Frm` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  `FrmSln` int(11) NULL DEFAULT NULL COMMENT '表单控制方案',
  `FK_Flow` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FrmType` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单类型',
  `IsPrint` int(11) NULL DEFAULT NULL COMMENT '是否可以打印',
  `IsEnableLoadData` int(11) NULL DEFAULT NULL COMMENT '是否启用装载填充事件',
  `IsDefaultOpen` int(11) NULL DEFAULT NULL COMMENT '是否默认打开',
  `IsCloseEtcFrm` int(11) NULL DEFAULT NULL COMMENT '打开时是否关闭其它的页面？',
  `IsEnableFWC` int(11) NULL DEFAULT NULL COMMENT '是否启用审核组件？',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `WhoIsPK` int(11) NULL DEFAULT NULL COMMENT '谁是主键？',
  `Is1ToN` int(11) NULL DEFAULT NULL COMMENT '是否1变N？',
  `HuiZong` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子线程要汇总的数据表',
  `FrmEnableRole` int(11) NULL DEFAULT NULL COMMENT '表单启用规则',
  `FrmEnableExp` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '启用的表达式',
  `TempleteFile` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模版文件',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否显示',
  `GuanJianZiDuan` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字段',
  `FrmNameShow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单显示名字',
  `CheckField` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签批字段',
  `BillNoField` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编号字段',
  `CheckFieldText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签批字段',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点表单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_frmorg
-- ----------------------------
DROP TABLE IF EXISTS `wf_frmorg`;
CREATE TABLE `wf_frmorg`  (
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单 - 主键',
  `OrgNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '到组织,主外键:对应物理表:Port_Org,表描述:独立组织',
  PRIMARY KEY (`FrmID`, `OrgNo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单对应组织' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_generworkerlist
-- ----------------------------
DROP TABLE IF EXISTS `wf_generworkerlist`;
CREATE TABLE `wf_generworkerlist`  (
  `WorkID` int(11) NOT NULL COMMENT '工作ID - 主键',
  `FK_Emp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员 - 主键',
  `FK_Node` int(11) NOT NULL COMMENT '节点ID - 主键',
  `FID` int(11) NULL DEFAULT NULL COMMENT '流程ID',
  `FK_EmpText` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员名称',
  `FK_NodeText` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员所在部门',
  `SDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应完成日期',
  `DTOfWarning` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '警告日期',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录时间(接受工作日期)',
  `CDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '完成时间',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否可用',
  `IsRead` int(11) NULL DEFAULT NULL COMMENT '是否读取',
  `IsPass` int(11) NULL DEFAULT NULL COMMENT '是否通过(对合流节点有效)',
  `WhoExeIt` int(11) NULL DEFAULT NULL COMMENT '谁执行它',
  `Sender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `PRI` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `PressTimes` int(11) NULL DEFAULT NULL COMMENT '催办次数',
  `DTOfHungUp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂起时间',
  `DTOfUnHungUp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预计解除挂起时间',
  `HungUpTimes` int(11) NULL DEFAULT NULL COMMENT '挂起次数',
  `GuestNo` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部用户编号',
  `GuestName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部用户名称',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  `AtPara` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'AtPara',
  PRIMARY KEY (`WorkID`, `FK_Emp`, `FK_Node`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工作者' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_generworkflow
-- ----------------------------
DROP TABLE IF EXISTS `wf_generworkflow`;
CREATE TABLE `wf_generworkflow`  (
  `WorkID` int(11) NOT NULL COMMENT 'WorkID - 主键',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `FK_FlowSort` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别,外键:对应物理表:WF_FlowSort,表描述:流程类别',
  `SysType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统类别',
  `FK_Flow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程,外键:对应物理表:WF_Flow,表描述:流程',
  `FlowName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `Title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `WFSta` int(11) NULL DEFAULT NULL COMMENT '流程状态,枚举类型:0 运行中;1 已完成;2 其他;',
  `WFState` int(11) NULL DEFAULT NULL COMMENT '流程状态,枚举类型:0 空白;1 草稿;2 运行中;3 已完成;4 挂起;5 退回;6 转发;7 删除;8 加签;9 冻结;10 批处理;11 加签回复状态;',
  `Starter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `StarterName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `Sender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录日期',
  `SendDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程活动时间',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT 'FK_Node',
  `NodeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前节点名称',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门,外键:对应物理表:Port_Dept,表描述:部门',
  `DeptName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `PRI` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `SDTOfNode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点应完成时间',
  `SDTOfFlow` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程应完成时间',
  `SDTOfFlowWarning` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程预警时间',
  `PFlowNo` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父流程编号',
  `PWorkID` int(11) NULL DEFAULT NULL COMMENT '父流程ID',
  `PNodeID` int(11) NULL DEFAULT NULL COMMENT '父流程调用节点',
  `PFID` int(11) NULL DEFAULT NULL COMMENT '父流程调用的PFID',
  `PEmp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程的调用人',
  `GuestNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `GuestName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `BillNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据编号',
  `FlowNote` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `TodoEmps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '待办人员',
  `TodoEmpsNum` int(11) NULL DEFAULT NULL COMMENT '待办人员数量',
  `TaskSta` int(11) NULL DEFAULT NULL COMMENT '共享状态',
  `AtPara` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数(流程运行设置临时存储的参数)',
  `Emps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参与人(格式:@zhangshan,张三@lishi,李四)',
  `GUID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'GUID',
  `FK_NY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '月份,外键:对应物理表:Pub_NY,表描述:月份',
  `WeekNum` int(11) NULL DEFAULT NULL COMMENT '周次',
  `TSpan` int(11) NULL DEFAULT NULL COMMENT '时间间隔',
  `TodoSta` int(11) NULL DEFAULT NULL COMMENT '待办状态',
  `Domain` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '域/系统编号',
  `PrjNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'PrjNo',
  `PrjName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'PrjNo',
  `OrgNo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OrgNo',
  PRIMARY KEY (`WorkID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程查询' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_hungup
-- ----------------------------
DROP TABLE IF EXISTS `wf_hungup`;
CREATE TABLE `wf_hungup`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `HungUpWay` int(11) NULL DEFAULT NULL COMMENT '挂起方式,枚举类型:0 无限挂起;1 按指定的时间解除挂起并通知我自己;2 按指定的时间解除挂起并通知所有人;',
  `Note` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '挂起原因(标题与内容支持变量)',
  `Rec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂起人',
  `DTOfHungUp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂起时间',
  `DTOfUnHungUp` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实际解除挂起时间',
  `DTOfUnHungUpPlan` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预计解除挂起时间',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '挂起' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_labnote
-- ----------------------------
DROP TABLE IF EXISTS `wf_labnote`;
CREATE TABLE `wf_labnote`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Name` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `FK_Flow` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `X` int(11) NULL DEFAULT NULL COMMENT 'X坐标',
  `Y` int(11) NULL DEFAULT NULL COMMENT 'Y坐标',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_node
-- ----------------------------
DROP TABLE IF EXISTS `wf_node`;
CREATE TABLE `wf_node`  (
  `NodeID` int(11) NOT NULL COMMENT 'NodeID - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FlowName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `WhoExeIt` int(11) NULL DEFAULT NULL COMMENT '谁执行它,枚举类型:0 操作员执行;1 机器执行;2 混合执行;',
  `ReadReceipts` int(11) NULL DEFAULT NULL COMMENT '已读回执,枚举类型:0 不回执;1 自动回执;2 由上一节点表单字段决定;3 由SDK开发者参数决定;',
  `CancelRole` int(11) NULL DEFAULT NULL COMMENT '撤销规则,枚举类型:0 上一步可以撤销;1 不能撤销;2 上一步与开始节点可以撤销;3 指定的节点可以撤销;',
  `CancelDisWhenRead` int(11) NULL DEFAULT NULL COMMENT '对方已经打开就不能撤销',
  `IsTask` int(11) NULL DEFAULT NULL COMMENT '允许分配工作否?',
  `IsExpSender` int(11) NULL DEFAULT NULL COMMENT '本节点接收人不允许包含上一步发送人',
  `IsRM` int(11) NULL DEFAULT NULL COMMENT '是否启用投递路径自动记忆功能?',
  `IsOpenOver` int(11) NULL DEFAULT NULL COMMENT '已阅即完成?',
  `IsToParentNextNode` int(11) NULL DEFAULT NULL COMMENT '子流程运行到该节点时，让父流程自动运行到下一步',
  `IsSendDraftSubFlow` int(11) NULL DEFAULT NULL COMMENT '是否发送草稿子流程?',
  `IsYouLiTai` int(11) NULL DEFAULT NULL COMMENT '该节点是否是游离态',
  `DTFrom` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生命周期从',
  `DTTo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生命周期到',
  `IsBUnit` int(11) NULL DEFAULT NULL COMMENT '是否是节点模版（业务单元）?',
  `FocusField` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '焦点字段',
  `IsGuestNode` int(11) NULL DEFAULT NULL COMMENT '是否是外部用户执行的节点(非组织结构人员参与处理工作的节点)?',
  `NodeAppType` int(11) NULL DEFAULT NULL COMMENT '节点业务类型',
  `FWCSta` int(11) NULL DEFAULT NULL COMMENT '审核组件状态,枚举类型:0 禁用;1 启用;2 只读;',
  `FWCAth` int(11) NULL DEFAULT NULL COMMENT '附件上传,枚举类型:0 不启用;1 多附件;2 单附件(暂不支持);3 图片附件(暂不支持);',
  `SelfParas` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义属性',
  `Step` int(11) NULL DEFAULT NULL COMMENT '步骤',
  `Tip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作提示',
  `RunModel` int(11) NULL DEFAULT NULL COMMENT '运行模式',
  `SubThreadType` int(11) NULL DEFAULT NULL COMMENT '子线程类型,枚举类型:0 同表单;1 异表单;',
  `PassRate` double NULL DEFAULT NULL COMMENT '完成通过率',
  `SubFlowStartWay` int(11) NULL DEFAULT NULL COMMENT '子线程启动方式,枚举类型:0 不启动;1 指定的字段启动;2 按明细表启动;',
  `SubFlowStartParas` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '启动参数',
  `ThreadIsCanDel` int(11) NULL DEFAULT NULL COMMENT '是否可以删除子线程(当前节点已经发送出去的线程，并且当前节点是分流，或者分合流有效，在子线程退回后的操作)？',
  `ThreadIsCanShift` int(11) NULL DEFAULT NULL COMMENT '是否可以移交子线程(当前节点已经发送出去的线程，并且当前节点是分流，或者分合流有效，在子线程退回后的操作)？',
  `IsAllowRepeatEmps` int(11) NULL DEFAULT NULL COMMENT '是否允许子线程接受人员重复(仅当分流点向子线程发送时有效)?',
  `AutoRunEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用自动运行？(仅当分流点向子线程发送时有效)',
  `AutoRunParas` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自动运行SQL',
  `IsSendBackNode` int(11) NULL DEFAULT NULL COMMENT '是否是发送返回节点(发送当前节点,自动发送给该节点的发送人,发送节点.)?',
  `AutoJumpRole0` int(11) NULL DEFAULT NULL COMMENT '处理人就是发起人',
  `AutoJumpRole1` int(11) NULL DEFAULT NULL COMMENT '处理人已经出现过',
  `AutoJumpRole2` int(11) NULL DEFAULT NULL COMMENT '处理人与上一步相同',
  `WhenNoWorker` int(11) NULL DEFAULT NULL COMMENT '(是)找不到人就跳转,(否)提示错误.',
  `SendLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送按钮标签',
  `SendJS` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮JS函数',
  `SaveLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存按钮标签',
  `SaveEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `ThreadLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子线程按钮标签',
  `ThreadEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `ThreadKillRole` int(11) NULL DEFAULT NULL COMMENT '子线程删除方式,枚举类型:0 不能删除;1 手工删除;2 自动删除;',
  `JumpWayLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转按钮标签',
  `JumpWay` int(11) NULL DEFAULT NULL COMMENT '跳转规则,枚举类型:0 不能跳转;1 只能向后跳转;2 只能向前跳转;3 任意节点跳转;4 按指定规则跳转;',
  `JumpToNodes` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可跳转的节点',
  `ReturnLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回按钮标签',
  `ReturnRole` int(11) NULL DEFAULT NULL COMMENT '退回规则,枚举类型:0 不能退回;1 只能退回上一个节点;2 可退回以前任意节点;3 可退回指定的节点;4 由流程图设计的退回路线决定;',
  `ReturnAlert` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被退回后信息提示',
  `IsBackTracking` int(11) NULL DEFAULT NULL COMMENT '是否可以原路返回(启用退回功能才有效)',
  `IsDeleteModel` int(11) NULL DEFAULT NULL COMMENT '是否删除中间节点信息(不是原来返回时有效)',
  `ReturnField` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回信息填写字段',
  `ReturnReasonsItems` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回原因',
  `ReturnCHEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用退回考核规则',
  `ReturnOneNodeRole` int(11) NULL DEFAULT NULL COMMENT '单节点退回规则,枚举类型:0 不启用;1 按照[退回信息填写字段]作为退回意见直接退回;2 按照[审核组件]填写的信息作为退回意见直接退回;',
  `CCLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送按钮标签',
  `CCRole` int(11) NULL DEFAULT NULL COMMENT '抄送规则,枚举类型:0 不能抄送;1 手工抄送;2 自动抄送;3 手工与自动;4 按表单SysCCEmps字段计算;5 在发送前打开抄送窗口;',
  `CCWriteTo` int(11) NULL DEFAULT NULL COMMENT '抄送写入规则,枚举类型:0 写入抄送列表;1 写入待办;2 写入待办与抄送列表;',
  `DoOutTime` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '超时处理内容',
  `DoOutTimeCond` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行超时的条件',
  `ShiftLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '移交按钮标签',
  `ShiftEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `DelLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除按钮标签',
  `DelEnable` int(11) NULL DEFAULT NULL COMMENT '删除规则,枚举类型:0 不能删除;1 逻辑删除;2 记录日志方式删除;3 彻底删除;4 让用户决定删除方式;',
  `EndFlowLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束流程按钮标签',
  `EndFlowEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `ShowParentFormLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查看父流程按钮标签',
  `ShowParentFormEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `OfficeBtnLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公文按钮标签',
  `OfficeBtnEnable` int(11) NULL DEFAULT NULL COMMENT '文件状态,枚举类型:0 不可用;1 可编辑;2 不可编辑;',
  `OfficeFileType` int(11) NULL DEFAULT NULL COMMENT '文件类型,枚举类型:0 word文件;1 WPS文件;',
  `OfficeBtnLocal` int(11) NULL DEFAULT NULL COMMENT '按钮位置,枚举类型:0 工具栏上;1 表单标签(divID=GovDocFile);',
  `PrintHtmlLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印Html标签',
  `PrintHtmlEnable` int(11) NULL DEFAULT NULL COMMENT '(打印Html)是否启用',
  `PrintHtmlMyView` int(11) NULL DEFAULT NULL COMMENT '(打印Html)显示在查看器工具栏?',
  `PrintHtmlMyCC` int(11) NULL DEFAULT NULL COMMENT '(打印Html)显示在抄送工具栏?',
  `PrintPDFLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印pdf标签',
  `PrintPDFEnable` int(11) NULL DEFAULT NULL COMMENT '(打印pdf)是否启用',
  `PrintPDFMyView` int(11) NULL DEFAULT NULL COMMENT '(打印pdf)显示在查看器工具栏?',
  `PrintPDFMyCC` int(11) NULL DEFAULT NULL COMMENT '(打印pdf)显示在抄送工具栏?',
  `PrintPDFModle` int(11) NULL DEFAULT NULL COMMENT 'PDF打印规则,枚举类型:0 全部打印;1 单个表单打印(针对树形表单);',
  `ShuiYinModle` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印水印规则',
  `PrintZipLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打包下载zip按钮标签',
  `PrintZipEnable` int(11) NULL DEFAULT NULL COMMENT '(打包下载zip)是否启用',
  `PrintZipMyView` int(11) NULL DEFAULT NULL COMMENT '(打包下载zip)显示在查看器工具栏?',
  `PrintZipMyCC` int(11) NULL DEFAULT NULL COMMENT '(打包下载zip)显示在抄送工具栏?',
  `PrintDocLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印单据按钮标签',
  `PrintDocEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `TrackLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '轨迹按钮标签',
  `TrackEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `HungLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '挂起按钮标签',
  `HungEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `SearchLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询按钮标签',
  `SearchEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `HuiQianLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会签标签',
  `HuiQianRole` int(11) NULL DEFAULT NULL COMMENT '会签模式,枚举类型:0 不启用;1 协作模式;4 组长模式;',
  `HuiQianLeaderRole` int(11) NULL DEFAULT NULL COMMENT '组长会签规则,枚举类型:0 只有一个组长;1 最后一个组长发送;2 任意组长发送;',
  `AddLeaderLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加主持人',
  `AddLeaderEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `TCLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流转自定义',
  `TCEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `WebOffice` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档按钮标签',
  `WebOfficeEnable` int(11) NULL DEFAULT NULL COMMENT '文档启用方式',
  `PRILab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重要性',
  `PRIEnable` int(11) NULL DEFAULT NULL COMMENT '重要性规则,枚举类型:0 不启用;1 只读;2 编辑;',
  `CHLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点时限',
  `CHRole` int(11) NULL DEFAULT NULL COMMENT '时限规则,枚举类型:0 禁用;1 启用;2 只读;3 启用并可以调整流程应完成时间;',
  `AllotLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分配按钮标签',
  `AllotEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `FocusLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关注',
  `FocusEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `ConfirmLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '确认按钮标签',
  `ConfirmEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `ListLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列表按钮标签',
  `ListEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `BatchLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批量审核标签',
  `BatchEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `NoteLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注标签',
  `NoteEnable` int(11) NULL DEFAULT NULL COMMENT '启用规则,枚举类型:0 禁用;1 启用;2 只读;',
  `HelpLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帮助标签',
  `HelpRole` int(11) NULL DEFAULT NULL COMMENT '帮助显示规则,枚举类型:0 禁用;1 启用;2 强制提示;3 选择性提示;',
  `TAlertRole` int(11) NULL DEFAULT NULL COMMENT '逾期提醒规则',
  `TAlertWay` int(11) NULL DEFAULT NULL COMMENT '逾期提醒方式',
  `WAlertRole` int(11) NULL DEFAULT NULL COMMENT '预警提醒规则',
  `WAlertWay` int(11) NULL DEFAULT NULL COMMENT '预警提醒方式',
  `TCent` float NULL DEFAULT NULL COMMENT '扣分(每延期1小时)',
  `CHWay` int(11) NULL DEFAULT NULL COMMENT '考核方式',
  `IsEval` int(11) NULL DEFAULT NULL COMMENT '是否工作质量考核',
  `OutTimeDeal` int(11) NULL DEFAULT NULL COMMENT '超时处理方式',
  `CCIsAttr` int(11) NULL DEFAULT NULL COMMENT '按表单字段抄送',
  `CCFormAttr` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送人员字段',
  `CCIsStations` int(11) NULL DEFAULT NULL COMMENT '是否启用？-按照岗位抄送',
  `CCStaWay` int(11) NULL DEFAULT NULL COMMENT '抄送岗位计算方式,枚举类型:0 仅按岗位计算;1 按岗位智能计算(当前节点);2 按岗位智能计算(发送到节点);3 按岗位与部门的交集;4 按直线上级部门找岗位下的人员(当前节点);5 按直线上级部门找岗位下的人员(接受节点);',
  `CCIsDepts` int(11) NULL DEFAULT NULL COMMENT '是否启用？-按照部门抄送',
  `CCIsEmps` int(11) NULL DEFAULT NULL COMMENT '是否启用？-按照人员抄送',
  `CCIsSQLs` int(11) NULL DEFAULT NULL COMMENT '是否启用？-按照SQL抄送',
  `CCSQL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SQL表达式',
  `CCTitle` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送标题',
  `CCDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '抄送内容(标题与内容支持变量)',
  `FWCLab` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示标签',
  `FWCShowModel` int(11) NULL DEFAULT NULL COMMENT '显示方式,枚举类型:0 表格方式;1 自由模式;',
  `FWCType` int(11) NULL DEFAULT NULL COMMENT '审核组件,枚举类型:0 审核组件;1 日志组件;2 周报组件;3 月报组件;',
  `FWCNodeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点意见名称',
  `FWCTrackEnable` int(11) NULL DEFAULT NULL COMMENT '轨迹图是否显示？',
  `FWCListEnable` int(11) NULL DEFAULT NULL COMMENT '历史审核信息是否显示？(否,仅出现意见框)',
  `FWCIsShowAllStep` int(11) NULL DEFAULT NULL COMMENT '在轨迹表里是否显示所有的步骤？',
  `FWCOpLabel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名词(审核/审阅/批示)',
  `FWCDefInfo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认审核信息',
  `SigantureEnabel` int(11) NULL DEFAULT NULL COMMENT '操作人显示方式,枚举类型:0 不签名;1 图片签名;2 写字板;',
  `FWCIsFullInfo` int(11) NULL DEFAULT NULL COMMENT '如果用户未审核是否按照默认意见填充？',
  `FWC_X` float(11, 2) NULL DEFAULT NULL COMMENT '位置X',
  `FWC_Y` float(11, 2) NULL DEFAULT NULL COMMENT '位置Y',
  `FWC_H` float(11, 2) NULL DEFAULT NULL COMMENT '高度(0=100%)',
  `FWC_W` float(11, 2) NULL DEFAULT NULL COMMENT '宽度(0=100%)',
  `FWCFields` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批格式字段',
  `FWCIsShowTruck` int(11) NULL DEFAULT NULL COMMENT '是否显示未审核的轨迹？',
  `FWCIsShowReturnMsg` int(11) NULL DEFAULT NULL COMMENT '是否显示退回信息？',
  `FWCOrderModel` int(11) NULL DEFAULT NULL COMMENT '协作模式下操作员显示顺序,枚举类型:0 按审批时间先后排序;1 按照接受人员列表先后顺序(官职大小);',
  `FWCMsgShow` int(11) NULL DEFAULT NULL COMMENT '审核意见显示方式,枚举类型:0 都显示;1 仅显示自己的意见;',
  `FWCVer` int(11) NULL DEFAULT NULL COMMENT '审核意见保存规则,枚举类型:0 1个节点1个人保留1个意见;1 保留节点历史意见(默认);',
  `CheckField` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签批字段',
  `CheckFieldText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签批字段',
  `FWCView` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见立场',
  `CheckNodes` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作节点s',
  `DeliveryWay` int(11) NULL DEFAULT NULL COMMENT '运行模式',
  `ICON` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点ICON图片路径',
  `NodeWorkType` int(11) NULL DEFAULT NULL COMMENT '节点类型',
  `FrmAttr` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FrmAttr',
  `TimeLimit` float(11, 2) NULL DEFAULT NULL COMMENT '限期(天)',
  `TWay` int(11) NULL DEFAULT NULL COMMENT '时间计算方式',
  `WarningDay` float(11, 2) NULL DEFAULT NULL COMMENT '工作预警(天)',
  `Doc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `DeliveryParas` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问规则设置',
  `NodeFrmID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点表单ID',
  `SaveModel` int(11) NULL DEFAULT NULL COMMENT '保存模式',
  `IsCanDelFlow` int(11) NULL DEFAULT NULL COMMENT '是否可以删除流程',
  `TodolistModel` int(11) NULL DEFAULT NULL COMMENT '多人处理规则',
  `TeamLeaderConfirmRole` int(11) NULL DEFAULT NULL COMMENT '组长确认规则',
  `TeamLeaderConfirmDoc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组长确认设置内容',
  `IsHandOver` int(11) NULL DEFAULT NULL COMMENT '是否可以移交',
  `BlockModel` int(11) NULL DEFAULT NULL COMMENT '阻塞模式',
  `BlockExp` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阻塞表达式',
  `BlockAlert` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被阻塞提示信息',
  `CondModel` int(11) NULL DEFAULT NULL COMMENT '方向条件控制规则',
  `SFActiveFlows` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可触发的子流程编号(多个用逗号分开)',
  `BatchRole` int(11) NULL DEFAULT NULL COMMENT '批处理',
  `BatchListCount` int(11) NULL DEFAULT NULL COMMENT '批处理数量',
  `BatchParas` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `FormType` int(11) NULL DEFAULT NULL COMMENT '表单类型',
  `FormUrl` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单URL',
  `TurnToDeal` int(11) NULL DEFAULT NULL COMMENT '转向处理',
  `TurnToDealDoc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送后提示信息',
  `NodePosType` int(11) NULL DEFAULT NULL COMMENT '位置',
  `HisStas` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位',
  `HisDeptStrs` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `HisToNDs` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转到的节点',
  `HisBillIDs` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单据IDs',
  `HisSubFlows` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'HisSubFlows',
  `PTable` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物理表',
  `GroupStaNDs` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位分组节点',
  `X` int(11) NULL DEFAULT NULL COMMENT 'X坐标',
  `Y` int(11) NULL DEFAULT NULL COMMENT 'Y坐标',
  `RefOneFrmTreeType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '独立表单类型',
  `AtPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  `SF_H` float(11, 2) NULL DEFAULT NULL COMMENT '高度',
  `SF_W` float(11, 2) NULL DEFAULT NULL COMMENT '宽度',
  `SelectAccepterLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接受人按钮标签',
  `SelectAccepterEnable` int(11) NULL DEFAULT NULL COMMENT '方式,枚举类型:0 不启用;1 单独启用;2 在发送前打开;3 转入新页面;',
  `WorkCheckLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核按钮标签',
  `WorkCheckEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `AskforLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加签标签',
  `AskforEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `SFLab` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示标签',
  `SFSta` int(11) NULL DEFAULT NULL COMMENT '组件状态,枚举类型:0 禁用;1 启用;2 只读;',
  `SFShowModel` int(11) NULL DEFAULT NULL COMMENT '显示方式,枚举类型:0 表格方式;1 自由模式;',
  `SFCaption` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接标题',
  `SFDefInfo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可启动的子流程编号(多个用逗号分开)',
  `SF_X` float(11, 2) NULL DEFAULT NULL COMMENT '位置X',
  `SF_Y` float(11, 2) NULL DEFAULT NULL COMMENT '位置Y',
  `SFFields` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批格式字段',
  `SFShowCtrl` int(11) NULL DEFAULT NULL COMMENT '显示控制方式,枚举类型:0 可以看所有的子流程;1 仅仅可以看自己发起的子流程;',
  `SFOpenType` int(11) NULL DEFAULT NULL COMMENT '打开子流程显示,枚举类型:0 工作查看器;1 傻瓜表单轨迹查看器;',
  `FrmThreadLab` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示标签',
  `FrmThreadSta` int(11) NULL DEFAULT NULL COMMENT '组件状态,枚举类型:0 禁用;1 启用;',
  `FrmThread_X` float(11, 2) NULL DEFAULT NULL COMMENT '位置X',
  `FrmThread_Y` float(11, 2) NULL DEFAULT NULL COMMENT '位置Y',
  `FrmThread_H` float(11, 2) NULL DEFAULT NULL COMMENT '高度',
  `FrmThread_W` float(11, 2) NULL DEFAULT NULL COMMENT '宽度',
  `FrmTrackLab` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示标签',
  `FrmTrackSta` int(11) NULL DEFAULT NULL COMMENT '组件状态,枚举类型:0 禁用;1 显示轨迹图;2 显示轨迹表;',
  `FrmTrack_X` float(11, 2) NULL DEFAULT NULL COMMENT '位置X',
  `FrmTrack_Y` float(11, 2) NULL DEFAULT NULL COMMENT '位置Y',
  `FrmTrack_H` float(11, 2) NULL DEFAULT NULL COMMENT '高度',
  `FrmTrack_W` float(11, 2) NULL DEFAULT NULL COMMENT '宽度',
  `FTCLab` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示标签',
  `FTCSta` int(11) NULL DEFAULT NULL COMMENT '组件状态,枚举类型:0 禁用;1 只读;2 可设置人员;',
  `FTCWorkModel` int(11) NULL DEFAULT NULL COMMENT '工作模式,枚举类型:0 简洁模式;1 高级模式;',
  `FTC_X` float(11, 2) NULL DEFAULT NULL COMMENT '位置X',
  `FTC_Y` float(11, 2) NULL DEFAULT NULL COMMENT '位置Y',
  `FTC_H` float(11, 2) NULL DEFAULT NULL COMMENT '高度',
  `FTC_W` float(11, 2) NULL DEFAULT NULL COMMENT '宽度',
  `SelectorModel` int(11) NULL DEFAULT NULL COMMENT '显示方式,枚举类型:0 按岗位;1 按部门;2 按人员;3 按SQL;4 按SQL模版计算;5 使用通用人员选择器;6 部门与岗位的交集;7 自定义Url;8 使用通用部门岗位人员选择器;9 按岗位智能计算(操作员所在部门);',
  `FK_SQLTemplate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SQL模版',
  `FK_SQLTemplateText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SQL模版',
  `IsAutoLoadEmps` int(11) NULL DEFAULT NULL COMMENT '是否自动加载上一次选择的人员？',
  `IsSimpleSelector` int(11) NULL DEFAULT NULL COMMENT '是否单项选择(只能选择一个人)？',
  `IsEnableDeptRange` int(11) NULL DEFAULT NULL COMMENT '是否启用部门搜索范围限定(对使用通用人员选择器有效)？',
  `IsEnableStaRange` int(11) NULL DEFAULT NULL COMMENT '是否启用岗位搜索范围限定(对使用通用人员选择器有效)？',
  `SelectorP1` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分组参数:可以为空,比如:SELECT No,Name,ParentNo FROM  Port_Dept',
  `SelectorP2` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作员数据源:比如:SELECT No,Name,FK_Dept FROM  Port_Emp',
  `SelectorP3` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '默认选择的数据源:比如:SELECT FK_Emp FROM  WF_GenerWorkerList WHERE FK_Node=102 AND WorkID=@WorkID',
  `SelectorP4` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '强制选择的数据源:比如:SELECT FK_Emp FROM  WF_GenerWorkerList WHERE FK_Node=102 AND WorkID=@WorkID',
  PRIMARY KEY (`NodeID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选择器' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_node
-- ----------------------------
INSERT INTO `wf_node` VALUES (101, '001', '请假流程-经典表单-演示', '填写请假申请单', 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, '2020-04-05 17:34', '2020-04-05 17:34', 0, '', 0, 0, 0, 0, '', 1, '', 0, 0, 100, 0, '', 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '发送', '', '保存', 1, '子线程', 0, 0, '跳转', 0, '', '退回', 0, '', 1, 0, '', '', 0, 0, '抄送', 0, 0, '', '', '移交', 0, '删除', 0, '结束流程', 0, '查看父流程', 0, '打开公文', 0, 0, 0, '打印Html', 0, 0, 0, '打印pdf', 0, 0, 0, 0, '', '打包下载', 0, 0, 0, '打印单据', 0, '轨迹', 1, '挂起', 0, '查询', 0, '会签', 0, 0, '加主持人', 0, '流转自定义', 0, '公文', 0, '重要性', 0, '节点时限', 0, '分配', 0, '关注', 0, '确认', 0, '列表', 1, '批量审核', 0, '备注', 0, '帮助提示', 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '', '审核信息', 1, 0, '', 1, 1, 0, '审核', '', 0, 1, 300.00, 500.00, 0.00, 400.00, '', 0, 0, 0, 0, 0, '', '', '', '', 4, '前台', 1, '', 2.00, 0, 1.00, '', '', '', 0, 0, 0, 0, '', 0, 0, '', '', 2, '', 0, 12, '', 0, 'http://', 0, '', 0, '', '', '@102', '', '', '', '@101@102@104', 52, 46, '', '@Conds_AutoNum=-1@IsYouLiTai=0@PushMsgs_AutoNum=-1@FrmEvents_AutoNum=-1', 300.00, 400.00, '接受人', 0, '审核', 0, '加签', 0, '子流程', 0, 1, '启动子流程', '', 5.00, 5.00, '', 0, 0, '子线程', 0, 5.00, 5.00, 300.00, 400.00, '轨迹', 0, 5.00, 5.00, 300.00, 400.00, '流转自定义', 0, 0, 5.00, 5.00, 300.00, 400.00, 5, '', '', 1, 0, 0, 0, '', '', '', '');
INSERT INTO `wf_node` VALUES (102, '001', '请假流程-经典表单-演示', '部门经理审批', 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, '2020-04-05 17:34', '2020-04-05 17:34', 0, '审核意见:@BMJLSP_Note', 0, 0, 0, 0, '', 2, '', 0, 0, 100, 0, '', 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '发送', '', '保存', 1, '子线程', 0, 0, '跳转', 0, '', '退回', 1, '', 1, 0, '', '', 0, 0, '抄送', 0, 0, '', '', '移交', 0, '删除', 0, '结束流程', 0, '查看父流程', 0, '打开公文', 0, 0, 0, '打印Html', 0, 0, 0, '打印pdf', 0, 0, 0, 0, '', '打包下载', 0, 0, 0, '打印单据', 0, '轨迹', 1, '挂起', 0, '查询', 0, '会签', 0, 0, '加主持人', 0, '流转自定义', 0, '公文', 0, '重要性', 0, '节点时限', 0, '分配', 0, '关注', 0, '确认', 0, '列表', 1, '批量审核', 0, '备注', 0, '帮助提示', 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '', '审核信息', 1, 0, '', 1, 1, 0, '审核', '', 0, 1, 300.00, 500.00, 0.00, 400.00, '', 0, 0, 0, 0, 0, '', '', '', '', 4, '审核', 0, '', 1.00, 1, 1.00, '', '', '', 0, 0, 0, 0, '', 0, 0, '', '', 2, '', 0, 12, '', 0, 'http://', 0, '', 1, '', '', '@103', '', '', '', '@101@102@104', 244, 221, '', '@IsYouLiTai=0@FrmEvents_AutoNum=0@PushMsgs_AutoNum=-1', 300.00, 400.00, '接受人', 0, '审核', 0, '加签', 0, '子流程', 0, 1, '启动子流程', '', 5.00, 5.00, '', 0, 0, '子线程', 0, 5.00, 5.00, 300.00, 400.00, '轨迹', 0, 5.00, 5.00, 300.00, 400.00, '流转自定义', 0, 0, 5.00, 5.00, 300.00, 400.00, 5, '', '', 1, 0, 0, 0, '', '', '', '');
INSERT INTO `wf_node` VALUES (103, '001', '请假流程-经典表单-演示', '总经理审批', 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, '2020-04-05 17:34', '2020-04-05 17:34', 0, '审核意见:@ZJLSP_Note', 0, 0, 0, 0, '', 3, '', 0, 0, 100, 0, '', 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '发送', '', '保存', 1, '子线程', 0, 0, '跳转', 0, '', '退回', 1, '', 1, 0, '', '', 0, 0, '抄送', 0, 0, '', '', '移交', 0, '删除', 0, '结束流程', 0, '查看父流程', 0, '打开公文', 0, 0, 0, '打印Html', 0, 0, 0, '打印pdf', 0, 0, 0, 0, '', '打包下载', 0, 0, 0, '打印单据', 0, '轨迹', 1, '挂起', 0, '查询', 0, '会签', 0, 0, '加主持人', 0, '流转自定义', 0, '公文', 0, '重要性', 0, '节点时限', 0, '分配', 0, '关注', 0, '确认', 0, '列表', 1, '批量审核', 0, '备注', 0, '帮助提示', 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '', '审核信息', 1, 0, '', 1, 1, 0, '审核', '', 0, 1, 300.00, 500.00, 0.00, 400.00, '', 0, 0, 0, 0, 1, '', '', '', '', 14, '审核.png', 0, '', 1.00, 1, 1.00, '', '', '', 0, 0, 0, 0, '', 0, 0, '', '', 2, '', 0, 12, '', 0, 'http://', 0, '', 1, '@01', '@01', '@104', '', '', '', '@103', 421, 84, '', '@IsYouLiTai=0@PushMsgs_AutoNum=-1@FrmEvents_AutoNum=-1', 300.00, 400.00, '接受人', 0, '审核', 0, '加签', 0, '子流程', 0, 1, '启动子流程', '', 5.00, 5.00, '', 0, 0, '子线程', 0, 5.00, 5.00, 300.00, 400.00, '轨迹', 0, 5.00, 5.00, 300.00, 400.00, '流转自定义', 0, 0, 5.00, 5.00, 300.00, 400.00, 5, '', '', 1, 0, 0, 0, '', '', '', '');
INSERT INTO `wf_node` VALUES (104, '001', '请假流程-经典表单-演示', '反馈给申请人', 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, '2020-04-05 17:34', '2020-04-05 17:34', 0, '', 0, 0, 0, 0, '', 4, '', 0, 0, 100, 0, '', 0, 0, 0, 0, '', 0, 0, 0, 0, 0, '发送', '', '保存', 1, '子线程', 0, 0, '跳转', 0, '', '退回', 1, '', 1, 0, '', '', 0, 0, '抄送', 0, 0, '', '', '移交', 0, '删除', 0, '结束流程', 0, '查看父流程', 0, '打开公文', 0, 0, 0, '打印Html', 0, 0, 0, '打印pdf', 0, 0, 0, 0, '', '打包下载', 0, 0, 0, '打印单据', 0, '轨迹', 1, '挂起', 0, '查询', 0, '会签', 0, 0, '加主持人', 0, '流转自定义', 0, '公文', 0, '重要性', 0, '节点时限', 0, '分配', 0, '关注', 0, '确认', 0, '列表', 1, '批量审核', 0, '备注', 0, '帮助提示', 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, '', 0, 0, 0, 0, 0, '', '', '', '审核信息', 1, 0, '', 1, 1, 0, '审核', '', 0, 1, 300.00, 500.00, 0.00, 400.00, '', 0, 0, 0, 0, 1, '', '', '', '', 7, '审核.png', 0, '', 1.00, 1, 1.00, '', '', '', 0, 0, 0, 0, '', 0, 0, '', '', 2, '', 0, 12, '', 0, 'http://', 0, '', 2, '', '', '', '', '', '', '@101@102@104', 580, 190, '', '@IsYouLiTai=0@PushMsgs_AutoNum=-1@FrmEvents_AutoNum=-1', 300.00, 400.00, '接受人', 0, '审核', 0, '加签', 0, '子流程', 0, 1, '启动子流程', '', 5.00, 5.00, '', 0, 0, '子线程', 0, 5.00, 5.00, 300.00, 400.00, '轨迹', 0, 5.00, 5.00, 300.00, 400.00, '流转自定义', 0, 0, 5.00, 5.00, 300.00, 400.00, 5, '', '', 1, 0, 0, 0, '', '', '', '');

-- ----------------------------
-- Table structure for wf_nodecancel
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodecancel`;
CREATE TABLE `wf_nodecancel`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `CancelTo` int(11) NOT NULL COMMENT '撤销到 - 主键',
  PRIMARY KEY (`FK_Node`, `CancelTo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '可撤销的节点' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodedept
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodedept`;
CREATE TABLE `wf_nodedept`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门,主外键:对应物理表:Port_Dept,表描述:部门',
  PRIMARY KEY (`FK_Node`, `FK_Dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodeemp
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodeemp`;
CREATE TABLE `wf_nodeemp`  (
  `FK_Node` int(11) NOT NULL COMMENT 'Node - 主键',
  `FK_Emp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '到人员,主外键:对应物理表:Port_Emp,表描述:用户',
  PRIMARY KEY (`FK_Node`, `FK_Emp`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodereturn
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodereturn`;
CREATE TABLE `wf_nodereturn`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `ReturnTo` int(11) NOT NULL COMMENT '退回到 - 主键',
  PRIMARY KEY (`FK_Node`, `ReturnTo`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '可退回的节点' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodestation
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodestation`;
CREATE TABLE `wf_nodestation`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `FK_Station` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工作岗位,主外键:对应物理表:Port_Station,表描述:岗位',
  PRIMARY KEY (`FK_Node`, `FK_Station`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wf_nodestation
-- ----------------------------
INSERT INTO `wf_nodestation` VALUES (103, '01');

-- ----------------------------
-- Table structure for wf_nodesubflow
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodesubflow`;
CREATE TABLE `wf_nodesubflow`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主流程编号',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '主流程节点',
  `SubFlowType` int(11) NULL DEFAULT NULL COMMENT '子流程类型',
  `SubFlowModel` int(11) NULL DEFAULT NULL COMMENT '子流程模式',
  `IsAutoSendSubFlowOver` int(11) NULL DEFAULT NULL COMMENT '子流程结束规则',
  `IsAutoSendSLSubFlowOver` int(11) NULL DEFAULT NULL COMMENT '同级子流程结束规则',
  `SubFlowNo` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程编号',
  `SubFlowName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程名称',
  `StartOnceOnly` int(11) NULL DEFAULT NULL COMMENT '仅能被调用1次',
  `IsEnableSpecFlowStart` int(11) NULL DEFAULT NULL COMMENT '指定的流程启动后,才能启动该子流程(请在文本框配置子流程).',
  `SpecFlowStart` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程编号',
  `SpecFlowStartNote` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `IsEnableSpecFlowOver` int(11) NULL DEFAULT NULL COMMENT '指定的流程结束后,才能启动该子流程(请在文本框配置子流程).',
  `SpecFlowOver` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程编号',
  `SpecFlowOverNote` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ExpType` int(11) NULL DEFAULT NULL COMMENT '表达式类型',
  `CondExp` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条件表达式',
  `YBFlowReturnRole` int(11) NULL DEFAULT NULL COMMENT '退回方式',
  `ReturnToNode` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要退回的节点',
  `SendModel` int(11) NULL DEFAULT NULL COMMENT '自动触发的子流程发送方式',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `InvokeTime` int(11) NULL DEFAULT NULL COMMENT '调用时间,枚举类型:0 发送时;1 工作到达时;',
  `CompleteReStart` int(11) NULL DEFAULT NULL COMMENT '该子流程运行结束后才可以重新发起.',
  `IsEnableSQL` int(11) NULL DEFAULT NULL COMMENT '按照指定的SQL配置.',
  `SpecSQL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SQL语句',
  `IsEnableSameLevelNode` int(11) NULL DEFAULT NULL COMMENT '按照指定平级子流程节点完成后启动.',
  `SameLevelNode` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平级子流程节点',
  `IsSubFlowGuide` int(11) NULL DEFAULT NULL COMMENT '是否启用子流程批量发起前置导航',
  `IsTreeConstruct` int(11) NULL DEFAULT NULL COMMENT '是否是树形结构',
  `ParentNo` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点的值',
  `SubFlowGuideSQL` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子流程前置导航配置SQL',
  `SubFlowGuideEnNoFiled` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体No字段',
  `SubFlowGuideEnNameFiled` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体Name字段',
  `ReturnToNodeText` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要退回的节点',
  `SubFlowSta` int(11) NULL DEFAULT 1 COMMENT '状态',
  `SubFlowLab` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '启动文字标签',
  `SubFlowHidTodolist` int(11) NULL DEFAULT 0 COMMENT '发送后是否隐藏父流程待办',
  `SubFlowCopyFields` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父流程字段对应子流程字段',
  `SubFlowStartModel` int(11) NULL DEFAULT 0 COMMENT '启动模式',
  `SubFlowShowModel` int(11) NULL DEFAULT 0 COMMENT '展现模式',
  `BackCopyRole` int(11) NULL DEFAULT 0 COMMENT '子流程结束后数据字段反填规则',
  `ParentFlowCopyFields` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '子流程字段对应父流程字段',
  `YanXuToNode` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '延续到的节点',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '子流程(所有类型子流程属性)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodeteam
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodeteam`;
CREATE TABLE `wf_nodeteam`  (
  `FK_Node` int(11) NOT NULL COMMENT '节点 - 主键',
  `FK_Team` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户组,主外键:对应物理表:Port_Team,表描述:用户组',
  PRIMARY KEY (`FK_Node`, `FK_Team`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点岗位' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_nodetoolbar
-- ----------------------------
DROP TABLE IF EXISTS `wf_nodetoolbar`;
CREATE TABLE `wf_nodetoolbar`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `Title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `ExcType` int(11) NULL DEFAULT NULL COMMENT '执行类型,枚举类型:0 超链接;1 函数;',
  `UrlExt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '连接/函数',
  `Target` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标',
  `IsMyFlow` int(11) NULL DEFAULT 0 COMMENT '工作处理器',
  `IsMyTree` int(11) NULL DEFAULT 0 COMMENT '流程树',
  `IsMyView` int(11) NULL DEFAULT 0 COMMENT '工作查看器',
  `IsMyCC` int(11) NULL DEFAULT 0 COMMENT '抄送工具栏',
  `IconPath` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ICON路径',
  `Idx` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `MyFileName` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `MyFilePath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFilePath',
  `MyFileExt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MyFileExt',
  `WebPath` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WebPath',
  `MyFileH` int(11) NULL DEFAULT 0 COMMENT 'MyFileH',
  `MyFileW` int(11) NULL DEFAULT 0 COMMENT 'MyFileW',
  `MyFileSize` float NULL DEFAULT NULL COMMENT 'MyFileSize',
  `ShowWhere` int(11) NULL DEFAULT 1 COMMENT '显示位置',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义工具栏' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_part
-- ----------------------------
DROP TABLE IF EXISTS `wf_part`;
CREATE TABLE `wf_part`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `PartType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `Tag0` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag0',
  `Tag1` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag1',
  `Tag2` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag2',
  `Tag3` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag3',
  `Tag4` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag4',
  `Tag5` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag5',
  `Tag6` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag6',
  `Tag7` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag7',
  `Tag8` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag8',
  `Tag9` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag9',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配件' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_powermodel
-- ----------------------------
DROP TABLE IF EXISTS `wf_powermodel`;
CREATE TABLE `wf_powermodel`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Model` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `PowerFlag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `PowerFlagName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标记名称',
  `PowerCtrlType` int(11) NULL DEFAULT NULL COMMENT '控制类型,枚举类型:0 岗位;1 人员;',
  `EmpNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `EmpName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员名称',
  `StaNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位编号',
  `StaName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `FlowNo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `FrmID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单ID',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限模型' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_pushmsg
-- ----------------------------
DROP TABLE IF EXISTS `wf_pushmsg`;
CREATE TABLE `wf_pushmsg`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `FK_Event` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件类型',
  `PushWay` int(11) NULL DEFAULT NULL COMMENT '推送方式,枚举类型:0 按照指定节点的工作人员;1 按照指定的工作人员;2 按照指定的工作岗位;3 按照指定的部门;4 按照指定的SQL;5 按照系统指定的字段;',
  `PushDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '推送保存内容',
  `Tag` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tag',
  `SMSPushWay` int(11) NULL DEFAULT NULL COMMENT '短消息发送方式',
  `SMSField` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短消息字段',
  `SMSDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '短消息内容模版',
  `SMSNodes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SMS节点s',
  `SMSPushModel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '短消息发送设置',
  `MailPushWay` int(11) NULL DEFAULT NULL COMMENT '邮件发送方式',
  `MailAddress` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件字段',
  `MailTitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件标题模版',
  `MailDoc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '邮件内容模版',
  `MailNodes` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Mail节点s',
  `BySQL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按照SQL计算',
  `ByEmps` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送给指定的人员',
  `AtPara` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AtPara',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息推送' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_rememberme
-- ----------------------------
DROP TABLE IF EXISTS `wf_rememberme`;
CREATE TABLE `wf_rememberme`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点',
  `FK_Emp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前操作人员',
  `Objs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分配人员',
  `ObjsExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分配人员Ext',
  `Emps` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '所有的工作人员',
  `EmpsExt` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '工作人员Ext',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '记忆我' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_returnwork
-- ----------------------------
DROP TABLE IF EXISTS `wf_returnwork`;
CREATE TABLE `wf_returnwork`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `ReturnNode` int(11) NULL DEFAULT NULL COMMENT '退回节点',
  `ReturnNodeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回节点名称',
  `Returner` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回人',
  `ReturnerName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回人名称',
  `ReturnToNode` int(11) NULL DEFAULT NULL COMMENT 'ReturnToNode',
  `ReturnToEmp` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '退回给',
  `BeiZhu` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '退回原因',
  `RDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回日期',
  `IsBackTracking` int(11) NULL DEFAULT NULL COMMENT '是否要原路返回?',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退回轨迹' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_selectaccper
-- ----------------------------
DROP TABLE IF EXISTS `wf_selectaccper`;
CREATE TABLE `wf_selectaccper`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '接受人节点',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `FK_Emp` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'FK_Emp',
  `EmpName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'EmpName',
  `DeptName` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `AccType` int(11) NULL DEFAULT NULL COMMENT '类型(@0=接受人@1=抄送人)',
  `Rec` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录人',
  `Info` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办理意见信息',
  `IsRemember` int(11) NULL DEFAULT NULL COMMENT '以后发送是否按本次计算',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号(可以用于流程队列审核模式)',
  `Tag` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维度信息Tag',
  `TimeLimit` int(11) NULL DEFAULT NULL COMMENT '时限-天',
  `TSpanHour` float NULL DEFAULT NULL COMMENT '时限-小时',
  `ADT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到达日期(计划)',
  `SDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应完成日期(计划)',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选择接受/抄送人信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_sqltemplate
-- ----------------------------
DROP TABLE IF EXISTS `wf_sqltemplate`;
CREATE TABLE `wf_sqltemplate`  (
  `No` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `SQLType` int(11) NULL DEFAULT NULL COMMENT '模版SQL类型,枚举类型:0 方向条件;1 接受人规则;2 下拉框数据过滤;3 级联下拉框;4 PopVal开窗返回值;5 人员选择器人员选择范围;',
  `Name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SQL说明',
  `Docs` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'SQL模版',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'SQL模板' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_task
-- ----------------------------
DROP TABLE IF EXISTS `wf_task`;
CREATE TABLE `wf_task`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `Starter` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `ToNode` int(11) NULL DEFAULT NULL COMMENT '到达的节点',
  `ToEmps` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到达人员',
  `Paras` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参数',
  `TaskSta` int(11) NULL DEFAULT NULL COMMENT '任务状态',
  `Msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '消息',
  `StartDT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起时间',
  `RDT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '插入数据时间',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_testapi
-- ----------------------------
DROP TABLE IF EXISTS `wf_testapi`;
CREATE TABLE `wf_testapi`  (
  `No` varchar(92) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试过程' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_testcase
-- ----------------------------
DROP TABLE IF EXISTS `wf_testcase`;
CREATE TABLE `wf_testcase`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `FK_Flow` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程编号',
  `ParaType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数类型',
  `Vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值s',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义流程测试' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_testsample
-- ----------------------------
DROP TABLE IF EXISTS `wf_testsample`;
CREATE TABLE `wf_testsample`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试名称',
  `FK_API` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试的API,外键:对应物理表:WF_TestAPI,表描述:测试过程',
  `FK_Ver` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '测试的版本,外键:对应物理表:WF_TestVer,表描述:测试版本',
  `DTFrom` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从',
  `DTTo` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到',
  `TimeUse` float NULL DEFAULT NULL COMMENT '用时(毫秒)',
  `TimesPerSecond` float NULL DEFAULT NULL COMMENT '每秒跑多少个?',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试明细' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_testver
-- ----------------------------
DROP TABLE IF EXISTS `wf_testver`;
CREATE TABLE `wf_testver`  (
  `No` varchar(92) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号 - 主键',
  `Name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`No`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试版本' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_transfercustom
-- ----------------------------
DROP TABLE IF EXISTS `wf_transfercustom`;
CREATE TABLE `wf_transfercustom`  (
  `MyPK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键MyPK - 主键',
  `WorkID` int(11) NULL DEFAULT NULL COMMENT 'WorkID',
  `FK_Node` int(11) NULL DEFAULT NULL COMMENT '节点ID',
  `NodeName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `Worker` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人(多个人用逗号分开)',
  `WorkerName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理人(多个人用逗号分开)',
  `SubFlowNo` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '要经过的子流程编号',
  `PlanDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划完成日期',
  `TodolistModel` int(11) NULL DEFAULT NULL COMMENT '多人工作处理模式',
  `IsEnable` int(11) NULL DEFAULT NULL COMMENT '是否启用',
  `Idx` int(11) NULL DEFAULT NULL COMMENT '顺序号',
  PRIMARY KEY (`MyPK`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义运行路径' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wf_workflowdeletelog
-- ----------------------------
DROP TABLE IF EXISTS `wf_workflowdeletelog`;
CREATE TABLE `wf_workflowdeletelog`  (
  `OID` int(11) NOT NULL COMMENT 'OID - 主键',
  `FID` int(11) NULL DEFAULT NULL COMMENT 'FID',
  `FK_Dept` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门,外键:对应物理表:Port_Dept,表描述:部门',
  `Title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `FlowStarter` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `FlowStartRDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起时间',
  `FK_Flow` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程,外键:对应物理表:WF_Flow,表描述:流程',
  `FlowEnderRDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后处理时间',
  `FlowEndNode` int(11) NULL DEFAULT NULL COMMENT '停留节点',
  `FlowDaySpan` float NULL DEFAULT NULL COMMENT '跨度(天)',
  `FlowEmps` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参与人',
  `Oper` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人员',
  `OperDept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人员部门',
  `OperDeptName` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人员名称',
  `DeleteNote` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '删除原因',
  `DeleteDT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除日期',
  PRIMARY KEY (`OID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程删除日志' ROW_FORMAT = Compact;

-- ----------------------------
-- View structure for port_dept
-- ----------------------------
DROP VIEW IF EXISTS `port_dept`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `port_dept` AS SELECT
	`platform-shop`.sys_dept.dept_id AS `No`,
	`platform-shop`.sys_dept.`name` AS `Name`,
	`platform-shop`.sys_dept.parent_id AS `ParentNo`,
	"" AS `OrgNo`,
	"" AS `Leader`,
	"" AS `Idx`,
	"" AS `DeptType`,
	"" AS `NameOfPath`
FROM
	`platform-shop`.sys_dept ;

-- ----------------------------
-- View structure for port_emp
-- ----------------------------
DROP VIEW IF EXISTS `port_emp`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `port_emp` AS SELECT
	`platform-shop`.sys_user.username AS `No`,
	`platform-shop`.sys_user.username AS `Name`,
	`platform-shop`.sys_user.`password` AS Pass,
	`platform-shop`.sys_user.dept_id AS FK_Dept,
	`platform-shop`.sys_user.mobile AS `Tel`,
	`platform-shop`.sys_user.email AS `Email`,
	"" AS `SID`,
	"" AS `PinYin`,
	"" AS `UserType`,
	"" AS `OrgNo`,
	"" AS `Idx`,
	"" AS `SignType`
FROM
	`platform-shop`.sys_user ;

-- ----------------------------
-- View structure for port_inc
-- ----------------------------
DROP VIEW IF EXISTS `port_inc`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `port_inc` AS SELECT * FROM Port_Dept WHERE (No='100' OR No='1060' OR No='1070') ;

-- ----------------------------
-- View structure for port_station
-- ----------------------------
DROP VIEW IF EXISTS `port_station`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `port_station` AS SELECT
	`platform-shop`.sys_role.role_id AS `No`,
	`platform-shop`.sys_role.role_name AS `Name`,
	"" AS `FK_StationType`,
	"" AS `OrgNo`
FROM
	`platform-shop`.sys_role ;

-- ----------------------------
-- View structure for v_flowstarterbpm
-- ----------------------------
DROP VIEW IF EXISTS `v_flowstarterbpm`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_flowstarterbpm` AS SELECT A.FK_Flow, a.FlowName, C.FK_Emp,C.OrgNo FROM WF_Node a, WF_NodeStation b, Port_DeptEmpStation c
 WHERE a.NodePosType=0 AND ( a.WhoExeIt=0 OR a.WhoExeIt=2 )
AND  a.NodeID=b.FK_Node AND B.FK_Station=C.FK_Station   AND (A.DeliveryWay=0 OR A.DeliveryWay=14)
  UNION
SELECT A.FK_Flow, a.FlowName, C.FK_Emp,C.OrgNo FROM WF_Node a, WF_NodeDept b, Port_DeptEmp c
 WHERE a.NodePosType=0 AND ( a.WhoExeIt=0 OR a.WhoExeIt=2 )
AND  a.NodeID=b.FK_Node AND B.FK_Dept=C.FK_Dept   AND A.DeliveryWay=1
  UNION
SELECT A.FK_Flow, a.FlowName, B.FK_Emp, '' as OrgNo FROM WF_Node A, WF_NodeEmp B
 WHERE A.NodePosType=0 AND ( A.WhoExeIt=0 OR A.WhoExeIt=2 )
AND A.NodeID=B.FK_Node  AND A.DeliveryWay=3
  UNION
SELECT A.FK_Flow, A.FlowName, B.No AS FK_Emp, B.OrgNo FROM WF_Node A, Port_Emp B
 WHERE A.NodePosType=0 AND ( A.WhoExeIt=0 OR A.WhoExeIt=2 )  AND A.DeliveryWay=4
  UNION
SELECT A.FK_Flow, a.FlowName, E.FK_Emp,E.OrgNo FROM WF_Node A, WF_NodeDept B, WF_NodeStation C,  Port_DeptEmpStation E
 WHERE a.NodePosType=0
 AND ( a.WhoExeIt=0 OR a.WhoExeIt=2 )
 AND  A.NodeID=B.FK_Node
 AND A.NodeID=C.FK_Node
 AND B.FK_Dept=E.FK_Dept
 AND C.FK_Station=E.FK_Station AND A.DeliveryWay=9
 UNION
 SELECT  A.FK_Flow, A.FlowName, C.No as FK_Emp, B.OrgNo FROM WF_Node A, WF_FlowOrg B, Port_Emp C
 WHERE A.FK_Flow=B.FlowNo AND B.OrgNo=C.OrgNo
 AND  A.DeliveryWay=22 ;

-- ----------------------------
-- View structure for v_gpm_empgroup
-- ----------------------------
DROP VIEW IF EXISTS `v_gpm_empgroup`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_gpm_empgroup` AS SELECT FK_Group,FK_Emp FROM GPM_GroupEmp
UNION
SELECT a.FK_Group,B.FK_Emp FROM GPM_GroupStation a, Port_DeptEmpStation b
WHERE a.FK_Station=b.FK_Station ;

-- ----------------------------
-- View structure for v_gpm_empstationmenu
-- ----------------------------
DROP VIEW IF EXISTS `v_gpm_empstationmenu`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_gpm_empstationmenu` AS SELECT b.FK_Station,a.FK_Emp,b.FK_Menu,b.IsChecked FROM Port_DeptEmpStation a,GPM_StationMenu b
WHERE a.FK_Station = b.FK_Station ;

-- ----------------------------
-- View structure for v_gpm_empgroupmenu
-- ----------------------------
DROP VIEW IF EXISTS `v_gpm_empgroupmenu`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_gpm_empgroupmenu` AS SELECT a.FK_Group,a.FK_Emp,b.FK_Menu,b.IsChecked FROM V_GPM_EmpGroup a, GPM_GroupMenu b
WHERE a.FK_Group=b.FK_Group ;

-- ----------------------------
-- View structure for v_gpm_empmenu
-- ----------------------------
DROP VIEW IF EXISTS `v_gpm_empmenu`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_gpm_empmenu` AS SELECT CONCAT(a.FK_Emp,'_',a.FK_Menu) as MyPK, a.FK_Emp, b.No as FK_Menu, b.* FROM
   GPM_EmpMenu a,GPM_Menu b WHERE a.FK_Menu=b.No AND B.IsEnable=1
UNION
SELECT CONCAT(a.FK_Emp,'_',a.FK_Menu) as MyPK, a.FK_Emp, b.No as FK_Menu, b.* FROM
  V_GPM_EmpGroupMenu a,GPM_Menu b  WHERE a.FK_Menu=b.No AND B.IsEnable=1
UNION
SELECT CONCAT(a.FK_Emp,'_',a.FK_Menu) as MyPK, a.FK_Emp, b.No as FK_Menu, b.* FROM
  V_GPM_EmpStationMenu a,GPM_Menu b WHERE a.FK_Menu=b.No AND B.IsEnable=1 ;

-- ----------------------------
-- View structure for v_gpm_empmenu_gpm
-- ----------------------------
DROP VIEW IF EXISTS `v_gpm_empmenu_gpm`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_gpm_empmenu_gpm` AS SELECT CONCAT(a.FK_Emp,'_',a.FK_Menu) as MyPK, a.FK_Emp,a.IsChecked, b.No as FK_Menu, b.* FROM
   GPM_EmpMenu a,GPM_Menu b WHERE a.FK_Menu=b.No AND B.IsEnable=1
UNION
SELECT CONCAT(a.FK_Emp,'_',a.FK_Menu) as MyPK, a.FK_Emp,a.IsChecked, b.No as FK_Menu, b.* FROM
  V_GPM_EmpGroupMenu a,GPM_Menu b  WHERE a.FK_Menu=b.No AND B.IsEnable=1 ;


-- ----------------------------
-- View structure for v_myflowdata
-- ----------------------------
DROP VIEW IF EXISTS `v_myflowdata`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_myflowdata` AS SELECT A.*, B.EmpNo as MyEmpNo FROM WF_GenerWorkflow A, WF_PowerModel B  WHERE  A.FK_Flow=B.FlowNo AND B.PowerCtrlType=1 AND A.WFState>= 2    UNION   SELECT A.*, c.No as MyEmpNo FROM WF_GenerWorkflow A, WF_PowerModel B, Port_Emp C, Port_DeptEmpStation D WHERE  A.FK_Flow=B.FlowNo  AND B.PowerCtrlType=0 AND C.No=D.FK_Emp AND B.StaNo=D.FK_Station AND A.WFState>=2 ;

-- ----------------------------
-- View structure for v_wf_authtodolist
-- ----------------------------
DROP VIEW IF EXISTS `v_wf_authtodolist`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_wf_authtodolist` AS SELECT B.FK_Emp Auther,B.FK_EmpText AuthName,A.PWorkID,A.FK_Node,A.FID,A.WorkID,C.EmpNo,  C.TakeBackDT, A.FK_Flow, A.FlowName,A.Title  FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B, WF_Auth C WHERE A.WorkID=B.WorkID AND C.AuthType=1 AND B.FK_Emp=C.Auther AND B.IsPass=0 AND B.IsEnable=1 AND A.FK_Node = B.FK_Node AND A.WFState >=2    UNION   SELECT B.FK_Emp Auther,B.FK_EmpText AuthName,A.PWorkID,A.FK_Node,A.FID,A.WorkID, C.EmpNo, C.TakeBackDT, A.FK_Flow, A.FlowName,A.Title FROM WF_GenerWorkFlow A, WF_GenerWorkerlist B, WF_Auth C WHERE A.WorkID=B.WorkID AND C.AuthType=2 AND B.FK_Emp=C.Auther AND B.IsPass=0 AND B.IsEnable=1 AND A.FK_Node = B.FK_Node AND A.WFState >=2 AND A.FK_Flow=C.FlowNo ;

-- ----------------------------
-- View structure for wf_empworks
-- ----------------------------
DROP VIEW IF EXISTS `wf_empworks`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `wf_empworks` AS SELECT A.PRI,A.WorkID,B.IsRead, A.Starter,A.StarterName,A.WFState,A.FK_Dept,A.DeptName, A.FK_Flow, A.FlowName,A.PWorkID,
A.PFlowNo,B.FK_Node, B.FK_NodeText AS NodeName, A.Title, A.RDT, B.RDT AS ADT,
B.SDT, B.FK_Emp,B.FID ,A.FK_FlowSort,A.SysType,A.SDTOfNode,B.PressTimes,
A.GuestNo,A.GuestName,A.BillNo,A.FlowNote,A.TodoEmps,A.TodoEmpsNum,A.TodoSta,A.TaskSta,0 as ListType,A.Sender,A.AtPara,
A.Domain
FROM  WF_GenerWorkFlow A, WF_GenerWorkerlist B
WHERE     (B.IsEnable = 1) AND (B.IsPass = 0)
 AND A.WorkID = B.WorkID AND A.FK_Node = B.FK_Node AND A.WFState!=0  AND WhoExeIt!=1
 UNION
SELECT A.PRI,A.WorkID,B.Sta AS IsRead, A.Starter,
A.StarterName,2 AS WFState,A.FK_Dept,A.DeptName, A.FK_Flow, A.FlowName,A.PWorkID,
A.PFlowNo,B.FK_Node, B.NodeName, A.Title, A.RDT, B.RDT AS ADT,
B.RDT AS SDT, B.CCTo as FK_Emp,B.FID ,A.FK_FlowSort,A.SysType,A.SDTOfNode, 0 as PressTimes,
A.GuestNo,A.GuestName,A.BillNo,A.FlowNote,A.TodoEmps,A.TodoEmpsNum,0 as TodoSta,0 AS TaskSta,1 as ListType,B.Rec as Sender,
'@IsCC=1'||A.AtPara as AtPara,
A.Domain
  FROM WF_GenerWorkFlow A, WF_CCList B WHERE A.WorkID=B.WorkID AND  B.Sta <=1 AND B.InEmpWorks = 1 AND A.WFState!=0 ;

SET FOREIGN_KEY_CHECKS = 1;
