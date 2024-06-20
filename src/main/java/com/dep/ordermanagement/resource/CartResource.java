/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.CartDto;
import com.dep.ordermanagement.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-products")
    public ResponseEntity<?> addProductsToCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addProductsToCart(cartDto), HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewCart(@PathVariable (value = "userId") int userId){
        return new ResponseEntity<>(cartService.viewItemsInTheCart(userId), HttpStatus.OK);

    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyCart(@RequestBody CartDto cartDto,
                                        @RequestParam(value = "operation", required = false) String operation){
        return new ResponseEntity<>(cartService.modifyCart(cartDto, operation), HttpStatus.OK);
    }
}