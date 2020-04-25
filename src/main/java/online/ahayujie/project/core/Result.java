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

    private Result(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage(), null);
    }

    private Result(ResultCode resultCode, String message) {
        this(resultCode.getCode(), message, null);
    }

    private Result(ResultCode resultCode, String message, T data) {
        this(resultCode.getCode(), message, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(ResultCode.SUCCESS, message);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAIL);
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCode.FAIL, message);
    }

    public static <T> Result<T> fail(ResultCode resultCode , String message) {
        return new Result<>(resultCode, message);
    }

    public static <T> Result<T> data(T data) {
        return data(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> data(ResultCode resultCode, T data) {
        return data(resultCode, resultCode.getMessage(), data);
    }

    public static <T> Result<T> data(String message, T data) {
        return data(ResultCode.SUCCESS, message, data);
    }

    public static <T> Result<T> data(ResultCode resultCode, String message, T data) {
        return new Result<>(resultCode, data == null ? "暂无承载数据" : message, data);
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
