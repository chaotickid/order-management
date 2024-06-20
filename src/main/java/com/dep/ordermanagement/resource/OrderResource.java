/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.OrderedItemDto;
import com.dep.ordermanagement.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderResource {


    @Autowired
    private OrderService orderService;

    @PostMapping("/submit/{userId}")
    public ResponseEntity<?> submitOrder(@PathVariable(value = "userId") int userId) {
        orderService.submitOrder(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<OrderedItemDto>> viewOrder(@PathVariable(value = "orderId") int orderId) {
        return new ResponseEntity<>(orderService.viewOrder(orderId), HttpStatus.OK);
    }

}