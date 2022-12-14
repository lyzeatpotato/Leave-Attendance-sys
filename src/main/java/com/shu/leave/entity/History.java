package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("absence_history")
public class History {

    // 主键id 自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 提交信息表的用户id
    @TableField(value = "userid")
    private Long userId;

    // 年度信息
    @TableField(value = "year")
    private String year;

    // 月度信息
    @TableField(value = "month")
    private String month;

    // 用户部门信息
    @TableField(value = "department")
    private String department;

    // 本月累计事假总天数
    @TableField(value = "shijia_days")
    private Integer shijiaDays;

    // 本月累计病假总天数
    @TableField(value = "bingjia_days")
    private Integer bingjiaDays;

    // 本月累计婚嫁总天数
    @TableField(value = "hunjia_days")
    private Integer hunjiaDays;

    // 本月累计生育假总天数
    @TableField(value = "shengyujia_days")
    private Integer shengyujiaDays;

    // 本月累计探亲假总天数
    @TableField(value = "tanqinjia_days")
    private Integer tanqinjiaDays;

    // 本月累计丧假总天数
    @TableField(value = "sangjia_days")
    private Integer sangjiaDays;

    // 本月累计工伤假总天数
    @TableField(value = "gongshangjia_days")
    private Integer gongshangjiaDays;

    // 本月累计公差数
    @TableField(value = "gongchai_days")
    private Integer gongchaiDays;

    // 本月累计旷工总天数
    @TableField(value = "kuanggong_days")
    private Integer kuanggongDays;

    // 本月累计活跃度为0总天数
    @TableField(value = "inactive_days")
    private Integer inactiveDays;

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
