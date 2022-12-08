package com.shu.leave.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Admin;
import com.shu.leave.entity.User;
import com.shu.leave.enums.ResponseCodeEnums;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.service.AdminService;
import com.shu.leave.service.UserService;
import com.shu.leave.utils.BasicResponseUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "1.用户相关")
@ApiSupport(order = 1)
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    AdminService adminService;

    @ApiOperation(value = "用户登录", notes = "username为root, password为123456。登录成功返回一个随即生成的token。")
    @ApiOperationSupport(order = 1, author = "lyz")
    @PostMapping("login")
    public ResultEntity userLogin(@ApiIgnore HttpServletResponse response,
                                  @ApiParam(name = "username", value = "登录用户的工号", required = true) @RequestParam("username") String username,
                                  @ApiParam(name = "password", value = "登录用户的密码(123456)", required = true) @RequestParam("password") String password) throws IOException {
        // 后续判定条件为:统一身份认证成功,根据返回的access_token与用户工号进行判别. 此处设置为假登录
        Map<String, Object> resultMap = new HashMap<>();
        Admin currentLoginAdmin = adminService.findAdminByUserId(username);
        String token = JwtUtils.createJWT("1", username, 60000L * 60 * 24 * 30); // 有效时间 30 days
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setPath("/");
        if (currentLoginAdmin != null) {
            response.addCookie(cookie);
            User currentLoginUser = userService.findUserByUserId(username);     // 获取当前登录的用户
            resultMap.put("user", currentLoginUser);
            resultMap.put("jwt_token", token);
            resultMap.put("role", "admin");      // 权限为用户
            return BasicResponseUtils.success(resultMap);
        } else {
            User currentLoginUser = userService.findUserByUserId(username);     // 获取当前登录的用户
            if (currentLoginUser != null && password.equals("123456")) {
                response.addCookie(cookie);
                resultMap.put("user", currentLoginUser);
                resultMap.put("jwt_token", token);
                resultMap.put("role", "user");      // 权限为用户
                return BasicResponseUtils.success(resultMap);
            }
        }
        return BasicResponseUtils.error(ResponseCodeEnums.LOGIN_ERROR);
    }

    @ApiOperation(value = "新增用户", notes = "要求完整输入全部用户信息 [userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 2, author = "lyz")
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
    @ApiOperationSupport(order = 3, author = "lyz")
    @GetMapping("deleteUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity deleteUser(@RequestParam("id") Long userid) {
        return BasicResponseUtils.success(userService.deleteLogicallyUser(userid));
    }

    @ApiOperation(value = "查询全部用户", notes = "显示全体未被逻辑删除的用户信息")
    @ApiOperationSupport(order = 4, author = "lyz")
    @GetMapping("findAllUser")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findAllUser() {
        return BasicResponseUtils.success(userService.findAllUser());
    }

    @ApiOperation(value = "根据主键查询用户", notes = "显示某一用户的详细信息")
    @ApiOperationSupport(order = 5, author = "lyz")
    @GetMapping("findUserById")

    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserById(@RequestParam("id") String key_id){
        // 进行前端传入数据的类型转换
        Long id = Long.valueOf(key_id);
        return BasicResponseUtils.success(userService.findUserById(id));
    }

    @ApiOperation(value = "根据工号查询用户", notes = "显示某一用户的详细信息")
    @ApiOperationSupport(order = 6, author = "lyz")
    @GetMapping("findUserByUserid")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserByUserid(@RequestParam("userid") String userId) {
        return BasicResponseUtils.success(userService.findUserByUserId(userId));
    }

    @ApiOperation(value = "分页地根据工号查询用户", notes = "显示某一用户的详细信息")
    @ApiOperationSupport(order = 7, author = "wsj")
    @GetMapping("findUserPageByUserid")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserPageByUserid(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam("userid") String userId) throws ParseException {
        Page<User> page = new Page(pageNum, 6);
        return BasicResponseUtils.success(userService.findUserPageByUserId(page,Long.parseLong(userId)));
    }

    @ApiOperation(value = "修改用户信息", notes = "要求完整输入修改后的用户信息 [id, userid, username, yuanxi, ptype, pstatus, gender]")
    @ApiOperationSupport(order = 8, author = "wsj")
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

    @ApiOperation(value = "分页查询全部用户", notes = "分页显示全体用户信息")
    @ApiOperationSupport(order = 9, author = "lyz")
    @GetMapping("findAllUserPagination")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findAllUserPagination(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        Page<User> page = new Page(pageNum, 6);
        return BasicResponseUtils.success(userService.findAllUserFormPagination(page));
    }

    @ApiOperation(value = "分页地筛选对应权限的用户", notes = "分页显示对应权限的用户信息。如果筛选多个权限的用户")
    @ApiOperationSupport(order = 10, author = "wsj")
    @GetMapping("findUserPageByRoleList")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findUserPageByRoleList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "roleList") String roleList) {
        Page<User> page = new Page(pageNum, 6);
        return BasicResponseUtils.success(userService.findUserPageByRoleList(page,roleList));
    }

    @ApiOperation(value = "分页查询权限不为0的用户", notes = "分页显示权限不为0的用户信息")
    @ApiOperationSupport(order = 11, author = "wsj")
    @GetMapping("findAdminUserPagination")
    //@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")})
    //@AuthToken
    public ResultEntity findAdminUserPagination(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        Page<User> page = new Page(pageNum, 6);
        return BasicResponseUtils.success(userService.findAdminUserFormPagination(page));
    }

}
