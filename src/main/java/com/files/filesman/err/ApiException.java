package com.files.filesman.err;

/**
 * 自定义API异常
 *
 */
public class ApiException extends RuntimeException {
    private Integer code;

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }
}
