package com.shu.leave.utils;

import com.shu.leave.common.ResponseCodeEnums;
import com.shu.leave.common.ResultEntity;

/**
 * 基本响应工具类，简化controller返回响应的代码。
 */
public class BasicResponseUtils {
    /**
     * 返回自定义响应（包含数据）
     */
    public static ResultEntity info(Integer code, String message, Object data) {
        return new ResultEntity(code, message, data);
    }

    /**
     * 成功并返回数据
     */
    public static ResultEntity success(Object data) {
        ResultEntity response = new ResultEntity();
        response.setCode(ResponseCodeEnums.OK.getCode());
        response.setMessage(ResponseCodeEnums.OK.getMsg());
        response.setData(data);
        return response;
    }

    /**
     * 成功但不返回数据
     */
    public static ResultEntity success() {
        ResultEntity response = new ResultEntity();
        response.setCode(ResponseCodeEnums.OK.getCode());
        response.setMessage(ResponseCodeEnums.OK.getMsg());
        return response;
    }

    /**
     * 出错并返回枚举类型错误信息
     */
    public static ResultEntity error(ResponseCodeEnums responseCodeEnums) {
        ResultEntity response = new ResultEntity();
        response.setCode(responseCodeEnums.getCode());
        response.setMessage(responseCodeEnums.getMsg());
        return response;
    }
}
