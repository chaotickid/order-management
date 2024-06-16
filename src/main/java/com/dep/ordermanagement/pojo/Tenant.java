/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Entity
@Data
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tenantName;

    private String state;

    private String zipCode;

    private String address;

    private String businessName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "tenant")
    private List<Items> itemsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "tenant")
    private List<User> userList = new ArrayList<>();

    //helper methods

    public void addItemList(Items items) {
        items.setTenant(this);
        this.addItemList(items);
    }

    public void removeItem(Items items){
        items.setTenant(null);
        this.removeItem(items);
    }

    //user
    public void addUserList(User user){
        user.setTenant(this);
        this.addUserList(user);
    }

    public void removeUser(User user){
        this.removeUser(user);
        user.setTenant(null);
    }
}
