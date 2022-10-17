package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("absence_history")
public class History {

    // 主键id 自增
    private Long id;

    // 提交信息表的用户id
    private Long userId;

    // 年度信息
    private String year;

    // 月度信息
    private String month;

    // 本月累计事假总天数
    private String shijiaDays;

    // 本月累计病假总天数
    private String bingjiaDays;

    // 本月累计婚嫁总天数
    private String hunjiaDays;

    // 本月累计生育假总天数
    private String shengyujiaDays;

    // 本月累计探亲假总天数
    private String tanqinjiaDays;

    // 本月累计丧假总天数
    private String sangjiaDays;

    // 本月累计工伤假总天数
    private String gongshangjiaDays;

    // 本月累计公差数
    private String gongchaiDays;

    // 本月累计旷工总天数
    private String kuanggongDays;

    // 本月累计活跃度为0总天数
    private String inactiveDays;

    // 逻辑删除
    private String isDeleted;

    // 创建时间
    private Date gmtCreate;

    // 修改时间
    private Date gmtModified;
}
