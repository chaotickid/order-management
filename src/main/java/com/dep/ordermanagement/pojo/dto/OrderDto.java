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
public class OrderDto {

    private Integer id;

    private String finalPrice;

    private String orderStatus;

    private ItemsDto itemId;

    private CartDto cartDto;
}
