package cn.hoxise.common.base.pojo;

import cn.hoxise.common.base.enums.ResultCodeEnum;
import cn.hoxise.common.base.exception.ServiceException;
import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


/**
 * 全局通用返回类
 *
 * @author hoxise
 * @param <T>
 * @since 2026/01/14 06:34:13
 */
@Data
public class CommonResult<T> implements Serializable {

    /** 错误码 */
    private Integer code;

    /** 返回数据 */
    private T data;

    /** 错误提示，用户可阅读 */
    private String msg;

    public CommonResult(){
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = ResultCodeEnum.SUCCESS.getMessage();
    }

    public static <T> CommonResult<T> ok(){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    public static <T> CommonResult<T> success(T data){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> CommonResult<T> error(String msg){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(ResultCodeEnum.UNKNOWN.getCode());
        result.setMsg(msg);
        return result;
    }

    public static <T> CommonResult<T> error(ResultCodeEnum resultCodeEnum) {
        return error(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.notEquals(ResultCodeEnum.SUCCESS.getCode(), code, "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, ResultCodeEnum.SUCCESS.getCode());
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isError() {
        return !isSuccess();
    }

    // ========= 和 Exception 异常体系集成 =========

    /**
     * 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常
     */
    public void checkError() throws ServiceException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new ServiceException(code, msg);
    }

    /**
     * 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常
     * 如果没有，则返回 {@link #data} 数据
     */
    @JsonIgnore // 避免 jackson 序列化
    public T getCheckedData() {
        checkError();
        return data;
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage());
    }
}
