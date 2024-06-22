///**
// * Copyright © 2024 Mavenir Systems
// */
//package com.dep.ordermanagement.resource;
//
//import com.dep.ordermanagement.pojo.dto.TenantDto;
//import com.dep.ordermanagement.services.TenantService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///***
// * @author Aditya Patil
// * @date 18-06-2024
// */
//
//@RestController
//@RequestMapping("/api/v1")
//public class TenantResource {
//
//    @Autowired
//    private TenantService tenantService;
//
//    @PostMapping("/tenant")
//    public ResponseEntity<?> createTenant(@RequestBody TenantDto tenantDto) {
//        return new ResponseEntity<>(tenantService.createTenant(tenantDto),
//                HttpStatus.CREATED);
//    }
//    @PostMapping("/user")
//    public ResponseEntity<?> createUser(@RequestBody TenantDto tenantDto) {
//        return new ResponseEntity<>(tenantService.createUser(tenantDto),
//                HttpStatus.CREATED);
//    }
//
//}
