package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.HistoryService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "3.教师月度考勤")
@ApiSupport(order = 3)
@RestController
@RequestMapping("history")
public class HistoryController {

    @Resource
    HistoryService historyService;

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
}
