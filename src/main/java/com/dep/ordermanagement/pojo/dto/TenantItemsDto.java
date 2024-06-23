/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantItemsDto {

    private int id;

    private String productName;

    private String price;

    private String description;

    private String specifications;

    private String discountedPrice;

    private String imagePath;

    private String quantity;

}
