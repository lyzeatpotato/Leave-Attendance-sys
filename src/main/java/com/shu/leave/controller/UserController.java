package com.shu.leave.controller;

import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.UserService;
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

@Api(tags = "1.用户相关")
@ApiSupport(order = 1)
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation(value = "新增用户", notes = "要求完整输入全部用户信息 [userid, username, yuanxi, ptype, pstatus, password, email, telephone]")
    @ApiOperationSupport(order = 1)
    @GetMapping("addUser")
    public ResultEntity addUser(@RequestParam("userid") String userid, @RequestParam("username") String username,
                                @RequestParam("yuanxi") String yuanxi, @RequestParam("ptype") String ptype,
                                @RequestParam("pstatus") String pstatus,  @RequestParam("password") String password,
                                @RequestParam("email") String email, @RequestParam("telephone") String telephone){
        String[] param = new String[]{userid, username, yuanxi, ptype, pstatus, password, email, telephone};
        return BasicResponseUtils.success(userService.addUser(param));
    }

    @ApiOperation(value = "删除用户", notes = "根据用户主键id，逻辑删除用户（数据保留在数据库中，但查询全部用户时看不到）")
    @ApiOperationSupport(order = 2)
    @GetMapping("deleteUser")
    public ResultEntity deleteUser(@RequestParam("id") Long userid){
        return BasicResponseUtils.success(userService.deleteLogicallyUser(userid));
    }

    @ApiOperation(value = "查询全部用户", notes = "显示全体未被逻辑删除的用户信息")
    @ApiOperationSupport(order = 3)
    @GetMapping("findAllUser")
    public ResultEntity findAllUser(){
        return BasicResponseUtils.success(userService.findAllUser());
    }

    @ApiOperation(value = "根据主键查询用户", notes = "显示id条件下的用户详细信息")
    @ApiOperationSupport(order = 4)
    @GetMapping("findUserById")
    public ResultEntity findAllUser(@RequestParam("id") Long userid){
        return BasicResponseUtils.success(userService.findUserById(userid));
    }
}
