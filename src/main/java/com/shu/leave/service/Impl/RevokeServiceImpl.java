package com.shu.leave.service.Impl;

import com.shu.leave.entity.Leave;
import com.shu.leave.entity.Revoke;
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
            if (count == 2) {
                revoke.setDepartmentStatus("0");
                revoke.setHrStatus("0");
            } else {
                revoke.setDepartmentStatus("0");
                revoke.setHrStatus("2");
            }

            int affectLines = revokeMapper.insert(revoke);
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
    public List<Revoke> getRevokeListByUserId(String userId) {
        // 根据用户工号查询出请假申请表中以审核完成并已经提交销假申请的数据
        return null;
    }
}
