package com.shu.leave.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请假类型
 */
@Getter
@AllArgsConstructor
public enum LeaveTypeCodeEnums {
    SHIJIA(1, "事假"),
    BINGJIA(2, "病假"),
    HUNJIA(3, "婚假"),
    CHANJIA(4, "产假"),
    PEICHANJIA(5, "陪产假"),
    SHENGYUJIA(6,"生育假"),
    TANQINJIA(6, "探亲假"),
    SANGJIA(7, "丧假"),
    YINGONGCHUCHAI(8, "因公出差"),
    GONGSHANGJIA(9, "工伤假");

    // 请假类型编号
    private int code;

    // 请假类型字段名称
    private String leaveType;

    /**
     * 提前判断，用于解决
     * Case中出现的Constant expression required
     * @param leaveType
     * @return
     */
    public static LeaveTypeCodeEnums getByValue(String leaveType) {
        for (LeaveTypeCodeEnums leaveTypeEnum : LeaveTypeCodeEnums.values()) {
            if (leaveTypeEnum.getLeaveType().equals(leaveType)) {
                return leaveTypeEnum;
            }
        }
        return null;
    }
}
