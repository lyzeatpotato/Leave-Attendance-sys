package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Admin;

import com.shu.leave.mapper.AdminMapper;
import com.shu.leave.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;


    @Override
    public int addAdmin(String[] params) {
        Admin Admin = new Admin();
        Admin.setUserId(params[0]);
        Admin.setUserName(params[1]);
        Admin.setIsDeleted("0");
        /***
         * 时间转换逻辑：
         * util.Date获取的时间能够精确到时分秒，但转换成sql.Date则只能保留日期
         * 做法是使用sql.Date的子类：sql.Timestamp来做数据转换
         */
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
        Admin.setGmtCreate(timeStamp);
        Admin.setGmtModified(timeStamp);
        return adminMapper.insert(Admin);
    }

    @Override
    public int deleteLogicallyAdmin(Long id) {
        return adminMapper.deleteLogicallyById(id);
    }

    @Override
    public List<Admin> findAllAdmin() {
//        Page<Admin> page = new Page<>(0, 10);    // 分页查询对象，从索引0开始，每页显示10条
//        IPage iPage = AdminMapper.selectPage(page, null);    // 此处使用Mybatis-plus中提供的selectPage方法
//        return iPage;
        return adminMapper.selectAll();
    }

    @Override
    public Admin findAdminById(Long id) {
        return adminMapper.selectById(id);
    }

    @Override
    public int updateAdmin(String[] params ) {
        Admin admin = new Admin();
        admin.setId(Long.parseLong(params[0]));
        admin.setUserId(params[1]);
        admin.setUserName(params[2]);

        /***
         * 时间转换逻辑：
         * util.Date获取的时间能够精确到时分秒，但转换成sql.Date则只能保留日期
         * 做法是使用sql.Date的子类：sql.Timestamp来做数据转换
         */
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
        admin.setGmtCreate(timeStamp);
        admin.setGmtModified(timeStamp);
        return adminMapper.update(admin);
    }
}
