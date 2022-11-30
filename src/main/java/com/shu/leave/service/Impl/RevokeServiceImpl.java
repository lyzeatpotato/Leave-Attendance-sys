package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.Revoke;
import com.shu.leave.entity.User;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.mapper.RevokeMapper;
import com.shu.leave.mapper.UserMapper;
import com.shu.leave.service.LeaveService;
import com.shu.leave.service.RevokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: RevokeServiceImpl
 * @Description: 销假业务逻辑层实现类
 * @author: lyz
 * @date: 2022 11 2022/11/30 21:30
 */

@Service
public class RevokeServiceImpl implements RevokeService {

    @Autowired
    RevokeMapper revokeMapper;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    LeaveService leaveService;

    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, String> addRevokeRecord(String[] params) throws ParseException {
        // 首先获取当前leave信息
        Leave currentLeave = leaveMapper.findById(Long.valueOf(params[0]));
        String resultStr;
        Map<String, String> resMap = new HashMap<>();
        String status = currentLeave.getStatus();
        if (!status.equals("1")) {
            switch (status) {
                case "0": {
                    resultStr = "添加销假记录失败（当前请假记录尚未完成）";
                }
                case "3": {
                    resultStr = "添加销假记录失败（当前请假记录已撤销）";
                }
                default: {
                    resultStr = "添加销假记录失败（当前请假记录未被通过）";
                }
            }
        } else {
            Revoke revoke = new Revoke();
            revoke.setFormId(Long.valueOf(params[0]));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            revoke.setRevokeReportTime(df.parse(params[1]));
            revoke.setRevokeSubmitTime(df.parse(params[2]));
            Date nowDate = new Date();
            revoke.setStatus("0");
            revoke.setIsDeleted("0");
            revoke.setGmtCreate(nowDate);
            revoke.setGmtModified(nowDate);
            /**
             * 此处需要判断当前请假表单是否已完成全部审核流程，并根据原始请假审核步骤判断销假步骤
             */
            int[] auditFlow = leaveService.judgeAuditFlow(params[3], currentLeave.getLeaveType(),
                    currentLeave.getLeaveStartTime(), currentLeave.getLeaveEndTime());  // 获取原始请假申请的流程
            int count = 0;      // 用于记录总计需要几个步骤
            for (int i = 0; i < auditFlow.length; i++) {
                if (auditFlow[i] == 0) {
                    count ++;
                }
            }
            if (count == 1) {
                revoke.setDepartmentStatus("0");
                revoke.setHrStatus("2");
            } else {
                revoke.setDepartmentStatus("0");
                revoke.setHrStatus("0");
            }

            int affectLines = revokeMapper.insert(revoke);      // 向数据库执行写入操作
            if (affectLines == 1) {
                resultStr = "添加销假记录成功";
            } else {
                resultStr = "添加销假记录失败（数据库插入异常）";
            }
        }
        resMap.put("result", resultStr);
        return resMap;
    }

    @Override
    public Map<String, String> undoRevoke(Long revokeId) {
        QueryWrapper<Revoke> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", revokeId);
        Revoke revoke = revokeMapper.selectOne(queryWrapper);
        revoke.setStatus("3");      // 修改状态为“已撤销”
        int affectedLines = revokeMapper.updateById(revoke);
        HashMap<String, String> resMap = new HashMap<>();
        if (affectedLines == 1) {
            resMap.put("result", "销假申请撤销成功");
        } else {
            resMap.put("result", "销假申请撤销失败");
        }
        return resMap;
    }

    @Override
    public List<Revoke> getRevokeListByUserId(String userId) {
        User currentUser = userMapper.findByUserid(userId);
        // 根据用户工号查询出请假申请表中以审核完成并已经提交销假申请的数据
        List<Revoke> allRevokeListByUserId = revokeMapper.findAllRevokeListByUserId(currentUser.getId());
        return allRevokeListByUserId;
    }
}
