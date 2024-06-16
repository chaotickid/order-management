/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */
@Data
public class CartDto {

    private Integer id;

    private String shippingAddress;

    private String cartStatus;

    private int cartPrice;

    private UserDto user;

    private List<OrderDto> orders = new ArrayList<>();

    //helper methods
    public void addOrder(OrderDto order){
        order.setCartDto(this);
        this.addOrder(order);
    }

    public void removeOrder(OrderDto order){
        order.setCartDto(null);
        this.removeOrder(order);
    }
}
