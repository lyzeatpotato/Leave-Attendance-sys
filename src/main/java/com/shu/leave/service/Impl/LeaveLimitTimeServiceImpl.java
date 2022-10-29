package com.shu.leave.service.Impl;

import com.shu.leave.entity.LeaveAuditLimitTime;
import com.shu.leave.mapper.LeaveAuditLimitTimeMapper;
import com.shu.leave.service.LeaveLimitTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
