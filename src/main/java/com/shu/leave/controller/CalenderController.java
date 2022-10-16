package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.CalenderService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@Api(tags = "4.校历假期数据表")
@ApiSupport(order = 4)
@RestController
@RequestMapping("calender")
public class CalenderController {

    @Resource
    CalenderService calenderService;
    @ApiOperation(value = "新增校历假期数据", notes = "要求给出以下校历假期数据 [adminid, holiday_name, holiday_start_name, holiday_end_date, description]")
    @ApiOperationSupport(order = 4)
    @GetMapping("addCalender")
    public ResultEntity addCalenderForm(@RequestParam("adminid") String adminId, @RequestParam("holiday_name") String holidayName,
                                     @RequestParam("holiday_start_name") String holidayStartName, @RequestParam("holiday_end_date") String holidayEndDate,
                                     @RequestParam("description") String description) throws ParseException {
        String[] params = new String[]{adminId,holidayName, holidayStartName, holidayEndDate, description};
        return BasicResponseUtils.success(calenderService.addCalenderForm(params));
    }
}
