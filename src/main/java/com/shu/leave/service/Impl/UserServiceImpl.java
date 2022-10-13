package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.User;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        user.setGender(params[5]);
        user.setRole("0");  // 此处默认初始新建的用户权限都为“基本教师权限”
        user.setIsDeleted("0");
        /***
         * 时间转换逻辑：
         * util.Date获取的时间能够精确到时分秒，但转换成sql.Date则只能保留日期
         * 做法是使用sql.Date的子类：sql.Timestamp来做数据转换
         */
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
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
        return userMapper.selectAll();
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectById(id);
    }
}
