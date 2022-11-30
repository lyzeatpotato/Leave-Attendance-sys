package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave")
public class Leave {

    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 提交信息表的用户id
    @TableField(value = "userid")
    private Long userId;


    //用户详细信息
    private User user;

    // 请假类型
    @TableField(value = "leave_type")
    private String leaveType;

    // 请假起始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone="GMT+8")
    @TableField(value = "leave_start_time")
    private Date leaveStartTime;

    // 请假结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone="GMT+8")
    @TableField(value = "leave_end_time")
    private Date leaveEndTime;

    // 请假事由
    @TableField(value = "leave_reason")
    private String leaveReason;

    // 请假证明材料
    @TableField(value = "leave_material")
    private String leaveMaterial;

    // 请假表单当前状态
    @TableField(value = "status")
    private String status;

    // 部门审核状态
    @TableField(value = "department_status")
    private String departmentStatus;

    // 人事处审核状态
    @TableField(value = "hr_status")
    private String hrStatus;

    // 校领导审核状态
    @TableField(value = "school_status")
    private String schoolStatus;

    // 前端根据用户权限显示的请假表单状态（已完成/未完成/未流经）
    @TableField(exist = false)
    private String showStatus;

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
