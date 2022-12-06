package com.shu.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Revoke;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface RevokeService {

    /**
     * 新增一条销假申请记录
     * @author liyuanzhe
     * @param params
     * @return 返回新增后的状态逻辑
     */
    Map<String, String> addRevokeRecord(String[] params) throws ParseException;

    /**
     * 撤销一条销假申请记录
     * @author liyuanzhe
     * @param revokeId
     * @return 返回撤销后的状态逻辑
     */
    Map<String, String> undoRevoke(Long revokeId);

    /**
     * 根据userid查询当前用户的全部销假记录
     * @author liyuanzhe
     * @param userId
     * @return 符合条件的销假记录信息
     */
    Page<Revoke> getRevokeListByUserId(Page<Revoke> page, String userId);

    /**
     * 根据销假表主键获取信息
     * @author liyuanzhe
     * @date 2022/12/5 18:39
     * @param revokeId
     * @return Revoke
     */
    Revoke getRevokeDetailById(Long revokeId);

    /**
     * 根据请假申请表主键查询是否存在对应的销假申请记录
     * @author liyuanzhe
     * @date 2022/12/6 18:56
     * @param leaveId
     * @return Map<Object>
     */
    Map<String, Object> findRevokeByLeaveId(Long leaveId);
}
