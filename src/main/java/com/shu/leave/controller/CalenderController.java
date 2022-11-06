package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.CalenderAdjustService;
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
    @Resource
    CalenderAdjustService calenderAdjustService;
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
    public ResultEntity findAllCalenderForm()  {
        return BasicResponseUtils.success(calenderService.findAllCalender());
    }

    @ApiOperation(value = "调休查询", notes = "根据所给的假期id显示对应假期的调休安排 ")
    @ApiOperationSupport(order = 5)
    @GetMapping("findAdjustById")
    public ResultEntity findAdjustById(@RequestParam("id") String calenderid, @RequestParam("adjust_name") String adjustName)  throws ParseException{
        return BasicResponseUtils.success(calenderAdjustService.findAdjustById(Long.parseLong(calenderid),adjustName));
    }

    @ApiOperation(value = "新增调休数据", notes = "要求给出以下调休数据 {calenderid, adjust_name, adjust_start_date, adjust_end_date]")
    @ApiOperationSupport(order = 6)
    @GetMapping("addCalenderAdjust")
    public ResultEntity addCalenderAdjust(@RequestParam("calenderid") String calenderId, @RequestParam("adjust_name") String adjustName,
                                        @RequestParam("adjust_start_date") String adjustStartDate, @RequestParam("adjust_end_date") String adjustEndDate
                                        ) throws ParseException {
        String[] params = new String[]{calenderId,adjustName, adjustStartDate, adjustEndDate};
        return BasicResponseUtils.success(calenderAdjustService.addCalenderAdjust(params));
    }

    @ApiOperation(value = "修改调休数据", notes = "要求给出以下调休数据 [id, calenderid, adjust_name, adjust_start_date, adjust_end_date]")
    @ApiOperationSupport(order = 7)
    @GetMapping("updateCalenderAdjust")
    public ResultEntity updateCalenderAdjust(@RequestParam("id") String id,
                                           @RequestParam("calenderid") String calenderId, @RequestParam("adjust_name") String adjustName,
                                           @RequestParam("adjust_start_date") String adjustStartDate, @RequestParam("adjust_end_date") String adjustEndDate
                                           ) throws ParseException {
        String[] params = new String[]{id,calenderId,adjustName, adjustStartDate, adjustEndDate,};
        return BasicResponseUtils.success(calenderAdjustService.updateCalenderAdjust(params));
    }

    @ApiOperation(value = "逻辑删除调休数据", notes = "要求给出要删除的id ")
    @ApiOperationSupport(order = 8)
    @GetMapping("deleteCalenderAdjust")
    public ResultEntity deleteCalenderAdjust(@RequestParam("id") String id) throws ParseException {
        return BasicResponseUtils.success(calenderAdjustService.deleteCalenderAdjust(Long.parseLong(id)));
    }


}
