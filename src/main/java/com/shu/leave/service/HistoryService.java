package com.shu.leave.service;

import org.springframework.stereotype.Service;

@Service
public interface HistoryService {

    /**
     * 新增一条请考勤历史记录 -> [userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material]
     * @param param
     * @return
     */
    int addUserLeaveHistory(String[] param);
}
