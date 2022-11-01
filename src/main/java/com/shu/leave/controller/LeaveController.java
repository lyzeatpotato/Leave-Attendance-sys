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


    @ApiOperation(value ="显示查询某个请假的详细信息", notes = "显示查询某个请假的详细信息")
    @ApiOperationSupport(order = 10)
    @GetMapping("SingleleaveDetail")
    public ResultEntity SingleleaveDetail(@RequestParam("role") String role,@RequestParam("yuanxi") String yuanxi,@RequestParam("id") long id){
        return BasicResponseUtils.success(leaveService.selectSingleLeave(role,yuanxi,id));
    }
    @ApiOperation(value ="查询某个步骤的信息", notes = "查询某个步骤的信息")
    @ApiOperationSupport(order = 11)
    @GetMapping("SingleleaveStep")
    public ResultEntity SingleleaveStep(@RequestParam("role") String role,@RequestParam("id") long id,@RequestParam("step") String step ){
        return BasicResponseUtils.success(leaveService.selectSingleLeaveStep(role,id,step));
    }
    @ApiOperation(value ="完成该部分审核", notes = "完成该部分审核")
    @ApiOperationSupport(order = 12)
    @GetMapping("SingleleaveAudit")
    public ResultEntity SingleleaveAudit(@RequestParam("role") String role, @RequestParam("userid") String userid, @RequestParam("id") long id, @Param("result") String result,@Param("recommend")String recommend){
        return BasicResponseUtils.success(leaveService.singleLeaveAudit(role,userid,id,result,recommend));
    }
    @ApiOperation(value = "查询用户名字对应的请假表列表", notes = "根据用户username查询该用户提交的全部请假表列表")
    @ApiOperationSupport(order = 13)
    @GetMapping("findLeaveFormByUsername")
    public ResultEntity findLeaveFormByUsername(@RequestParam("username") String username) {
        // 对前端传入数据做数据类型转换
        String userName = String.valueOf(username);
        return BasicResponseUtils.success(leaveService.findLeaveFormByUsername(userName));
    }
    @ApiOperation(value = "查询需要部门审核，且根据工号查询的请假列表", notes = "根据id查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 14)
    @GetMapping("findLeaveFormByUseridInDept")
    public ResultEntity findLeaveFormByUseridInDept(@RequestParam("userid") String userid,@RequestParam("department") String department) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUseridInDept(userid,department));
    }
    @ApiOperation(value = "查询需要部门审核，且根据名字查询的请假列表", notes = "根据name查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 15)
    @GetMapping("findLeaveFormByUsernameInDept")
    public ResultEntity findLeaveFormByUsernameInDept(@RequestParam("username") String username,@RequestParam("department") String department) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUsernameInDept(username,department));
    }
    @ApiOperation(value = "查询需要人事处审核，且根据工号查询的请假列表", notes = "根据id查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 16)
    @GetMapping("findLeaveFormByUseridInHR")
    public ResultEntity findLeaveFormByUseridInHR(@RequestParam("userid") String userid) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUseridInHR(userid));
    }
    @ApiOperation(value = "查询需要人事处审核，且根据名字查询的请假列表", notes = "根据name查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 17)
    @GetMapping("findLeaveFormByUsernameInHR")
    public ResultEntity findLeaveFormByUsernameInHR(@RequestParam("username") String username) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUsernameInHR(username));
    }
    @ApiOperation(value = "查询需要校领导审核，且根据工号查询的请假列表", notes = "根据id查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 18)
    @GetMapping("findLeaveFormByUseridInSchool")
    public ResultEntity findLeaveFormByUseridInSchool(@RequestParam("userid") String userid,@RequestParam("department") String department) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUseridInSchool(userid,department));
    }
    @ApiOperation(value = "查询需要校领导审核，且根据名字查询的请假列表", notes = "根据name查询部门需要审核的请假表列表")
    @ApiOperationSupport(order = 19)
    @GetMapping("findLeaveFormByUsernameInSchool")
    public ResultEntity findLeaveFormByUsernameInSchool(@RequestParam("username") String username,@RequestParam("department") String department) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(leaveService.findLeaveFormByUsernameInSchool(username,department));
    }

}
