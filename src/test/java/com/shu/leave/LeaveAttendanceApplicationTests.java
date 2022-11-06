package com.shu.leave;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leave.entity.Leave;
import com.shu.leave.entity.SchoolDepartment;
import com.shu.leave.mapper.AbsenceHistoryMapper;
import com.shu.leave.mapper.LeaveMapper;
import com.shu.leave.mapper.SchoolDepartmentMapper;
import com.shu.leave.service.Impl.LeaveServiceImpl;
import com.shu.leave.service.LeaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class LeaveAttendanceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    LeaveService leaveService;

    @Autowired
    AbsenceHistoryMapper absenceHistoryMapper;

    @Autowired
    SchoolDepartmentMapper schoolDepartmentMapper;

    public Date strToDate(String str){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*@Test
    void testSelectLeaveFromDept(){

//        int[] res = new int[0];
//        try {
//            res = leaveService.judgeAuditFlow( "20221111","病假",strToDate("2022-10-29 00:00:00"),strToDate("2022-12-30 00:00:00"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(""+res[0]+"  "+res[1]+"  "+res[2]);

    }*/

    public String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    @Test
    void testAuditFlow(){
       int i =  absenceHistoryMapper.selectShiJiaDaysByUidAndYear(new Long(1), getCurrentYear());
        System.out.println(i);
    }
}
