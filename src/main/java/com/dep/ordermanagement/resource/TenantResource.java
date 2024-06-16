/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.services.TenantService;
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
public class TenantResource {

    @Autowired
    private TenantService tenantService;

    @PostMapping("/org")
    public ResponseEntity<?> createOrganization(@RequestBody TenantDto tenantDto){
        return new ResponseEntity<>(tenantService.createTenant(tenantDto), HttpStatus.CREATED);
    }

    @GetMapping("/org/{id}")
    public ResponseEntity<?> getTenant(@PathVariable (value = "id") int id){
        return new ResponseEntity<>(tenantService.getTenant(id), HttpStatus.OK);
    }
}
