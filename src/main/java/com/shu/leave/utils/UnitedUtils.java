package com.shu.leave.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: UnitedUtils
 * @Description: 编写统一工具类方法
 * @author: lyz
 * @date: 2022 10 2022/10/13 22:49
 */

public class UnitedUtils {

    /**
     * 根据请假类型字符串判别属于第几个请假类型
     * @param leaveType
     * @return
     */
    public static int getLeaveTypeIndex(String leaveType) {
        switch (leaveType) {
            case "病假":
                return 1;
            case "婚假":
                return 2;
            case "产假":
                return 3;
            case "陪产假":
                return 4;
            case "丧假":
                return 5;
            case "因公出差":
                return 6;
            case "工伤假":
                return 7;
            default:
                return 0;
        }
    }

    /**
     * 计算两个日期之间所差天数
     * @param startDate
     * @param endDate
     * @return 整型天数值
     * @throws ParseException
     */
    public static int getDayDiffer(Date startDate, Date endDate) throws ParseException {
        //判断是否跨年
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String startYear = yearFormat.format(startDate);
        String endYear = yearFormat.format(endDate);
        if (startYear.equals(endYear)) {
            /*  使用Calendar跨年的情况会出现问题    */
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
            return endDay - startDay;
        } else {
            /*  跨年不会出现问题，需要注意不满24小时情况（2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0）  */
            //  只格式化日期，消除不满24小时影响
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
            return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
        }
    }
}
