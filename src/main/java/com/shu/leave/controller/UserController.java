package com.shu.leave.controller;

import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.UserService;
import com.shu.leave.utils.BasicResponseUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "1.用户相关")
@ApiSupport(order = 1)
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation(value = "新增用户", notes = "要求完整输入全部用户信息 [userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 1)
    @PostMapping("addUser")
    public ResultEntity addUser(@RequestParam("userid") String userid, @RequestParam("username") String username,
                                @RequestParam("yuanxi") String yuanxi, @RequestParam("ptype") String ptype,
                                @RequestParam("pstatus") String pstatus,  @RequestParam("gender") String gender){
        String[] param = new String[]{userid, username, yuanxi, ptype, pstatus, gender};
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
    public ResultEntity findUserById(@RequestParam("id") String userid){
        System.out.println(userid);
        // 进行前端传入数据的类型转换
        Long userId = Long.valueOf(userid);
        return BasicResponseUtils.success(userService.findUserById(userId));
    }

    @ApiOperation(value = "修改用户信息", notes = "要求完整输入修改后的用户信息 [id, userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 5)
    @GetMapping("updateUser")
    public ResultEntity updateUser( @RequestParam("id") String id,
                                    @RequestParam("userid") String userid, @RequestParam("username") String username,
                                    @RequestParam("yuanxi") String yuanxi, @RequestParam("ptype") String ptype,
                                    @RequestParam("pstatus") String pstatus,  @RequestParam("gender") String gender){

        String[] param = new String[]{id, userid, username, yuanxi, ptype, pstatus, gender};
        return BasicResponseUtils.success(userService.updateUser(param));
    }


}
