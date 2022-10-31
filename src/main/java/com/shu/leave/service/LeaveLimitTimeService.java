package com.shu.leave.service;

import com.shu.leave.entity.LeaveAuditLimitTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveLimitTimeService {

    /**
     * 查询系统默认的请假类型最大天数
     * @return 对应时限列表
     */
    List<LeaveAuditLimitTime> findAllLimitTimeBySystem();

    /**
     * author: 王仕杰
     * 按role_id来查询各请假类型最大天数
     * @return 对应时限列表
     */
    List<LeaveAuditLimitTime> findAllLimitTimeByRoleId(String roleId);

    /**
     * author: 王仕杰
     * 按id来修改各请假类型最大天数
     * @return 对应时限列表
     */
    int updateLimitTimeById(String[] params);
}
