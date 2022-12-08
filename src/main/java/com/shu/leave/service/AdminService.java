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
    //IPage findAllAdmin();
    List<Admin>  findAllAdmin();

    /**
     * 根据id查询用户
     * @param id
     * @return id对应的用户信息
     */
    Admin findAdminById(Long id);

    /**
     * 根据工号查询管理员
     * @author liyuanzhe
     * @date 2022/12/8 17:51
     * @param userId
     * @return Admin
     */
    Admin findAdminByUserId(String userId);

    /**
     * 根据id查询用户
     * @param params -> [id, userid, username]
     * @return id对应的用户信息
     */
    int updateAdmin(String[] params);
}
