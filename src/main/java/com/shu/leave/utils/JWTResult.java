package com.shu.leave.utils;

import com.shu.leave.enums.ResponseCodeEnums;
import io.jsonwebtoken.Claims;

public class JWTResult {

    /**
     * 错误编码。在JWTUtils中定义的常量。
     * 200为正确
     */
    private ResponseCodeEnums codeEnum;

    /**
     * 是否成功，代表结果的状态。
     */
    private boolean success;

    /**
     * 验证过程中payload中的数据。
     */
    private Claims claims;

    public ResponseCodeEnums getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(ResponseCodeEnums codeEnum) {
        this.codeEnum = codeEnum;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

}
