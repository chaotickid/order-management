/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.db.Items;
import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.dto.ItemsDto;
import com.dep.ordermanagement.repositories.ItemRepo;
import com.dep.ordermanagement.repositories.TenantRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */
@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private TenantRepo tenantRepo;

    public ItemsDto createItem(ItemsDto itemsDto, int tenantId){

        Tenant tenant = tenantRepo.findById(tenantId).orElseThrow(()-> new RuntimeException("Tenant not found"));
        Items items = new Items();
        items.setItemName(itemsDto.getItemName());
        items.setPrice(itemsDto.getPrice());
        items.setAvailableQuantity(itemsDto.getAvailableQuantity());
        Items savedItem = itemRepo.save(items);
        tenant.addItemList(savedItem);
        return itemsDto;
    }
}