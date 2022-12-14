package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leave.entity.History;
import com.shu.leave.entity.Leave;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    public List<Date> getMonthDeptAbsenceDate(String year, String month, String dept) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //String startDay = df.format(new Date());
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Integer.parseInt(startDay.substring(0, 4)), Integer.parseInt(startDay.substring(5, 7)) - 1, 1);
        //String firstDayOfMonth = new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime());
        //System.out.println("第一天：" + firstDayOfMonth);
        ////这里将日期值减去一天，从而获取到要求的月份最后一天
        //calendar.add(Calendar.DATE, -1);
        //String lastDayOfMonth = new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime());
        //System.out.println("最后一天：" + lastDayOfMonth);
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
        return resultDateList;
    }
}
