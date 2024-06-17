/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.db.User;
import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.repositories.TenantRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Service
@Slf4j
public class TenantService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private UserRepo userRepo;

    public TenantDto createTenant(TenantDto tenantDto){
        Tenant tenant = modelMapper.map(tenantDto, Tenant.class);
        User user = new User();
        user.setEmail("tenant@yopmail.com");
        User savedUserReference = userRepo.save(user);
        tenant.addUserList(savedUserReference);
        tenantRepo.save(tenant);
        return tenantDto;
    }

    public TenantDto getTenant(int id){
        return null;
    }
}
