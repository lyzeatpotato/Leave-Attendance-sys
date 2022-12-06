package com.shu.leave.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Revoke;

import java.util.Map;

public interface RevokeAuditService {

    /**
     * 根据权限审核销假表单
     * @author liyuanzhe
     * @date 2022/12/6 21:02
     * @param role 人员权限
     * @param userid 审核人员工号
     * @param id 销假表单主键字段
     * @param result 审核结果（通过/不通过）
     * @param recommend 审核理由
     * @return Map<String>
     */
    Map<String, Object> addRevokeAuditMsg(String role, String userid, Long id, String result, String recommend);

    /**
     * 分页显示当前登录审核户可查看的销假信息
     * @author liyuanzhe
     * @date 2022/12/6 21:45
     * @param page
     * @param userId
     * @return Page<Revoke>
     */
    Page<Revoke> getAuditLoadingDataByUserId(Page<Revoke> page, String userId);

    /**
     * 返回某一销假信息对应已完成的审核信息（未完成的显示为"无需此步骤"）
     * @author liyuanzhe
     * @date 2022/12/6 22:47
     * @param leaveId
     * @return Map<Object>
     */
    Map<String, Object> getCurrentRevokeAuditMsgById(Long revokeId);
}
