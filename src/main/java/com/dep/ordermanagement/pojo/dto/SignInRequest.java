/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/***
 * @author Aditya Patil
 * @date 22-06-2024
 */
@Data
public class SignInRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

}