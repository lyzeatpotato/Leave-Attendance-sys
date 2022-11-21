package com.shu.leave.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public interface AuditService {
    @Transactional
    int singleLeaveAudit(String role, String userid, Long id, String result, String recommend);

    /**
     * 分页显示当前登录用户可查看的请假信息
     * @param page
     * @param userId
     * @return 当前登录用户权限所对应的请假信息列表
     */
    Page<Leave> getAuditDataListByUserid(Page<Leave> page, String userId);
}
