package com.shu.leave.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Leave {

    // 主键id 自增
    private Long id;

    // 提交信息表的用户id
    private Long userId;

    // 请假类型
    private String leaveType;

    // 请假起始时间
    private Date leaveStartTime;

    // 请假结束时间
    private Date leaveEndTime;

    // 请假事由
    private String leaveReason;

    // 请假证明材料
    private String leaveMaterial;

    // 请假表单当前状态
    private String status;

    // 部门审核状态
    private String departmentStatus;

    // 人事处审核状态
    private String hrStatus;

    // 校领导审核状态
    private String schoolStatus;

    // 逻辑删除
    private String isDeleted;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;
}
