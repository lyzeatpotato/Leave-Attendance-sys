package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.CalenderAdjustService;
import com.shu.leave.service.CalenderService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "8.校历维护")
@ApiSupport(order = 8)
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

    @ApiOperation(value = "删除一条校历假期数据", notes = "要求给出要删除的id ")
    @ApiOperationSupport(order = 3)
    @GetMapping("deleteCalender")
    public ResultEntity deleteCalender(@RequestParam("id") String id) throws ParseException {
        return BasicResponseUtils.success(calenderService.deleteCalender(Long.parseLong(id)));
    }

    @ApiOperation(value = "查询全部校历数据", notes = "显示全部未被逻辑删除的用户信息 ")
    @ApiOperationSupport(order = 4)
    @GetMapping("findAllCalender")
    public ResultEntity findAllCalenderForm(@RequestParam("year") String year)  {
        return BasicResponseUtils.success(calenderService.findAllCalender(year));
    }

    @ApiOperation(value = "调休查询", notes = "根据所给的假期id显示对应假期的调休安排 ")
    @ApiOperationSupport(order = 5)
    @GetMapping("findAdjustById")
    public ResultEntity findAdjustById(@RequestParam("id") String calenderid)  throws ParseException{
        return BasicResponseUtils.success(calenderAdjustService.findAdjustById(Long.parseLong(calenderid)));
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

    @ApiOperation(value = "逻辑删除一条请假调休数据", notes = "要求给出要删除的id ")
    @ApiOperationSupport(order = 8)
    @GetMapping("deleteCalenderAdjust")
    public ResultEntity deleteCalenderAdjust(@RequestParam("id") String id) throws ParseException {
        return BasicResponseUtils.success(calenderAdjustService.deleteCalenderAdjust(Long.parseLong(id)));
    }

    @ApiOperation(value = "教学周识别", notes = "判断所输入的日期为哪一学期的第几周")
    @ApiOperationSupport(order = 9)
    @GetMapping("checkTeachingDate")
    public ResultEntity checkTeachingDate(@RequestParam("checking_date") String checkingDate) throws ParseException {
        Map<String, String> resMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = simpleDateFormat.parse(checkingDate);
        resMap.put("dateIndex", calenderService.getCurrentExcateDate(inputDate));
        return BasicResponseUtils.success(resMap);
    }

    @ApiOperation(value = "系统参考的实际请假天数", notes = "时间格式yyyy-MM-DD HH:MM:ss")
    @ApiOperationSupport(order = 10, author = "lyz")
    @GetMapping("getReferenceLeaveDay")
    public ResultEntity getReferenceLeaveDay(@ApiParam(name = "leave_start_time", value = "请假开始日期", required = true) @RequestParam("leave_start_time") String startTime,
                                             @ApiParam(name = "leave_end_time", value = "请假结束日期", required = true) @RequestParam("leave_end_time") String endTime,
                                             @ApiParam(name = "leave_type", value = "请假类型", required = true) @RequestParam("leave_type") String leaveType) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
        Date sTime = sf.parse(startTime);
        Date eTime = sf.parse(endTime);
        return BasicResponseUtils.success(calenderService.realLeaveDayDiffer(sTime, eTime, leaveType));
    }

}
