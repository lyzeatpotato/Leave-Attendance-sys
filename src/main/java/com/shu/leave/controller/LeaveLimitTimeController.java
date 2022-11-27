package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.entity.LeaveAuditLimitTime;
import com.shu.leave.service.LeaveLimitTimeService;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:王仕杰
 */
@Api(tags = "6.请假时间限制")
@ApiSupport(order = 6)
@RestController
@RequestMapping("leave_audit_limit_time")
public class LeaveLimitTimeController {
    @Resource
    LeaveLimitTimeService limitTimeService;

    @ApiOperation(value = "查询所有请假类型最大天数——系统默认")
    @ApiOperationSupport(order = 10)
    @GetMapping("findAllLimitTimeBySystem")
    public ResultEntity findAllLimitTimeBySystem(){
        return BasicResponseUtils.success(limitTimeService.findAllLimitTimeBySystem());
    }

    @ApiOperation(value = "查询请假类型最大天数——按照role_id")
    @ApiOperationSupport(order = 11)
    @GetMapping("findAllLimitTimeByRoleId")
    public ResultEntity findAllLimitTimeBySystem(@RequestParam("role_id")String roleId){
        if (Long.parseLong(roleId)==2) {
            List<LeaveAuditLimitTime> leaveLimitTimesList= limitTimeService.findAllLimitTimeByRoleId(roleId);
            leaveLimitTimesList.addAll(limitTimeService.findAllLimitTimeByRoleId(Integer.toString(3)));
            return BasicResponseUtils.success(leaveLimitTimesList);
        }
        if (Long.parseLong(roleId)==4) {
            List<LeaveAuditLimitTime> leaveLimitTimesList= limitTimeService.findAllLimitTimeByRoleId(roleId);
            leaveLimitTimesList.addAll(limitTimeService.findAllLimitTimeByRoleId(Integer.toString(5)));
            return BasicResponseUtils.success(leaveLimitTimesList);
        }
        return BasicResponseUtils.success(limitTimeService.findAllLimitTimeByRoleId(roleId));
    }

    @ApiOperation(value = "修改请假类型最大天数")
    @ApiOperationSupport(order = 12)
    @GetMapping("UpdateLimitTimeById")
    public ResultEntity UpdateLimitTimeById(@RequestParam("id")String id,
                                            @RequestParam("type")String type,
                                            @RequestParam("limittime")String limitTime){
        String[] params = new String[]{id,type,limitTime};
        return BasicResponseUtils.success(limitTimeService.updateLimitTimeById(params));
    }
}
