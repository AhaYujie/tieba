package online.ahayujie.project.core;

import org.springframework.http.HttpStatus;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author aha
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(HttpStatus.OK.value(), "成功"),

    /**
     * 服务器内部错误
     */
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "失败"),

    /**
     * 未认证
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "未认证"),

    /**
     *拒绝访问
     */
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "拒绝访问"),

    /**
     * 接口不存在
     */
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "接口不存在");

    private final int code;

    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
