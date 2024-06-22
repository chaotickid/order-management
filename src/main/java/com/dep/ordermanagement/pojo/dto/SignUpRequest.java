/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import lombok.Data;

/***
 * @author Aditya Patil
 * @date 22-06-2024
 */
@Data
public class SignUpRequest {

    private int id;

    private String email;

    private String userName;

    private String password;

    private int tenantId;

}