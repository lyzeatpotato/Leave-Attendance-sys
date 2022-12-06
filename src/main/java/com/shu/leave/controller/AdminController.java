package com.shu.leave.controller;

import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.AdminService;
import com.shu.leave.utils.BasicResponseUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "2.管理员相关")
@ApiSupport(order = 2)
@RestController
@RequestMapping("admin_info")
public class AdminController {

    @Resource
    AdminService adminService;

    @ApiOperation(value = "新增管理数据", notes = "要求完整输入全部管理信息 [userid, username]")
    @ApiOperationSupport(order = 1)
    @GetMapping("addAdmin")
    public ResultEntity addAdmin(@RequestParam("userid") String Adminid, @RequestParam("username") String Username){
        String[] param = new String[]{Adminid, Username};
        return BasicResponseUtils.success(adminService.addAdmin(param));
    }

    @ApiOperation(value = "删除管理数据", notes = "根据用户主键id，逻辑删除管理数据（数据保留在数据库中，但查询全部管理数据时看不到）")
    @ApiOperationSupport(order = 2)
    @GetMapping("deleteAdmin")
    public ResultEntity deleteAdmin(@RequestParam("id") Long Adminid){
        return BasicResponseUtils.success(adminService.deleteLogicallyAdmin(Adminid));
    }

    @ApiOperation(value = "查询全部管理数据", notes = "显示全体未被逻辑删除的管理数据")
    @ApiOperationSupport(order = 3)
    @GetMapping("findAllAdmin")
    public ResultEntity findAllAdmin(){
        return BasicResponseUtils.success(adminService.findAllAdmin());
    }

    @ApiOperation(value = "根据主键查询管理数据", notes = "显示id条件下的管理数据详细信息")
    @ApiOperationSupport(order = 4)
    @GetMapping("findAdminById")
    public ResultEntity findAllAdmin(@RequestParam("id") Long Adminid){
        return BasicResponseUtils.success(adminService.findAdminById(Adminid));
    }

    @ApiOperation(value = "修改管理员信息", notes = "要求完整输入修改后的管理信息 [id, userid, username]")
    @ApiOperationSupport(order = 5)
    @GetMapping("updateAdmin")
    public ResultEntity updateUser( @RequestParam("id") String id,
                                    @RequestParam("userid") String userid, @RequestParam("username") String username){

        String[] param = new String[]{id, userid, username};
        return BasicResponseUtils.success(adminService.updateAdmin(param));
    }
}
