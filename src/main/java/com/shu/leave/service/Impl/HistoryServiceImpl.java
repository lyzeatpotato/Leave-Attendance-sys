package com.shu.leave.service.Impl;

import com.shu.leave.entity.History;
import com.shu.leave.mapper.HistoryMapper;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.HistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Resource
    UserMapper userMapper;

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
        return historyMapper.selectByTypeYear(userPrimaryKey, leaveTypeJDBC, year);
    }
}
