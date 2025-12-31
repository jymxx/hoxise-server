package cn.hoxise.common.file.aop;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Author hoxise
 * @Description: 文件存储切面
 * @Date 2025-12-31 下午4:00
 */
@Aspect
@Component
@Slf4j
@ConditionalOnProperty("fileStorage.minio.endpoint")
public class FileStorageServiceAOP {

    @Value("${fileStorage.serializerPrefix}")
    private String serializerPrefix;

    @Value("${fileStorage.minio.endpoint}")
    private String endpoint;

    @Pointcut("execution(* cn.hoxise.common.file.service.MinioServiceImpl.getPresignedUrl(..)) " +
            "|| execution(* cn.hoxise.common.file.service.MinioServiceImpl.getAbsoluteUrl(..))")
    public void serializerMethods() {}

    @Around("serializerMethods()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed(); // 执行原方法
        //序列化成比如CDN服务器的路径
        if (result instanceof String && StrUtil.isNotBlank((String) result)) {
            result = ((String) result).replace(endpoint, serializerPrefix);
        }
        return result;
    }
}
