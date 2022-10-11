package com.shu.leave.service;

import org.springframework.stereotype.Service;

@Service
public interface HistoryService {

    boolean addUserLeaveHistory(String[] param);
}
