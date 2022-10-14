package com.shu.leave.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface LeaveService {

    /**
     * 新增一条请假表单
     * @param params -> [userid, leave_type, leave_start_time, leave_end_time, leave_reason, leave_material]
     * @return 新增请假表的主键id
     */
    int addLeaveForm(String[] params) throws ParseException;

    /**
     * 查询全部请假申请表
     * @return 请假申请表列表
     */
    List<Leave> findAllLeaveForm();

    /**
     * 分页查询全部请假表信息
     * @return 当前页的请假申请表数量&当前属于第几页
     */
    IPage<Leave> findAllLeaveFormPagination();

    /**
     * 根据请假表id查询详细信息
     * @param id
     * @return 对应id的请假表详情信息
     */
    Leave findLeaveFormById(Long id);

    /**
     * 根据用户id查询请假表信息
     * @param userid
     * @return 对应id用户的全部请假表列表
     */
    List<Leave> findLeaveFormByUserid(Long userid);

    /**
     * 按部门查询: 查询某一部门下的全部请假记录
     * @param department
     * @return 对应部门的全部请假列表
     */
    List<Leave> findLeaveFormByUserDept(String department);

    /**
     * 按部门条件查询: 查询某一部门下需要人事处审核，但尚未完成的全部请假记录
     * @param department
     * @return 对应的请假列表
     */
    List<Leave> findLeaveFormByUserDeptAndUnfinishedHR(String department);

    /**
     * 按部门条件查询: 查询某一部门下需要校领导审核，但尚未完成的全部请假记录
     * @param department
     * @return 对应的请假列表
     */
    List<Leave> findLeaveFormByUserDeptAndUnfinishedSchool(String department);
}
