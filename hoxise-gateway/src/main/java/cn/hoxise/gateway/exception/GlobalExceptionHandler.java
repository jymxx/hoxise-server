package cn.hoxise.gateway.exception;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebExchange;

import static cn.hoxise.common.base.enums.ResultCodeEnum.*;

/**
 * GlobalExceptionHandler 全局异常处理
 *
 * @author hoxise
 * @since 2026/01/14 06:15:17
 */
@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {


    /**
     * 处理404异常
     * <p>
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    public CommonResult<?> serviceExceptionHandler(NoResourceFoundException ex) {
        log.info("[网关404异常]", ex);
        return CommonResult.error(NOT_FOUND);
    }

    /**
     * 处理业务异常 ServiceException
     * <p>
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
        log.info("[业务异常]", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(ServerWebExchange exchange, Throwable ex) {
        log.error("[defaultExceptionHandler] uri: {}", exchange.getRequest().getURI());
        log.error("[defaultExceptionHandler]", ex);
        // 返回 ERROR CommonResult
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }
}