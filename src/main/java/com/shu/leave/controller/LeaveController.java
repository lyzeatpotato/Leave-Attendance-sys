package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.LeaveService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@Api(tags = "2.请假申请表")
@ApiSupport(order = 2)
@RestController
@RequestMapping("leave")
public class LeaveController {

    @Resource
    LeaveService leaveService;

    @ApiOperation(value = "新增请假申请表信息", notes = "要求一次性传入请假表单参数 [userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, status, department_status, hr_status, school_status]")
    @ApiOperationSupport(order = 1)
    @PostMapping("addLeaveForm")
    public ResultEntity addLeaveForm(@RequestParam("userid") String userId, @RequestParam("leave_type") String leaveType,
                                     @RequestParam("leave_start_time") String leaveStartTime, @RequestParam("leave_end_time") String leaveEndTime,
                                     @RequestParam("leave_reason") String leaveReason, @RequestParam("leave_material") String leaveMaterial,
                                     @RequestParam("status") String status, @RequestParam("department_status") String departmentStatus,
                                     @RequestParam("hr_status") String hrStatus, @RequestParam("school_status") String schoolStatus) throws ParseException {
        String[] params = new String[]{userId, leaveType, leaveStartTime, leaveEndTime, leaveReason, leaveMaterial, status, departmentStatus, hrStatus, schoolStatus};
        return BasicResponseUtils.success(leaveService.addLeaveForm(params));
    }

    @ApiOperation(value = "查询全部请假表单信息", notes = "一次性显示数据库中全部未被删除的用户请假申请表")
    @ApiOperationSupport(order = 2)
    @GetMapping("findAllLeaveForm")
    public ResultEntity findAllLeaveForm() {
        return BasicResponseUtils.success(leaveService.findAllLeaveForm());
    }

    @ApiOperation(value = "分页查询请假表单信息", notes = "默认分页显示，每页至多10条记录")
    @ApiOperationSupport(order = 3)
    @GetMapping("findAllLeaveFormPagination")
    public ResultEntity findAllLeaveFormPagination() {
        return BasicResponseUtils.success(leaveService.findAllLeaveForm());
    }

    @ApiOperation(value = "查询请假表单详情", notes = "根据请假申请表主键id查询表单详情内容")
    @ApiOperationSupport(order = 4)
    @GetMapping("findLeaveFormById")
    public ResultEntity findLeaveFormById(@RequestParam("id") String id) {
        // 对前端传入数据做数据类型转换
        Long formId = Long.valueOf(id);
        return BasicResponseUtils.success(leaveService.findLeaveFormById(formId));
    }

    @ApiOperation(value = "查询用户对应的请假表列表", notes = "根据用户id查询该用户提交的全部请假表列表")
    @ApiOperationSupport(order = 5)
    @GetMapping("findLeaveFormByUserid")
    public ResultEntity findLeaveFormByUserid(@RequestParam("userid") String userid) {
        // 对前端传入数据做数据类型转换
        Long userId = Long.valueOf(userid);
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserid(userId));
    }
}
