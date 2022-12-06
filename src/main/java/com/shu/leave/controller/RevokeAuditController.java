package com.shu.leave.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.entity.Revoke;
import com.shu.leave.service.RevokeAuditService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: RevokeAuditController
 * @Description: 销假申请相关接口
 * @author: lyz
 * @date: 2022 12 2022/12/6 21:36
 */

@Api(tags = "6.销假审核")
@ApiSupport(order = 6)
@RestController
@RequestMapping("revokeAudit")
public class RevokeAuditController {

    @Autowired
    RevokeAuditService revokeAuditService;

    @ApiOperation(value = "新增销假审核信息", notes = "所需参数[role,userid,id,result,recommend]")
    @ApiOperationSupport(order = 1, author = "lyz")
    @PostMapping("addRevokeAudit")
    public ResultEntity addRevokeAudit(@ApiParam(name = "role", value = "审核人员权限", required = true) @RequestParam("role") String role,
                                       @ApiParam(name = "user_id", value = "审核人员工号", required = true) @RequestParam("user_id") String userid,
                                       @ApiParam(name = "id", value = "销假申请表单主键", required = true) @RequestParam("id") String id,
                                       @ApiParam(name = "result", value = "审核结果（通过/不通过）", required = true) @Param("result") String result,
                                       @ApiParam(name = "recommend", value = "审核意见", required = true) @Param("recommend")String recommend){
        Long revokeId = Long.valueOf(id);
        return BasicResponseUtils.success(revokeAuditService.addRevokeAuditMsg(role, userid, revokeId, result, recommend));
    }

    @ApiOperation(value = "审核页默认请假列表接口", notes = "根据传入用户工号显示对应权限看到的请假详情信息")
    @ApiOperationSupport(order = 2, author = "lyz")
    @GetMapping("getAuditLoadingDataByUserId")
    public ResultEntity getAuditLoadingDataByUserId(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam("userid") String userId){
        Page<Revoke> page = new Page<>(pageNum, 10);         // 当前页为传入的参数，默认每页显示10条数据
        return BasicResponseUtils.success(revokeAuditService.getAuditLoadingDataByUserId(page, userId));
    }


    @ApiOperation(value = "获取销假审核信息", notes = "根据销假审核进度返回当前审核详情信息")
    @ApiOperationSupport(order = 4, author = "lyz")
    @GetMapping("getCurrentRevokeAuditMsg")
    public ResultEntity getCurrentRevokeAuditMsg(@ApiParam(name = "revoke_id", value = "销假申请表主键id", required = true)
                                                     @RequestParam("revoke_id") Long revokeId) {
        return BasicResponseUtils.success(revokeAuditService.getCurrentRevokeAuditMsgById(revokeId));
    }
}
