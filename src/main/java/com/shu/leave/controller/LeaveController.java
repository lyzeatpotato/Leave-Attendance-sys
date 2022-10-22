package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.BasicResponseUtils;
import com.shu.leave.vo.SingleLeaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@Api(tags = "2.部门审核查询")
@ApiSupport(order = 2)
@RestController
@RequestMapping("leave")
public class LeaveController {

    @Resource
    LeaveService leaveService;

    public ResultEntity addLeaveForm(@RequestParam("userid") String userId, @RequestParam("leave_type") String leaveType,
                                     @RequestParam("leave_start_time") String leaveStartTime, @RequestParam("leave_end_time") String leaveEndTime,
                                     @RequestParam("leave_reason") String leaveReason, @RequestParam("leave_material") String leaveMaterial,
                                     @RequestParam("status") String status, @RequestParam("department_status") String departmentStatus,
                                     @RequestParam("hr_status") String hrStatus, @RequestParam("school_status") String schoolStatus) throws ParseException {
        String[] params = new String[]{userId, leaveType, leaveStartTime, leaveEndTime, leaveReason, leaveMaterial, status, departmentStatus, hrStatus, schoolStatus};
        return BasicResponseUtils.success(leaveService.addLeaveForm(params));
    }

    @ApiOperation(value ="显示查询某个请假的详细信息", notes = "显示查询某个请假的详细信息")
    @ApiOperationSupport(order = 1)
    @GetMapping("SingleleaveDetail")
    public ResultEntity SingleleaveDetail(@RequestParam("role") String role,@RequestParam("yuanxi") String yuanxi,@RequestParam("id") long id){
        return BasicResponseUtils.success(leaveService.selectSingleLeave(role,yuanxi,id));
    }

    @ApiOperation(value ="查询某个步骤的信息", notes = "查询某个步骤的信息")
    @ApiOperationSupport(order = 2)
    @GetMapping("SingleleaveStep")
    public ResultEntity SingleleaveStep(@RequestParam("role") String role,@RequestParam("id") long id,@RequestParam("step") String step ){
        return BasicResponseUtils.success(leaveService.selectSingleLeaveStep(role,id,step));
    }
    @ApiOperation(value ="完成该部分审核", notes = "完成该部分审核")
    @ApiOperationSupport(order = 3)
    @GetMapping("SingleleaveAudit")
    public ResultEntity SingleleaveAudit(@RequestParam("role") String role, @RequestParam("userid") String userid, @RequestParam("id") long id, @Param("result") String result,@Param("recommend")String recommend){
        return BasicResponseUtils.success(leaveService.singleLeaveAudit(role,userid,id,result,recommend));
    }

}