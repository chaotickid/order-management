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
@Entity
@Data
@Table(name = "my_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderStatus;

    private String createdAt;

    private String expectedDelivery;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade =  CascadeType.ALL, fetch =  FetchType.LAZY,orphanRemoval = true)
    private List<OrderItems> orderItemsList = new ArrayList<>();

    public void addOrderItemUnderOrder(OrderItems orderItems){
        this.orderItemsList.add(orderItems);
        orderItems.setOrder(this);
    }

    public void removeOrderItemUnderOrder(OrderItems orderItems){
        this.orderItemsList.remove(orderItems);
        orderItems.setOrder(null);
    }

}
