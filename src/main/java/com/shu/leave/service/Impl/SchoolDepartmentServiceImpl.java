package com.shu.leave.service.Impl;

import com.shu.leave.entity.SchoolDepartment;
import com.shu.leave.mapper.SchoolDepartmentMapper;
import com.shu.leave.service.SchoolDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolDepartmentServiceImpl implements SchoolDepartmentService {
    @Autowired
    SchoolDepartmentMapper schoolDepartmentMapper;
    @Override
    public List<SchoolDepartment> findSchoolDepartmentById(String school_leader_id) {
        return schoolDepartmentMapper.selectBySchoolLeaderId(school_leader_id);
    }

    @Override
    public List<SchoolDepartment> getAllSchoolDeptMsg() {
        return schoolDepartmentMapper.selectAllSchoolDept();
    }

}
