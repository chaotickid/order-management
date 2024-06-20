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
@Table(name = "my_products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private String price;

    private String description;

    private String specifications;

    private boolean isSelectedForFinalOrder;

    private String quantity;

    private String tenantItemId;

    @ManyToOne
    private Cart cart;
}
