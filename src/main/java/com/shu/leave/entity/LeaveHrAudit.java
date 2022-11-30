package com.shu.leave.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave_hr_audit")
public class LeaveHrAudit {

    //主键，自增，唯一标识id
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //连接请假表单，表单id
    @TableField(value = "formid")
    private Long formId;

    //人事处人事干事工号
    private String hrOfficerId;

    //人事处人事干事初审结果(通过/不通过)
    private String hrOfficerResult;

    //人事处人事干事审核意见
    private String hrOfficerRecommend;

    //人事处人事干事审核时间
    private Date hrOfficerTime;

    //人事处人事干事审核完成情况(0表示未完成，1表示已完成)
    private String hrOfficerStatus;

    //人事处负责人工号
    private String hrLeaderId;

    //人事处负责人审核结果(通过/不通过)
    private String hrLeaderResult;

    //人事处负责人审核意见
    private String hrLeaderRecommend;

    //人事处负责人审核时间
    private Date hrLeaderTime;

    //人事处负责人审核完成情况(0表示未完成，1表示已完成)
    private String hrLeaderStatus;

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
