package com.shu.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * 新增用户
     * @param params -> [userid, username, yuanxi, ptype, pstatus, gender]
     * @return 新增用户id
     */
    int addUser(String[] params);

    /**
     * 逻辑删除用户
     * @param id
     * @return 被逻辑删除的用户id
     */
    int deleteLogicallyUser(Long id);

    /**
     * 查询全部用户
     * @return 用户信息列表
     */
    List<User> findAllUser();

    /**
     * 分页查询全部用户
     * @return 用户信息列表
     */
    public IPage findAllUserFormPagination(Page<User> page);

    /**
     * 根据id查询用户
     * @param id
     * @return id对应的用户信息
     */
    User findUserById(Long id);

    /**
     * 根据工号（user_id）查询用户
     * @param userId
     * @return id对应的用户信息
     */
    User findUserByUserId(String userId);

    /**
     * 根据id查询用户
     * @param params -> [id, userid, username, yuanxi, ptype, pstatus, gender]
     * @return id对应的用户信息
     */
    int updateUser(String[] params);
}
