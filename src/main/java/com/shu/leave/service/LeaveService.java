package com.shu.leave.service;

import com.shu.leave.vo.SingleLeaveStepVo;
import com.shu.leave.vo.SingleLeaveVo;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.text.ParseException;

@Service
public interface LeaveService {

    /**
     * 新增一条请假表单
     * @param params -> [userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material, status, department_status, hr_status, school_status]
     * @return 新增请假表的主键id
     */
    int addLeaveForm(String[] params) throws ParseException;
    /**
     * 查询单个请假信息
     */
    SingleLeaveVo selectSingleLeave(String role, String yuanxi, long id);
    SingleLeaveStepVo selectSingleLeaveStep(String role,long id,String step);
    int singleLeaveAudit(String role, String userid, long id,String result,String recommend);
}
