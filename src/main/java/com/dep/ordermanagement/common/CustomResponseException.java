/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.common;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Getter
public class CustomResponseException extends ResponseStatusException {

    private String errorCode;

    private String errorMessage;

    private HttpStatus httpStatus;

    private String href;

    private String methodType;

    private String timeStamp;

    private String traceId;

    private String spanId;

    public CustomResponseException(ErrorCodeEnum errorCodeEnum,
                                   HttpStatus httpStatus){
        super(httpStatus, errorCodeEnum.getErrorMessage());
        HttpServletRequest request =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                        .getRequest();
        this.errorCode = errorCodeEnum.getErrorCode();
        this.errorMessage =  errorCodeEnum.getErrorMessage();
        this.httpStatus = httpStatus;
        this.href = request.getRequestURI();
        this.methodType = request.getMethod();
        this.timeStamp = String.valueOf(Instant.now());
    }
}