/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.ItemsDto;
import com.dep.ordermanagement.services.ItemService;
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
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @PostMapping("/item/{tenantId}")
    public ResponseEntity <ItemsDto> createItem(@RequestBody ItemsDto itemsDto, @PathVariable (value = "tenantId") int tenantId){
        return new ResponseEntity<>(itemService.createItem(itemsDto, tenantId), HttpStatus.CREATED);
    }
}