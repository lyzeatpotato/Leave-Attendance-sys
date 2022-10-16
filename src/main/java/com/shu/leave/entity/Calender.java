package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("calender")     // 类名跟表明不一致时才需要加这个注解，用于告知mybatis-plus去查哪个表
public class Calender {

    // 主键id 自增
    private Long id;

    // 提交校历修改的的管理员id 外键 连接系统管理员表（admin_info）-系统用户id
    private Long adminId;

    // 校历假期名称
    private String holidayName;

    // 校历假期开始日期
    private Date holidayStartName;

    // 校历假期结束日期
    private Date holidayEndDate;

    // 校历假期描述信息
    private String description;

    // 逻辑删除
    private String isDeleted;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;
}