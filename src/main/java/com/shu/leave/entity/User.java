package com.shu.leave.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    // 主键id 自增
    private Long id;

    // 当前登录用户的工号
    private String userId;

    // 当前登录用户的姓名
    private String userName;

    // 院系
    private String yuanXi;

    // 人员类型
    private String pType;

    // 人员状态
    private String pStatus;

    // 当前用户的性别
    private String gender;

    // 用户权限
    private String role;

    // 逻辑删除
    private String isDeleted;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;
}
