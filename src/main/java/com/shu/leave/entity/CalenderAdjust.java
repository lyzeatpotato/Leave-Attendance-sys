package com.shu.leave.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("calender_leave_adjust")     // 类名跟表明不一致时才需要加这个注解，用于告知mybatis-plus去查哪个表
public class CalenderAdjust {
    // 主键id 自增
    private Long id;
    // 外键 连接日历管理表（calender）-假期id
    private Long calenderId;

    // 调休名称
    private String holidayName;

    // 调休开始日期
    private Date holidayStartDate;

    // 调休结束日期
    private Date holidayEndDate;

    // 逻辑删除
    private String isDeleted;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;
}
