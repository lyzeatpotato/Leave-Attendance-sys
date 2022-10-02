/*
 Navicat Premium Data Transfer

 Source Server         : 远程服务器SQL Server数据库
 Source Server Type    : SQL Server
 Source Server Version : 15004261
 Source Host           : 139.224.56.4:1433
 Source Catalog        : shu_leave
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 15004261
 File Encoding         : 65001

 Date: 01/10/2022 22:16:03
*/


-- ----------------------------
-- Table structure for absence_history
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[absence_history]') AND type IN ('U'))
	DROP TABLE [dbo].[absence_history]
GO

CREATE TABLE [dbo].[absence_history] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] bigint  NOT NULL,
  [year] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [month] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [shijia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [bingjia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hunjia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [shengyujia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [tanqinjia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [sangjia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gongshangjia_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [kuanggong_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [inactive_days] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[absence_history] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接用户信息表（user_info）-用户id',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'userid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'年度信息',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'year'
GO

EXEC sp_addextendedproperty
'MS_Description', N'月度信息',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'month'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计事假总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'shijia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计病假总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'bingjia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计婚嫁总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'hunjia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计生育假总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'shengyujia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计探亲假总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'tanqinjia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计丧家总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'sangjia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计工伤假总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'gongshangjia_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计旷工天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'kuanggong_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'本月累计活跃度为0的总天数',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'inactive_days'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'absence_history',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of absence_history
-- ----------------------------
SET IDENTITY_INSERT [dbo].[absence_history] ON
GO

SET IDENTITY_INSERT [dbo].[absence_history] OFF
GO


-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[admin_info]') AND type IN ('U'))
	DROP TABLE [dbo].[admin_info]
GO

CREATE TABLE [dbo].[admin_info] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [username] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[admin_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前登录用户的工号（读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'userid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前登录用户的姓名（读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'username'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除（1为已删除，0为未删除）',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'admin_info',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of admin_info
-- ----------------------------
SET IDENTITY_INSERT [dbo].[admin_info] ON
GO

SET IDENTITY_INSERT [dbo].[admin_info] OFF
GO


-- ----------------------------
-- Table structure for calender
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[calender]') AND type IN ('U'))
	DROP TABLE [dbo].[calender]
GO

CREATE TABLE [dbo].[calender] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [adminid] bigint  NOT NULL,
  [holiday_name] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [holiday_start_name] date  NOT NULL,
  [holiday_end_date] date  NOT NULL,
  [description] varchar(max) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[calender] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接系统管理员表（admin_info）-系统用户id',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'adminid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校历假期名称',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'holiday_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校历假期开始日期',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'holiday_start_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校历假期结束日期',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'holiday_end_date'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校历假期描述信息',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'description'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'calender',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of calender
-- ----------------------------
SET IDENTITY_INSERT [dbo].[calender] ON
GO

SET IDENTITY_INSERT [dbo].[calender] OFF
GO


-- ----------------------------
-- Table structure for leave
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[leave]') AND type IN ('U'))
	DROP TABLE [dbo].[leave]
GO

CREATE TABLE [dbo].[leave] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] bigint  NOT NULL,
  [leave_type] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [leave_start_time] datetime  NOT NULL,
  [leave_end_time] datetime  NOT NULL,
  [leave_reason] varchar(max) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [leave_material] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [department_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [school_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[leave] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接用户信息表-用户id',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'userid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假类型',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'leave_type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假起始时间（到小时）',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'leave_start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假结束时间（到小时）',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'leave_end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假事由说明',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'leave_reason'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假相关证明材料本地路径',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'leave_material'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请假表单状态（0表示待审核；1表示已审核-通过；2表示已审核-不通过；3表示已撤销）',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门审核状态(0表示未完成，1表示已完成，2表示无需此步骤)',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'department_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处审核状态(0表示未完成，1表示已完成，2表示无需此步骤)',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'hr_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导审核状态(0表示未完成，1表示已完成，2表示无需此步骤)',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'school_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'leave',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of leave
-- ----------------------------
SET IDENTITY_INSERT [dbo].[leave] ON
GO

SET IDENTITY_INSERT [dbo].[leave] OFF
GO


-- ----------------------------
-- Table structure for leave_department_audit
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[leave_department_audit]') AND type IN ('U'))
	DROP TABLE [dbo].[leave_department_audit]
GO

CREATE TABLE [dbo].[leave_department_audit] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [formid] bigint  NOT NULL,
  [dp_officer_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_officer_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_officer_recommend] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_officer_time] datetime  NOT NULL,
  [dp_officer_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_recommend] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_time] datetime  NOT NULL,
  [dp_leader_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[leave_department_audit] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接请假申请表（leave）-请假申请表id',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门人事干事工号',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_officer_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门人事干事初审结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_officer_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门人事干事审核意见',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_officer_recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门认识干事审核时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_officer_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门人事干事审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_officer_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人工号',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_leader_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_leader_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核意见',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_leader_recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_leader_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'dp_leader_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_department_audit',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of leave_department_audit
-- ----------------------------
SET IDENTITY_INSERT [dbo].[leave_department_audit] ON
GO

SET IDENTITY_INSERT [dbo].[leave_department_audit] OFF
GO


-- ----------------------------
-- Table structure for leave_hr_audit
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[leave_hr_audit]') AND type IN ('U'))
	DROP TABLE [dbo].[leave_hr_audit]
GO

CREATE TABLE [dbo].[leave_hr_audit] (
  [id] bigint  NOT NULL,
  [formid] bigint  NOT NULL,
  [hr_officer_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_officer_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_officer_recommend] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_officer_time] datetime  NOT NULL,
  [hr_officer_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_recommend] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_time] datetime  NOT NULL,
  [hr_leader_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[leave_hr_audit] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接请假申请表（leave）-表单id',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处人事科员工号',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_officer_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处人事科员初审结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_officer_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处人事科员审核意见',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_officer_recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处人事科员审核时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_officer_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处人事科员审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_officer_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人工号',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_leader_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_leader_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核意见',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_leader_recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_leader_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'hr_leader_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_hr_audit',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of leave_hr_audit
-- ----------------------------

-- ----------------------------
-- Table structure for leave_school_audit
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[leave_school_audit]') AND type IN ('U'))
	DROP TABLE [dbo].[leave_school_audit]
GO

CREATE TABLE [dbo].[leave_school_audit] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [formid] bigint  NOT NULL,
  [sc_leader_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [sc_leader_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [sc_leader_recommend] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [sc_leader_time] datetime  NOT NULL,
  [sc_leader_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[leave_school_audit] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接请假申请表（leave）-表单id',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导工号',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'sc_leader_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导审核机构（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'sc_leader_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导审核意见',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'sc_leader_recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导审核时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'sc_leader_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'校领导审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'sc_leader_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'leave_school_audit',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of leave_school_audit
-- ----------------------------
SET IDENTITY_INSERT [dbo].[leave_school_audit] ON
GO

SET IDENTITY_INSERT [dbo].[leave_school_audit] OFF
GO


-- ----------------------------
-- Table structure for revoke
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[revoke]') AND type IN ('U'))
	DROP TABLE [dbo].[revoke]
GO

CREATE TABLE [dbo].[revoke] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [formid] bigint  NOT NULL,
  [revoke_submit_time] datetime  NOT NULL,
  [revoke_report_time] datetime  NOT NULL,
  [status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [department_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[revoke] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接请假申请表（leave）-表单id',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'销假表单提交时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'revoke_submit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'返岗报道时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'revoke_report_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'销假表单状态（0表示待审核；1表示已审核-通过；2表示已审核-不通过；3表示已撤销）',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门审核状态(0表示未完成，1表示已完成，2表示无需此步骤)',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'department_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处审核状态(0表示未完成，1表示已完成，2表示无需此步骤)',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'hr_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of revoke
-- ----------------------------
SET IDENTITY_INSERT [dbo].[revoke] ON
GO

SET IDENTITY_INSERT [dbo].[revoke] OFF
GO


-- ----------------------------
-- Table structure for revoke_department_audit
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[revoke_department_audit]') AND type IN ('U'))
	DROP TABLE [dbo].[revoke_department_audit]
GO

CREATE TABLE [dbo].[revoke_department_audit] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [revoke_formid] bigint  NOT NULL,
  [dp_leader_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [dp_leader_time] datetime  NOT NULL,
  [dp_leader_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[revoke_department_audit] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接销假申请表（revoke）-表单id',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'revoke_formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人工号',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'dp_leader_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'dp_leader_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'dp_leader_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门负责人审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'dp_leader_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_department_audit',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of revoke_department_audit
-- ----------------------------
SET IDENTITY_INSERT [dbo].[revoke_department_audit] ON
GO

SET IDENTITY_INSERT [dbo].[revoke_department_audit] OFF
GO


-- ----------------------------
-- Table structure for revoke_hr_audit
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[revoke_hr_audit]') AND type IN ('U'))
	DROP TABLE [dbo].[revoke_hr_audit]
GO

CREATE TABLE [dbo].[revoke_hr_audit] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [revoke_formid] bigint  NOT NULL,
  [hr_leader_id] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_result] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [hr_leader_time] datetime  NOT NULL,
  [hr_leader_status] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[revoke_hr_audit] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接销假申请表（revoke）-销假表id',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'revoke_formid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人工号',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'hr_leader_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核结果（通过/不通过）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'hr_leader_result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'hr_leader_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人事处负责人审核完成情况（0表示未完成，1表示已完成）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'hr_leader_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'revoke_hr_audit',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of revoke_hr_audit
-- ----------------------------
SET IDENTITY_INSERT [dbo].[revoke_hr_audit] ON
GO

SET IDENTITY_INSERT [dbo].[revoke_hr_audit] OFF
GO


-- ----------------------------
-- Table structure for router
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[router]') AND type IN ('U'))
	DROP TABLE [dbo].[router]
GO

CREATE TABLE [dbo].[router] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] bigint  NOT NULL,
  [page] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[router] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'router',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'外键 连接用户信息表（user_info）-用户id',
'SCHEMA', N'dbo',
'TABLE', N'router',
'COLUMN', N'userid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户可访问的页面路由字符串',
'SCHEMA', N'dbo',
'TABLE', N'router',
'COLUMN', N'page'
GO


-- ----------------------------
-- Records of router
-- ----------------------------
SET IDENTITY_INSERT [dbo].[router] ON
GO

SET IDENTITY_INSERT [dbo].[router] OFF
GO


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[user_info]') AND type IN ('U'))
	DROP TABLE [dbo].[user_info]
GO

CREATE TABLE [dbo].[user_info] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [username] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [yuanxi] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [ptype] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [pstatus] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gender] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [role] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [is_deleted] char(1) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [gmt_create] datetime  NOT NULL,
  [gmt_modified] datetime  NOT NULL
)
GO

ALTER TABLE [dbo].[user_info] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键 自增 唯一标识id',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前登录用户的工号（读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'userid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前登录用户的姓名（读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'username'
GO

EXEC sp_addextendedproperty
'MS_Description', N'院系（读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'yuanxi'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人员类别（教师/学生/工作人员-读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'ptype'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人员状态（在编不在岗/在岗不在编/在编在岗/...-读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'pstatus'
GO

EXEC sp_addextendedproperty
'MS_Description', N'性别',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'gender'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户权限（0为普通教师用户；1为各部门人事干事；2为各部门负责人；3为人事处人事科员；4为人事处负责人；5为校领导）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'role'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除字段（1表示删除，0表示未删除）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'is_deleted'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'gmt_modified'
GO


-- ----------------------------
-- Records of user_info
-- ----------------------------
SET IDENTITY_INSERT [dbo].[user_info] ON
GO

SET IDENTITY_INSERT [dbo].[user_info] OFF
GO


-- ----------------------------
-- Auto increment value for absence_history
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[absence_history]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table absence_history
-- ----------------------------
ALTER TABLE [dbo].[absence_history] ADD CONSTRAINT [PK__absence___3213E83FF2421DC0] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for admin_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[admin_info]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table admin_info
-- ----------------------------
ALTER TABLE [dbo].[admin_info] ADD CONSTRAINT [PK__admin_in__3213E83F8D389A30] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for calender
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[calender]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table calender
-- ----------------------------
ALTER TABLE [dbo].[calender] ADD CONSTRAINT [PK__calender__3213E83FBAFE00D3] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for leave
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[leave]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table leave
-- ----------------------------
ALTER TABLE [dbo].[leave] ADD CONSTRAINT [PK__leave__3213E83FA8613F0C] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for leave_department_audit
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[leave_department_audit]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table leave_department_audit
-- ----------------------------
ALTER TABLE [dbo].[leave_department_audit] ADD CONSTRAINT [PK__leave_de__3213E83FBC578202] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table leave_hr_audit
-- ----------------------------
ALTER TABLE [dbo].[leave_hr_audit] ADD CONSTRAINT [PK__leave_hr__3213E83FF6D5298B] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for leave_school_audit
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[leave_school_audit]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table leave_school_audit
-- ----------------------------
ALTER TABLE [dbo].[leave_school_audit] ADD CONSTRAINT [PK__leave_sc__3213E83F4070AFE2] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for revoke
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[revoke]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table revoke
-- ----------------------------
ALTER TABLE [dbo].[revoke] ADD CONSTRAINT [PK__revoke__3213E83F83DD15E9] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for revoke_department_audit
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[revoke_department_audit]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table revoke_department_audit
-- ----------------------------
ALTER TABLE [dbo].[revoke_department_audit] ADD CONSTRAINT [PK__revoke_d__3213E83F15755612] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for revoke_hr_audit
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[revoke_hr_audit]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table revoke_hr_audit
-- ----------------------------
ALTER TABLE [dbo].[revoke_hr_audit] ADD CONSTRAINT [PK__revoke_h__3213E83FE78AEA86] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for router
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[router]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table router
-- ----------------------------
ALTER TABLE [dbo].[router] ADD CONSTRAINT [PK__router__3213E83F11C251DE] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for user_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[user_info]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE [dbo].[user_info] ADD CONSTRAINT [PK__user_inf__3213E83F6AFA5231] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

