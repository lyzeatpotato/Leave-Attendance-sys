package com.shu.leave.service;


import org.springframework.stereotype.Service;

@Service
public interface AuditService {

    int singleLeaveAudit(String role, String userid, Long id, String result, String recommend);
}
