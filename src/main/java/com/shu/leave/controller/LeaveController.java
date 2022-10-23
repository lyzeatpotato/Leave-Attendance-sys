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
import java.util.Map;

import static com.shu.leave.common.ResponseCodeEnums.RANGE_ERROR;

@Api(tags = "2.请假申请表")
@ApiSupport(order = 2)
@RestController
@RequestMapping("leave")
public class LeaveController {

    @Resource
    LeaveService leaveService;


    @ApiOperation(value = "新增请假申请表信息", notes = "所需参数[userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material]")
    @ApiOperationSupport(order = 1)
    @PostMapping("addLeaveForm")
    public ResultEntity addLeaveForm(@RequestParam("userid") String userId, @RequestParam("leave-type") String leaveType,
                                     @RequestParam("leave-start-time") String leaveStartTime, @RequestParam("leave-end-time") String leaveEndTime,
                                     @RequestParam("leave-reason") String leaveReason, @RequestParam("leave-material") String leaveMaterial) throws ParseException {

        String[] params = new String[]{userId, leaveType, leaveStartTime, leaveEndTime, leaveReason, leaveMaterial};
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

    @ApiOperation(value = "根据工号查询用户的全部请假记录", notes = "传入用户工号")
    @ApiOperationSupport(order = 5)
    @GetMapping("findLeaveFormByUserid")
    public ResultEntity findLeaveFormByUserid(@RequestParam("userid") String userid) {
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserid(userid));
    }

//    @ApiOperation(value = "查询部门下的请假表列表", notes = "传入对应部门和查询type：type=0,查询全部；type=1,查询部门下人事处尚未审核记录；type=2,查询部门下校领导尚未审核记录")
//    @ApiOperationSupport(order = 6)
//    @GetMapping("findLeaveFormByDept")
//    public ResultEntity findLeaveFormByDept(@RequestParam("department") String department, @RequestParam("type") int type){
//        if(type == 0){
//            return BasicResponseUtils.success(leaveService.findLeaveFormByUserDept(department));
//        }else if(type == 1){
//            return BasicResponseUtils.success(leaveService.findLeaveFormByUserDeptAndUnfinishedHR(department));
//        }else if(type == 2){
//            return BasicResponseUtils.success(leaveService.findLeaveFormByUserDeptAndUnfinishedSchool(department));
//        }else {
//            return BasicResponseUtils.error(RANGE_ERROR);
//        }
//    }

    @ApiOperation(value = "查询部门下的请假表列表", notes = "传入对应部门")
    @ApiOperationSupport(order = 6)
    @GetMapping("findLeaveFormByDept")
    public ResultEntity findLeaveFormByDept(@RequestParam("department") String department){
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserDept(department));
    }

    @ApiOperation(value = "查询部门下需要人事处审核，但尚未审核请假记录", notes = "传入对应部门")
    @ApiOperationSupport(order = 7)
    @GetMapping("findLeaveFormByDeptAndUnfinishedHR")
    public ResultEntity findLeaveFormByDeptAndUnfinishedHR(@RequestParam("department") String department){
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserDeptAndUnfinishedHR(department));
    }

    @ApiOperation(value = "查询部门下需要校领导审核，但尚未审核请假记录", notes = "传入对应部门")
    @ApiOperationSupport(order = 8)
    @GetMapping("findLeaveFormByDeptAndUnfinishedSchool")
    public ResultEntity findLeaveFormByDeptAndUnfinishedSchool(@RequestParam("department") String department){
        return BasicResponseUtils.success(leaveService.findLeaveFormByUserDeptAndUnfinishedSchool(department));
    }

    @ApiOperation(value = "查询全校范围内部门审核已完成，但人事处未审核的请假记录", notes = "传入对应部门")
    @ApiOperationSupport(order = 9)
    @GetMapping("findAllLeaveFormByUnfinishedHR")
    public ResultEntity findAllLeaveFormByUnfinishedHR(){
        return BasicResponseUtils.success(leaveService.findAllLeaveFormByUnfinishedHR());
    }
}
