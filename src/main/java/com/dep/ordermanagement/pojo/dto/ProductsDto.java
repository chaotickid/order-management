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
public class ProductsDto {

    private int id;

    private String productName;

    private String price;

    private String description;

    private String specifications;

    private boolean isSelectedForFinalOrder;

    private String quantity;

}