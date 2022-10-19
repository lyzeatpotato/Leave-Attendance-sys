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
    @ApiOperation(value = "新增校历假期数据", notes = "要求给出以下校历假期数据 [adminid, holiday_name, holiday_start_date, holiday_end_date, description]")
    @ApiOperationSupport(order = 1)
    @GetMapping("addCalender")
    public ResultEntity addCalenderForm(@RequestParam("adminid") String adminId, @RequestParam("holiday_name") String holidayName,
                                     @RequestParam("holiday_start_date") String holidayStartDate, @RequestParam("holiday_end_date") String holidayEndDate,
                                     @RequestParam("description") String description) throws ParseException {
        String[] params = new String[]{adminId,holidayName, holidayStartDate, holidayEndDate, description};
        return BasicResponseUtils.success(calenderService.addCalenderForm(params));
    }

    @ApiOperation(value = "修改校历假期数据", notes = "要求给出以下校历假期数据 [id,adminid, holiday_name, holiday_start_date, holiday_end_date, description]")
    @ApiOperationSupport(order = 2)
    @GetMapping("updateCalender")
    public ResultEntity updateCalenderForm(@RequestParam("id") String id,
                                        @RequestParam("adminid") String adminId, @RequestParam("holiday_name") String holidayName,
                                        @RequestParam("holiday_start_date") String holidayStartDate, @RequestParam("holiday_end_date") String holidayEndDate,
                                        @RequestParam("description") String description) throws ParseException {
        String[] params = new String[]{id,adminId,holidayName, holidayStartDate, holidayEndDate, description};
        return BasicResponseUtils.success(calenderService.updateCalenderForm(params));
    }

    @ApiOperation(value = "逻辑删除一条校历假期数据", notes = "要求给出要删除的id ")
    @ApiOperationSupport(order = 3)
    @GetMapping("deleteCalender")
    public ResultEntity deleteCalender(@RequestParam("id") String id) throws ParseException {
        return BasicResponseUtils.success(calenderService.deleteCalender(Long.parseLong(id)));
    }

    @ApiOperation(value = "查询全部校历数据", notes = "显示全部未被逻辑删除的用户信息 ")
    @ApiOperationSupport(order = 4)
    @GetMapping("findAllCalender")
    public ResultEntity findAllCalenderForm() throws ParseException {
        return BasicResponseUtils.success(calenderService.findAllCalender());
    }
}
