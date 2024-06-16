/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.repositories.TenantRepo;
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
    public ModelMapper modelMapper;

    @Autowired
    public TenantRepo tenantRepo;

    public TenantDto createTenant(TenantDto tenantDto){
        return null;
    }

    public TenantDto getTenant(int id){
        return null;
    }
}
