package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

@Data
@TableName("school_department")
public class SchoolDepartment {
    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 当前校领导id
    @TableField(value = "school_leader_id")
    private String school_leader_id;

    // 校领导用户详细信息
    @Ignore
    private User leaderUser;

    // 当前校领导分管部门名称
    @TableField(value = "department")
    private String department;
}
