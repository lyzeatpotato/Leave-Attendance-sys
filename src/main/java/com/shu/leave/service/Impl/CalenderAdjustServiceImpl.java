package com.shu.leave.service.Impl;

import com.shu.leave.entity.CalenderAdjust;
import com.shu.leave.mapper.CalenderAdjustMapper;
import com.shu.leave.service.CalenderAdjustService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class CalenderAdjustServiceImpl implements CalenderAdjustService {

    @Resource
    CalenderAdjustMapper calenderAdjustMapper;
    /**
     * author:王仕杰
     * 按照id查询某个假期对应的调休安排
     * @param calenderId
     * @param adjustName
     * @return 对应假期调休时间安排
     */
    @Override
    public List<CalenderAdjust> findAdjustById(Long calenderId, String adjustName) {
        return calenderAdjustMapper.selectAdjustById(calenderId, adjustName);
    }

    /**
     * author:王仕杰
     * 新增一条调休表单数据
     * @param params -> [calenderid, adjust_name, adjust_start_date, adjust_end_date]
     * @return 新增校历假期的主键id
     */
    @Override
    public int addCalenderAdjust(String[] params) throws ParseException{
        CalenderAdjust calenderAdjust = new CalenderAdjust();
        calenderAdjust.setCalenderId(Long.valueOf(params[0]));
        calenderAdjust.setAdjustName(params[1]);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df0.parse(params[2]);
        Date endDate = df0.parse(params[3]);
        calenderAdjust.setAdjustStartDate(startDate);
        calenderAdjust.setAdjustEndDate(endDate);
        calenderAdjust.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        calenderAdjust.setGmtCreate(timeStamp);
        calenderAdjust.setGmtModified(timeStamp);
        return calenderAdjustMapper.insertAdjust(calenderAdjust);
    }

    /**
     * author:王仕杰
     * 修改一个调休表单数据
     * @param params -> [id, calenderid, adjust_name, adjust_start_date, adjust_end_date]
     * @return 修改数据的id
     */
    @Override
    public int updateCalenderAdjust(String[] params) throws ParseException{
        CalenderAdjust calenderAdjust = new CalenderAdjust();
        calenderAdjust.setId(Long.valueOf(params[0]));
        calenderAdjust.setCalenderId(Long.valueOf(params[1]));
        calenderAdjust.setAdjustName(params[2]);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df0.parse(params[3]);
        Date endDate = df0.parse(params[4]);
        calenderAdjust.setAdjustStartDate(startDate);
        calenderAdjust.setAdjustEndDate(endDate);
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        calenderAdjust.setGmtModified(timeStamp);
        return calenderAdjustMapper.updateAdjustById(calenderAdjust);
    }

    /**
     * author:王仕杰
     * 逻辑删除某个调休表单数据
     * @param  id
     * @return 逻辑删除数据的id
     */
    @Override
    public int deleteCalenderAdjust(Long id) throws ParseException{
        return calenderAdjustMapper.deleteLogicallyById(id);
    }
}
