package com.shu.leave.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.service.LeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public List<Leave> findAllLeaveForm() {
        return leaveMapper.selectAllLeave();
    }

    @Override
    public IPage<Leave> findAllLeaveFormPagination() {
        Page<Leave> page = new Page<>(0, 10);
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<Leave>();
        queryWrapper.eq("is_deleted", "0");
        IPage iPage = leaveMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public Leave findLeaveFormById(Long id) {
        return leaveMapper.selectById(id);
    }

    @Override
    public List<Leave> findLeaveFormByUserid(Long userid) {
        return leaveMapper.selectByUserid(userid);
    }
}
