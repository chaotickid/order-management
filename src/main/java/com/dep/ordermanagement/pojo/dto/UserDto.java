/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import jakarta.persistence.*;
import lombok.Data;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Data
public class UserDto {

    private Integer id;

    private String email;

    private String firstName;

    private String userType;

    private String address;

    private String country;

    private String zipCode;

    private CartDto cartDto;

    private TenantDto tenant;
}
