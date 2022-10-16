package com.shu.leave.service.Impl;

import com.shu.leave.entity.Calender;
import com.shu.leave.mapper.CalenderMapper;
import com.shu.leave.service.CalenderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        calenderForm.setHolidayStartName(startDate);
        calenderForm.setHolidayEndDate(endDate);
        calenderForm.setDescription(params[4]);
        calenderForm.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        calenderForm.setGmtCreate(timeStamp);
        calenderForm.setGmtModified(timeStamp);
        return calenderMapper.insert(calenderForm);
    }
}
