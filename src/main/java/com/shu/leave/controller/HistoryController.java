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
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Api(tags = "7.考勤相关")
@ApiSupport(order = 7)
@RestController
@RequestMapping("history")
public class HistoryController {

    @Resource
    HistoryService historyService;
    @Resource
    CalenderService calenderService;

    @ApiOperation(value = "新增一条考勤历史记录", notes = "在每次用户提交完请假申请表后就执行插入，按年-月对应")
    @ApiOperationSupport(order = 1, author = "lyz")
    @PostMapping("addHistory")
    public ResultEntity addUserAbsenceHistory(@RequestParam() Map<String, String> historyJson) {
        String[] param = new String[] {historyJson.get("userid"), historyJson.get("shijia"), historyJson.get("bingjia"), historyJson.get("hunjia"), historyJson.get("shengyujia"),
                                        historyJson.get("tanqinjia"), historyJson.get("sangjia"), historyJson.get("gongshang"), historyJson.get("kuanggongjia"), historyJson.get("inactive")};
        return BasicResponseUtils.success(historyService.addUserLeaveHistory(param));
    }

    @ApiOperation(value = "用户个人：请假记录统计", notes = "根据用户工号，查询某用户的历史请假记录")
    @ApiOperationSupport(order = 2, author = "lyz")
    @GetMapping("findUserHitoryByUserId")
    public ResultEntity findUserHistoryByUserId(@RequestParam("userid") String userId) {
        return BasicResponseUtils.success(historyService.findHistoryByUserId(userId));
    }

    @ApiOperation(value = "用户个人：查询某年月请假记录统计", notes = "根据用户工号，查询某用户在某年某月度下的请假记录")
    @ApiOperationSupport(order = 3, author = "lyz")
    @GetMapping("findUserHistoryYearMonth")
    public ResultEntity findUserYearMonth(@RequestParam("userid") String userId, @RequestParam("year") String year, @RequestParam("month") String month) {
        return BasicResponseUtils.success(historyService.findHistoryByUserIdYearMonth(userId, year, month));
    }

    @ApiOperation(value = "用户个人：查询年度请假类型总数", notes = "根据用户工号，查询用户某年某种请假类型下的总天数")
    @ApiOperationSupport(order = 4, author = "lyz")
    @GetMapping("sumHistoryLeaveType")
    public ResultEntity sumHistoryLeaveType(@RequestParam("userid") String userId, @RequestParam("year") String year, @RequestParam("type") String leaveType) {
        return BasicResponseUtils.success(historyService.sumHistoryLeaveType(userId, year, leaveType));
    }

    @ApiOperation(value = "用户个人：系统请假时长参考值", notes = "根据请假开始时间,请假结束时间判断")
    @ApiOperationSupport(order = 5, author = "lyz")
    @GetMapping("getCurrentLeaveDays")
    public ResultEntity getCurrentLeaveDays(@RequestParam("leave-start-time") String leaveStartTime, @RequestParam("leave-end-time") String leaveEndTime,
                                            @RequestParam("leave-type") String leaveType) throws ParseException {
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = df0.parse(leaveStartTime);
        Date endDate = df0.parse(leaveEndTime);
        int dayDiffer = calenderService.realLeaveDayDiffer(startDate, endDate, leaveType);
        Map<String, String> respMap = new LinkedHashMap<>();
        respMap.put("real-leave-days", String.valueOf(dayDiffer));
        return BasicResponseUtils.success(respMap);
    }

    @ApiOperation(value = "部门管理：某月缺勤日期列表", notes = "返回某月某部门下有缺勤记录的日期列表")
    @ApiOperationSupport(order = 6, author = "lyz")
    @GetMapping("getMonthDeptAbsenceDate")
    public ResultEntity getMonthDeptAbsenceDate(@ApiParam(name = "year", value = "年度信息", required = true) @RequestParam("year") String year,
                                                @ApiParam(name = "month", value = "月度信息", required = true) @RequestParam("month") String month,
                                                @ApiParam(name = "dept", value = "部门信息", required = true) @RequestParam("dept") String dept) {
        return BasicResponseUtils.success(historyService.getMonthDeptAbsenceDate(year, month, dept));
    }
}
