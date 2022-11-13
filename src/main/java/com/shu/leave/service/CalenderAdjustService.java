package com.shu.leave.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shu.leave.entity.CalenderAdjust;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
public interface CalenderAdjustService {

    /**
     * author:王仕杰
     * 按照id查询某个假期对应的调休安排
     * @param calenderId
     * @return 对应假期调休时间安排
     */
    List<CalenderAdjust> findAdjustById(Long calenderId);

    /**
     * author:王仕杰
     * 新增一条调休表单数据
     * @param params -> [calenderid, adjust_name, adjust_start_date, adjust_end_date]
     * @return 新增校历假期的主键id
     */
    int addCalenderAdjust(String[] params) throws ParseException;

    /**
     * author:王仕杰
     * 修改一个调休表单数据
     * @param params -> [calenderid, adjust_name, adjust_start_date, adjust_end_date]
     * @return 修改数据的id
     */

    int updateCalenderAdjust(String[] params) throws ParseException;

    /**
     * author:王仕杰
     * 逻辑删除某个调休表单数据
     * @param  id
     * @return 逻辑删除数据的id
     */

    int deleteCalenderAdjust(Long id) throws ParseException;
}
