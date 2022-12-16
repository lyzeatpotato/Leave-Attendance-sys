package com.shu.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.History;
import com.shu.leave.entity.Leave;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    Map<String, Object> getMonthDeptAbsenceDate(String year, String month, String dept);

    /**
     * 获取各部门下请假类型的数量
     * @author liyuanzhe
     * @date 2022/12/14 18:28
     * @param dept
     * @return Map<Integer>
     */
    Map[] getDeptHistoryLeaveTypeCount(String dept);

    /**
     * 获取各部门下各请假类型累计天数
     * @author liyuanzhe
     * @date 2022/12/14 21:11
     * @param dept
     * @return int[]
     */
    int[] getDeptHistoryLeaveDays(String dept) throws ParseException;

    /**
     * 获取各部门下员工请假年累计次数排名
     * @author liyuanzhe
     * @date 2022/12/14 21:34
     * @param year
     * @param dept
     * @return Map<Object>
     */
    Map<String, Object> getDeptMemberFrequencyList(String year, String dept);

    /**
     * 部门审核人员返回对应全部历史请假记录
     * @author liyuanzhe
     * @date 2022/12/14 23:34
     * @param page
     * @param dept
     * @return Page<Leave>
     */
    Page<Leave> getHistoryLoadingList(Page<Leave> page, String dept);

    /**
     * 筛选部门请假起止时间包含选定时间的历史请假记录
     * @author liyuanzhe
     * @date 2022/12/15 16:00
     * @param page
     * @param dept
     * @param nowDate
     * @return Page<Leave>
     */
    Page<Leave> getHistoryRecordByOneDate(Page<Leave> page, String dept, Date nowDate);
}
