package com.shu.leave.service;

import com.shu.leave.entity.History;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     * 根据用户工号查询个人的全部请假记录
     * @param userId
     * @return
     */
    List<History> findHistoryByUserId(String userId);

    /**
     * 查询某名教师，某年某月的请假记录
     * @param userId
     * @param year
     * @param month
     * @return
     */
    History findHistoryByUserIdYearMonth(String userId, String year, String month);

    /**
     * 获取用户在某年度下某种请假类型的总天数
     * @param userId
     * @param year
     * @param leaveType
     * @return 年度请假类型的总天数
     */
    int sumHistoryLeaveType(String userId, String year, String leaveType);


    /**
     * 返回部门下某年某月的缺勤日期列表
     * @author liyuanzhe
     * @date 2022/12/14 3:23
     * @param year
     * @param month
     * @param dept
     * @return List<Date>
     */
    List<Date> getMonthDeptAbsenceDate(String year, String month, String dept);
}
