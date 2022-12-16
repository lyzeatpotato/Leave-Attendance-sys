package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.History;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.User;
import com.shu.leave.enums.LeaveTypeCodeEnums;
import com.shu.leave.mapper.HistoryMapper;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.HistoryService;
import com.shu.leave.utils.UnitedUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Resource
    UserMapper userMapper;

    @Resource
    LeaveMapper leaveMapper;

    @Resource
    HistoryMapper historyMapper;

    @Override
    public int addUserLeaveHistory(String[] param) {
        History history = new History();
        Date date = new Date();
        LocalDate current_date = LocalDate.now();
        long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(param[0]);
        history.setUserId(userPrimaryKey);
        history.setYear(String.valueOf(current_date.getYear()));
        history.setMonth(String.valueOf(current_date.getMonth()));
        history.setShijiaDays(Integer.valueOf(param[1]));
        history.setBingjiaDays(Integer.valueOf(param[2]));
        history.setHunjiaDays(Integer.valueOf(param[3]));
        history.setShengyujiaDays(Integer.valueOf(param[4]));
        history.setTanqinjiaDays(Integer.valueOf(param[5]));
        history.setSangjiaDays(Integer.valueOf(param[6]));
        history.setGongshangjiaDays(Integer.valueOf(param[7]));
        history.setKuanggongDays(Integer.valueOf(param[8]));
        history.setInactiveDays(Integer.valueOf(param[11]));
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        history.setGmtCreate(timeStamp);
        history.setGmtModified(timeStamp);
        return historyMapper.insert(history);
    }

    @Override
    public List<History> findHistoryByUserId(String userId) {
        Long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(userId);
        return historyMapper.selectWithUserId(userPrimaryKey);
    }

    @Override
    public History findHistoryByUserIdYearMonth(String userId, String year, String month) {
        Long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(userId);
        return historyMapper.selectWithMonthYear(userPrimaryKey, month, year);
    }

    @Override
    public int sumHistoryLeaveType(String userId, String year, String leaveType) {
        Long userPrimaryKey = userMapper.getUserPrimaryKeyByUserId(userId);
        String leaveTypeJDBC;
        switch (leaveType) {
            case "事假":
                leaveTypeJDBC = "shijia_days";
                break;
            case "病假":
                leaveTypeJDBC = "bingjia_days";
                break;
            case "婚假":
                leaveTypeJDBC = "hunjia_days";
                break;
            case "产假":
                leaveTypeJDBC = "shengyujia_days";
                break;
            case "陪产假":
                leaveTypeJDBC = "shengyujia_days";
                break;
            case "探亲假":
                leaveTypeJDBC = "tanqinjia_days";
                break;
            case "丧假":
                leaveTypeJDBC = "sangjia_days";
                break;
            case "因公出差":
                leaveTypeJDBC = "gongchai_days";
                break;
            case "工伤假":
                leaveTypeJDBC = "gongshangjia_days";
                break;
            case "旷工":
                leaveTypeJDBC = "kuanggong_days";
                break;
            case "不活跃天数":
                leaveTypeJDBC = "inactive_days";
                break;
            default:
                leaveTypeJDBC = "shijia_days";
        }
        try {
            int days = historyMapper.selectByTypeYear(userPrimaryKey, leaveTypeJDBC, year);
            return days;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Map<String, Object> getMonthDeptAbsenceDate(String year, String month, String dept) {
        Map<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = UnitedUtils.getFirstDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
        System.out.println("第一天：" + firstDayOfMonth);
        String lastDayOfMonth = UnitedUtils.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
        System.out.println("最后一天：" + lastDayOfMonth);

        QueryWrapper<History> historyQueryWrapper = new QueryWrapper<>();
        historyQueryWrapper.eq("year", year);
        historyQueryWrapper.eq("month", month);
        historyQueryWrapper.eq("department", dept);
        List<History> histories = historyMapper.selectList(historyQueryWrapper);
        List<Long> userIds = histories.stream().map(o -> o.getUserId()).collect(Collectors.toList());       // 获取查询出的用户的id
        QueryWrapper<Leave> leaveQueryWrapper = new QueryWrapper<>();
        if (userIds.size() != 0) {
            leaveQueryWrapper.in("userid", userIds);
        } else {
            resultMap.put("result", "当前部门无审核完成的请假记录");
            resultMap.put("resultCode", "0");
            return resultMap;
        }
        leaveQueryWrapper.eq("status", "1");    // 筛选请假信息为已完成了审核的请假记录
        leaveQueryWrapper.ge("leave_start_time", firstDayOfMonth);
        leaveQueryWrapper.le("leave_end_time", lastDayOfMonth);

        List<Leave> leaves = leaveMapper.selectList(leaveQueryWrapper);
        List<Date> resultDateList = leaves.stream().map(leave -> {
            try {
                return df.parse(df.format(leave.getLeaveStartTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        resultMap.put("result", resultDateList);
        resultMap.put("resultCode", "1");
        return resultMap;
    }

    @Override
    public Map[] getDeptHistoryLeaveTypeCount(String dept) {
        List<Leave> leaveList = leaveMapper.selectByUserDept(dept);
        int[] typeCounts = new int[8];
        Map[] dataMap = new Map[8];
        LeaveTypeCodeEnums leaveTypeCodeEnums = null;
        for (int i = 0; i < leaveList.size(); i++) {
            leaveTypeCodeEnums = LeaveTypeCodeEnums.getByValue(leaveList.get(i).getLeaveType());
            if (leaveTypeCodeEnums == null) {
                return (Map[]) dataMap[0].put("errorMsg", "后端报错");
            } else {
                switch (leaveTypeCodeEnums) {
                    case SHIJIA: {
                        typeCounts[0] ++;
                        break;
                    }
                    case BINGJIA: {
                        typeCounts[1] ++;
                        break;
                    }
                    case HUNJIA: {
                        typeCounts[2] ++;
                        break;
                    }
                    case CHANJIA:
                    case PEICHANJIA: {
                        typeCounts[3] ++;
                        break;
                    }
                    case TANQINJIA: {
                        typeCounts[4] ++;
                        break;
                    }
                    case SANGJIA: {
                        typeCounts[5] ++;
                        break;
                    }
                    case YINGONGCHUCHAI: {
                        typeCounts[6] ++;
                        break;
                    }
                    default: {
                        typeCounts[7] ++;
                        break;
                    }
                }
            }

        }
        for (int i = 0; i < dataMap.length; i++) {
            dataMap[i] = new HashMap();
            dataMap[i].put("value", typeCounts[i]);
        }
        dataMap[0].put("name", leaveTypeCodeEnums.SHIJIA.getLeaveType());
        dataMap[1].put("name", leaveTypeCodeEnums.BINGJIA.getLeaveType());
        dataMap[2].put("name", leaveTypeCodeEnums.HUNJIA.getLeaveType());
        dataMap[3].put("name", leaveTypeCodeEnums.SHENGYUJIA.getLeaveType());
        dataMap[4].put("name", leaveTypeCodeEnums.TANQINJIA.getLeaveType());
        dataMap[5].put("name", leaveTypeCodeEnums.SANGJIA.getLeaveType());
        dataMap[6].put("name", leaveTypeCodeEnums.YINGONGCHUCHAI.getLeaveType());
        dataMap[7].put("name", leaveTypeCodeEnums.GONGSHANGJIA.getLeaveType());
        return dataMap;
    }

    @Override
    public int[] getDeptHistoryLeaveDays(String dept) throws ParseException {
        List<Leave> leaveList = leaveMapper.selectByUserDept(dept);
        int[] typeCounts = new int[8];
        LeaveTypeCodeEnums leaveTypeCodeEnums = null;
        for (int i = 0; i < leaveList.size(); i++) {
            leaveTypeCodeEnums = LeaveTypeCodeEnums.getByValue(leaveList.get(i).getLeaveType());
            if (leaveTypeCodeEnums == null) {
                return new int[]{-1};
            } else {
                int dayDiffer = UnitedUtils.getDayDiffer(leaveList.get(i).getLeaveStartTime(), leaveList.get(i).getLeaveEndTime());
                switch (leaveTypeCodeEnums) {
                    case SHIJIA: {
                        typeCounts[0] += dayDiffer;
                        break;
                    }
                    case BINGJIA: {
                        typeCounts[1] += dayDiffer;
                        break;
                    }
                    case HUNJIA: {
                        typeCounts[2] += dayDiffer;
                        break;
                    }
                    case CHANJIA:
                    case PEICHANJIA: {
                        typeCounts[3] += dayDiffer;
                        break;
                    }
                    case TANQINJIA: {
                        typeCounts[4] += dayDiffer;
                        break;
                    }
                    case SANGJIA: {
                        typeCounts[5] += dayDiffer;
                        break;
                    }
                    case YINGONGCHUCHAI: {
                        typeCounts[6] += dayDiffer;
                        break;
                    }
                    default: {
                        typeCounts[7] += dayDiffer;
                        break;
                    }
                }
            }
        }
        return typeCounts;
    }

    @Override
    public Map<String, Object> getDeptMemberFrequencyList(String year, String dept) {
        Map<String, Object> resultMap = new HashMap<>();        // 最终的返回值
        // 首先在history表中查询有过请假记录的员工
        QueryWrapper<History> historyQueryWrapper = new QueryWrapper<>();
        historyQueryWrapper.eq("year", year);
        historyQueryWrapper.eq("department", dept);
        List<History> historyList = historyMapper.selectList(historyQueryWrapper);
        List<Long> userIds = historyList.stream().map(o -> o.getUserId()).collect(Collectors.toList());       // 获取查询出的用户的id

        QueryWrapper<Leave> leaveQueryWrapper = new QueryWrapper<>();
        if (userIds.size() != 0) {
            leaveQueryWrapper.in("userid", userIds);
        } else {
            resultMap.put("errorMsg", "当前部门本年度无请假记录");
            return resultMap;
        }
        leaveQueryWrapper.eq("status", "1");    // 筛选请假信息为已完成了审核的请假记录
        List<Leave> leaveList = leaveMapper.selectList(leaveQueryWrapper);
        List<String> userNameList = leaveList.stream().map(leave -> {
            Long userPrimaryKey = leave.getUserId();
            User user = userMapper.findById(userPrimaryKey);
            return user.getUserName();
        }).collect(Collectors.toList());
        // 给名称list去重
        UnitedUtils.removeDuplicate(userNameList);
        int[] numArr = new int[userNameList.size()];
        for (int i = 0; i < leaveList.size(); i++) {
            Long userId = leaveList.get(i).getUserId();
            User nowUser = userMapper.findById(userId);
            for (int j = 0; j < userNameList.size(); j++) {
                if (nowUser.getUserName().equals(userNameList.get(j))) {
                    numArr[j] ++;
                }
            }
        }
        resultMap.put("nameList", userNameList);
        resultMap.put("numList", numArr);
        return resultMap;
    }

    @Override
    public Page<Leave> getHistoryLoadingList(Page<Leave> page, String dept) {
        Page<Leave> result = historyMapper.selectPaginByUserDept(page, dept);
        return result;
    }

    @Override
    public Page<Leave> getHistoryRecordByOneDate(Page<Leave> page, String dept, Date nowDate) {
        Page<Leave> result = historyMapper.selectPaginByNowDateDept(page, dept, nowDate);
        return result;
    }
}
