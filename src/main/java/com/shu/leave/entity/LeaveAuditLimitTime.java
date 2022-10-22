package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("leave_audit_limit_time")
public class LeaveAuditLimitTime {
    // 主键id 自增
    private Long id;

    //对应请假类型 事假、病假、婚嫁、产假、陪产假、丧假、因公出差、工伤假
    private String type;

    //最长时间（-1表示没有限制）
    private Integer limitTime;

}
