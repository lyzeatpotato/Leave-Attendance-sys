package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Calender;
import com.shu.leave.entity.User;
import com.shu.leave.mapper.CalenderMapper;
import com.shu.leave.service.CalenderService;
import com.shu.leave.utils.UnitedUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CalenderServiceImpl implements CalenderService {

    @Resource
    CalenderMapper calenderMapper;

    @Override
    public int addCalenderForm(String[] params) throws ParseException {
        Calender calenderForm = new Calender();
        calenderForm.setAdminId(Long.valueOf(params[0]));
        calenderForm.setHolidayName(params[1]);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df0.parse(params[2]);
        Date endDate = df0.parse(params[3]);
        calenderForm.setHolidayStartDate(startDate);
        calenderForm.setHolidayEndDate(endDate);
        calenderForm.setDescription(params[4]);
        calenderForm.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        calenderForm.setGmtCreate(timeStamp);
        calenderForm.setGmtModified(timeStamp);
//        System.out.println(calenderForm);
        return calenderMapper.insert(calenderForm);
    }

    @Override
    public int updateCalenderForm(String[] params) throws ParseException {
        Calender calenderForm = new Calender();
        calenderForm.setId(Long.valueOf(params[0]));
        calenderForm.setAdminId(Long.valueOf(params[1]));
        calenderForm.setHolidayName(params[2]);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df0.parse(params[3]);
        Date endDate = df0.parse(params[4]);
        calenderForm.setHolidayStartDate(startDate);
        calenderForm.setHolidayEndDate(endDate);
        calenderForm.setDescription(params[5]);
        calenderForm.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        calenderForm.setGmtCreate(timeStamp);
        calenderForm.setGmtModified(timeStamp);
//        System.out.println(calenderForm);
        return calenderMapper.update(calenderForm);
    }

    @Override
    public int deleteCalender(Long id)  {
        return calenderMapper.deleteLogicallyById(id);
    }


//    public IPage findAllCalender() {
//        Page<Calender> page = new Page<>(0, 10);    // 分页查询对象，从索引0开始，每页显示10条
//        page = (Page<Calender>)calenderMapper.selectAll();
//        IPage iPage = calenderMapper.selectPage(page, null);    // 此处使用Mybatis-plus中提供的selectPage方法
//        return iPage;
//    }
    @Override
    public List<Calender> findAllCalender() {
        return calenderMapper.selectAll();
    }

    @Override
    public int totalExtendHolidays(Date leaveStartDate, Date leaveEndDate) {
        /**
         * 此处判断逻辑：
         * 1.首先判断用户提交的请假起止时间范围内，是否有法定节假日
         * 2.如不存在数据，则进一步判断请假起止时间是否落在了某个法定节假日范围内
         */
        List<Calender> calendersInScope = calenderMapper.selectHolidayByStartEndTimeInner(leaveStartDate, leaveEndDate);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含法定节假日
            List<Calender> calendersConScope = calenderMapper.selectHolidayByStartEndTimeContainer(leaveStartDate, leaveEndDate);
            if (calendersConScope.isEmpty()) {
                // 如也没有落在某个假期范围内，则说明不需要顺延
                return 0;
            } else {
                return -1;  // 表示当前提交的请假申请不需要记录缺勤信息
            }
        } else {
            Date holidayStartDate = calendersInScope.get(0).getHolidayStartDate();
            Date holidayEndDate = calendersInScope.get(0).getHolidayEndDate();
            Date[] dates = new Date[] {holidayStartDate, holidayEndDate, leaveStartDate, leaveEndDate};
            Date[] sortedDateArray = UnitedUtils.getSortedDateArray(dates);
            int extendDays = 0;     // 需要延长的假期天数
            try {
                extendDays = UnitedUtils.getDayDiffer(sortedDateArray[2], sortedDateArray[1]);
                return extendDays;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int totalExtendVocation(Date leaveStartDate, Date leaveEndDate) {
        /**
         * 此处判断逻辑：
         * 1.首先判断用户提交的请假起止时间范围内，是否有寒暑假
         * 2.如不存在数据，则进一步判断请假起止时间是否落在了某个寒暑假范围内
         */
        List<Calender> calendersInScope = calenderMapper.selectVocationByStartEndTimeInner(leaveStartDate, leaveEndDate);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含法定节假日
            List<Calender> calendersConScope = calenderMapper.selectVocationByStartEndTimeContainer(leaveStartDate, leaveEndDate);
            if (calendersConScope.isEmpty()) {
                // 如也没有落在某个假期范围内，则说明不需要顺延
                return 0;
            } else {
                return -1;  // 表示当前提交的请假申请不需要记录缺勤信息
            }
        } else {
            Date holidayStartDate = calendersInScope.get(0).getHolidayStartDate();
            Date holidayEndDate = calendersInScope.get(0).getHolidayEndDate();
            Date[] dates = new Date[] {holidayStartDate, holidayEndDate, leaveStartDate, leaveEndDate};
            Date[] sortedDateArray = UnitedUtils.getSortedDateArray(dates);
            int extendDays = 0;     // 需要延长的假期天数
            try {
                extendDays = UnitedUtils.getDayDiffer(sortedDateArray[2], sortedDateArray[1]);
                return extendDays;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int totalExtendVocation(Date leaveStartDate, Date leaveEndDate, String description) {
        /**
         * 此处判断逻辑：
         * 1.首先判断用户提交的请假起止时间范围内，是否有寒暑假
         * 2.如不存在数据，则进一步判断请假起止时间是否落在了某个寒暑假范围内
         */
        List<Calender> calendersInScope = calenderMapper.selectHolidayByStartEndTimeInner(leaveStartDate, leaveEndDate, description);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含法定节假日
            List<Calender> calendersConScope = calenderMapper.selectHolidayByStartEndTimeContainer(leaveStartDate, leaveEndDate, description);
            if (calendersConScope.isEmpty()) {
                // 如也没有落在某个假期范围内，则说明不需要顺延
                return 0;
            } else {
                return -1;  // 表示当前提交的请假申请不需要记录缺勤信息
            }
        } else {
            Date holidayStartDate = calendersInScope.get(0).getHolidayStartDate();
            Date holidayEndDate = calendersInScope.get(0).getHolidayEndDate();
            Date[] dates = new Date[] {holidayStartDate, holidayEndDate, leaveStartDate, leaveEndDate};
            Date[] sortedDateArray = UnitedUtils.getSortedDateArray(dates);
            int extendDays = 0;     // 需要延长的假期天数
            try {
                extendDays = UnitedUtils.getDayDiffer(sortedDateArray[2], sortedDateArray[1]);
                return extendDays;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
