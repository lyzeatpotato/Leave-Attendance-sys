package com.shu.leave.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.LeaveDepartmentAudit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@Mapper
public interface LeaveDepartmentAuditMapper extends BaseMapper<LeaveDepartmentAudit> {
    //部门人事审核
    @Insert({
            "insert into leave_department_audit",
            "(formid,dp_officer_id,dp_officer_result,dp_officer_recommend,dp_officer_time,dp_officer_status,dp_leader_id,dp_leader_result,dp_leader_recommend,dp_leader_time,dp_leader_status,is_deleted,gmt_create,gmt_modified)",
            "values (#{formId,jdbcType=BIGINT},#{dpOfficerId,jdbcType=VARCHAR},#{dpOfficerResult,jdbcType=VARCHAR},#{dpOfficerRecommend,jdbcType=VARCHAR}, #{dpOfficerTime,jdbcType=TIMESTAMP},#{dpOfficerStatus,jdbcType=CHAR},",
            "#{dpLeaderId,jdbcType=VARCHAR},#{dpLeaderResult,jdbcType=VARCHAR},#{dpLeaderRecommend,jdbcType=VARCHAR}, #{dpLeaderTime,jdbcType=TIMESTAMP},#{dpLeaderStatus,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    void addLeaveDepartmentAudit(LeaveDepartmentAudit leaveDepartmentAudit);

    //部门负责人审核
    @Update({
            "update leave_department_audit",
            "set dp_leader_id=#{userid,jdbcType=VARCHAR},dp_leader_result=#{result,jdbcType=VARCHAR},dp_leader_recommend=#{recommend,jdbcType=VARCHAR},dp_leader_time=#{time,jdbcType=TIMESTAMP},dp_leader_status='1',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where formid=#{id,jdbcType=BIGINT}"
    })
    void updateLeaveDepartmentAudit(String userid,Long id,String result,String recommend,Date time);

    /*
    部门人事审核通过与否对应得到leave表的修改
     */
    @Update({
            "update leave",
            "set status='0',department_status='3',hr_status='0',school_status='0',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT} and 1=1"
    })
    void dpOfficerAudityy(Long id,Date time);


    @Update({
            "update leave",
            "set status='2',department_status='1',hr_status='2',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void dpOfficerAuditnn(Long id,Date time);

    /*
    部门负责人审核通过与否对应得到leave表的修改
     */
    @Update({
            "update leave",
            "set status='0',department_status='1',hr_status='0',school_status='0',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void dpLeaderAudityy(Long id,Date time);
    @Update({
            "update leave",
            "set status='2',department_status='1',hr_status='2',school_status='2',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void dpLeaderAuditnn(Long id,Date time);

    /**
     * 根据请假表主键查询部门审核详情
     * @author liyuanzhe
     * @date 2022/11/24 14:01
     * @param formId
     * @return LeaveDepartmentAudit对象
     */
    @Select({
            "select id,formid,dp_officer_id,dp_officer_result,dp_officer_recommend,dp_officer_time,dp_officer_status,dp_leader_id,dp_leader_result,dp_leader_recommend,dp_leader_time,dp_leader_status,is_deleted,gmt_create,gmt_modified",
            "from leave_department_audit",
            "where formid=#{formId,jdbcType=BIGINT} and is_deleted=0"
    })
    @Results(id = "deptResultMapper", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "formid", property = "formId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dp_officer_id", property = "dpOfficerId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_officer_result", property = "dpOfficerResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_officer_recommend", property = "dpOfficerRecommend", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_officer_time", property = "dpOfficerTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "dp_officer_status", property = "dpOfficerStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "dp_leader_id", property = "dpLeaderId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_leader_result", property = "dpLeaderResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_leader_recommend", property = "dpLeaderRecommend", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dp_leader_time", property = "dpLeaderTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "dp_leader_status", property = "dpLeaderStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
    })
    LeaveDepartmentAudit findDeptAuditMsgByFormId(Long formId);
}
