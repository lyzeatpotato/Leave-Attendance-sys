package com.shu.leave.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface CalenderService {

    /**
     * 新增一条校历假期表单数据
     * @param params -> [adminid, holiday_name, holiday_start_name, holiday_end_date, description]
     * @return 新增校历假期的主键id
     */
    int addCalenderForm(String[] params) throws ParseException;
}
