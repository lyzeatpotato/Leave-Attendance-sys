package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.CalenderService;
import com.shu.leave.service.HistoryService;
import com.shu.leave.utils.BasicResponseUtils;
import com.shu.leave.utils.UnitedUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Api(tags = "3.教师月度考勤")
@ApiSupport(order = 3)
@RestController
@RequestMapping("history")
public class HistoryController {

    @Resource
    HistoryService historyService;
    @Resource
    CalenderService calenderService;

    @ApiOperation(value = "新增一条考勤历史记录", notes = "在每次用户提交完请假申请表后就执行插入，按年-月对应")
    @ApiOperationSupport(order = 1)
    @PostMapping("addHistory")
    public ResultEntity addUserAbsenceHistory(@RequestParam() Map<String, String> historyJson) {
        String[] param = new String[] {historyJson.get("userid"), historyJson.get("shijia"), historyJson.get("bingjia"), historyJson.get("hunjia"), historyJson.get("shengyujia"),
                                        historyJson.get("tanqinjia"), historyJson.get("sangjia"), historyJson.get("gongshang"), historyJson.get("kuanggongjia"), historyJson.get("inactive")};
        return BasicResponseUtils.success(historyService.addUserLeaveHistory(param));
    }

    @ApiOperation(value = "用户历史请假记录", notes = "根据用户工号，查询某用户的历史请假记录")
    @ApiOperationSupport(order = 2)
    @GetMapping("findUserHitoryByUserId")
    public ResultEntity findUserHistoryByUserId(@RequestParam("userid") String userId) {
        return BasicResponseUtils.success(historyService.findHistoryByUserId(userId));
    }

    @ApiOperation(value = "用户某年月考勤记录", notes = "根据用户工号，查询某用户在某年某月度下的请假记录")
    @ApiOperationSupport(order = 3)
    @GetMapping("findUserHistoryYearMonth")
    public ResultEntity findUserYearMonth(@RequestParam("userid") String userId, @RequestParam("year") String year, @RequestParam("month") String month) {
        return BasicResponseUtils.success(historyService.findHistoryByUserIdYearMonth(userId, year, month));
    }

    @ApiOperation(value = "用户年度请假类型统计", notes = "根据用户工号，查询用户某年某种请假类型下的总天数")
    @ApiOperationSupport(order = 4)
    @GetMapping("sumHistoryLeaveType")
    public ResultEntity sumHistoryLeaveType(@RequestParam("userid") String userId, @RequestParam("year") String year, @RequestParam("type") String leaveType) {
        return BasicResponseUtils.success(historyService.sumHistoryLeaveType(userId, year, leaveType));
    }

    @ApiOperation(value = "获取实际请假时长参考值", notes = "根据请假开始时间,请假结束时间判断")
    @ApiOperationSupport(order = 5)
    @GetMapping("getCurrentLeaveDays")
    public ResultEntity getCurrentLeaveDays(@RequestParam("leave-start-time") String leaveStartTime, @RequestParam("leave-end-time") String leaveEndTime,
                                            @RequestParam("leave-type") String leaveType) throws ParseException {
        int dayDiffer = 0;
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = df0.parse(leaveStartTime);
        Date endDate = df0.parse(leaveEndTime);
        if (leaveType.equals("事假") || leaveType.equals("丧假")) {
            // 事假、丧假 判别公休日和法定节假日
            int holidayExtends = calenderService.totalExtendHolidays(startDate, endDate);   // 遇到法定节假日需要顺延的天数
            if (holidayExtends != -1) {     // =>此处判断不等于-1是确认用户选择的请假范围未被某一个假期范围所包含，如被包含则不记录请假时长(dayDiffer=0)
                // 请假天数=当前申请天数-遇到公休/法定节假日顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate) - holidayExtends;
            }
        } else if (leaveType.equals("婚假") || leaveType.equals("产假") || leaveType.equals("陪产假")) {
            // 婚假、产假、陪产假 判别法定节假日和寒暑假
            int holidayExtends = calenderService.totalExtendHolidays(startDate, endDate);   // 遇到法定节假日需要顺延的天数
            int vocationExtends = calenderService.totalExtendVocation(startDate, endDate);  // 遇到寒暑假需要顺延的天数
            if (holidayExtends != -1 && vocationExtends != -1) {
                // 请假天数=当前申请天数-遇到法定节假日/寒暑假顺延的天数
                dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate) - holidayExtends - vocationExtends;
            }
        } else {
            dayDiffer = UnitedUtils.getDayDiffer(startDate, endDate);
        }
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        respMap.put("real-leave-days", String.valueOf(dayDiffer));
        return BasicResponseUtils.success(respMap);
    }
}
