package com.wisedevlife.whytalkauth.common.exception;

import com.wisedevlife.whytalkauth.common.enums.ErrorCodeEnum;
import com.wisedevlife.whytalkauth.common.helper.ResponseHandler;
import com.wisedevlife.whytalkauth.dto.response.ReturnResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ReturnResponse<Object>> globalExceptionHandler(Exception e) {
        log.error("Catching unknown runtime exception.", e);
        return ResponseHandler.badRequest(ErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity<ReturnResponse<Object>> authExceptionHandler(AuthException e) {
        return ResponseHandler.responseWithStatus(e.getCode(), e.getMessage(), e.getHttpStatus());
    }
}
