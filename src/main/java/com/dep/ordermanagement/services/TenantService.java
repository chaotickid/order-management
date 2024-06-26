/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.common.CustomResponseException;
import com.dep.ordermanagement.pojo.db.Cart;
import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.db.User;
import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.repositories.CartRepo;
import com.dep.ordermanagement.repositories.TenantRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.dep.ordermanagement.common.ErrorCodeEnum.*;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */

@Service
@Transactional
public class TenantService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private CartRepo cartRepo;

    /***
     * 1] create user first for tenant
     * 2] create tenant
     * 3] Add user under tenant
     *
     * @param tenantDto
     */
    public TenantDto createTenant(TenantDto tenantDto) {
        Tenant tenant = null;
        User user = null;
        TenantDto tenantDtoToUI = new TenantDto();
        //1] create user first for tenant
        try {
            user = new User();
            user.setEmail(tenantDto.getEmail());
            user.setPassword(tenantDto.getPassword());
            user.setUserName(tenantDto.getTenantName());
            user.setUserType("TENANT");
            userRepo.save(user);
        } catch (Exception e) {
            throw e;
        }
        //2] create tenant
        try {
            tenant = new Tenant();
            tenant.setTenantName(tenantDto.getTenantName());
            tenant.setCreatedAt(String.valueOf(Instant.now()));
            tenantRepo.save(tenant);
        } catch (Exception e) {
            throw e;
        }
        //3] Add user under tenant
        tenant.addUserIntoList(user);
        tenantDtoToUI.setId(tenant.getId());
        tenantDtoToUI.setTenantName(tenant.getTenantName());
        tenantDtoToUI.setEmail(tenantDto.getEmail());
        return tenantDtoToUI;
    }

    /***
     * 1] create user first for tenant
     * 2] create tenant
     * 3] Add user under tenant
     * 4] create a empty cart
     * 5] add that cart to user
     *
     * @param tenantDto
     */
    public TenantDto createUser(TenantDto tenantDto) {
        Tenant tenant = null;
        User user = null;
        Cart cart = null;
        TenantDto tenantDtoToUI = new TenantDto();
        //1] create user first for tenant
        try {
            user = new User();
            user.setEmail(tenantDto.getEmail());
            user.setPassword(tenantDto.getPassword());
            user.setUserName(tenantDto.getTenantName());
            user.setUserType("CONSUMER");
            userRepo.save(user);
        } catch (Exception e) {
            throw new CustomResponseException(ER10001, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //2] fetch tenant
        try {
            tenant = tenantRepo.findById(tenantDto.getId()).orElseThrow(() -> new RuntimeException("tenant does not exist"));
        } catch (Exception e) {
            throw new CustomResponseException(ER10002, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //3] Add user under tenant
        tenant.addUserIntoList(user);

        //4] create a empty cart
        cart = new Cart();
        try {
            cartRepo.save(cart);
        } catch (Exception e) {
            throw new CustomResponseException(ER10003, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //5] add that cart to user
        user.addCartToUser(cart);
        tenantDtoToUI.setId(tenant.getId());
        tenantDtoToUI.setTenantName(tenant.getTenantName());
        tenantDtoToUI.setEmail(tenantDto.getEmail());
        return tenantDtoToUI;
    }
}
