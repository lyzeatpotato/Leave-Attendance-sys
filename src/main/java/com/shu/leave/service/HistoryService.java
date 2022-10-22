package com.shu.leave.service;

import com.shu.leave.entity.History;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoryService {

    /**
     * 新增一条请考勤历史记录 -> [userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material]
     * @param param
     * @return
     */
    int addUserLeaveHistory(String[] param);

    /**
     * 根据前端传入的用户工号查询对应的全部请假信息
     * @param userid
     * @return 用户请假信息列表
     */
    List<History> findUserLeaveHistoryByUserid(String userid);
}
