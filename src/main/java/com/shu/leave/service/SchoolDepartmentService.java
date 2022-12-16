package com.shu.leave.service;

import com.shu.leave.entity.SchoolDepartment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolDepartmentService {
    /**
     * 根据school_leader_id查询校领导对应分管部门信息
     * @param school_leader_id
     * @return 对应的分管部门信息
     */
    List<SchoolDepartment> findSchoolDepartmentById(String school_leader_id);

    /**
     * 获取全校校领导-部门对应信息
     * @author liyuanzhe
     * @date 2022/12/15 17:11
     * @return List<SchoolDepartment>
     */
    List<SchoolDepartment> getAllSchoolDeptMsg();
}
