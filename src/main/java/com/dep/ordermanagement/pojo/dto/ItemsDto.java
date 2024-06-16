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
public class ItemsDto {

    private Integer id;

    private String price;

    private String itemName;

    private String specifications;

    private String availableQuantity;

    private TenantDto tenant;

    private OrderDto order;
}
