package com.shu.leave.service.Impl;

import com.shu.leave.entity.CalenderAdjust;
import com.shu.leave.mapper.CalenderAdjustMapper;
import com.shu.leave.service.CalenderAdjustService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CalenderAdjustServiceImpl implements CalenderAdjustService {

    @Resource
    CalenderAdjustMapper calenderAdjustMapper;
    /**
     * author:王仕杰
     * 按照id查询某个假期对应的调休安排
     * @param calenderId
     * @param holidayName
     * @return 对应假期调休时间安排
     */
    @Override
    public List<CalenderAdjust> findAdjustById(Long calenderId, String holidayName) {
        return calenderAdjustMapper.selectAdjustById(calenderId, holidayName);
    }
}
