/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */

@Component
public class LoggingUtils {

    @Autowired
    private ObjectMapper objectMapper;

    public String prettifyJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }
    }
}
