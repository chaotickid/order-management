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
@Table(name = "tenant_items")
public class TenantItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private String price;

    private String description;

    private String specifications;

    private String imagePath;

    @ManyToOne
    private Tenant tenant;
}
