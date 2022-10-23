package com.shu.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shu.leave.entity.Calender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public interface CalenderService {

    /**
     * 新增一条校历假期表单数据
     * @param params -> [adminid, holiday_name, holiday_start_date, holiday_end_date, description]
     * @return 新增校历假期的主键id
     */
    int addCalenderForm(String[] params) throws ParseException;

    /**
     * 修改某个校历假期表单数据
     * @param params -> [adminid, holiday_name, holiday_start_date, holiday_end_date, description]
     * @return 修改数据的id
     */

    int updateCalenderForm(String[] params) throws ParseException;

    /**
     * 逻辑删除某个校历假期表单数据
     * @param  id
     * @return 逻辑删除数据的id
     */

    int deleteCalender(Long id) throws ParseException;

    /**
     * 查询全部校历数据
     * @return 未被逻辑删除的校历数据
     */
    List<Calender> findAllCalender();

    /**
     * 统计当前用户提交的请假申请中，所包含的法定节假日总天数
     * @param leaveStartDate
     * @param leaveEndDate
     * @return 需要顺延的法定节假日总天数
     */
    int totalExtendHolidays(Date leaveStartDate, Date leaveEndDate);

    /**
     * 统计当前用户提交的请假申请中，所包含的寒暑假总天数
     * @param leaveStartDate
     * @param leaveEndDate
     * @return 需要顺延的寒暑假总天数
     */
    int totalExtendVocation(Date leaveStartDate, Date leaveEndDate);
}
