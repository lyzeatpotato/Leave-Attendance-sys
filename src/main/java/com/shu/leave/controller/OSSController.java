package com.shu.leave.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.shu.leave.enums.ResponseCodeEnums;
import com.shu.leave.common.ResultEntity;
import com.shu.leave.utils.BasicResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: lyz
 * @date: 2022-11-2022/11/4
 * @description: 前端获取服务器数字签名接口
 */
@Api(tags = "9.OSS对象存储")
@ApiSupport(order = 9)
@RestController
@RequestMapping("oss")
public class OSSController {

    @ApiOperation(value = "获取验证信息", notes = "获取前端文件直传所需的数字签名,policy等")
    @ApiOperationSupport(order = 1)
    @GetMapping("getPolicy")
    public ResultEntity policy() {
        String accessId = "LTAI5tEUP6FdQ5xxYtxpmhhb";
        String accessKey = "ru9yggp3oWaX1ZLNSkq7cxPe7Jvbdf";
        // Endpoint华东2（上海）
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        // Bucket名称。
        String bucket = "shurenshichu-leave-oss-20221101";
        // Host地址，格式为https://shurenshichu-leave-oss-20221101.oss-cn-shanghai.aliyuncs.com。
        String host = "https://" + bucket + "." + endpoint;
        //// 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
        //String callbackUrl = "https://192.168.0.0:8888";
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下(按照日期区分文件夹)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date());
        String dir = "leaveMaterial/" + format + "/";

        // 创建ossClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);    // oss数字签名

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessId", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

            // 待服务端上线后,配置后端回调用于日志记录
            ////JSONObject jasonCallback = new JSONObject();
            ////jasonCallback.put("callbackUrl", callbackUrl);
            ////jasonCallback.put("callbackBody",
            ////        "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            ////jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            ////String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            ////respMap.put("callback", base64CallbackBody);
            //
            //JSONObject ja1 = JSONObject.fromObject(respMap);
            //// System.out.println(ja1.toString());
            //response.setHeader("Access-Control-Allow-Origin", "*");
            //response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            //response(request, response, ja1.toString());
            return BasicResponseUtils.success(respMap);
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return BasicResponseUtils.error(ResponseCodeEnums.DOWNLOAD_ERROR);
    }
}
