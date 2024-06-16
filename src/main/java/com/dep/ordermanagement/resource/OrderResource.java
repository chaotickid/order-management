/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.CartDto;
import com.dep.ordermanagement.pojo.dto.ItemList;
import com.dep.ordermanagement.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@RestController
@RequestMapping("/api/v1")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{userId}")
    public ResponseEntity<CartDto> createOrdersAndAddToCart(@RequestBody ItemList itemList,
                                                            @PathVariable(value = "userId") int userId) {
        return new ResponseEntity<>(orderService.createOrdersAndAddToCart(itemList, userId), HttpStatus.OK);
    }
}
