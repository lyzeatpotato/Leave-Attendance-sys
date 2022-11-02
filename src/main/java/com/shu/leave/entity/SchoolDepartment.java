package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school_department")
public class SchoolDepartment {
    // 主键id 自增
    private Long id;

    // 当前校领导id
    private String school_leader_id;

    // 当前校领导分管部门名称
    private String department;
}
