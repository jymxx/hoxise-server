package cn.hoxise.common.base.exception;

import cn.hoxise.common.base.enums.ResultCodeEnum;

/**
 * 业务逻辑异常 Exception
 */
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


    public Integer getCode() {
        return code;
    }

    public ServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

}
