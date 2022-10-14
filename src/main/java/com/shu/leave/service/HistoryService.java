package com.shu.leave.service;

import org.springframework.stereotype.Service;

@Service
public interface HistoryService {

    int addUserLeaveHistory(String[] param);
}
