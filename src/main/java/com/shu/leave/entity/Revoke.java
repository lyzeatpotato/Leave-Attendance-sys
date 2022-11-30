package com.shu.leave.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Revoke
 * @Description: 销假申请表实体类
 * @author: lyz
 * @date: 2022 11 2022/11/30 21:12
 */

@Data
@TableName("revoke")
public class Revoke {

    // 主键 自增 唯一标识id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 外键 连接请假申请表-表单id
    @TableField(value = "formid")
    private Long formId;

    // 返岗报道日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "revoke_report_time")
    private Date revokeReportTime;

    // 销假表提交日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(value = "revoke_submit_time")
    private Date revokeSubmitTime;

    // 销假表单状态(0表示待审核，1表示已审核-通过，2表示已审核-不通过，3表示已撤销)
    @TableField(value = "status")
    private String status;

    // 部门审核状态
    @TableField(value = "department_status")
    private String departmentStatus;

    // 人事处审核状态
    @TableField(value = "hr_status")
    private String hrStatus;

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
