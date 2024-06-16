/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */
@Data
@Entity
@Table(name = "my_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String shippingAddress;

    private String cartStatus;

    private String cartPrice;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    //helper methods
    public void addOrder(Order order){
        order.setCart(this);
        this.addOrder(order);
    }

    public void removeOrder(Order order){
        order.setCart(null);
        this.removeOrder(order);
    }
}
