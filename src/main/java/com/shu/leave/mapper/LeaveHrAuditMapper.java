package com.shu.leave.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.LeaveHrAudit;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface LeaveHrAuditMapper extends BaseMapper<LeaveHrAudit> {
    //人事处人事审核
    @Insert({
            "insert into leave_hr_audit",
            "(formid,hr_officer_id,hr_officer_result,hr_officer_recommend,hr_officer_time,hr_officer_status,hr_leader_id,hr_leader_result,hr_leader_recommend,hr_leader_time,hr_leader_status,is_deleted,gmt_create,gmt_modified)",
            "values (#{formId,jdbcType=BIGINT},#{hrOfficerId,jdbcType=VARCHAR},#{hrOfficerResult,jdbcType=VARCHAR},#{hrOfficerRecommend,jdbcType=VARCHAR}, #{hrOfficerTime,jdbcType=TIMESTAMP},#{hrOfficerStatus,jdbcType=CHAR},",
            "#{hrLeaderId,jdbcType=VARCHAR},#{hrLeaderResult,jdbcType=VARCHAR},#{hrLeaderRecommend,jdbcType=VARCHAR}, #{hrLeaderTime,jdbcType=TIMESTAMP},#{hrLeaderStatus,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    void addLeaveHrAudit(LeaveHrAudit leaveHrAudit);

    //人事处负责人审核
    @Update({
            "update leave_hr_audit",
            "set hr_leader_id=#{userid,jdbcType=VARCHAR},hr_leader_result=#{result,jdbcType=VARCHAR},hr_leader_recommend=#{recommend,jdbcType=VARCHAR},hr_leader_time=#{time,jdbcType=TIMESTAMP},hr_leader_status='1',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where leave_hr_audit.formid=#{id,jdbcType=BIGINT}"
    })
    void updateLeaveHrAudit(String userid,Long id,String result, String recommend, Date time);

    /*
    人事处科员审核通过与否对应得到leave表的修改
    */
    @Update({
            "update leave",
            "set status='0',hr_status='3',school_status='0',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrOfficerAudity(Long id,Date time);
    @Update({
            "update leave",
            "set status='2',hr_status='1',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrOfficerAuditn(Long id,Date time);

    /*
    人事处负责人审核通过与否对应得到leave表的修改
     */
    @Update({
            "update leave",
            "set status='0',hr_status='1',school_status='0',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrLeaderAudity(Long id,Date time);
    @Update({
            "update leave",
            "set status='2',hr_status='1',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrLeaderAuditn(Long id,Date time);
}
