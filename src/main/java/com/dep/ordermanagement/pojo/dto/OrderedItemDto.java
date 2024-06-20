/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import lombok.Data;

/***
 * @author Aditya Patil
 * @date 19-06-2024
 */
@Data
public class OrderedItemDto {

    private String productName;

    private String price;

    private String description;

    private String specifications;

    private String quantity;

}