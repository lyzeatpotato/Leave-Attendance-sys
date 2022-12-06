package com.shu.leave.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.entity.Revoke;
import com.shu.leave.service.RevokeService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.text.ParseException;

/**
 * @ClassName: RevokeController
 * @Description: 销假相关接口类
 * @author: lyz
 * @date: 2022 11 2022/11/30 21:35
 */

@Api(tags = "4.销假申请")
@ApiSupport(order = 4)
@RestController
@RequestMapping("revoke")
public class RevokeController {

    @Autowired
    RevokeService revokeService;

    @ApiOperation(value = "新增销假记录", notes = "传入销假记录相关信息")
    @ApiOperationSupport(order = 1, author = "lyz")
    @PostMapping("addRevokeRecord")
    public ResultEntity addRevokeRecord(@ApiParam(name = "leave_id", value = "请假表单主键", required = true) @RequestParam("leave_id") String formId,
                                        @ApiParam(name = "user_id", value = "提交销假申请的用户工号", required = true) @RequestParam("user_id") String userId,
                                        @ApiParam(name = "revoke_report_time", value = "教师返校报道销假时间", required = true) @RequestParam("revoke_report_time") String revokeReportTime,
                                        @ApiParam(name = "revoke_submit_time", value = "销假申请提交时间", required = true) @RequestParam("revoke_submit_time") String revokeSubmitTime) throws ParseException {
        String[] params = new String[] {formId, revokeReportTime, revokeSubmitTime, userId};
        return BasicResponseUtils.success(revokeService.addRevokeRecord(params));
    }

    @ApiOperation(value = "撤销销假申请", notes = "输入销假申请主键后撤销销假申请")
    @ApiOperationSupport(order = 2, author = "lyz")
    @GetMapping("undoRevoke")
    public ResultEntity undoRevoke(@ApiParam(name = "revoke_form_id", value = "销假申请主键", required = true) @RequestParam("revoke_form_id") Long revokeId) {
        return BasicResponseUtils.success(revokeService.undoRevoke(revokeId));
    }

    @ApiOperation(value = "分页显示个人销假列表", notes = "传入个人工号")
    @ApiOperationSupport(order = 3, author = "lyz")
    @GetMapping("getRevokeListByUserId")
    public ResultEntity getRevokeListByUserId(@ApiParam(name = "pageNum", value = "分页编号", required = true) @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @ApiParam(name = "user_id", value = "用户工号", required = true) @RequestParam("user_id") String userId) {
        Page<Revoke> page = new Page<>(pageNum, 10);
        return BasicResponseUtils.success(revokeService.getRevokeListByUserId(page, userId));
    }

    @ApiOperation(value = "获取销假申请详情信息", notes = "销假申请表主键")
    @ApiOperationSupport(order = 4, author = "lyz")
    @GetMapping("getRevokeDetailById")
    public ResultEntity getRevokeDetailById(@ApiParam(name = "revoke_id", value = "销假表单主键", required = true) @RequestParam("revoke_id") Long revokeId) {
        return BasicResponseUtils.success(revokeService.getRevokeDetailById(revokeId));
    }

    @ApiOperation(value = "查找请假申请对应的销假记录", notes = "请假申请表主键")
    @ApiOperationSupport(order = 5, author = "lyz")
    @GetMapping("findRevokeByLeaveId")
    public ResultEntity findRevokeByLeaveId(@ApiParam(name = "leave_id", value = "请假表单主键", required = true) @RequestParam("leave_id") Long leaveId) {
        return BasicResponseUtils.success(revokeService.findRevokeByLeaveId(leaveId));
    }


}
