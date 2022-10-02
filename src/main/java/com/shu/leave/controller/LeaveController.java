package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@Api(tags = "2.请假申请表")
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
}
