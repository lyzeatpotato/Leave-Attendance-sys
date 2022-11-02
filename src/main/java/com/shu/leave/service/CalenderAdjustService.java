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
     * @param holidayName
     * @return 对应假期调休时间安排
     */
    List<CalenderAdjust> findAdjustById(Long calenderId, String holidayName);
}
