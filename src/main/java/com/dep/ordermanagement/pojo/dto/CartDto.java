/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import lombok.Data;

import java.util.List;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@Data
public class CartDto {

    private String userId;

    private List<TenantItemsDto> tenantItemsList;

    private List<ProductsDto> productsList;

}