package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
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

@Api(tags = "6.请假时间限制")
@ApiSupport(order = 6)
@RestController
@RequestMapping("leave_audit_limit_time")
public class LeaveLimitTimeController {
    @Resource
    LeaveLimitTimeService limitTimeService;


    @ApiOperation(value = "查询请假类型最大天数——系统默认")
    @ApiOperationSupport(order = 10)
    @GetMapping("findAllLimitTimeBySystem")
    public ResultEntity findAllLimitTimeBySystem(){
        return BasicResponseUtils.success(limitTimeService.findAllLimitTimeBySystem());
    }

    @ApiOperation(value = "查询请假类型最大天数——按照role_id")
    @ApiOperationSupport(order = 11)
    @GetMapping("findAllLimitTimeByRoleId")
    public ResultEntity findAllLimitTimeBySystem(@RequestParam("roleid")String roleId){
        return BasicResponseUtils.success(limitTimeService.findAllLimitTimeByRoleId(roleId));
    }
}
