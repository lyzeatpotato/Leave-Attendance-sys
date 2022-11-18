package com.shu.leave.utils;

import com.shu.leave.enums.ResponseCodeEnums;
import io.jsonwebtoken.*;
import net.iharder.Base64;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class JwtUtils {
    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return String
     *
     */
    public static String createJWT(String id, String subject, long ttlMillis) throws IOException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject) // 主题
                .setIssuer("wsg") // 签发者
                .setIssuedAt(now) // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static JWTResult validateJWT(String jwtStr) {
        JWTResult checkResult = new JWTResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) { // token过期
            checkResult.setCodeEnum(ResponseCodeEnums.JWT_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) { // token错误
            checkResult.setCodeEnum(ResponseCodeEnums.JWT_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setCodeEnum(ResponseCodeEnums.JWT_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 从headers中验证token
     * @param headers HttpHeaders
     * @return JWTResult
     */
    public static JWTResult validateJWT(HttpHeaders headers) {
        String token = Objects.requireNonNull(headers.get("token")).get(0);
        return validateJWT(token);
    }

    private static SecretKey generalKey() throws IOException {
        byte[] encodedKey = Base64.decode("shusecret");
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT字符串
     * @param jwt
     * @return jwt的三部分内容
     * @throws Exception
     */
    private static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}
