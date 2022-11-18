package com.shu.leave.controller;

import com.shu.leave.annotation.AuthToken;
import com.shu.leave.enums.ResponseCodeEnums;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.UserService;
import com.shu.leave.utils.BasicResponseUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "1.用户相关")
@ApiSupport(order = 1)
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation(value = "用户登录", notes = "username为root, password为123456。登录成功返回一个随即生成的token。")
    @PostMapping("login")
    public ResultEntity userLogin(@ApiIgnore HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        // 后续判定条件为:统一身份认证成功,根据返回的access_token与用户工号进行判别. 此处设置为假登录
        if (username.equals("root") && password.equals("123456")) {
            String token = JwtUtils.createJWT("1", username, 60000L * 60 * 24 * 30); // 有效时间 30 days
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return BasicResponseUtils.success(token);
        }
        return BasicResponseUtils.error(ResponseCodeEnums.LOGIN_ERROR);
    }

    @ApiOperation(value = "新增用户", notes = "要求完整输入全部用户信息 [userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 1)
    @PostMapping("addUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity addUser(@RequestParam("userid") String userid, @RequestParam("username") String username,
                                @RequestParam("yuanxi") String yuanxi, @RequestParam("ptype") String ptype,
                                @RequestParam("pstatus") String pstatus, @RequestParam("gender") String gender) {
        String[] param = new String[]{userid, username, yuanxi, ptype, pstatus, gender};
        return BasicResponseUtils.success(userService.addUser(param));
    }

    @ApiOperation(value = "删除用户", notes = "根据用户主键id，逻辑删除用户")
    @ApiOperationSupport(order = 2)
    @GetMapping("deleteUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity deleteUser(@RequestParam("id") Long userid) {
        return BasicResponseUtils.success(userService.deleteLogicallyUser(userid));
    }

    @ApiOperation(value = "查询全部用户", notes = "显示全体未被逻辑删除的用户信息")
    @ApiOperationSupport(order = 3)
    @GetMapping("findAllUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findAllUser() {
        return BasicResponseUtils.success(userService.findAllUser());
    }

    @ApiOperation(value = "根据主键查询用户", notes = "显示某一用户的详细信息")
    @ApiOperationSupport(order = 4)
    @GetMapping("findUserById")

    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserById(@RequestParam("id") String key_id){
        // 进行前端传入数据的类型转换
        Long id = Long.valueOf(key_id);
        return BasicResponseUtils.success(userService.findUserById(id));
    }

    @ApiOperation(value = "根据工号查询用户", notes = "显示某一用户的详细信息")
    @ApiOperationSupport(order = 5)
    @GetMapping("findUserByUserid")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserByUserid(@RequestParam("userid") String userId) {
        return BasicResponseUtils.success(userService.findUserByUserId(userId));
    }

    @ApiOperation(value = "修改用户信息", notes = "要求完整输入修改后的用户信息 [id, userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 5)
    @GetMapping("updateUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity updateUser(@RequestParam("id") String id,
                                   @RequestParam("userid") String userid, @RequestParam("username") String username,
                                   @RequestParam("yuanxi") String yuanxi, @RequestParam("ptype") String ptype,
                                   @RequestParam("pstatus") String pstatus, @RequestParam("gender") String gender,
                                   @RequestParam("role") String role) {

        String[] param = new String[]{id, userid, username, yuanxi, ptype, pstatus, gender, role};
        return BasicResponseUtils.success(userService.updateUser(param));
    }

    @ApiOperation(value = "分页查询全部用户", notes = "分页显示全体未被逻辑删除的用户信息")
    @ApiOperationSupport(order = 6)
    @GetMapping("findAllUserPagination")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findAllUserPagination() {
        return BasicResponseUtils.success(userService.findAllUserFormPagination());
    }


}
