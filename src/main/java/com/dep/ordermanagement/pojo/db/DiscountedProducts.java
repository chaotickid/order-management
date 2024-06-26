/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.db;

import jakarta.persistence.*;
import lombok.Data;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */

@Data
@Entity
@Table(name = "discounted_products")
public class DiscountedProducts {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private String discountedPrice;

    private String tenantItemId;

    @ManyToOne
    private Cart cart;
}
