package com.shu.leave;

import com.shu.leave.entity.Leave;
import com.shu.leave.mapper.LeaveMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LeaveAttendanceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    LeaveMapper leaveMapper;

    @Test
    void testSelectLeaveFromDept(){
        List<Leave> leaves = leaveMapper.selectByUserDept("1");
        System.out.println(leaves);

    }

}
