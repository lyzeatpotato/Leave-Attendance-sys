package com.shu.leave.vo;

import lombok.Data;

import java.util.Date;
@Data
public class SingleLeaveStepVo {
    private Long id;
    private String dpOfficerId;
    private String dpOfficerName;
    private String dpOfficerResult;
    private String dpOfficerRecommend;
    private Date dpOfficerTime;
    private char dpOfficerStatus;
    private static final long serialVersionUID = 1L;
}
