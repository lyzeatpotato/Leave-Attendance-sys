package com.shu.leave.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举类
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnums {
    OK(200, "成功"),
    ERROR(-1, "失败"),
    LOGIN_ERROR(-100, "用户名或密码错误"),
    JWT_NOT_EXIST(-101, "缺少TOKEN，拒绝访问"),
    JWT_FAIL(-102, "TOKEN认证错误"),
    JWT_EXPIRE(-103, "TOKEN失效"),
    INTERNAL_ERROR(-500, "服务器错误"),
    RANGE_ERROR(-2, "参数范围错误"),
    NO_RECORD(-3, "数据库中不存在这条记录"),
    CAN_NOT_EMPTY(-4, "输入字段不能为空"),
    RECORD_EXIST(-5, "该条目已存在"),
    RECORD_INVALID(-6, "该条目已经失效"),
    UPLOAD_ERROR(-7, "上传文件失败"),
    FILTERED_PARAM_ERROR(-8, "过滤参数错误"),
    REFLECT_ERROR(-9, "反射调用失败"),
    VALUE_ERROR(-10, "参数值错误"),
    STATUS_ERROR(-11, "状态错误"),
    DELETE_ERROR(-12, "删除失败"),
    DOWNLOAD_ERROR(-13, "下载文件失败"),
    FILE_NOT_EXIST(-14, "文件不存在");

    //状态码
    private Integer code;

    //状态码信息
    private String msg;
}
