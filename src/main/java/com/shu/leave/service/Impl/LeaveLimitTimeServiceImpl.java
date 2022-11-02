package com.shu.leave.service.Impl;

import com.shu.leave.entity.LeaveAuditLimitTime;
import com.shu.leave.mapper.LeaveAuditLimitTimeMapper;
import com.shu.leave.service.LeaveLimitTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * author：王仕杰
 */
@Service
public class LeaveLimitTimeServiceImpl implements LeaveLimitTimeService {
    @Resource
    LeaveAuditLimitTimeMapper leaveAuditLimitTimeMapper;

    @Override
    public List<LeaveAuditLimitTime> findAllLimitTimeBySystem() {
        return leaveAuditLimitTimeMapper.selectAllBySystem();
    }

    @Override
    public List<LeaveAuditLimitTime> findAllLimitTimeByRoleId(String roleId) {
        return leaveAuditLimitTimeMapper.selectAllByRoleId(Long.parseLong(roleId));
    }

    @Override
    public int updateLimitTimeById(String[] params) {
        LeaveAuditLimitTime leaveAuditLimitTime = new LeaveAuditLimitTime();
        leaveAuditLimitTime.setId(Long.parseLong(params[0]));
        leaveAuditLimitTime.setType(params[1]);
        leaveAuditLimitTime.setLimitTime(Integer.parseInt(params[2]));
        return leaveAuditLimitTimeMapper.updateLimitTimeById(leaveAuditLimitTime);
    }

    @Override
    public int getLimitTimeByRoleIdAndLeaveType(Long roleId, String leaveType) {
        return leaveAuditLimitTimeMapper.selectByRoleAndType(roleId, leaveType);
    }
}
