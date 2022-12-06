package com.shu.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.Revoke;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RevokeMapper extends BaseMapper<Revoke> {

    /**
     * 根据用户主键查询当前用户的全部销假记录
     * @author liyuanzhe
     * @param userId
     * @return 销假记录列表
     */
    @Select({
            "select tb_revoke.id,tb_revoke.formid,tb_revoke.revoke_submit_time,tb_revoke.revoke_report_time,",
            "tb_revoke.status,tb_revoke.department_status,tb_revoke.hr_status,tb_revoke.is_deleted,tb_revoke.is_deleted,tb_revoke.gmt_create,tb_revoke.gmt_modified",
            "from leave,tb_revoke",
            "where (leave.id = tb_revoke.formid and leave.userid = #{userId,jdbcType=BIGINT} and tb_revoke.is_deleted = 0 and tb_revoke.status != 3)",
            "ORDER BY tb_revoke.id DESC"
    })
    @Results(id = "RevokeMapper", value = {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="formid", property="formId", jdbcType=JdbcType.BIGINT),
            @Result(column="revoke_submit_time", property="revokeSubmitTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="revoke_report_time", property="revokeReportTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType= JdbcType.TIMESTAMP),
            @Result(
                    column = "formid", property = "leave",  javaType = Leave.class,
                    one = @One(select = "com.shu.leave.mapper.LeaveMapper.findById")
            )
    })
    Page<Revoke> findAllRevokeListByUserId(Page<Revoke> page, Long userId);

    /**
     * 根据销假申请表主键获取销假申请校庆
     * @author liyuanzhe
     * @date 2022/12/6 20:40
     * @param revokeId
     * @return Revoke
     */
    @Select({
            "select tb_revoke.id,tb_revoke.formid,tb_revoke.revoke_submit_time,tb_revoke.revoke_report_time,",
            "tb_revoke.status,tb_revoke.department_status,tb_revoke.hr_status,tb_revoke.is_deleted,tb_revoke.is_deleted,tb_revoke.gmt_create,tb_revoke.gmt_modified",
            "from leave,tb_revoke",
            "where (leave.id = tb_revoke.formid and tb_revoke.id = #{revokeId,jdbcType=BIGINT})"
    })
    @ResultMap("RevokeMapper")
    Revoke findRevokeDetailById(Long revokeId);

    /**
     * 分页查询“部门审核员用户”初始加载的全部本部门销假信息
     * @param page
     * @param department
     * @param userId
     * @return 包含{user.leave.revoke}信息的实体类List
     */
    @Select({
            "select tb_revoke.id,tb_revoke.formid,tb_revoke.revoke_submit_time,tb_revoke.revoke_report_time,",
            "tb_revoke.status,tb_revoke.department_status,tb_revoke.hr_status,tb_revoke.is_deleted,tb_revoke.is_deleted,tb_revoke.gmt_create,tb_revoke.gmt_modified",
            "from leave,tb_revoke,user_info",
            "where (leave.id = tb_revoke.formid and leave.userid = user_info.id and ",
            "tb_revoke.status = 0 and tb_revoke.is_deleted = 0 and",
            "user_info.yuanxi = #{department, jdbcType=VARCHAR} and leave.userid != #{userId, jdbcType=BIGINT})",
            "ORDER BY leave.id DESC"
    })
    @Results(id = "RevokeComplexMapper", value = {
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="formid", property="formId", jdbcType=JdbcType.BIGINT),
            @Result(column="revoke_submit_time", property="revokeSubmitTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="revoke_report_time", property="revokeReportTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
            @Result(column="department_status", property="departmentStatus", jdbcType=JdbcType.CHAR),
            @Result(column="hr_status", property="hrStatus", jdbcType=JdbcType.CHAR),
            @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.CHAR),
            @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="gmt_modified", property="gmtModified", jdbcType= JdbcType.TIMESTAMP),
            @Result(
                    column = "formid", property = "leave",  javaType = Leave.class,
                    one = @One(select = "com.shu.leave.mapper.LeaveMapper.findLeaveWithUserById")
            )
    })
    Page<Revoke> selectPageByDeptUser(Page<Revoke> page, String department, Long userId);

    /**
     * 分页查询“人事处、校领导审核员用户”初始加载的全校范围销假信息
     * @author liyuanzhe
     * @date 2022/12/6 22:27
     * @param page
     * @param userId
     * @return Page<Revoke>
     */
    @Select({
            "select tb_revoke.id,tb_revoke.formid,tb_revoke.revoke_submit_time,tb_revoke.revoke_report_time,",
            "tb_revoke.status,tb_revoke.department_status,tb_revoke.hr_status,tb_revoke.is_deleted,tb_revoke.is_deleted,tb_revoke.gmt_create,tb_revoke.gmt_modified",
            "from leave,tb_revoke,user_info",
            "where (leave.id = tb_revoke.formid and leave.userid = user_info.id and ",
            "tb_revoke.status = 0 and tb_revoke.is_deleted = 0 and tb_revoke.department_status = 1 and ",
            "tb_revoke.hr_status != 2 and leave.userid != #{userId, jdbcType=BIGINT})",
            "ORDER BY leave.id DESC"
    })
    @ResultMap("RevokeComplexMapper")
    Page<Revoke> selectPageByHrAndSchoolUser(Page<Revoke> page, Long userId);
}
