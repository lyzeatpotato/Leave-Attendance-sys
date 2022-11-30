package com.shu.leave.service;

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
    List<Revoke> getRevokeListByUserId(String userId);
}
