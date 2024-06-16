/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo;

import jakarta.persistence.*;
import lombok.Data;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */
@Entity
@Data
@Table(name = "my_items_list")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String price;

    private String availableQuantity;

    @ManyToOne
    private Tenant tenant;

    @OneToOne(mappedBy = "itemId")
    private Order order;
}
