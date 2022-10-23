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

    public ResultEntity addLeaveForm(@RequestParam("userid") String userId, @RequestParam("leave_type") String leaveType,
                                     @RequestParam("leave_start_time") String leaveStartTime, @RequestParam("leave_end_time") String leaveEndTime,
                                     @RequestParam("leave_reason") String leaveReason, @RequestParam("leave_material") String leaveMaterial,
                                     @RequestParam("status") String status, @RequestParam("department_status") String departmentStatus,
                                     @RequestParam("hr_status") String hrStatus, @RequestParam("school_status") String schoolStatus) throws ParseException {
        String[] params = new String[]{userId, leaveType, leaveStartTime, leaveEndTime, leaveReason, leaveMaterial, status, departmentStatus, hrStatus, schoolStatus};
        return BasicResponseUtils.success(leaveService.addLeaveForm(params));
    }
    @ApiOperation(value = "查询部门下的请假表列表", notes = "传入对应部门")
    @ApiOperationSupport(order = 1)
    @GetMapping("findLeaveFormByDept")
    public ResultEntity findLeaveFormByDept(@RequestParam("department") String department){
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserDept(department));
    }
    @ApiOperation(value = "查询用户名字对应的请假表列表", notes = "根据用户username查询该用户提交的全部请假表列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("findLeaveFormByUsername")
    public ResultEntity findLeaveFormByUsername(@RequestParam("username") String username) {
        // 对前端传入数据做数据类型转换
        String userName = String.valueOf(username);
        return BasicResponseUtils.success(leaveService.findLeaveFormByUsername(userName));
    }
    @ApiOperation(value = "查询用户工号对应的请假表列表", notes = "根据用户id查询该用户提交的全部请假表列表")
    @ApiOperationSupport(order = 3)
    @GetMapping("findLeaveFormByUserid")
    public ResultEntity findLeaveFormByUserid(@RequestParam("userid") String userid) {
        // 对前端传入数据做数据类型转换
        Long userId = Long.valueOf(userid);
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserid(userId));
    }
    @ApiOperation(value = "分页查询请假表单信息", notes = "默认分页显示，每页至多10条记录")
    @ApiOperationSupport(order = 4)
    @GetMapping("findAllLeaveFormPagination")
    public ResultEntity findAllLeaveFormPagination() {
        return BasicResponseUtils.success(leaveService.findAllLeaveFormPagination());
    }
    @ApiOperation(value = "查询请假表单详情", notes = "根据请假申请表主键id查询表单详情内容")
    @ApiOperationSupport(order = 5)
    @GetMapping("findLeaveFormById")
    public ResultEntity findLeaveFormById(@RequestParam("id") String id) {
        // 对前端传入数据做数据类型转换
        Long formId = Long.valueOf(id);
        return BasicResponseUtils.success(leaveService.findLeaveFormById(formId));
    }
}
