package cn.hoxise.common.base.pojo;

import cn.hoxise.common.base.enums.ResultCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


/**
 * * 全局通用返回类
 * @param <T>
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
}
