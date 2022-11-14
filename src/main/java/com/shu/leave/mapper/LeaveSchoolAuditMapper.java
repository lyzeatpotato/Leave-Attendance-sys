package com.shu.leave.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.LeaveSchoolAudit;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface LeaveSchoolAuditMapper extends BaseMapper<LeaveSchoolAudit> {
    //学校领导审核
    @Insert({
            "insert into leave_school_audit",
            "(formid,sc_leader_id,sc_leader_result,sc_leader_recommend,sc_leader_time,sc_leader_status,is_deleted,gmt_create,gmt_modified)",
            "values (#{formId,jdbcType=BIGINT},#{scLeaderId,jdbcType=VARCHAR},#{scLeaderResult,jdbcType=VARCHAR},#{scLeaderRecommend,jdbcType=VARCHAR}, #{scLeaderTime,jdbcType=TIMESTAMP},#{scLeaderStatus,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    void addSchoolAudit(LeaveSchoolAudit leaveSchoolAudit);

    /*
    校领导审核通过与否对应得到leave表的修改
    */
    @Update({
            "update leave",
            "set status='1',school_status='1',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void scLeaderAudityy(Long id, Date time);
    @Update({
            "update leave",
            "set status='2',school_status='1',gmt_modified=#{time,jdbcType=TIMESTAMP}",
            "where id=#{id,jdbcType=BIGINT}"
    })
    void scLeaderAuditnn(Long id,Date time);

}
