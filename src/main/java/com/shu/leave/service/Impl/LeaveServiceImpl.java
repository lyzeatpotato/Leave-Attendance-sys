package com.shu.leave.service.Impl;

import com.shu.leave.entity.Leave;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.service.LeaveService;
import com.shu.leave.vo.SingleLeaveStepVo;
import com.shu.leave.vo.SingleLeaveVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Resource
    LeaveMapper leaveMapper;

    @Override
    public int addLeaveForm(String[] params) throws ParseException {
        Leave leaveForm = new Leave();
        leaveForm.setUserId(Long.valueOf(params[0]));
        leaveForm.setLeaveType(params[1]);
        SimpleDateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = df0.parse(params[2]);
        Date endDate = df0.parse(params[3]);
        leaveForm.setLeaveStartTime(startDate);
        leaveForm.setLeaveEndTime(endDate);
        leaveForm.setLeaveReason(params[4]);
        leaveForm.setLeaveMaterial(params[5]);
        leaveForm.setStatus(params[6]);
        leaveForm.setDepartmentStatus(params[7]);
        leaveForm.setHrStatus(params[8]);
        leaveForm.setSchoolStatus(params[9]);
        leaveForm.setIsDeleted("0");
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = Timestamp.valueOf(df1.format(date));
        leaveForm.setGmtCreate(timeStamp);
        leaveForm.setGmtModified(timeStamp);
        return leaveMapper.insert(leaveForm);
    }

    @Override
    public SingleLeaveVo selectSingleLeave(String role, String yuanxi, long id){
        SingleLeaveVo singleLeaveVo = leaveMapper.selectSingleLeave(yuanxi,id);
        return singleLeaveVo;
    }
    @Override
    public SingleLeaveStepVo selectSingleLeaveStep(String role,long id,String step){
        if (step=="1") {
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="2"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="3"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else if (step=="4"){
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
        else {
            SingleLeaveStepVo singleLeaveStepVo= leaveMapper.electSingleLeaveStepOne(role, id);
            return singleLeaveStepVo;
        }
    }
    /*
    审核工作
     */
    @Override
    public int singleLeaveAudit(String role, String userid, long id, String result, String recommend) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = df.format(System.currentTimeMillis());
        switch (role) {
            case "1": {
                leaveMapper.dpOfficerAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.dpOfficerAudity(id,time);
                else
                    leaveMapper.dpOfficerAuditn(id,time);
                break;
            }
            case "2": {
                leaveMapper.dpLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.dpLeaderAudity(id,time);
                else
                    leaveMapper.dpLeaderAuditn(id,time);
                break;
            }
            case "3": {
                leaveMapper.hrOfficerAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.hrOfficerAudity(id,time);
                else
                    leaveMapper.hrLeaderAuditn(id,time);
                break;
            }
            case "4": {
                leaveMapper.hrLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.hrLeaderAudity(id,time);
                else
                    leaveMapper.hrLeaderAuditn(id,time);
                break;
            }
            case "5": {
                leaveMapper.scLeaderAudit(userid, id, result, recommend, time);
                if (result=="通过")
                    //通过就修改状态
                    leaveMapper.scLeaderAudity(id,time);
                else
                    leaveMapper.scLeaderAuditn(id,time);
                break;
            }
            default:
                return 0;
        }
        return 1;
    }
}

