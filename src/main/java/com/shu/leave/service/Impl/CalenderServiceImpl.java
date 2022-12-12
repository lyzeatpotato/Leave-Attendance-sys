package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalenderServiceImpl implements CalenderService {

    @Resource
    CalenderMapper calenderMapper;
    @Resource
    CalenderService calenderService;

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
        return calenderMapper.deleteById(id);
    }


//    public IPage findAllCalender() {
//        Page<Calender> page = new Page<>(0, 10);    // 分页查询对象，从索引0开始，每页显示10条
//        page = (Page<Calender>)calenderMapper.selectAll();
//        IPage iPage = calenderMapper.selectPage(page, null);    // 此处使用Mybatis-plus中提供的selectPage方法
//        return iPage;
//    }
    @Override
    public List<Calender> findAllCalender(String year) {
        return calenderMapper.selectAll(Long.valueOf(year));
    }

    @Override
    public int totalExtendHolidays(Date leaveStartDate, Date leaveEndDate) {
        /**
         * 此处判断逻辑：
         * 1.首先判断用户提交的请假起止时间范围内，是否有法定节假日
         * 2.如不存在数据，则进一步判断请假起止时间是否落在了某个法定节假日范围内
         */
        QueryWrapper<Calender> innerQueryWrapper = new QueryWrapper<>();
        innerQueryWrapper.between("holiday_start_date", leaveStartDate, leaveEndDate);
        innerQueryWrapper.between("holiday_end_date", leaveEndDate, leaveEndDate);
        innerQueryWrapper.eq("description", "法定节假日");
        innerQueryWrapper.eq("is_deleted", 0);
        List<Calender> calendersInScope = calenderMapper.selectList(innerQueryWrapper);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含法定节假日
            QueryWrapper<Calender> conQueryWrapper = new QueryWrapper<>();
            conQueryWrapper.le("holiday_start_date", leaveStartDate);
            conQueryWrapper.ge("holiday_end_date", leaveEndDate);
            conQueryWrapper.eq("description", "法定节假日");
            conQueryWrapper.eq("is_deleted", 0);
            List<Calender> calendersConScope = calenderMapper.selectList(conQueryWrapper);
//            List<Calender> calendersConScope = calenderMapper.selectHolidayByStartEndTimeContainer(leaveStartDate, leaveEndDate);
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
        QueryWrapper<Calender> inQueryWrapper = new QueryWrapper<>();
        inQueryWrapper.between("holiday_start_date", leaveStartDate, leaveEndDate);
        inQueryWrapper.between("holiday_end_date", leaveEndDate, leaveEndDate);
        inQueryWrapper.eq("description", "寒暑假");
        inQueryWrapper.eq("is_deleted", 0);
        List<Calender> calendersInScope = calenderMapper.selectList(inQueryWrapper);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含寒暑假
            QueryWrapper<Calender> conQueryWrapper = new QueryWrapper<>();
            conQueryWrapper.le("holiday_start_date", leaveStartDate);
            conQueryWrapper.ge("holiday_end_date", leaveEndDate);
            conQueryWrapper.eq("description", "寒暑假");
            conQueryWrapper.eq("is_deleted", 0);
            List<Calender> calendersConScope = calenderMapper.selectList(conQueryWrapper);
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
    public int totalExtendDiscribtionVocation(Date leaveStartDate, Date leaveEndDate, String description) {
        /**
         * 此处判断逻辑：
         * 1.首先判断用户提交的请假起止时间范围内，是否有寒暑假
         * 2.如不存在数据，则进一步判断请假起止时间是否落在了某个寒暑假范围内
         */
        List<Calender> calendersInScope = calenderMapper.selectHolidayByStartEndTimeDiscribtionInner(leaveStartDate, leaveEndDate, description);
        if (calendersInScope.isEmpty()) {
            // 当前用户选择的请假起止时间不包含法定节假日
            List<Calender> calendersConScope = calenderMapper.selectHolidayByStartEndTimeDiscribtionContainer(leaveStartDate, leaveEndDate, description);
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
    public int realLeaveDayDiffer(Date leaveStartDate, Date leaveEndDate, String leaveType) throws ParseException {
        /**
         * 根据请假类型判断假期是否需要顺延，并根据校历信息对请假起止时间进行修改（暂未考虑补休）
         * 规则：1.病假遇寒暑假、公休日和法定节假日不顺延；
         *      2.事假、丧假遇到公休日和法定节假日顺延；
         *      3.婚假、产假、生育假与配偶陪产假遇寒暑假和法定节假日顺延。
         */
        int dayDiffer = 0;
        if (leaveType.equals("事假") || leaveType.equals("丧假")) {
            // 事假、丧假 判别公休日和法定节假日
            int holidayExtends = calenderService.totalExtendHolidays(leaveStartDate, leaveEndDate);   // 遇到法定节假日需要顺延的天数
            if (holidayExtends != -1) {     // =>此处判断不等于-1是确认用户选择的请假范围未被某一个假期范围所包含，如被包含则不记录请假时长(dayDiffer=0)
                // 请假天数=当前申请天数-遇到公休/法定节假日顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(leaveStartDate, leaveEndDate) - holidayExtends;
            }
        } else if (leaveType.equals("婚假") || leaveType.equals("产假") || leaveType.equals("陪产假")) {
            // 婚假、产假、陪产假 判别法定节假日和寒暑假
            int holidayExtends = calenderService.totalExtendHolidays(leaveStartDate, leaveEndDate);   // 遇到法定节假日需要顺延的天数
            int vocationExtends = calenderService.totalExtendVocation(leaveStartDate, leaveEndDate);  // 遇到寒暑假需要顺延的天数
            if (holidayExtends != -1 && vocationExtends != -1) {
                // 请假天数=当前申请天数-遇到法定节假日/寒暑假顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(leaveStartDate, leaveEndDate) - holidayExtends - vocationExtends;
            }
        } else {
            dayDiffer = UnitedUtils.getDayDiffer(leaveStartDate, leaveEndDate);
        }
        return dayDiffer;
    }

    @Override
    public String getCurrentExcateDate(Date checkingDate) {
        String resultStr;   // 统一结果返回值
        // 首先判断当前日期是否处在教学周内
        QueryWrapper<Calender> queryWrapper0 = new QueryWrapper<>();
        queryWrapper0.le("holiday_start_date", checkingDate);
        queryWrapper0.ge("holiday_end_date", checkingDate);
        queryWrapper0.eq("description", "寒暑假");
        Calender calender1 = calenderMapper.selectOne(queryWrapper0);
        if (calender1 == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(checkingDate);
            Integer weekNumbe = calendar.get(Calendar.WEEK_OF_YEAR);
            System.out.println("当前为本年度" + weekNumbe + "周");
            // 前往数据库中查询，看当前日期落在哪一个教学周内
            QueryWrapper<Calender> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("holiday_start_date", checkingDate);
            queryWrapper.ge("holiday_end_date", checkingDate);
            queryWrapper.eq("description", "教学周");
            Calender calender = null;
            int resWeek;
            try {
                calender = calenderMapper.selectOne(queryWrapper);
                Date holidayStartDate = calender.getHolidayStartDate();
                if (!calender.getHolidayName().equals("冬季学期")) {
                    calendar.setTime(holidayStartDate);
                    Integer startTimeWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
                    System.out.println("学期开始日期为本年度" + startTimeWeekNum + "周");
                    resWeek = weekNumbe - startTimeWeekNum + 1;
                } else {
                    // 冬季学期需要额外减去寒假（寒假不计入教学周）
                    QueryWrapper<Calender> queryWrapperHoliday = new QueryWrapper<>();
                    queryWrapperHoliday.eq("holiday_name", "寒假");
                    Calender calenderHoliday = calenderMapper.selectOne(queryWrapperHoliday);       // 找出本年度寒假的时间
                    int dayDiffer;
                    if (checkingDate.compareTo(calenderHoliday.getHolidayEndDate()) != -1) {
                        // 若当前日期落在寒假结束日期以后
                        int hanjiaDiffer = UnitedUtils.getDayDiffer(calenderHoliday.getHolidayStartDate(), calenderHoliday.getHolidayEndDate());
                        dayDiffer = UnitedUtils.getDayDiffer(holidayStartDate, checkingDate) - hanjiaDiffer;
                    } else {
                        dayDiffer = UnitedUtils.getDayDiffer(holidayStartDate, checkingDate);
                    }
                    resWeek = dayDiffer / 7 + 1;
                }
                resultStr = calender.getHolidayName() + "第" + String.valueOf(resWeek) + "周";
            } catch (Exception e) {
                resultStr = "非校历定义的日期";
                e.printStackTrace();
            }
        } else {
            resultStr = "非教学周";
        }
        return resultStr;
    }
}
