package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.*;
import com.shu.leave.mapper.*;
import com.shu.leave.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AuditSercicelmpl implements AuditService {
    @Autowired
    LeaveDepartmentAuditMapper leaveDepartmentAuditMapper;

    @Autowired
    LeaveHrAuditMapper leaveHrAuditMapper;

    @Autowired
    LeaveSchoolAuditMapper leaveSchoolAuditMapper;

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    UserMapper userMapper;

    /*
    审核工作
    */
    @Override
    @Transactional
    public int singleLeaveAudit(String role, String userid, Long id, String result, String recommend) {
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //String time = df.format(System.currentTimeMillis());
        /* 时间转换逻辑：
         * util.Date获取的时间能够精确到时分秒，但转换成sql.Date则只能保留日期
         * 做法是使用sql.Date的子类：sql.Timestamp来做数据转换
         */
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
        String time = df.format(timeStamp);
        switch (role) {
            case "1": {
                LeaveDepartmentAudit leaveDepartmentAudit = new LeaveDepartmentAudit();
                //System.out.println(leaveDepartmentAudit);
                leaveDepartmentAudit.setFormId(id);
                leaveDepartmentAudit.setDpOfficerId(userid);
                leaveDepartmentAudit.setDpOfficerResult(result);
                leaveDepartmentAudit.setDpOfficerRecommend(recommend);
                leaveDepartmentAudit.setDpOfficerTime(timeStamp);
                leaveDepartmentAudit.setDpOfficerStatus("1");
                leaveDepartmentAudit.setDpLeaderId("无");
                leaveDepartmentAudit.setDpLeaderResult("无");
                leaveDepartmentAudit.setDpLeaderRecommend("无");
                leaveDepartmentAudit.setDpLeaderTime(timeStamp);
                leaveDepartmentAudit.setDpLeaderStatus("0");
                leaveDepartmentAudit.setIsDeleted("0");
                leaveDepartmentAudit.setGmtCreate(timeStamp);
                leaveDepartmentAudit.setGmtModified(timeStamp);

                leaveDepartmentAuditMapper.addLeaveDepartmentAudit(leaveDepartmentAudit);
                System.out.println("123");
                switch (result){
                    case "通过":{
                        leaveDepartmentAuditMapper.dpOfficerAudityy(id, timeStamp);
                        break;
                    }
                    case "不通过":{
                        leaveDepartmentAuditMapper.dpOfficerAuditnn(id,timeStamp);
                        break;
                    }
                }
                break;
            }
            case "2": {
                leaveDepartmentAuditMapper.updateLeaveDepartmentAudit(userid,id,result,recommend,timeStamp);
                switch (result){
                    case "通过":{
                        leaveDepartmentAuditMapper.dpLeaderAudityy(id, timeStamp);
                        break;
                    }
                    case "不通过":{
                        leaveDepartmentAuditMapper.dpLeaderAuditnn(id,timeStamp);
                        break;
                    }
                }
                break;
            }
            case "3": {
                LeaveHrAudit leaveHrAudit = new LeaveHrAudit();
                leaveHrAudit.setFormId(id);
                leaveHrAudit.setHrOfficerId(userid);
                leaveHrAudit.setHrOfficerResult(result);
                leaveHrAudit.setHrOfficerRecommend(recommend);
                leaveHrAudit.setHrOfficerTime(timeStamp);
                leaveHrAudit.setHrOfficerStatus("1");
                leaveHrAudit.setHrLeaderId("无");
                leaveHrAudit.setHrLeaderResult("无");
                leaveHrAudit.setHrLeaderRecommend("无");
                leaveHrAudit.setHrLeaderTime(timeStamp);
                leaveHrAudit.setHrLeaderStatus("0");
                leaveHrAudit.setIsDeleted("0");
                leaveHrAudit.setGmtCreate(timeStamp);
                leaveHrAudit.setGmtModified(timeStamp);
                leaveHrAuditMapper.addLeaveHrAudit(leaveHrAudit);

                //leaveMapper.hrOfficerAudit(userid, id, result, recommend, time);
                switch (result){
                    case "通过":{
                        leaveHrAuditMapper.hrOfficerAudityy(id, timeStamp);
                        break;
                    }
                    case "不通过":{
                        leaveHrAuditMapper.hrOfficerAuditnn(id, timeStamp);
                        break;
                    }
                }
                break;
            }
            case "4": {
                leaveHrAuditMapper.updateLeaveHrAudit(userid, id, result, recommend, timeStamp);
                switch (result){
                    case "通过":{
                        leaveHrAuditMapper.hrLeaderAudityy(id, timeStamp);
                        break;
                    }
                    case "不通过":{
                        leaveHrAuditMapper.hrLeaderAuditnn(id, timeStamp);
                        break;
                    }
                }
                break;
            }
            case "5": {
                LeaveSchoolAudit leaveSchoolAudit = new LeaveSchoolAudit();
                leaveSchoolAudit.setFormId(id);
                leaveSchoolAudit.setScLeaderId(userid);
                leaveSchoolAudit.setScLeaderResult(result);
                leaveSchoolAudit.setScLeaderRecommend(recommend);
                leaveSchoolAudit.setScLeaderTime(timeStamp);
                leaveSchoolAudit.setScLeaderStatus("1");
                leaveSchoolAudit.setIsDeleted("0");
                leaveSchoolAudit.setGmtCreate(timeStamp);
                leaveSchoolAudit.setGmtModified(timeStamp);
                leaveSchoolAuditMapper.addSchoolAudit(leaveSchoolAudit);

                switch (result){
                    case "通过":{
                        leaveSchoolAuditMapper.scLeaderAudityy(id, timeStamp);
                        break;
                    }
                    case "不通过":{
                        leaveSchoolAuditMapper.scLeaderAuditnn(id, timeStamp);
                        break;
                    }
                }
                break;
            }
        }
        return 1;
    }

    @Override
    public Page<Leave> getAuditDataListByUserid(Page<Leave> page, String userId) {
        // 首先根据工号查询出当前登录的用户信息
        User currentUser = userMapper.findByUserid(userId);
        // 判断当前用户的权限
        String currentUserRole = currentUser.getRole();
        Page<Leave> resPage;
        switch (currentUserRole) {
            case "1": {
                // 权限为各部门干事，则当前用户可看到本部门审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                resPage = leaveMapper.selectPageByDeptOfficer(page, department);// 注：这里不适用mybatis-plus的条件构造器，由于涉及多表联查所以重写自定义的分页查询方法

                break;
            }
            case "2": {
                // 权限为各部门负责人，则当前用户可看到本部门审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                resPage = leaveMapper.selectPageByDeptLeader(page, department);
                break;
            }
            case "3":
                // 权限为人事处干事，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                resPage = leaveMapper.selectPageByHrOfficer(page);
                break;
            case "4":
                // 权限为人事处负责人，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                resPage = leaveMapper.selectPageByHrLeader(page);
                break;
            default: {
                // 权限为校领导，则当前用户可看到”本人所负责的部门下“审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                resPage = leaveMapper.selectPageBySchool(page, department);
                break;
            }
        }

        return resPage;
    }

    @Override
    public Page<Leave> getAuditSelected(Page<Leave> page, String userId, String[] params) {
        User currentUser = userMapper.findByUserid(userId);
        // 判断当前用户的权限
        String currentUserRole = currentUser.getRole();
        Page<Leave> resPage;
        switch (currentUserRole) {
            case "1": {
                // 权限为各部门干事，则当前用户可看到本部门审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageByDeptOfficerWrappered(page, department, params[0], params[1]);// 注：这里不适用mybatis-plus的条件构造器，由于涉及多表联查所以重写自定义的分页查询方法
                break;
            }
            case "2": {
                // 权限为各部门负责人，则当前用户可看到本部门审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageByDeptLeaderWrappered(page, department, params[0], params[1]);
                break;
            }
            case "3":
                // 权限为人事处干事，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageByHrOfficerWrappered(page, params[0], params[1], params[2]);
                break;
            case "4":
                // 权限为人事处负责人，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageByHrLeaderWrappered(page, params[0], params[1], params[2]);
                break;
            default: {
                // 权限为校领导，则当前用户可看到”本人所负责的部门下“审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageBySchoolWrappered(page, department, params[0], params[1]);
                break;
            }
        }

        return resPage;
    }


}
