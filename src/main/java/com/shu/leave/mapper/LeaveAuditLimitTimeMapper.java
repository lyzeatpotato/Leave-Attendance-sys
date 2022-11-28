package com.shu.leave.mapper;

import com.shu.leave.entity.LeaveAuditLimitTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LeaveAuditLimitTimeMapper {

    /**
     * author：王仕杰
     * 查询系统默认的各项请假事项的最长时限
     * @return 时限列表
     */
    @Select("select id, type, limit_time from leave_audit_limit_time where role_id = 1")
    List<LeaveAuditLimitTime> selectAllBySystem();

    /**
     * author：王仕杰
     * 按照role_id来查询各项请假事项的最长时限
     * @return 对应时限列表
     */
    @Select("select id, role_id, type, limit_time from leave_audit_limit_time where role_id = #{roleid,jdbcType=VARCHAR}")
    List<LeaveAuditLimitTime> selectAllByRoleId(Long roleid);

    /**
     * author：王仕杰
     * 按照id来修改各项请假事项的最长时限
     * @return 对应时限列表
     */
    @Update("update leave_audit_limit_time set limit_time =  #{limitTime,jdbcType=VARCHAR} where id = #{id,jdbcType=VARCHAR} AND type=#{type,jdbcType=VARCHAR}")
    int updateLimitTimeById(LeaveAuditLimitTime leaveAuditLimitTime);


    /**根据角色id和请假类型查询最长时限
     * @author 谢煜颖
     * @param roleId 角色id，系统审核、HR审核、School审核
     * @param leaveType 请假类型
     * @return
     */
    @Select({
            "select limit_time from leave_audit_limit_time ",
            "where role_id=#{roleId,jdbcType=BIGINT} and type=#{leaveType,jdbcType=VARCHAR}"
    })
    int selectByRoleAndType(Long roleId, String leaveType);
}
