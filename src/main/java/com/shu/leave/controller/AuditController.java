package com.shu.leave.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.entity.Leave;
import com.shu.leave.service.AuditService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "分页展示审核页请假列表信息", notes = "根据传入用户工号显示对应权限看到的请假详情信息")
    @ApiOperationSupport(order = 2)
    @GetMapping("getAuditLoadingDataByUserid")
    public ResultEntity getAuditLoadingDataByUserid(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam("userid") String userId){
        Page<Leave> page = new Page<>(pageNum, 10);         // 当前页为传入的参数，默认每页显示10条数据
        return BasicResponseUtils.success(auditService.getAuditDataListByUserid(page, userId));
    }

}
