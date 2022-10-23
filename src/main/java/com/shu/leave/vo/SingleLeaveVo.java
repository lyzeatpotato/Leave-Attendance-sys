package com.shu.leave.vo;

import lombok.Data;

import java.util.Date;
@Data
public class SingleLeaveVo {
    private Long id;
    private String username;
    private String yuanXi;
    private String leaveType;
    private Date leaveStartTime;
    private Date leaveEndTime;
    private String leaveReason;
    private String leaveMaterial;
    private String departmentStatus;
    private String hrStatus;
    private String schoolStatus;
    private static final long serialVersionUID = 1L;
}
