package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public IPage findAllCalender() {
        Page<Calender> page = new Page<>(0, 10);    // 分页查询对象，从索引0开始，每页显示10条
        page = (Page<Calender>)calenderMapper.selectAll();
        IPage iPage = calenderMapper.selectPage(page, null);    // 此处使用Mybatis-plus中提供的selectPage方法
        return iPage;
    }
}
