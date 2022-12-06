package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: RevokeHrAudit
 * @Description: 销假申请人事处审核实体类
 * @author: lyz
 * @date: 2022 12 2022/12/6 20:54
 */

@TableName(value = "revoke_hr_audit")
@Data
public class RevokeHrAudit {

    // 主键 自增 唯一标识id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 外键 连接请假申请表-表单id
    @TableField(value = "revoke_formid")
    private Long revokeFormId;

    // 人事处负责人工号
    @TableField(value = "hr_leader_id")
    private String hrLeaderId;

    // 人事处负责人审核结果（通过/不通过）
    @TableField(value = "hr_leader_result")
    private String hrLeaderResult;

    // 人事处负责人审核意见
    @TableField(value = "hr_leader_recommend")
    private String hrLeaderRecommend;

    // 人事处负责人审核时间
    @TableField(value = "hr_leader_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date hrLeaderTime;

    // 人事处负责人审核完成情况(0表示未完成，1表示已完成)
    @TableField(value = "hr_leader_status")
    private String hrLeaderStatus;

    // 逻辑删除
    @TableField(value = "is_deleted")
    @TableLogic(delval = "0")
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
