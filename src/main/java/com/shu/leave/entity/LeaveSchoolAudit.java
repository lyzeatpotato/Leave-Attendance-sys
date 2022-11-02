package com.shu.leave.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave_school_audit")
public class LeaveSchoolAudit {
    //主键，自增，唯一标识id
    private Long id;

    //连接请假表单，表单id
    private Long formId;

    //校领导工号
    private String scLeaderId;

    //校领导审核结果(通过/不通过)
    private String scLeaderResult;

    //校领导审核意见
    private String scLeaderRecommend;

    //校领导审核时间
    private Date scLeaderTime;

    //校领导审核完成情况(0表示未完成，1表示已完成)
    private String scLeaderStatus;

    //逻辑删除字段（1表示删除，0表示未删除）
    private String isDeleted;

    //创建时间
    private Date gmtCreate;

    //修改时间
    private Date gmtModified;
}


