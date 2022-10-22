package com.shu.leave.mapper;

import com.shu.leave.entity.Leave;
import com.shu.leave.vo.SingleLeaveStepVo;
import com.shu.leave.vo.SingleLeaveVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import javax.print.DocFlavor;
import java.util.Date;

@Mapper
public interface LeaveMapper {

    /**
     * 新增一条请假申请表单数据（插入除id以外完整的用户信息）
     * @param leave
     * @return 新增请假申请表的主键id值
     */
    @Insert({
            "insert into leave",
            "(userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, status,",
            " department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified)",
            "values (#{userId,jdbcType=VARCHAR}, #{leaveType,jdbcType=VARCHAR}, #{leaveStartTime,jdbcType=VARCHAR}, #{leaveEndTime,jdbcType=VARCHAR},",
            "#{leaveReason,jdbcType=VARCHAR}, #{leaveMaterial,jdbcType=VARCHAR}, #{status,jdbcType=CHAR},",
            "#{departmentStatus,jdbcType=CHAR}, #{hrStatus,jdbcType=CHAR}, #{schoolStatus,jdbcType=CHAR},",
            "#{isDeleted,jdbcType=CHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, #{gmtModified,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT SCOPE_IDENTITY();", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Leave leave);

    /**
     * 查询单个信息
     */
    SingleLeaveVo selectSingleLeave(@Param("yuanxi") String yuanxi,@Param("id") long id);
    /*
    查询步骤信息,五个步骤
     */
    SingleLeaveStepVo electSingleLeaveStepOne(@Param("role") String role,@Param("id") long id);
    SingleLeaveStepVo electSingleLeaveStepTwo(@Param("role") String role,@Param("id") long id);
    SingleLeaveStepVo electSingleLeaveStepThree(@Param("role") String role,@Param("id") long id);
    SingleLeaveStepVo electSingleLeaveStepFour(@Param("role") String role,@Param("id") long id);
    SingleLeaveStepVo electSingleLeaveStepFive(@Param("role") String role,@Param("id") long id);
    /*
    审核过程定义
     */
    void dpOfficerAudit(@Param("userid") String userid, @Param("id") long id, @Param("result") String result, @Param("recommend") String recommend, @Param("time") String time);
    void dpLeaderAudit(@Param("userid") String userid,@Param("id") long id,@Param("result") String result,@Param("recommend") String recommend, @Param("time")String  time);
    void hrOfficerAudit(@Param("userid") String userid,@Param("id") long id,@Param("result") String result,@Param("recommend") String recommend, @Param("time")String time);
    void hrLeaderAudit(@Param("userid") String userid,@Param("id") long id,@Param("result") String result,@Param("recommend") String recommend, @Param("time")String time);
    void scLeaderAudit(@Param("userid") String userid,@Param("id") long id,@Param("result") String result,@Param("recommend") String recommend, @Param("time")String time);
    /*
    部门人事审核
     */
    void dpOfficerAudity(@Param("id") long id,@Param("time") String time);
    void dpOfficerAuditn(@Param("id") long id,@Param("time") String time);
    /*
    部门领导审核
     */
    void dpLeaderAudity(@Param("id") long id,@Param("time") String time);
    void dpLeaderAuditn(@Param("id") long id,@Param("time") String time);
    /*
    人事处人事审核
     */
    void hrOfficerAudity(@Param("id") long id,@Param("time") String time);
    void hrOfficerAuditn(@Param("id") long id,@Param("time") String time);
    /*
    人事处领导审核
     */
    void hrLeaderAudity(@Param("id") long id,@Param("time") String time);
    void hrLeaderAuditn(@Param("id") long id,@Param("time") String time);
    /*
    学校领导审核
     */
    void scLeaderAudity(@Param("id") long id,@Param("time") String time);
    void scLeaderAuditn(@Param("id") long id,@Param("time") String time);
}
