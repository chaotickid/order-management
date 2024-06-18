/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo.db;
/***
 * @author Aditya Patil
 * @date 18-06-2024
 */

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tenantName;

    private String createdAt;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TenantItems> tenantItemsList = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<User> userList = new ArrayList<>();

    //helper methods
    public void addUserIntoList(User user) {
        this.userList.add(user);
        user.setTenant(this);
    }

    public void removeUserFromList(User user) {
        this.userList.remove(user);
        user.setTenant(null);
    }

    public void addTenantItemsToList(TenantItems tenantItems){
        this.tenantItemsList.add(tenantItems);
        tenantItems.setTenant(this);
    }

    public void removeTenantItemsFromList(TenantItems tenantItems){
        this.tenantItemsList.remove(tenantItems);
        tenantItems.setTenant(null);
    }

}
