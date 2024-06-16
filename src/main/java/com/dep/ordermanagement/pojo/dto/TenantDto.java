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
public class TenantDto {

    private Integer id;

    private String tenantName;

    private String state;

    private String zipCode;

    private String address;

    private String businessName;

    private List<ItemsDto> itemsDtoList = new ArrayList<>();

    private List<UserDto> userList = new ArrayList<>();

    //helper methods

    public void addItemList(ItemsDto itemsDto) {
        itemsDto.setTenant(this);
        this.addItemList(itemsDto);
    }

    public void removeItem(ItemsDto itemsDto){
        itemsDto.setTenant(null);
        this.removeItem(itemsDto);
    }

    //user
    public void addUserList(UserDto user){
        user.setTenant(this);
        this.addUserList(user);
    }

    public void removeUser(UserDto user){
        this.removeUser(user);
        user.setTenant(null);
    }
}
