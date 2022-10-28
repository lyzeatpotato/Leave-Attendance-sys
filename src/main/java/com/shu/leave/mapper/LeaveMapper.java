package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import com.shu.leave.vo.SingleLeaveStepVo;
import com.shu.leave.vo.SingleLeaveVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import javax.print.DocFlavor;
import java.util.Date;

@Mapper
public interface LeaveMapper extends BaseMapper<Leave> {

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
    int addLeave(Leave leave);

    /**
     * 查询全部请假申请表
     * @return 请假表单信息
     */
    @Select({
            "select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material,",
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified",
            "from leave where is_deleted=0"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_type", property="leaveType", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_start_time", property="leaveStartTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_end_time", property="leaveEndTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_reason", property="leaveReason", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_material", property="leaveMaterial", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="school_status", property="schoolStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
    })
    List<Leave> selectAllLeave();

    /**
     * 根据id查询请假申请表详细信息
     * @param id
     * @return id对应申请表的详细信息
     */
    @Select({
            "select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material,",
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified",
            "from leave where id=#{id,jdbcType=BIGINT} and is_deleted=0"
    })
    Leave findById(Long id);

    /**
     * 根据教师id查询其对应的请假申请表信息
     * @param userid
     * @return 当前id的教师对应的全部请假申请表
     */
    @Select({
            "select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material,",
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified",
            "from leave where userid=#{userid,jdbcType=BIGINT} and is_deleted=0"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_type", property="leaveType", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_start_time", property="leaveStartTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_end_time", property="leaveEndTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_reason", property="leaveReason", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_material", property="leaveMaterial", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="school_status", property="schoolStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
            @Result(
                    column = "userid",property = "user",  javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUserid(Long userid);

    /**
     * 按部门查询: 查询某一部门下的全部请假记录
     * @param department
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR}")
    @Results(id = "leaveDeptRelatedMapper", value = {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_type", property="leaveType", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_start_time", property="leaveStartTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_end_time", property="leaveEndTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_reason", property="leaveReason", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_material", property="leaveMaterial", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="school_status", property="schoolStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
            @Result(
                    column = "userid",property = "user",  javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUserDept(String department);

    /**
     * 按部门条件查询: 查询某一部门下需要人事处审核，但是尚未完成的全部请假记录（根据hr_status字段）
     * @param department
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} " +
            "and leave.hr_status = 0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectByUserDeptAndUnfinishedHR(String department);

    /**
     * 按部门条件查询: 查询某一部门下需要校领导审核，但是尚未完成的全部请假记录（根据school_status字段）
     * @param department
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} " +
            "and leave.school_status = 0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectByUserDeptAndUnfinishedSchool(String department);

    /**
     * 全校范围内部门审核已完成，但人事处未审核的请假表单信息
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and leave.department_status = 1 "+
            "and leave.hr_status = 0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectAllByUnfinishedHR();

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
    /**
     * liugai
     * 根据教师名字查询其对应的请假申请表信息
     * @param username
     * @return 当前username的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " + " and user_info.username=#{username,jdbcType=VARCHAR} "+"and leave.is_deleted=0"
    })
    @Results( {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="userid", property="userId", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_type", property="leaveType", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_start_time", property="leaveStartTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_end_time", property="leaveEndTime", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_reason", property="leaveReason", jdbcType=JdbcType.VARCHAR),
            @Result(column="leave_material", property="leaveMaterial", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="school_status", property="schoolStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType= JdbcType.TIMESTAMP),
            @Result(
                    column = "userid",property = "user",  javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUsername(String username);
}
