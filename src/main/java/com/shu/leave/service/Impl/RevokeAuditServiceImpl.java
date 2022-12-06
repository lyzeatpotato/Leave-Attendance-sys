package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.*;
import com.shu.leave.mapper.RevokeDepartmentAuditMapper;
import com.shu.leave.mapper.RevokeHrAuditMapper;
import com.shu.leave.mapper.RevokeMapper;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.RevokeAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: RevokeAuditServiceImpl
 * @Description: 销假申请审核业务逻辑实现类
 * @author: lyz
 * @date: 2022 12 2022/12/6 21:00
 */

@Service
public class RevokeAuditServiceImpl implements RevokeAuditService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RevokeMapper revokeMapper;

    @Autowired
    RevokeDepartmentAuditMapper revokeDepartmentAuditMapper;

    @Autowired
    RevokeHrAuditMapper revokeHrAuditMapper;

    @Override
    public Map<String, Object> addRevokeAuditMsg(String role, String userid, Long id, String result, String recommend) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
        String commonRes = "审核异常，用户权限异常";
        int code = 0;
        HashMap<String, Object> resultMap = new HashMap<>();
        switch (role) {
            case "1": {
                commonRes = "当前销假申请仅需部门负责人审核";
                code = 2;
                break;
            }
            case "2": {
                Revoke currentRevoke = revokeMapper.selectById(id);
                RevokeDepartmentAudit revokeDepartmentAudit = new RevokeDepartmentAudit();
                revokeDepartmentAudit.setRevokeFormId(id);
                revokeDepartmentAudit.setDpLeaderId(userid);
                revokeDepartmentAudit.setDpLeaderResult(result);
                revokeDepartmentAudit.setDpLeaderRecommend(recommend);
                revokeDepartmentAudit.setDpLeaderTime(timeStamp);
                revokeDepartmentAudit.setDpLeaderStatus("1");
                revokeDepartmentAudit.setIsDeleted("0");
                revokeDepartmentAudit.setGmtCreate(timeStamp);
                revokeDepartmentAudit.setGmtModified(timeStamp);
                revokeDepartmentAuditMapper.insert(revokeDepartmentAudit);  // 执行写入操作
                commonRes = "部门负责人销假申请审核已完成";

                if (result.equals("通过")) {
                    // 如部门审核通过则判断是否还需要后续审核，如不需要则直接修改revoke表状态
                    if (currentRevoke.getHrStatus().equals("2")) {
                        currentRevoke.setStatus("1");
                    }
                    currentRevoke.setDepartmentStatus("1");
                } else {
                    currentRevoke.setStatus("2");
                }
                revokeMapper.updateById(currentRevoke);     // 修改revoke表状态
                code = 1;
                break;
            }
            case "3": {
                commonRes = "当前销假申请仅需人事处负责人审核";
                code = 2;
                break;
            }
            case "4": {
                Revoke currentRevoke = revokeMapper.selectById(id);
                RevokeHrAudit revokeHrAudit = new RevokeHrAudit();
                revokeHrAudit.setRevokeFormId(id);
                revokeHrAudit.setHrLeaderId(userid);
                revokeHrAudit.setHrLeaderResult(result);
                revokeHrAudit.setHrLeaderRecommend(recommend);
                revokeHrAudit.setHrLeaderTime(timeStamp);
                revokeHrAudit.setHrLeaderStatus("1");
                revokeHrAudit.setIsDeleted("0");
                revokeHrAudit.setGmtCreate(timeStamp);
                revokeHrAudit.setGmtModified(timeStamp);
                revokeHrAuditMapper.insert(revokeHrAudit);      // 执行写入操作
                commonRes = "人事处负责人销假申请审核完成";

                if (result.equals("通过")) {
                    currentRevoke.setHrStatus("1");
                    currentRevoke.setStatus("1");
                } else {
                    currentRevoke.setStatus("2");
                }
                revokeMapper.updateById(currentRevoke);     // 修改revoke表状态
                code = 1;
                break;
            }
            default: {
                commonRes = "当前销假申请无需校领导用户审核";
                code = 2;
            }
        }
        resultMap.put("result", commonRes);
        resultMap.put("resultCode", code);
        return resultMap;
    }

    @Override
    public Page<Revoke> getAuditLoadingDataByUserId(Page<Revoke> page, String userId) {
        User currentUser = userMapper.findByUserid(userId);     // 获取当前的用户信息
        // 判断当前用户的权限
        String currentUserRole = currentUser.getRole();
        Page<Revoke> resPage;       // 统一的返回值
        switch (currentUserRole) {
            case "1": case"2": {
                // 部门科员与部门负责人看到的界面是一致的
                resPage = revokeMapper.selectPageByDeptUser(page, currentUser.getYuanXi(), currentUser.getId());
                break;
            }
            default: {
                // 人事处科员与人事处负责人与校领导都可以看到全校的数据
                resPage = revokeMapper.selectPageByHrAndSchoolUser(page, currentUser.getId());
                break;
            }
        }
        return resPage;
    }

    @Override
    public Page<Revoke> getRevokeAuditSelected(Page<Revoke> page, String userId, String[] params) {
        User currentUser = userMapper.findByUserid(userId);     // 获取当前的用户信息
        // 判断当前用户的权限
        String currentUserRole = currentUser.getRole();
        Page<Revoke> resPage;
        switch (currentUserRole) {
            case "1": case "2": {
                // 部门审核人员用户：根据工号和用户姓名查询
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = revokeMapper.selectPageByUserIdOrUserName(page, currentUser.getId(), currentUser.getYuanXi(), params[0], params[1]);
                break;
            }
            default: {
                // 人事处审核人员用户：根据工号和用户姓名以及部门名称查询
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = revokeMapper.selectPageByUserIdOrUserNameOrDept(page, currentUser.getId(), params[0], params[1], params[2]);
                break;
            }
        }
        return resPage;
    }

    @Override
    public Map<String, Object> getCurrentRevokeAuditMsgById(Long revokeId) {
        // 首先根据主键查询出leave请假信息
        Revoke currentRevoke = revokeMapper.findRevokeDetailById(revokeId);
        String departmentStatus = currentRevoke.getDepartmentStatus();
        String hrStatus = currentRevoke.getHrStatus();
        // 初始化统一的返回值类型
        Map<String, Object> resMap = new LinkedHashMap<>();
        /**
         * 业务判断流程
         * 1.首先查是否已有部门审核记录->有，执行2；没有，说明部门还未审核，DpObject为字符串"尚未进行部门审核"
         * 2.判断是否还需要人事处审核->需要，执行3；不需要，返回最终结果
         * 3.查看是否已有人事处审核记录->有，执行4；没有，说明人事处还未审核，HrObject为字符串"尚未进行人事处审核"
         * 4.判断是否还需要校领导审核->需要，执行5；不需要，返回最终结果
         */
        QueryWrapper<RevokeDepartmentAudit> auditQueryWrapper = new QueryWrapper<>();
        auditQueryWrapper.eq("revoke_formid", revokeId);
        try {
            RevokeDepartmentAudit revokeDepartmentAudit = revokeDepartmentAuditMapper.selectOne(auditQueryWrapper);
            if (revokeDepartmentAudit != null) {
                resMap.put("departmentAuditMsg", revokeDepartmentAudit);
            } else {
                resMap.put("departmentAuditMsg", "尚未进行部门审核");
            }
            if (!hrStatus.equals("2")) {
                QueryWrapper<RevokeHrAudit> hrAuditQueryWrapper = new QueryWrapper<>();
                auditQueryWrapper.eq("revoke_formid", revokeId);
                RevokeHrAudit revokeHrAudit = revokeHrAuditMapper.selectOne(hrAuditQueryWrapper);
                if (revokeHrAudit != null) {
                    resMap.put("hrAuditMsg", revokeHrAudit);
                } else {
                    resMap.put("hrAuditMsg", "尚未进行人事处审核");
                }
            }
        } catch (Exception e) {
            resMap.put("warningError", "数据库查询有误");
            e.printStackTrace();
        }
        return resMap;
    }
}
