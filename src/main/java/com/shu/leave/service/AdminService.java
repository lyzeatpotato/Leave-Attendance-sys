package com.shu.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shu.leave.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    /**
     * 新增用户
     * @param params -> [Userid, Username]
     * @return 新增用户id
     */
    int addAdmin(String[] params);

    /**
     * 逻辑删除用户
     * @param id
     * @return 被逻辑删除的用户id
     */
    int deleteLogicallyAdmin(Long id);

    /**
     * 查询全部用户
     * @return 用户信息列表
     */
    IPage findAllAdmin();

    /**
     * 根据id查询用户
     * @param id
     * @return id对应的用户信息
     */
    Admin findAdminById(Long id);
}
