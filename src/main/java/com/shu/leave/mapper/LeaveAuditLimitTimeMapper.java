package com.shu.leave.mapper;

import com.shu.leave.entity.LeaveAuditLimitTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeaveAuditLimitTimeMapper {

    /**
     * 查询系统默认的各项请假事项的最长时限
     * @return 时限列表
     */
    @Select("select id, type, limit_time from leave_audit_limit_time")
    List<LeaveAuditLimitTime> selectAllBySystem();
}
