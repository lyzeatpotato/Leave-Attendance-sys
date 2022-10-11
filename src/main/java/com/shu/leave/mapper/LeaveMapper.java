package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leave.entity.Leave;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

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
    int insert(Leave leave);

    /**
     * 查询全部请假申请表
     * @return
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
    Leave selectById(Long id);

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
    })
    List<Leave> selectByUserid(Long userid);
}
