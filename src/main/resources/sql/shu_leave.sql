/*
 Navicat Premium Data Transfer

 Source Server         : 本地SQL Server(GJZC,shu-car,shu-ky)
 Source Server Type    : SQL Server
 Source Server Version : 15002095
 Source Host           : localhost:1433
 Source Catalog        : shu_leave
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 15002095
 File Encoding         : 65001

 Date: 28/09/2022 17:58:07
*/


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[user_info]') AND type IN ('U'))
	DROP TABLE [dbo].[user_info]
GO

CREATE TABLE [dbo].[user_info] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [userid] varchar(20) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [username] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [yuanxi] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [ptype] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [pstatus] varchar(50) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [password] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [email] varchar(50) COLLATE Chinese_PRC_CI_AS  NULL,
  [telephone] varchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
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
'MS_Description', N'当前登录用户的工号',
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
'MS_Description', N'人员类型（教师/学生/工作人员 读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'ptype'
GO

EXEC sp_addextendedproperty
'MS_Description', N'人员状态（在编在岗/在岗不在编/在编不在岗 读取自统一身份认证）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'pstatus'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前用户的密码',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'password'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前用户的邮箱',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'email'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前用户的电话号码',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'telephone'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户权限（普通教师用户：0、各部门人事干事：1、各部门负责人：2、人事处人事科人员：3、人事处负责人：4、校领导：5）',
'SCHEMA', N'dbo',
'TABLE', N'user_info',
'COLUMN', N'role'
GO

EXEC sp_addextendedproperty
'MS_Description', N'逻辑删除（1表示是，0表示否）',
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

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'1', N'21721687', N'test01', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'2', N'21721687', N'test01', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'1', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'3', N'21721687', N'test02', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'4', N'21721687', N'test02', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'5', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'6', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'7', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'8', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'9', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

INSERT INTO [dbo].[user_info] ([id], [userid], [username], [yuanxi], [ptype], [pstatus], [password], [email], [telephone], [role], [is_deleted], [gmt_create], [gmt_modified]) VALUES (N'10', N'21721687', N'test06', N'计算机工程与科学学院', N'教师', N'在岗不在编', N'123456', N'850209171@qq.com', N'18621852732', N'0', N'0', N'2022-09-28 00:00:00.000', N'2022-09-28 00:00:00.000')
GO

SET IDENTITY_INSERT [dbo].[user_info] OFF
GO


-- ----------------------------
-- Auto increment value for user_info
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[user_info]', RESEED, 10)
GO


-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE [dbo].[user_info] ADD CONSTRAINT [PK__user_inf__3213E83F72EDA01C] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

