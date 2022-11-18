package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_info")     // 类名跟表明不一致时才需要加这个注解，用于告知mybatis-plus去查哪个表
public class User {

    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 当前登录用户的工号
    @TableField(value = "userid")
    private String userId;

    // 当前登录用户的姓名
    @TableField(value = "username")
    private String userName;

    // 院系
    @TableField(value = "yuanxi")
    private String yuanXi;

    // 人员类型
    @TableField(value = "ptype")
    private String pType;

    // 人员状态
    @TableField(value = "pstatus")
    private String pStatus;

    // 当前用户的性别
    @TableField(value = "gender")
    private String gender;

    // 用户权限
    @TableField(value = "role")
    private String role;

    // 逻辑删除
    @TableField(value = "is_deleted")
    private String isDeleted;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    // 修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
