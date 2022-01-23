package online.ahayujie.project.exception;


import online.ahayujie.project.core.ResultCodeEnum;

/**
 * API异常
 * @author aha
 * @date 2020/5/21
 */
public class ApiException extends RuntimeException {

    private final ResultCodeEnum resultCode;

    public ApiException(ResultCodeEnum resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    /**
     * @param message 错误信息
     */
    public ApiException(String message) {
        super(message);
        this.resultCode = ResultCodeEnum.FAIL;
    }

    /**
     * @param cause 错误原因
     */
    public ApiException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCodeEnum.FAIL;
    }

    /**
     * @param message 错误信息
     * @param cause 错误原因
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCodeEnum.FAIL;
    }

    public ResultCodeEnum getResultCode() {
        return resultCode;
    }
}
