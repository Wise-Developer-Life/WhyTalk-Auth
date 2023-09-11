package com.wisedevlife.whytalkauth.common.exception;

import com.wisedevlife.whytalkauth.common.enums.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthException extends RuntimeException {
    private final int code;
    private final HttpStatus httpStatus;

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public AuthException(ErrorCodeEnum errorCode, HttpStatus httpStatus) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.httpStatus = httpStatus;
    }
}
