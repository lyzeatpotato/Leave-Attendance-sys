package com.shu.leave.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.AuditService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "8.请假审核")
@ApiSupport(order = 8)
@RestController
@RequestMapping("audit")
public class AuditController {
    @Resource
    AuditService auditService;
    @ApiOperation(value = "审核请假单", notes = "所需参数[role,userid,id,result,recommend]")
    @ApiOperationSupport(order = 1)
    @PostMapping("SingleleaveAudit")
    public ResultEntity SingleleaveAudit(@RequestParam("role") String role, @RequestParam("userid") String userid,
                                         @RequestParam("id") String id, @Param("result") String result, @Param("recommend")String recommend){
        Long Id = Long.valueOf(id);
        return BasicResponseUtils.success(auditService.singleLeaveAudit(role,userid,Id,result,recommend));
    }

}
