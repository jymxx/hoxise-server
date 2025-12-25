package cn.hoxise.system.biz.service.sms;

import cn.hoxise.common.base.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author hoxise
 * @Description: 阿里云短信客户端
 * 文档 https://help.aliyun.com/zh/pnvs/developer-reference/api-dypnsapi-2017-05-25-sendsmsverifycode
 * @Date 2025-12-13 上午12:12
 */
@Slf4j
@Component
public class AliyunSmsClient {

    @Value("${sms.aliyun.accessKey}")
    private String accessKeyId;

    @Value("${sms.aliyun.accessSecret}")
    private String accessKeySecret;

    private static final String ENDPOINT = "dypnsapi.aliyuncs.com";

    private static Client client;

    @PostConstruct
    protected void setClient() throws Exception {
        Config config = new Config()
                // 工程代码建议使用更安全的无 AK 方式，凭据配置方式请参见：https://help.aliyun.com/document_detail/378657.html。
                // new`现在用spring-dotenv环境变量配置
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
                .setEndpoint(ENDPOINT);
        client = new Client(config);
        log.info("阿里云短信客户端初始化完成");
    }

    /**
     * @Author hoxise
     * @Description: 发送验证码
     * @Date 2025-12-13 上午12:12
     */
    public static SendSmsVerifyCodeResponseBody sendVerifyCodeAliyun(String mobile) {
        if (mobile == null) {
            throw new ServiceException("手机号码不能为空");
        }
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
     * @Author hoxise
     * @Description: 校验验证码
     * @Date 2025-12-13 上午12:12
     */
    public static boolean checkVerifyCodeAliyun(String mobile, String code) {
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
