package cn.hoxise.module.system.service.sms;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 阿里云短信客户端
 * 文档https://help.aliyun.com/zh/pnvs/developer-reference/api-dypnsapi-2017-05-25-sendsmsverifycode
 * 因为目前只有这一种 暂时这样放着
 *
 * @author hoxise
 * @since 2026/1/14 上午5:34
 */
@Slf4j
@Component
public class AliyunSmsClient {

    @Value("${sms.aliyun.access-key}")
    private String accessKeyId;

    @Value("${sms.aliyun.access-secret}")
    private String accessKeySecret;

    @Value("${sms.aliyun.endpoint}")
    private String endpoint;

    private static Client client;

    @PostConstruct
    protected void setClient() throws Exception {
        Config config = new Config()
                // 工程代码建议使用更安全的无 AK 方式，凭据配置方式请参见：https://help.aliyun.com/document_detail/378657.html。
                // new`现在用spring-dotenv环境变量配置
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
                .setEndpoint(endpoint);
        client = new Client(config);
        log.info("阿里云短信客户端初始化完成");
    }

    /**
     * @param mobile 手机号
     * @return @return
     * @author hoxise
     * @since 2026/01/14
     */
    public static SendSmsVerifyCodeResponseBody sendVerifyCodeAliyun(String mobile) {
        Assert.notBlank(mobile, "手机号码不能为空");
        SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest();
        request.setPhoneNumber(mobile);
        request.setSignName("速通互联验证码"); //签名
        request.setTemplateCode("100001");// 验证码模板
        String param = """
                {"code":"##code##","min":"5"}
                """;
        request.setTemplateParam(param);
        try {
            SendSmsVerifyCodeResponse response = client.sendSmsVerifyCode(request);
            SendSmsVerifyCodeResponseBody body = response.getBody();
            log.info(JSONObject.toJSONString(body));
            return body;
        }catch (Exception error) {
            log.error("aliyun-sms发送服务异常...{0}", error);
            throw new RuntimeException("aliyun-sms发送服务异常");
        }
    }


    /**
     * @param mobile 手机号
     * @param code 验证码
     * @return @return boolean
     * @author hoxise
     *  2026/01/14
     */
    public static boolean checkVerifyCodeAliyun(String mobile, String code) {
        Assert.notBlank(mobile, "手机号码不能为空");
        Assert.notBlank(code, "验证码不能为空");
        CheckSmsVerifyCodeRequest request = new CheckSmsVerifyCodeRequest();
        request.setPhoneNumber(mobile);
        request.setVerifyCode(code);
        try {
            CheckSmsVerifyCodeResponse response = client.checkSmsVerifyCode(request);
            CheckSmsVerifyCodeResponseBody body = response.getBody();
            if (body.getSuccess()){
                return "PASS".equals(body.getModel().getVerifyResult());
            }else {
                return false;
            }
        }catch (Exception error) {
            log.error("aliyun-sms校验服务异常...{0}", error);
            throw new RuntimeException("aliyun-sms校验服务异常");
        }
    }
}
