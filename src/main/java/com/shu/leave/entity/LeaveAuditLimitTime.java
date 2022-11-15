package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave_audit_limit_time")
public class LeaveAuditLimitTime {

    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 外键 连接leave_audit_role， 明确对应那种审核下的限制
    @TableField(value = "role_id")
    private Long roleId;

    // 外键 连接系统管理员表-进行修改的系统管理员id
    @TableField(value = "admin_id")
    private Long adminId;

    //对应请假类型 事假、病假、婚嫁、产假、陪产假、丧假、因公出差、工伤假
    @TableField(value = "type")
    private String type;

    //最长时间（-1表示没有限制）
    @TableField(value = "limit_time")
    private Integer limitTime;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    // 修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_modified")
    private Date gmtModified;

}
