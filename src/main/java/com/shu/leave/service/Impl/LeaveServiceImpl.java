package com.shu.leave.service.Impl;

import com.shu.leave.entity.Leave;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.service.LeaveService;
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
}
