/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.JwtTokenResponse;
import com.dep.ordermanagement.pojo.dto.SignInRequest;
import com.dep.ordermanagement.pojo.dto.SignUpRequest;
import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.services.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author Aditya Patil
 * @date 22-06-2024
 */

@RestController
@RequestMapping("/api/v1/auth")
public class LoginResource {

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<JwtTokenResponse> authenticateUser(
            @Valid @RequestBody SignInRequest signInRequest) throws JsonProcessingException {
        return new ResponseEntity<>(loginService.signIn(signInRequest), HttpStatus.OK);
    }

    @PostMapping("/signup/user")
    public ResponseEntity<TenantDto> signUpAsUser(
            @Valid @RequestBody SignUpRequest signUpRequest) throws JsonProcessingException {
        return new ResponseEntity<>(loginService.signUpUser(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signup/tenant")
    public ResponseEntity<TenantDto> signUpAsTenant(
            @Valid @RequestBody SignUpRequest signUpRequest) throws JsonProcessingException {
        return new ResponseEntity<>(loginService.signUpTenant(signUpRequest), HttpStatus.CREATED);
    }
}