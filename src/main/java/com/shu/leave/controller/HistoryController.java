package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.HistoryService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
