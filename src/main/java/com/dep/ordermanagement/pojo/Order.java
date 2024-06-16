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
@Data
@Entity
@Table(name = "my_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String finalPrice;

    private String orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId", referencedColumnName = "id")
    private Items itemId;

    @ManyToOne
    private Cart cart;
}
