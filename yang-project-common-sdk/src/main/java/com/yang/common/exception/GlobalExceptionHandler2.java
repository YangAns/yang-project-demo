package com.yang.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/10/13
 */
public class GlobalExceptionHandler2 extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler2() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("参数错误", HttpStatus.BAD_REQUEST);
    }
}
