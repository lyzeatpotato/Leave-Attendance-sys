package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.RevokeService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @ClassName: RevokeController
 * @Description: 销假相关接口类
 * @author: lyz
 * @date: 2022 11 2022/11/30 21:35
 */

@Api(tags = "10.销假相关")
@ApiSupport(order = 10)
@RestController
@RequestMapping("revoke")
public class RevokeController {

    @Autowired
    RevokeService revokeService;

    @ApiOperation(value = "新增销假记录", notes = "传入销假记录相关信息")
    @ApiOperationSupport(order = 1, author = "lyz")
    @PostMapping("addRevokeRecord")
    public ResultEntity addRevokeRecord(@RequestParam("leave_form_id") String formId, @RequestParam("user_id") String userId,
                                        @RequestParam("revoke_report_time") String revokeReportTime, @RequestParam("revoke_submit_time") String revokeSubmitTime) throws ParseException {
        String[] params = new String[] {formId, revokeReportTime, revokeSubmitTime, userId};
        return BasicResponseUtils.success(revokeService.addRevokeRecord(params));
    }

    @ApiOperation(value = "撤销销假申请")
    @ApiOperationSupport(order = 2, author = "lyz")
    @PostMapping("undoRevoke")
    public ResultEntity undoRevoke(@RequestParam("revoke_form_id") Long revokeId) {
        return BasicResponseUtils.success(revokeService.undoRevoke(revokeId));
    }

    @ApiOperation(value = "根据工号获取个人销假列表", notes = "传入个人工号")
    @ApiOperationSupport(order = 3, author = "lyz")
    @PostMapping("getRevokeListByUserId")
    public ResultEntity getRevokeListByUserId(@RequestParam("user_id") String userId) {
        return BasicResponseUtils.success(revokeService.getRevokeListByUserId(userId));
    }
}
