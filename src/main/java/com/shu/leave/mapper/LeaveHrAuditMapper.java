package com.shu.leave.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.LeaveHrAudit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

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
    void hrOfficerAudityy(Long id,Date time);
    @Update({
            "update leave",
            "set status='2',hr_status='1',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrOfficerAuditnn(Long id,Date time);

    /*
    人事处负责人审核通过与否对应得到leave表的修改
     */
    @Update({
            "update leave",
            "set status='0',hr_status='1',school_status='0',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrLeaderAudityy(Long id,Date time);
    @Update({
            "update leave",
            "set status='2',hr_status='1',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void hrLeaderAuditnn(Long id,Date time);

    /**
     * 根据请假表主键查询人事处审核详情
     * @author liyuanzhe
     * @date 2022/11/24 14:01
     * @param formId
     * @return LeaveDepartmentAudit对象
     */
    @Select({
            "select id,formid,hr_officer_id,hr_officer_result,hr_officer_recommend,hr_officer_time,hr_officer_status,",
            "hr_leader_id,hr_leader_result,hr_leader_recommend,hr_leader_time,hr_leader_status,is_deleted,gmt_create,gmt_modified",
            "from leave_hr_audit",
            "where formid=#{formId,jdbcType=BIGINT} and is_deleted=0"
    })
    @Results(id = "hrResultMapper", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "formid", property = "formId", jdbcType = JdbcType.BIGINT),
            @Result(column = "hr_officer_id", property = "hrOfficerId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_officer_result", property = "hrOfficerResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_officer_recommend", property = "hrOfficerRecommend", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_officer_time", property = "hrOfficerTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hr_officer_status", property = "hrOfficerStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_leader_id", property = "hrLeaderId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_leader_result", property = "hrLeaderResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_leader_recommend", property = "hrLeaderRecommend", jdbcType = JdbcType.VARCHAR),
            @Result(column = "hr_leader_time", property = "hrLeaderTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hr_leader_status", property = "hrLeaderStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
    })
    LeaveHrAudit findHrAuditMsgByFormId(Long formId);
}
