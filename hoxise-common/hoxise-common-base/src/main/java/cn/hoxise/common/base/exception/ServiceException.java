package cn.hoxise.common.base.exception;

import cn.hoxise.common.base.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务逻辑异常
 *
 * @author hoxise
 * @since 2026/01/14 06:34:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     *
     * @see ResultCodeEnum
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    public ServiceException(String message) {
        this.code = ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
    }

    public ServiceException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }


}
