package com.shu.leave;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shu.leave.mapper")
public class  LeaveAttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaveAttendanceApplication.class, args);
    }

}
