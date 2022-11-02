package com.shu.leave.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时限管理的不同类型角色
 * @author xieyuying
 */
@Getter
@AllArgsConstructor
public enum  AuditLimitTimeRoleEnum {
    SYSTEM(1L,"系统"),
    HR(2L,"人事处"),
    HR_YEAR(3L,"人事处年度"),
    SCHOOL(4L,"校领导"),
    SCHOOL_YEAR(5L,"校领导年度");

    //role_id
    private Long id;
    //type
    private String type;
}
