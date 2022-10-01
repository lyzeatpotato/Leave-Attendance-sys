package com.shu.leave.service;

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
     * 根据id查询用户
     * @param id
     * @return id对应的用户信息
     */
    User findUserById(Long id);
}
