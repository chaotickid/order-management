/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.db;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String userName;

    private String userType;

    @ManyToOne
    private Tenant tenant;

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

    //helper method
    public void addCartToUser(Cart cart){
        this.setCart(cart);
        cart.setUser(this);
    }

    public void addOrderToUser(Order order){
        this.getOrderList().add(order);
        order.setUser(this);
    }

    public void removeOrderToUser(Order order){
        this.getOrderList().remove(order);
        order.setUser(null);
    }
}
