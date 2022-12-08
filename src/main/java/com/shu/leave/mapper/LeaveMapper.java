package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

import com.shu.leave.vo.SingleLeaveStepVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

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
     * 查询全部请假信息表
     * @author liyuanzhe
     * @date 2022/12/1 10:51
     * @return List<Leave>
     */
    @Select({
            "select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material,",
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified",
            "from leave where is_deleted=0"
    })
    @Results(id = "leaveEasyMapper", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
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
    @ResultMap(value = "leaveEasyMapper")
    Leave findById(Long id);

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
    @ResultMap(value = "leaveDeptRelatedMapper")
    Leave findLeaveWithUserById(Long id);

    /**
     * 根据教师id查询其对应的请假申请表信息
     *
     * @param userid
     * @return 当前id的教师对应的全部请假申请表
     */
    @Select({
            "select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material,",
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified",
            "from leave where userid=#{userid,jdbcType=BIGINT} and is_deleted=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
    })
    List<Leave> selectByUserid(Long userid);

    /**
     * 按部门查询: 查询某一部门下的全部请假记录
     *
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
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUserDept(String department);

    /**
     * 按部门条件查询: 查询某一部门下需要人事处审核，但是尚未完成的全部请假记录（根据hr_status字段）
     *
     * @param department
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} " +
            "and leave.hr_status != 2" + " and leave.status=0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectByUserDeptAndUnfinishedHR(String department);

    /**
     * 部门条件查询: 查询某一部门下需要校领导审核，但是尚未完成的全部请假记录（根据school_status字段）
     *
     * @param department
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} " +
            "and leave.school_status != 2" + " and leave.status=0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectByUserDeptAndUnfinishedSchool(String department);

    /**
     * 全校范围内部门审核已完成，但人事处未审核的请假表单信息
     *
     * @return 返回请假列表
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time," +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE leave.userid = user_info.id " +
            "and leave.department_status = 1 " +
            "and leave.hr_status = 0")
    @ResultMap(value = "leaveDeptRelatedMapper")
    List<Leave> selectAllByUnfinishedHR();

    int insert(Leave leave);


    /**
     * 查询单个信息的实现方式
     *
     * @param id
     * @return
     * @author zhangshao
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + " and leave.id=#{id,jdbcType=BIGINT} " + "and leave.is_deleted=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    Leave selectSingleLeave(@Param("id") Long id);
    /*
     * 查询单个信息
     */
    //SingleLeaveVo selectSingleLeave(@Param("yuanxi") String yuanxi, @Param("id") Long id);

    /*
    查询步骤信息,五个步骤
     */
    SingleLeaveStepVo electSingleLeaveStepOne(@Param("role") String role, @Param("id") Long id);

    SingleLeaveStepVo electSingleLeaveStepTwo(@Param("role") String role, @Param("id") Long id);

    SingleLeaveStepVo electSingleLeaveStepThree(@Param("role") String role, @Param("id") Long id);

    SingleLeaveStepVo electSingleLeaveStepFour(@Param("role") String role, @Param("id") Long id);

    SingleLeaveStepVo electSingleLeaveStepFive(@Param("role") String role, @Param("id") Long id);

    /**
     * liugai
     * 根据教师名字查询其对应的请假申请表信息
     *
     * @param username
     * @return 当前username的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + " and user_info.username=#{username,jdbcType=VARCHAR} " + "and leave.is_deleted=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUsername(String username);


    /**
     * 根据请假时间范围查询某个用户请假列表
     *
     * @param startTime 请假开始时间
     * @param endTime   请假结束时间
     * @return 请假列表
     * @author xieyuying
     */
    @Select("select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, " +
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified " +
            "from leave where leave_start_time >= #{startTime} and leave_end_time <= #{endTime} " +
            "and userid = #{userId} and is_deleted=0")
    List<Leave> selectByTimePeriod(@Param("userId") Long userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据审核状态查询某个用户请假列表
     *
     * @param status 审核状态
     * @return 请假列表
     * @author xieyuying
     */
    @Select("select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, " +
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified " +
            "from leave where status = #{status}  and userid = #{userId} and is_deleted=0")
    List<Leave> selectByAuditStatus(@Param("userId") Long userId, @Param("status") int status);

    /**
     * 根据请假时间范围和审核状态查询某个用户请假列表
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @param status
     * @return
     * @author xieyuying
     */
    @Select("select id, userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, " +
            "status, department_status, hr_status, school_status, is_deleted, gmt_create, gmt_modified " +
            "from leave where leave_start_time >= #{startTime} and leave_end_time <= #{endTime} " +
            "and status = #{status}  and userid = #{userId} and is_deleted=0")
    List<Leave> selectByTimePeriodAndAuditStatus(@Param("userId") Long userId, @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime, @Param("status") int status);


    /**
     * liugai
     * 需要部门审核的根据id查询其对应的请假申请表信息
     *
     * @param userid
     * @return 当前userid的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + "and user_info.userid=#{userid,jdbcType=BIGINT} " + " and user_info.yuanxi = #{department, jdbcType=VARCHAR}" + " and leave.status= 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUseridInDept(String userid, String department);

    /**
     * liugai
     * 需要部门审核的根据name查询其对应的请假申请表信息
     *
     * @param username
     * @return 当前username的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + "and user_info.username = #{username,jdbcType=VARCHAR} " + " and user_info.yuanxi = #{department, jdbcType=VARCHAR}" + " and leave.status= 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUsernameInDept(String username, String department);

    /**
     * liugai
     * 需要人事处审核的根据id查询其对应的请假申请表信息
     *
     * @param userid
     * @return 当前userid的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + "and user_info.userid=#{userid,jdbcType=BIGINT} " + " and leave.hr_status !=2" + "and leave.status = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUseridInHR(String userid);

    /**
     * liugai
     * 需要人事处审核的根据name查询其对应的请假申请表信息
     *
     * @param username
     * @return 当前username的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + " and user_info.username=#{username,jdbcType=VARCHAR} " + " and leave.hr_status !=2" + "and leave.status = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUsernameInHR(String username);

    /**
     * liugai
     * 需要校领导审核的根据id查询其对应的请假申请表信息
     *
     * @param userid
     * @return 当前userid的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + "and user_info.userid=#{userid,jdbcType=BIGINT} " + " and user_info.yuanxi = #{department, jdbcType=VARCHAR}" + " and leave.school_status !=2" + "and leave.status = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUseridInSchool(String userid, String department);

    /**
     * liugai
     * 需要校领导审核的根据name查询其对应的请假申请表信息
     *
     * @param username
     * @return 当前username的教师对应的全部请假申请表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + "and user_info.username=#{username,jdbcType=VARCHAR}  " + " and user_info.yuanxi = #{department, jdbcType=VARCHAR}" + " and leave.school_status !=2" + "and leave.status = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUsernameInSchool(String username, String department);

    /**
     * liugai
     * 按部门查询: 查询需要部门审核的全部请假记录(根据status状态)
     *
     * @param department
     * @return 返回请假列表
     */
    @Select({
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
                    "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
                    "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
                    "FROM leave, user_info " +
                    "WHERE leave.userid = user_info.id " + " and user_info.yuanxi = #{department, jdbcType=VARCHAR}" + " and leave.status= 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "userid", property = "userId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_type", property = "leaveType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_start_time", property = "leaveStartTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_end_time", property = "leaveEndTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leave_reason", property = "leaveReason", jdbcType = JdbcType.VARCHAR),
            @Result(column = "leave_material", property = "leaveMaterial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "department_status", property = "departmentStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "hr_status", property = "hrStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "school_status", property = "schoolStatus", jdbcType = JdbcType.CHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.CHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.TIMESTAMP),
            @Result(
                    column = "userid", property = "user", javaType = User.class,
                    one = @One(select = "com.shu.leave.mapper.UserMapper.selectById")
            )
    })
    List<Leave> selectByUserDeptCheck(String department);

    /**
     * 分页查询“部门科员”初始加载的全部本部门请假信息
     * @param page
     * @param department
     * @return 权限为“部门科员”用户可查看的“本部门-待审核-部门科员尚未审核”请假信息
     * @Author liyuanzhe
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0 " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} and leave.userid != #{userId, jdbcType=BIGINT}) " +
            "ORDER BY leave.id DESC"
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByDeptOfficer(Page<Leave> page, String department, Long userId);

    /**
     * 分页查询“部门领导”初始加载的全部本部门请假信息
     *
     * @param page
     * @param department
     * @return 权限为“部门领导”用户可查看的“本部门-待审核”请假信息
     * @Author liyuanzhe
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.department_status = 3 and leave.is_deleted = 0 " +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} and leave.userid != #{userId, jdbcType=BIGINT}) " +
            "ORDER BY leave.id DESC"
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByDeptLeader(Page<Leave> page, String department, Long userId);

    /**
     * 分页查询“人事处干事”初始加载的全校请假信息
     *
     * @param page
     * @return 权限为“人事处干事”用户可查看的“部门审核已完成-待审核”请假信息
     * @Author liyuanzhe
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0" +
            "and leave.department_status = 1 and leave.hr_status != 2 and leave.userid != #{userId, jdbcType=BIGINT})" +
            "ORDER BY leave.id DESC"
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByHrOfficer(Page<Leave> page, Long userId);

    /**
     * 分页查询“人事处领导”初始加载的全校请假信息
     *
     * @param page
     * @return 权限为“人事处领导”用户可查看的“部门审核已完成-待审核”请假信息
     * @Author liyuanzhe
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.hr_status = 3 and leave.is_deleted = 0" +
            "and leave.department_status = 1 and leave.hr_status != 2 and leave.userid != #{userId, jdbcType=BIGINT})" +
            "ORDER BY leave.id DESC"
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByHrLeader(Page<Leave> page, Long userId);

    /**
     * 分页查询“校领导”初始加载的全校请假信息
     *
     * @param page
     * @return 权限为“人事处领导”校领导“本部门-人事处审核已完成-待审核”请假信息
     * @Author liyuanzhe
     */
    @Select("SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, " +
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, " +
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified " +
            "FROM leave, user_info " +
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0" +
            "and leave.hr_status = 1  and leave.school_status != 2" +
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} and leave.userid != #{userId, jdbcType=BIGINT}) " +
            "ORDER BY leave.id DESC"
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageBySchool(Page<Leave> page, String department, Long userId);

    /**
     * 多条件复杂分页查询“部门科员”用户的数据
     *
     * @param page
     * @param department
     * @param userId
     * @param userName
     * @return Page<Leave> 权限为“部门科员”用户可查看的“本部门-待审核-部门科员尚未审核”请假信息，可根据“工号/姓名”查询
     * @author liyuanzhe
     * @date 2022/11/22 9:51
     */
    @Select({"<script>",
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0 ",
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} ",
            "<when test='userid!=null'>",
            "AND user_info.userid = #{userid}",
            "</when>",
            "<when test='username!=null'>",
            "AND user_info.username = #{username}",
            "</when>)",
            "ORDER BY leave.id DESC",
            "</script>"}
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByDeptOfficerWrappered(Page<Leave> page, String department, @Param("userid") String userId, @Param("username") String userName);

    /**
     * 多条件复杂分页查询“部门负责人”用户的数据
     *
     * @param page
     * @param department
     * @param userId
     * @param userName
     * @return Page<Leave> 权限为“部门负责人”用户可查看的“本部门-待审核-部门科员尚未审核”请假信息，可根据“工号/姓名”查询
     * @author liyuanzhe
     * @date 2022/11/22 9:52
     */
    @Select({"<script>",
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.department_status = 3 and leave.is_deleted = 0 ",
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} ",
            "<when test='userid!=null'>",
            "AND user_info.userid = #{userid}",
            "</when>",
            "<when test='username!=null'>",
            "AND user_info.username = #{username}",
            "</when>)",
            "ORDER BY leave.id DESC",
            "</script>"}
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByDeptLeaderWrappered(Page<Leave> page, String department, @Param("userid") String userId, @Param("username") String userName);

    /**
     * 多条件复杂分页查询“人事处干事”用户的数据
     *
     * @param page
     * @param userId
     * @param userName
     * @param department
     * @return Page<Leave> 权限为“人事处干事”用户可查看的“部门审核已完成-待审核”请假信息，可根据“工号/姓名/部门”查询
     * @author liyuanzhe
     * @date 2022/11/22 9:53
     */
    @Select({"<script>",
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0",
            "and leave.department_status = 1 and leave.hr_status != 2",
            "<when test='userid!=null'>",
            "AND user_info.userid = #{userid}",
            "</when>",
            "<when test='username!=null'>",
            "AND user_info.username = #{username}",
            "</when>",
            "<when test='department!=null'>",
            "AND user_info.yuanxi = #{department}",
            "</when>)",
            "ORDER BY leave.id DESC",
            "</script>"}
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByHrOfficerWrappered(Page<Leave> page, @Param("userid") String userId, @Param("username") String userName, @Param("department") String department);

    /**
     * 多条件复杂分页查询“人事处领导”用户的数据
     *
     * @param page
     * @param userId
     * @param userName
     * @param department
     * @return Page<Leave> 权限为“人事处领导”用户可查看的“部门审核已完成-待审核”请假信息，可根据“工号/姓名/部门”查询
     * @author liyuanzhe
     * @date 2022/11/22 9:57
     */
    @Select({"<script>",
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.hr_status = 3 and leave.is_deleted = 0",
            "and leave.department_status = 1 and leave.hr_status != 2",
            "<when test='userid!=null'>",
            "AND user_info.userid = #{userid}",
            "</when>",
            "<when test='username!=null'>",
            "AND user_info.username = #{username}",
            "</when>",
            "<when test='department!=null'>",
            "AND user_info.yuanxi = #{department}",
            "</when>)",
            "ORDER BY leave.id DESC",
            "</script>"}
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageByHrLeaderWrappered(Page<Leave> page, @Param("userid") String userId, @Param("username") String userName, @Param("department") String department);

    /**
     * 多条件复杂分页查询“校领导”用户的数据
     *
     * @param page
     * @param department
     * @param userId
     * @param userName
     * @return Page<Leave> 权限为“校领导”用户可查看的“本部门-人事处审核已完成-待审核”请假信息
     * @author liyuanzhe
     * @date 2022/11/22 9:58
     */
    @Select({"<script>",
            "SELECT leave.id, leave.userid, leave.leave_type, leave.leave_start_time, leave.leave_end_time, ",
            "leave.leave_reason, leave.leave_material, leave.status, leave.department_status, ",
            "leave.hr_status, leave.school_status, leave.is_deleted, leave.gmt_create, leave.gmt_modified ",
            "FROM leave, user_info ",
            "WHERE (leave.userid = user_info.id and leave.status = 0 and leave.is_deleted = 0",
            "and leave.hr_status = 1  and leave.school_status != 2",
            "and user_info.yuanxi = #{department, jdbcType=VARCHAR} ",
            "<when test='userid!=null'>",
            "AND user_info.userid = #{userid}",
            "</when>",
            "<when test='username!=null'>",
            "AND user_info.username = #{username}",
            "</when>)",
            "ORDER BY leave.id DESC",
            "</script>"}
    )
    @ResultMap("leaveDeptRelatedMapper")
    Page<Leave> selectPageBySchoolWrappered(Page<Leave> page, String department, @Param("userid") String userId, @Param("username") String userName);

}
