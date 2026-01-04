package cn.hoxise.common.log.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.ai.uitls.SaTokenUtil;
import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.base.utils.servlet.ServletUtil;
import cn.hoxise.common.log.annotations.OperateLog;
import cn.hoxise.common.log.enums.OperateTypeEnum;
import cn.hoxise.common.log.service.OperateLogBaseDTO;
import cn.hoxise.common.log.service.OperateLogBaseService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static cn.hoxise.common.base.enums.ResultCodeEnum.INTERNAL_SERVER_ERROR;
import static cn.hoxise.common.base.enums.ResultCodeEnum.SUCCESS;


/**
 * 拦截使用 @OperateLog 注解，如果满足条件，则生成操作日志。
 * 满足如下任一条件，则会进行记录：
 * 1. 使用 @ApiOperation + 非 @GetMapping
 * 2. 使用 @OperateLog 注解
 * <p>
 * 但是，如果声明 @OperateLog 注解时，将 enable 属性设置为 false 时，强制不记录。
 *
 * @author 芋道源码
 */
@Aspect
@Slf4j
@Component
public class OperateLogAspect {

    /**
     * 用于记录操作内容的上下文
     *
     * @see OperateLogBaseDTO#getContent()
     */
    private static final ThreadLocal<String> CONTENT = new ThreadLocal<>();

    @Resource private OperateLogBaseService operateLogBaseService;

//    @Around("@annotation(operation)")
//    public Object around(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {
//        // 可能也添加了 @ApiOperation 注解
//        OperateLog operateLog = getMethodAnnotation(joinPoint, OperateLog.class);
//        return around0(joinPoint, operateLog, operation);
//    }
//
//    @Around("!@annotation(io.swagger.v3.oas.annotations.Operation) && @annotation(operateLog)")
//    // 兼容处理，只添加 @OperateLog 注解的情况
//    public Object around(ProceedingJoinPoint joinPoint,OperateLog operateLog) throws Throwable {
//        return around0(joinPoint, operateLog, null);
//    }

    //只记录显示注解的操作日志
    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint joinPoint,OperateLog operateLog) throws Throwable {
        Operation operation = getMethodAnnotation(joinPoint, Operation.class);
        return around0(joinPoint, operateLog, operation);
    }

    private Object around0(ProceedingJoinPoint joinPoint,
                           OperateLog operateLog,
                           Operation operation) throws Throwable {

        // 记录开始时间
        LocalDateTime startTime = LocalDateTime.now();
        try {
            // 执行原有方法
            Object result = joinPoint.proceed();
            // 记录正常执行时的操作日志
            this.log(joinPoint, operateLog, operation, startTime, result, null);
            return result;
        } catch (Throwable exception) {
            this.log(joinPoint, operateLog, operation, startTime, null, exception);
            throw exception;
        } finally {
            clearThreadLocal();
        }
    }

    public static void setContent(String content) {
        CONTENT.set(content);
    }

    private static void clearThreadLocal() {
        CONTENT.remove();
    }

    private void log(ProceedingJoinPoint joinPoint,
                     OperateLog operateLog,
                     Operation operation,
                     LocalDateTime startTime, Object result, Throwable exception) {
        try {
            // 判断不记录的情况
            if (!isLogEnable(joinPoint, operateLog)) {
                return;
            }
            // 真正记录操作日志
            this.log0(joinPoint, operateLog, operation, startTime, result, exception);
        } catch (Throwable ex) {
            log.error("[log][记录操作日志时，发生异常，其中参数是 joinPoint({}) operateLog({}) apiOperation({}) result({}) exception({}) ]",
                    joinPoint, operateLog, operation, result, exception, ex);
        }
    }

    private void log0(ProceedingJoinPoint joinPoint,
                      OperateLog operateLog,
                      Operation operation,
                      LocalDateTime startTime, Object result, Throwable exception) {
        OperateLogBaseDTO operateLogObj = new OperateLogBaseDTO();
        // 补全通用字段
        operateLogObj.setStartTime(startTime);
        // 补充用户信息
        fillUserFields(operateLogObj);
        // 补全模块信息
        fillModuleFields(operateLogObj, joinPoint, operateLog, operation);
        // 补全请求信息
        fillRequestFields(operateLogObj);
        // 补全方法信息
        fillMethodFields(operateLogObj, joinPoint, operateLog, startTime, result, exception);

        // 异步记录日志
        operateLogBaseService.createOperateLog(operateLogObj);
    }

    private static void fillUserFields(OperateLogBaseDTO operateLogObj) {
        operateLogObj.setUserId(SaTokenUtil.getLoginId());
    }

    private static void fillModuleFields(OperateLogBaseDTO operateLogObj,
                                         ProceedingJoinPoint joinPoint,
                                         OperateLog operateLog,
                                         Operation operation) {
        // module 属性
        if (operateLog != null) {
            operateLogObj.setModule(operateLog.module());
        }
        if (StrUtil.isEmpty(operateLogObj.getModule())) {
            Tag tag = getClassAnnotation(joinPoint, Tag.class);
            if (tag != null) {
                // 优先读取 @Tag 的 name 属性
                if (StrUtil.isNotEmpty(tag.name())) {
                    operateLogObj.setModule(tag.name());
                }
                // 没有的话，读取 @API 的 description 属性
                if (StrUtil.isEmpty(operateLogObj.getModule()) && ArrayUtil.isNotEmpty(tag.description())) {
                    operateLogObj.setModule(tag.description());
                }
            }
        }
        // name 属性
        if (operateLog != null) {
            operateLogObj.setName(operateLog.name());
        }
        if (StrUtil.isEmpty(operateLogObj.getName()) && operation != null) {
            operateLogObj.setName(operation.summary());
        }
        // type 属性
        if (operateLog != null && ArrayUtil.isNotEmpty(operateLog.type())) {
            operateLogObj.setType(operateLog.type()[0].getType());
        }
        if (operateLogObj.getType() == null) {
            RequestMethod requestMethod = obtainFirstMatchRequestMethod(obtainRequestMethod(joinPoint));
            OperateTypeEnum operateLogType = convertOperateLogType(requestMethod);
            operateLogObj.setType(operateLogType != null ? operateLogType.getType() : null);
        }
        // content 和 exts 属性
        operateLogObj.setContent(CONTENT.get());
    }

    private static void fillRequestFields(OperateLogBaseDTO operateLogObj) {
        // 获得 Request 对象
        HttpServletRequest request = ServletUtil.getRequest();
        if (request == null) {
            return;
        }
        // 补全请求信息
        operateLogObj.setRequestMethod(request.getMethod());
        operateLogObj.setRequestUrl(request.getRequestURI());
        operateLogObj.setUserIp(ServletUtil.getClientIP(request));
        operateLogObj.setUserAgent(ServletUtil.getUserAgent(request));
    }

    private static void fillMethodFields(OperateLogBaseDTO operateLogObj,
                                         ProceedingJoinPoint joinPoint,
                                         OperateLog operateLog,
                                         LocalDateTime startTime, Object result, Throwable exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        operateLogObj.setJavaMethod(methodSignature.toString());
        if (operateLog == null || operateLog.logArgs()) {
            operateLogObj.setJavaMethodArgs(obtainMethodArgs(joinPoint));
        }
        if (operateLog == null || operateLog.logResultData()) {
            operateLogObj.setResultData(obtainResultData(result));
        }
        operateLogObj.setDuration((int) (LocalDateTimeUtil.between(startTime, LocalDateTime.now()).toMillis()));
        // （正常）处理 resultCode 和 resultMsg 字段
        if (result instanceof CommonResult<?> commonResult) {
            operateLogObj.setResultCode(commonResult.getCode());
            operateLogObj.setResultMsg(commonResult.getMsg());
        } else {
            operateLogObj.setResultCode(SUCCESS.getCode());
        }
        // （异常）处理 resultCode 和 resultMsg 字段
        if (exception != null) {
            operateLogObj.setResultCode(INTERNAL_SERVER_ERROR.getCode());
            operateLogObj.setResultMsg(ExceptionUtil.getRootCauseMessage(exception));
        }
    }

    private static boolean isLogEnable(ProceedingJoinPoint joinPoint,
                                       OperateLog operateLog) {
        Object loginIdDefaultNull = StpUtil.getLoginIdDefaultNull();
        System.out.println(loginIdDefaultNull);
        // 没有用户信息也不记录
        if (!SaTokenUtil.isLogin()){
            return false;
        }
        // 有 @OperateLog 注解的情况下
        if (operateLog != null) {
            return operateLog.enable();
        }
        // 没有 @ApiOperation 注解的情况下，只记录 POST、PUT、DELETE 的情况
        return obtainFirstLogRequestMethod(obtainRequestMethod(joinPoint)) != null;
    }

    private static RequestMethod obtainFirstLogRequestMethod(RequestMethod[] requestMethods) {
        if (ArrayUtil.isEmpty(requestMethods)) {
            return null;
        }
        return Arrays.stream(requestMethods).filter(requestMethod ->
                        requestMethod == RequestMethod.POST
                                || requestMethod == RequestMethod.PUT
                                || requestMethod == RequestMethod.DELETE)
                .findFirst().orElse(null);
    }

    private static RequestMethod obtainFirstMatchRequestMethod(RequestMethod[] requestMethods) {
        if (ArrayUtil.isEmpty(requestMethods)) {
            return null;
        }
        // 优先，匹配最优的 POST、PUT、DELETE
        RequestMethod result = obtainFirstLogRequestMethod(requestMethods);
        if (result != null) {
            return result;
        }
        // 然后，匹配次优的 GET
        result = Arrays.stream(requestMethods).filter(requestMethod -> requestMethod == RequestMethod.GET)
                .findFirst().orElse(null);
        if (result != null) {
            return result;
        }
        // 兜底，获得第一个
        return requestMethods[0];
    }

    private static OperateTypeEnum convertOperateLogType(RequestMethod requestMethod) {
        if (requestMethod == null) {
            return null;
        }
        return switch (requestMethod) {
            case GET -> OperateTypeEnum.GET;
            case POST -> OperateTypeEnum.CREATE;
            case PUT -> OperateTypeEnum.UPDATE;
            case DELETE -> OperateTypeEnum.DELETE;
            default -> OperateTypeEnum.OTHER;
        };
    }

    private static RequestMethod[] obtainRequestMethod(ProceedingJoinPoint joinPoint) {
        RequestMapping requestMapping = AnnotationUtils.getAnnotation( // 使用 Spring 的工具类，可以处理 @RequestMapping 别名注解
                ((MethodSignature) joinPoint.getSignature()).getMethod(), RequestMapping.class);
        return requestMapping != null ? requestMapping.method() : new RequestMethod[]{};
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(annotationClass);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Annotation> T getClassAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(annotationClass);
    }

    private static String obtainMethodArgs(ProceedingJoinPoint joinPoint) {
        // TODO 提升：参数脱敏和忽略
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        // 拼接参数
        Map<String, Object> args = Maps.newHashMapWithExpectedSize(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            // 被忽略时，标记为 ignore 字符串，避免和 null 混在一起
            args.put(argName, !isIgnoreArgs(argValue) ? argValue : "[ignore]");
        }
        return JSONObject.toJSONString(args);
    }

    private static String obtainResultData(Object result) {
        // TODO 提升：结果脱敏和忽略
        if (result instanceof CommonResult<?>) {
            result = ((CommonResult<?>) result).getData();
        }
        return JSONObject.toJSONString(result);
    }

    private static boolean isIgnoreArgs(Object object) {
        Class<?> clazz = object.getClass();
        // 处理数组的情况
        if (clazz.isArray()) {
            return IntStream.range(0, Array.getLength(object))
                    .anyMatch(index -> isIgnoreArgs(Array.get(object, index)));
        }
        // 递归，处理数组、Collection、Map 的情况
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) object).stream()
                    .anyMatch((Predicate<Object>) OperateLogAspect::isIgnoreArgs);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return isIgnoreArgs(((Map<?, ?>) object).values());
        }
        // obj
        return object instanceof MultipartFile
                || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse
                || object instanceof BindingResult;
    }

}
