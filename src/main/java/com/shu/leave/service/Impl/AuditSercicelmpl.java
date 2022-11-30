package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.*;
import com.shu.leave.mapper.*;
import com.shu.leave.service.AuditService;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;


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
    public String singleLeaveAudit(String role, String userid, Long id, String result, String recommend) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df.format(date));
        String commonRes = "审核异常，用户权限异常";
        switch (role) {
            case "1": {
                Leave currentLeave = leaveMapper.findById(id);      // 获取当前正待审核的请假信息
                // 部门初审：部门科员用户为第一步审核，向数据库执行写操作
                LeaveDepartmentAudit leaveDepartmentAudit = new LeaveDepartmentAudit();
                leaveDepartmentAudit.setFormId(id);
                leaveDepartmentAudit.setDpOfficerId(userid);
                leaveDepartmentAudit.setDpOfficerResult(result);
                leaveDepartmentAudit.setDpOfficerRecommend(recommend);
                leaveDepartmentAudit.setDpOfficerTime(timeStamp);
                leaveDepartmentAudit.setDpOfficerStatus("1");
                // 剩余字段暂时无人审核，写入默认值
                leaveDepartmentAudit.setDpLeaderId("部门领导暂未审核");
                leaveDepartmentAudit.setDpLeaderResult("部门领导暂未审核");
                leaveDepartmentAudit.setDpLeaderRecommend("部门领导暂未审核");
                leaveDepartmentAudit.setDpLeaderTime(timeStamp);
                leaveDepartmentAudit.setDpLeaderStatus("0");
                leaveDepartmentAudit.setIsDeleted("0");
                leaveDepartmentAudit.setGmtCreate(timeStamp);
                leaveDepartmentAudit.setGmtModified(timeStamp);
                leaveDepartmentAuditMapper.insert(leaveDepartmentAudit);
                commonRes = "部门科员审核提交成功";

                // 部门科员审核完毕后修改leave表中的状态字段
                if (result.equals("通过")) {
                    currentLeave.setDepartmentStatus("3");      // 部门科员审核完毕，已通过，等待后续审核
                    leaveMapper.updateById(currentLeave);
                } else if (result.equals("不通过")) {
                    currentLeave.setStatus("2");        // 部门科员审核完毕，不通过，无需后续审核步骤，直接退回
                    leaveMapper.updateById(currentLeave);
                }
                break;
            }
            case "2": {
                Leave currentLeave = leaveMapper.findById(id);      // 获取当前正待审核的请假信息
                // 部门复审：部门负责人用户为第二步审核，首先在数据库中查询，如有则修改，如无则报错“尚未流经至本用户审核”
                try {
                    QueryWrapper<LeaveDepartmentAudit> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("formid", id);
                    LeaveDepartmentAudit leaveDepartmentAudit = leaveDepartmentAuditMapper.selectOne(queryWrapper);
                    leaveDepartmentAudit.setDpLeaderId(userid);
                    leaveDepartmentAudit.setDpLeaderResult(result);
                    leaveDepartmentAudit.setDpLeaderRecommend(recommend);
                    leaveDepartmentAudit.setDpLeaderTime(timeStamp);
                    leaveDepartmentAudit.setDpLeaderStatus("1");
                    leaveDepartmentAudit.setGmtModified(timeStamp);
                    leaveDepartmentAuditMapper.update(leaveDepartmentAudit, queryWrapper);

                    // 部门负责人审核完毕后修改leave表中的状态字段
//                    QueryWrapper<Leave> leaveWrapper = new QueryWrapper<>();
//                    queryWrapper.eq("id", id);
                    if (result.equals("通过")) {
                        if (currentLeave.getHrStatus().equals("2")) {
                            // 说明当前请假单仅需“部门审核”，审核流程结束，修改status字段
                            currentLeave.setStatus("1");
                            currentLeave.setDepartmentStatus("1");
                            leaveMapper.updateById(currentLeave);
                        } else {
                            // 说明当前请假单还需后续审核
                            currentLeave.setDepartmentStatus("1");
                            leaveMapper.updateById(currentLeave);
                        }
                    } else  if (result.equals("不通过")) {
                        currentLeave.setStatus("2");      // 部门负责人审核完毕，不通过，无需后续审核步骤，直接退回
                        leaveMapper.updateById(currentLeave);
                    }
                    commonRes = "部门负责人审核提交成功";
                } catch (Exception e) {
                    commonRes = "尚未流经至本用户审核";
                    e.printStackTrace();
                }
                break;
            }
            case "3": {
                Leave currentLeave = leaveMapper.findById(id);      // 获取当前正待审核的请假信息
                // 人事处初审：部门负责人用户为第三步审核，向数据库执行写操作
                LeaveHrAudit leaveHrAudit = new LeaveHrAudit();
                leaveHrAudit.setFormId(id);
                leaveHrAudit.setHrOfficerId(userid);
                leaveHrAudit.setHrOfficerResult(result);
                leaveHrAudit.setHrOfficerRecommend(recommend);
                leaveHrAudit.setHrOfficerTime(timeStamp);
                leaveHrAudit.setHrOfficerStatus("1");
                // 剩余字段暂时无人审核，写入默认值
                leaveHrAudit.setHrLeaderId("人事处领导暂未审核");
                leaveHrAudit.setHrLeaderResult("人事处领导暂未审核");
                leaveHrAudit.setHrLeaderRecommend("人事处领导暂未审核");
                leaveHrAudit.setHrLeaderTime(timeStamp);
                leaveHrAudit.setHrLeaderStatus("0");
                leaveHrAudit.setIsDeleted("0");
                leaveHrAudit.setGmtCreate(timeStamp);
                leaveHrAudit.setGmtModified(timeStamp);
                leaveHrAuditMapper.insert(leaveHrAudit);
                commonRes = "人事处干事审核提交成功";

                // 人事处干事审核完毕后修改leave表中的状态字段
                if (result.equals("通过")) {
                    currentLeave.setHrStatus("3");      // 人事处科员审核完毕，已通过，等待后续审核
                    leaveMapper.updateById(currentLeave);
                } else if (result.equals("不通过")) {
                    currentLeave.setStatus("2");      // 人事处科员审核完毕，不通过，无需后续审核步骤，直接退回
                    leaveMapper.updateById(currentLeave);
                }
                break;
            }
            case "4": {
                Leave currentLeave = leaveMapper.findById(id);      // 获取当前正待审核的请假信息
                // 人事处复审：人事处负责人用户为第四步审核，首先在数据库中查询，如有则修改，如无则报错“尚未流经至本用户审核”
                try {
                    QueryWrapper<LeaveHrAudit> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("formid", id);
                    LeaveHrAudit leaveHrAudit = leaveHrAuditMapper.selectOne(queryWrapper);
                    leaveHrAudit.setHrLeaderId(userid);
                    leaveHrAudit.setHrLeaderResult(result);
                    leaveHrAudit.setHrLeaderRecommend(recommend);
                    leaveHrAudit.setHrLeaderTime(timeStamp);
                    leaveHrAudit.setHrLeaderStatus("0");
                    leaveHrAudit.setGmtModified(timeStamp);
                    leaveHrAuditMapper.update(leaveHrAudit, queryWrapper);

                    // 人事处负责人审核完毕后修改leave表中的状态字段
                    if (result.equals("通过")) {
                        if (currentLeave.getSchoolStatus().equals("2")) {
                            // 说明当前请假单仅需“部门审核”和“人事处审核”，审核流程结束，修改status字段
                            currentLeave.setStatus("1");
                            currentLeave.setHrStatus("1");
                            leaveMapper.updateById(currentLeave);
                        } else {
                            // 说明当前请假单还需后续审核
                            currentLeave.setHrStatus("1");
                            leaveMapper.updateById(currentLeave);
                        }
                    } else  if (result.equals("不通过")) {
                        currentLeave.setStatus("2");      // 人事处负责人审核完毕，不通过，无需后续审核步骤，直接退回
                        leaveMapper.updateById(currentLeave);
                    }
                    commonRes = "人事处负责人审核提交成功";
                } catch (Exception e) {
                    commonRes = "尚未流经至本用户审核";
                    e.printStackTrace();
                }
                break;
            }
            case "5": {
                Leave currentLeave = leaveMapper.findById(id);      // 获取当前正待审核的请假信息
                // 校领导审核：校领导用户为第五步审核，执行写入操作
                LeaveSchoolAudit schoolAudit = new LeaveSchoolAudit();
                schoolAudit.setFormId(id);
                schoolAudit.setScLeaderId(userid);
                schoolAudit.setScLeaderResult(result);
                schoolAudit.setScLeaderRecommend(recommend);
                schoolAudit.setScLeaderTime(timeStamp);
                schoolAudit.setScLeaderStatus("1");
                schoolAudit.setIsDeleted("0");
                schoolAudit.setGmtCreate(timeStamp);
                schoolAudit.setGmtModified(timeStamp);
                leaveSchoolAuditMapper.insert(schoolAudit);
                commonRes = "校领导审核提交成功";

                // 校领导审核完毕后修改leave表中的状态字段
                if (result.equals("通过")) {
                    currentLeave.setSchoolStatus("1");
                    currentLeave.setStatus("1");
                    leaveMapper.updateById(currentLeave);
                } else if (result.equals("不通过")) {
                    currentLeave.setSchoolStatus("0");
                    currentLeave.setStatus("2");      // 校领导审核完毕，不通过，无需后续审核步骤，直接退回
                    leaveMapper.updateById(currentLeave);
                }
                break;
            }
        }
        return commonRes;
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
                resPage = leaveMapper.selectPageByDeptOfficer(page, department, currentUser.getId());// 注：这里不适用mybatis-plus的条件构造器，由于涉及多表联查所以重写自定义的分页查询方法
                List<Leave> records = resPage.getRecords();
                for (int i = 0; i < records.size(); i++) {
                    String departmentStatus = records.get(i).getDepartmentStatus();
                    if (departmentStatus.equals("0")) {
                        records.get(i).setShowStatus("待审核");
                    } else {
                        records.get(i).setShowStatus("已审核");
                    }
                }
                break;
            }
            case "2": {
                // 权限为各部门负责人，则当前用户可看到本部门审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                resPage = leaveMapper.selectPageByDeptLeader(page, department, currentUser.getId());
                List<Leave> deptLeaderRecords = resPage.getRecords();
                for (int i = 0; i < deptLeaderRecords.size(); i++) {
                    String departmentStatus = deptLeaderRecords.get(i).getDepartmentStatus();
                    if (departmentStatus.equals("1")) {
                        deptLeaderRecords.get(i).setShowStatus("已审核");
                    } else if (departmentStatus.equals("3")) {
                        deptLeaderRecords.get(i).setShowStatus("待审核");
                    } else {
                        deptLeaderRecords.get(i).setShowStatus("未流经");
                    }
                }
                break;
            }
            case "3":
                // 权限为人事处干事，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                resPage = leaveMapper.selectPageByHrOfficer(page, currentUser.getId());
                List<Leave> hrRecords = resPage.getRecords();
                for (int i = 0; i < hrRecords.size(); i++) {
                    String hrStatus = hrRecords.get(i).getHrStatus();
                    if (hrStatus.equals("0")) {
                        hrRecords.get(i).setShowStatus("待审核");
                    } else {
                        hrRecords.get(i).setShowStatus("已审核");
                    }
                }
                break;
            case "4":
                // 权限为人事处负责人，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                resPage = leaveMapper.selectPageByHrLeader(page, currentUser.getId());
                List<Leave> hrLeaderRecords = resPage.getRecords();
                for (int i = 0; i < hrLeaderRecords.size(); i++) {
                    String hrStatus = hrLeaderRecords.get(i).getHrStatus();
                    if (hrStatus.equals("1")) {
                        hrLeaderRecords.get(i).setShowStatus("已审核");
                    } else if (hrStatus.equals("3")) {
                        hrLeaderRecords.get(i).setShowStatus("待审核");
                    } else {
                        hrLeaderRecords.get(i).setShowStatus("未流经");
                    }
                }
                break;
            default: {
                // 权限为校领导，则当前用户可看到”本人所负责的部门下“审核状态为待审核状态的全部请假信息
                String department = currentUser.getYuanXi();
                resPage = leaveMapper.selectPageBySchool(page, department, currentUser.getId());
                List<Leave> schoolRecords = resPage.getRecords();
                for (int i = 0; i < schoolRecords.size(); i++) {
                    String schoolStatus = schoolRecords.get(i).getSchoolStatus();
                    if (schoolStatus.equals("0")) {
                        schoolRecords.get(i).setShowStatus("待审核");
                    }
                }
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
                List<Leave> deptOfficerRecords = resPage.getRecords();
                String showStatus = params[3];
                if (showStatus != null) {
                    switch (showStatus) {
                        case "待审核": {
                            List<Leave> newDeptOfficerRecords = new ArrayList<>();
                            for (int i = 0; i < deptOfficerRecords.size(); i++) {
                                if (deptOfficerRecords.get(i).getDepartmentStatus().equals("0")) {
                                    newDeptOfficerRecords.add(deptOfficerRecords.get(i));
                                }
                            }
                            // 给查询出的数据添加上showStatus字段信息
                            List<Leave> leaves = giveShowStatusParam(newDeptOfficerRecords, "1");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "已审核": {
                            List<Leave> newDeptOfficerRecords = new ArrayList<>();
                            for (int i = 0; i < deptOfficerRecords.size(); i++) {
                                if (!deptOfficerRecords.get(i).getDepartmentStatus().equals("0")) {
                                    newDeptOfficerRecords.add(deptOfficerRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newDeptOfficerRecords, "1");
                            resPage.setRecords(leaves);
                            break;
                        }
                    }
                } else {
                    List<Leave> leaves = giveShowStatusParam(deptOfficerRecords, "1");
                    resPage.setRecords(leaves);
                }
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
                List<Leave> deptLeaderRecords = resPage.getRecords();
                String showStatus = params[3];
                if (showStatus != null) {
                    switch (showStatus) {
                        case "待审核": {
                            List<Leave> newDeptLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < deptLeaderRecords.size(); i++) {
                                if (deptLeaderRecords.get(i).getDepartmentStatus().equals("3")) {
                                    newDeptLeaderRecords.add(deptLeaderRecords.get(i));
                                }
                            }
                            // 给查询出的数据添加上showStatus字段信息
                            List<Leave> leaves = giveShowStatusParam(newDeptLeaderRecords, "2");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "已审核": {
                            List<Leave> newDeptLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < deptLeaderRecords.size(); i++) {
                                if (!deptLeaderRecords.get(i).getDepartmentStatus().equals("1")) {
                                    newDeptLeaderRecords.add(deptLeaderRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newDeptLeaderRecords, "2");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "未流经": {
                            List<Leave> newDeptLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < deptLeaderRecords.size(); i++) {
                                if (!deptLeaderRecords.get(i).getDepartmentStatus().equals("1") && !deptLeaderRecords.get(i).getDepartmentStatus().equals("3")) {
                                    newDeptLeaderRecords.add(deptLeaderRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newDeptLeaderRecords, "2");
                            resPage.setRecords(leaves);
                            break;
                        }
                    }
                } else {
                    List<Leave> leaves = giveShowStatusParam(deptLeaderRecords, "2");
                    resPage.setRecords(leaves);
                }
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
                List<Leave> hrOfficerRecords = resPage.getRecords();
                String showStatus = params[3];
                if (showStatus != null) {
                    switch (showStatus) {
                        case "待审核": {
                            List<Leave> newhrOfficerRecords = new ArrayList<>();
                            for (int i = 0; i < hrOfficerRecords.size(); i++) {
                                if (hrOfficerRecords.get(i).getDepartmentStatus().equals("0")) {
                                    newhrOfficerRecords.add(hrOfficerRecords.get(i));
                                }
                            }
                            // 给查询出的数据添加上showStatus字段信息
                            List<Leave> leaves = giveShowStatusParam(newhrOfficerRecords, "3");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "已审核": {
                            List<Leave> newhrOfficerRecords = new ArrayList<>();
                            for (int i = 0; i < hrOfficerRecords.size(); i++) {
                                if (!hrOfficerRecords.get(i).getDepartmentStatus().equals("0")) {
                                    newhrOfficerRecords.add(hrOfficerRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newhrOfficerRecords, "3");
                            resPage.setRecords(leaves);
                            break;
                        }
                    }
                } else {
                    List<Leave> leaves = giveShowStatusParam(hrOfficerRecords, "3");
                    resPage.setRecords(leaves);
                }
                break;
            case "4":
                // 权限为人事处负责人，则当前用户可看到全校”部门审核已完成-审核状态为待审核“的全部请假信息
                for (int i = 0; i < params.length; i++) {
                    if (params[i].equals("null")) {
                        params[i] = null;
                    }
                }
                resPage = leaveMapper.selectPageByHrLeaderWrappered(page, params[0], params[1], params[2]);
                List<Leave> hrLeaderRecords = resPage.getRecords();
                String showhrStatus = params[3];
                if (showhrStatus != null) {
                    switch (showhrStatus) {
                        case "待审核": {
                            List<Leave> newHrLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < hrLeaderRecords.size(); i++) {
                                if (hrLeaderRecords.get(i).getDepartmentStatus().equals("3")) {
                                    newHrLeaderRecords.add(hrLeaderRecords.get(i));
                                }
                            }
                            // 给查询出的数据添加上showStatus字段信息
                            List<Leave> leaves = giveShowStatusParam(newHrLeaderRecords, "4");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "已审核": {
                            List<Leave> newHrLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < hrLeaderRecords.size(); i++) {
                                if (!hrLeaderRecords.get(i).getDepartmentStatus().equals("1")) {
                                    newHrLeaderRecords.add(hrLeaderRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newHrLeaderRecords, "4");
                            resPage.setRecords(leaves);
                            break;
                        }
                        case "未流经": {
                            List<Leave> newHrLeaderRecords = new ArrayList<>();
                            for (int i = 0; i < hrLeaderRecords.size(); i++) {
                                if (!hrLeaderRecords.get(i).getDepartmentStatus().equals("1") && !hrLeaderRecords.get(i).getDepartmentStatus().equals("3")) {
                                    newHrLeaderRecords.add(hrLeaderRecords.get(i));
                                }
                            }
                            List<Leave> leaves = giveShowStatusParam(newHrLeaderRecords, "4");
                            resPage.setRecords(leaves);
                            break;
                        }
                    }
                } else {
                    List<Leave> leaves = giveShowStatusParam(hrLeaderRecords, "2");
                    resPage.setRecords(leaves);
                }
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
                List<Leave> schoolRecords = resPage.getRecords();
                String showSchoolStatus = params[3];
                if (showSchoolStatus != null) {
                    switch (showSchoolStatus) {
                        case "待审核": {
                            List<Leave> newSchoolRecords = new ArrayList<>();
                            for (int i = 0; i < schoolRecords.size(); i++) {
                                if (schoolRecords.get(i).getDepartmentStatus().equals("0")) {
                                    newSchoolRecords.add(schoolRecords.get(i));
                                }
                            }
                            // 给查询出的数据添加上showStatus字段信息
                            List<Leave> leaves = giveShowStatusParam(newSchoolRecords, "5");
                            resPage.setRecords(leaves);
                            break;
                        }
                    }
                } else {
                    List<Leave> leaves = giveShowStatusParam(schoolRecords, "5");
                    resPage.setRecords(leaves);
                }
                break;
            }
        }

        return resPage;
    }

    @Override
    public Map<String, Object> getCurrentAuditMsgById(Long leaveId) {
        // 首先根据主键查询出leave请假信息
        Leave currentLeave = leaveMapper.findById(leaveId);
        String departmentStatus = currentLeave.getDepartmentStatus();
        String hrStatus = currentLeave.getHrStatus();
        String schoolStatus = currentLeave.getSchoolStatus();
        // 初始化统一的返回值类型
        Map<String, Object> resMap = new LinkedHashMap<>();
        /**
         * 业务判断流程
         * 1.首先查是否已有部门审核记录->有，执行2；没有，说明部门还未审核，DpObject为字符串"尚未进行部门审核"
         * 2.判断是否还需要人事处审核->需要，执行3；不需要，返回最终结果
         * 3.查看是否已有人事处审核记录->有，执行4；没有，说明人事处还未审核，HrObject为字符串"尚未进行人事处审核"
         * 4.判断是否还需要校领导审核->需要，执行5；不需要，返回最终结果
         * 5.查看是否已有校领导审核记录->有，返回最终结果；没有，说明校领导还未审核，ScObject为字符串"尚未进行校领导审核"，并返回最终结果
         */
        LeaveDepartmentAudit deptAuditMsgByFormId = leaveDepartmentAuditMapper.findDeptAuditMsgByFormId(leaveId);
        if (deptAuditMsgByFormId != null) {
            resMap.put("departmentAuditMsg", deptAuditMsgByFormId);
        } else {
            resMap.put("departmentAuditMsg", "尚未进行部门审核");
        }
        if (!hrStatus.equals("2")) {
            LeaveHrAudit hrAuditMsgByFormId = leaveHrAuditMapper.findHrAuditMsgByFormId(leaveId);
            if (hrAuditMsgByFormId != null) {
                resMap.put("hrAuditMsg", hrAuditMsgByFormId);
            } else {
                resMap.put("hrAuditMsg", "尚未进行人事处审核");
            }
        }
        if (!schoolStatus.equals("2")) {
            LeaveSchoolAudit schoolAuditMsgByFormId = leaveSchoolAuditMapper.findSchoolAuditMsgByFormId(leaveId);
            if (schoolAuditMsgByFormId != null) {
                resMap.put("schoolAuditMsg", schoolAuditMsgByFormId);
            } else {
                resMap.put("schoolAuditMsg", "尚未进行校领导审核");
            }
        }
        return resMap;
    }

    /**
     * 根据identity身份为输入的查询出的数据列表，依次添加showStatus字段
     * @author liyuanzhe
     * @date 2022/11/22 12:39
     * @param inputList
     * @param identity
     * @return List<Leave> 添加完showStatus字段后的数据
     */
    public static List<Leave> giveShowStatusParam(List<Leave> inputList, String identity) {
        switch (identity) {
            case "1": {     // departmentOfficer
                for (int i = 0; i < inputList.size(); i++) {
                    String departmentStatus = inputList.get(i).getDepartmentStatus();
                    if (departmentStatus.equals("0")) {
                        inputList.get(i).setShowStatus("待审核");
                    } else {
                        inputList.get(i).setShowStatus("已审核");
                    }
                }
                break;
            }
            case "2": {     // departmentLeader
                for (int i = 0; i < inputList.size(); i++) {
                    String departmentStatus = inputList.get(i).getDepartmentStatus();
                    if (departmentStatus.equals("1")) {
                        inputList.get(i).setShowStatus("已审核");
                    } else if (departmentStatus.equals("3")) {
                        inputList.get(i).setShowStatus("待审核");
                    } else {
                        inputList.get(i).setShowStatus("未流经");
                    }
                }
                break;
            }
            case "3": {     // hrOfficer
                for (int i = 0; i < inputList.size(); i++) {
                    String hrStatus = inputList.get(i).getHrStatus();
                    if (hrStatus.equals("0")) {
                        inputList.get(i).setShowStatus("待审核");
                    } else {
                        inputList.get(i).setShowStatus("已审核");
                    }
                }
                break;
            }
            case "4": {     // hrLeader
                for (int i = 0; i < inputList.size(); i++) {
                    String hrStatus = inputList.get(i).getHrStatus();
                    if (hrStatus.equals("1")) {
                        inputList.get(i).setShowStatus("已审核");
                    } else if (hrStatus.equals("3")) {
                        inputList.get(i).setShowStatus("待审核");
                    } else {
                        inputList.get(i).setShowStatus("未流经");
                    }
                }
                break;
            }
            case "5": {     // school
                for (int i = 0; i < inputList.size(); i++) {
                    String schoolStatus = inputList.get(i).getSchoolStatus();
                    if (schoolStatus.equals("0")) {
                        inputList.get(i).setShowStatus("待审核");
                    }
                }
                break;
            }

        }
        return inputList;
    }

}
