package com.shu.leave.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave_department_audit")
public class    LeaveDepartmentAudit {
    //主键，自增，唯一标识id
    //@TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //连接请假表单，表单id
    private Long formId;

    //部门人事干事工号
    private String dpOfficerId;

    //部门人事干事初审结果(通过/不通过)
    private String dpOfficerResult;

    //部门人事干事审核意见
    private String dpOfficerRecommend;

    //部门人事干事审核时间
    private Date dpOfficerTime;

    //部门人事干事审核完成情况(0表示未完成，1表示已完成)
    private String dpOfficerStatus;

    //部门负责人工号
    private String dpLeaderId;

    //部门负责人审核结果(通过/不通过)
    private String dpLeaderResult;

    //部门负责人审核意见
    private String dpLeaderRecommend;

    //部门负责人审核时间
    private Date dpLeaderTime;

    //部门负责人审核完成情况(0表示未完成，1表示已完成)
    private String dpLeaderStatus;

    //逻辑删除字段（1表示删除，0表示未删除）
    private String isDeleted;

    //创建时间
    private Date gmtCreate;

    //修改时间
    private Date gmtModified;
}
