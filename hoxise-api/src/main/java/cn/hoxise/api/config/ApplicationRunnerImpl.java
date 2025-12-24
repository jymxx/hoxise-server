package cn.hoxise.api.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import cn.hoxise.common.base.utils.base.LocalhostInfoUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author 永远的十七岁
 * @description: 项目启动后执行
 * @date 2022/11/8 15:27
 */
@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Resource private LocalhostInfoUtil localhostInfoUtil;

    @Resource private RedisTemplate redisTemplate;

    @Value("${project.isClearCache}")
    @Schema(name = "项目启动后是否清空redis缓存")
    private Boolean isClearRedis;

    @Value("${project.runMode}")
    private String runMode;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        printInfo();
        clearRedisCache();
        if ("dev".equals(runMode)){
            disableSSLValidation();
        }
    }

    private void printInfo() throws UnknownHostException {
        String commandLog = "\n--------------------------------------------------------------------------------\n" +
                "                        项目启动成功!!!                                             \n" +
                "             本地访问地址: " + localhostInfoUtil.getLocalhost() + "                      \n" +
                "             网络访问地址: " + localhostInfoUtil.getInternetInfo() + "                   \n" +
                "             Swagger地址: " + localhostInfoUtil.getSwaggerUrl() + "                     \n" +
                "             " + localhostInfoUtil.getVersionInfo() + "                                \n" +
                "             " + localhostInfoUtil.getInfo() + "                                       " +
                "\n--------------------------------------------------------------------------------\n";
        log.info(commandLog);
    }

    private void clearRedisCache() {
        if(isClearRedis){
            Set keys = redisTemplate.keys(Constants.ASTERISK);
            if (ObjectUtil.isNotNull(keys)) {
                redisTemplate.delete(keys);
            }
            log.info("---项目启动后清除redis缓存完成.");
        }
    }

    private void disableSSLValidation() {
//        // 仅用于开发环境
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
//        System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");
//
//        log.warn("---禁用SSL验证");
    }
}
