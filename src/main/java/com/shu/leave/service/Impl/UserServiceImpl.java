package com.shu.leave.service.Impl;

import com.shu.leave.entity.User;
import com.shu.leave.entity.UserExample;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;


    @Override
    public int addUser(String[] params) {
        User user = new User();
        user.setUserId(params[0]);
        user.setUserName(params[1]);
        user.setYuanXi(params[2]);
        user.setPType(params[3]);
        user.setPStatus(params[4]);
        user.setPassword(params[5]);
        user.setEmail(params[6]);
        user.setTelephone(params[7]);
        user.setRole("0");  // 此处默认初始新建的用户权限都为“基本教师权限”
        user.setIsDeleted("0");
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        user.setGmtCreate(timeStamp);
        user.setGmtModified(timeStamp);
        return userMapper.insert(user);
    }

    @Override
    public int deleteLogicallyUser(Long id) {
        return userMapper.deleteLogicallyById(id);
    }

    @Override
    public List<User> findAllUser() {
        UserExample user = new UserExample();
        return userMapper.selectAll(user);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectById(id);
    }
}
