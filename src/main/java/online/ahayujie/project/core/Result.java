package online.ahayujie.project.core;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 * @author aha
 */
public class Result<T> implements Serializable {

    /**
     *响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMessage(), null);
    }

    private Result(ResultCodeEnum resultCodeEnum, String message) {
        this(resultCodeEnum.getCode(), message, null);
    }

    private Result(ResultCodeEnum resultCodeEnum, String message, T data) {
        this(resultCodeEnum.getCode(), message, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(ResultCodeEnum.SUCCESS, message);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCodeEnum.FAIL);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        return new Result<>(resultCodeEnum);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCodeEnum.FAIL, message);
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum, String message) {
        return new Result<>(resultCodeEnum, message);
    }

    public static <T> Result<T> data(T data) {
        return data(ResultCodeEnum.SUCCESS, data);
    }

    public static <T> Result<T> data(ResultCodeEnum resultCodeEnum, T data) {
        return data(resultCodeEnum, resultCodeEnum.getMessage(), data);
    }

    public static <T> Result<T> data(String message, T data) {
        return data(ResultCodeEnum.SUCCESS, message, data);
    }

    public static <T> Result<T> data(ResultCodeEnum resultCodeEnum, String message, T data) {
        return new Result<>(resultCodeEnum, data == null ? "暂无承载数据" : message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
