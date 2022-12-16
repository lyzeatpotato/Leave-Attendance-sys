package com.shu.leave.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public static Date[] getSortedDateArray(Date[] inputDateArray) {
        for(int i = 0; i < inputDateArray.length - 1; i++) {
            for (int j = i + 1; j < inputDateArray.length; j++) {
                if (inputDateArray[i].getTime() < inputDateArray[j].getTime()) {
                    Date temp = inputDateArray[i];
                    inputDateArray[i] = inputDateArray[j];
                    inputDateArray[j] = temp;
                }
            }
        }
        return inputDateArray;
    }


    /**
     * 获取当年年份
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //清除可能存在的缓存
        cal.clear();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //清除可能存在的缓存
        cal.clear();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 为list列表降重
     * @author liyuanzhe
     * @date 2022/12/14 22:06
     * @param list
     */
    public static void removeDuplicate(List<String> list) {
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }



}
