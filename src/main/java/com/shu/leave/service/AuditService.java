package com.shu.leave.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public interface AuditService {
    @Transactional
    int singleLeaveAudit(String role, String userid, Long id, String result, String recommend);
}
