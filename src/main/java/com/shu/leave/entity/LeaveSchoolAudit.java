package com.shu.leave.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leave_school_audit")
public class LeaveSchoolAudit {

    //主键，自增，唯一标识id
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    //连接请假表单，表单id
    @TableField(value = "formid")
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


