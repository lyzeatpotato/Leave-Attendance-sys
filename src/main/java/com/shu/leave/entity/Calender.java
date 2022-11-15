package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("calender")     // 类名跟表明不一致时才需要加这个注解，用于告知mybatis-plus去查哪个表
public class Calender {

    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 提交校历修改的的管理员id 外键 连接系统管理员表（admin_info）-系统用户id
    @TableField(value = "adminid")
    private Long adminId;

    // 年度信息
    @TableField(value = "year")
    private String year;

    // 校历假期名称
    @TableField(value = "holiday_name")
    private String holidayName;

    // 校历假期开始日期
    @TableField(value = "holiday_start_date")
    private Date holidayStartDate;

    // 校历假期结束日期
    @TableField(value = "holiday_end_date")
    private Date holidayEndDate;

    // 校历假期描述信息
    @TableField(value = "description")
    private String description;

    // 逻辑删除
    @TableField(value = "is_deleted")
    private String isDeleted;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    // 修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}