/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.common;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionResponse {

    private String errorCode;

    private String errorMessage;

    private String httpStatus;

    private String href;

    private String methodType;

    private String timeStamp;

    private String traceId;

    private String spanId;

}