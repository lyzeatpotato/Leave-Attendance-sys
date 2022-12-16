package com.shu.leave.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.SchoolDepartmentService;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "10.校领导分管部门")
@ApiSupport(order = 10)
@RestController
@RequestMapping("school_department")
public class SchoolDepartmentController {
    @Resource
    SchoolDepartmentService schoolDepartmentService;

    @ApiOperation(value = "全校校领导分管部门")
    @ApiOperationSupport(order = 1, author = "lyz")
    @GetMapping("getAllSchoolDeptMsg")
    public ResultEntity getAllSchoolDeptMsg() {
        return BasicResponseUtils.success(schoolDepartmentService.getAllSchoolDeptMsg());
    }

    @ApiOperation(value = "查询校领导负责的部门", notes = "根据school_leader_id查询")
    @ApiOperationSupport(order = 2, author = "lg")
    @GetMapping("findSchoolDepartmentById")
    public ResultEntity findSchoolDepartmentById(@RequestParam("school_leader_id") String school_leader_id) {
        // 对前端传入数据做数据类型转换
        return BasicResponseUtils.success(schoolDepartmentService.findSchoolDepartmentById(school_leader_id));
    }

}
