package cn.hoxise.common.security.satoken.web;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hoxise.common.base.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.hoxise.common.base.enums.ResultCodeEnum.FORBIDDEN;
import static cn.hoxise.common.base.enums.ResultCodeEnum.UNAUTHORIZED;

/**
 * sa-token异常
 *
 * @author hoxise
 * @since 2026/2/3 下午1:47
 */
@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 处理 satoken 框架产生的未登录异常
     * <p>
     */
    @ExceptionHandler(value = NotLoginException.class)
    public CommonResult<?> notLoginExceptionHandler(NotLoginException ex) {
        log.warn("[未登录]", ex);
        return CommonResult.error(UNAUTHORIZED.getCode(),ex.getMessage());
    }

    /**
     * 处理 satoken 框架产生的其它异常
     * <p>
     * 无角色、无权限等等
     */
    @ExceptionHandler(value = SaTokenException.class)
    public CommonResult<?> saTokenExceptionHandler(SaTokenException ex) {
        log.warn("[satoken异常]", ex);
        return CommonResult.error(FORBIDDEN);
    }
}
