package com.shu.leave.service.Impl;

import com.shu.leave.entity.LeaveDepartmentAudit;
import com.shu.leave.entity.LeaveHrAudit;
import com.shu.leave.entity.LeaveSchoolAudit;
import com.shu.leave.mapper.LeaveDepartmentAuditMapper;
import com.shu.leave.mapper.LeaveHrAuditMapper;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.mapper.LeaveSchoolAuditMapper;
import com.shu.leave.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


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




    /*
    审核工作
    */
    @Override
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

                if (result=="通过")
                    //通过就修改leave表状态
                    leaveDepartmentAuditMapper.dpOfficerAudity(id,timeStamp);
                else
                    leaveDepartmentAuditMapper.dpOfficerAuditn(id,timeStamp);
                break;
            }
            case "2": {
                leaveDepartmentAuditMapper.updateLeaveDepartmentAudit(userid,id,result,recommend,timeStamp);
                if (result=="通过")
                    //通过就修改状态
                    leaveDepartmentAuditMapper.dpLeaderAudity(id,timeStamp);
                else
                    leaveDepartmentAuditMapper.dpLeaderAuditn(id,timeStamp);
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
                if (result=="通过")
                    //通过就修改leave表状态
                    leaveHrAuditMapper.hrOfficerAudity(id,timeStamp);
                else
                    leaveHrAuditMapper.hrOfficerAuditn(id,timeStamp);
                break;
            }
            case "4": {
                leaveHrAuditMapper.updateLeaveHrAudit(userid, id, result, recommend, timeStamp);
                if (result=="通过")
                    //通过就修改状态
                    leaveHrAuditMapper.hrLeaderAudity(id,timeStamp);
                else
                    leaveHrAuditMapper.hrLeaderAuditn(id,timeStamp);
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

                if (result=="通过")
                    //通过就修改状态
                    leaveSchoolAuditMapper.scLeaderAudity(id,timeStamp);
                else
                    leaveSchoolAuditMapper.scLeaderAuditn(id,timeStamp);
                break;
            }
            default:
                return 0;
        }
        return 1;
    }


}
