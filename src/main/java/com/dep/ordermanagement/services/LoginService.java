/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.common.CustomResponseException;
import com.dep.ordermanagement.configs.security.CustomUserDetails;
import com.dep.ordermanagement.configs.security.JwtUtils;
import com.dep.ordermanagement.pojo.db.Cart;
import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.db.User;
import com.dep.ordermanagement.pojo.dto.JwtTokenResponse;
import com.dep.ordermanagement.pojo.dto.SignInRequest;
import com.dep.ordermanagement.pojo.dto.SignUpRequest;
import com.dep.ordermanagement.pojo.dto.TenantDto;
import com.dep.ordermanagement.repositories.CartRepo;
import com.dep.ordermanagement.repositories.TenantRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.dep.ordermanagement.common.ErrorCodeEnum.*;

/***
 * @author Aditya Patil
 * @date 22-06-2024
 */

@Slf4j
@Service
@Transactional
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private CartRepo cartRepo;

    /***
     * signIn
     *
     * @param signInRequest
     * @return
     */
    public JwtTokenResponse signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        jwtTokenResponse.setEmail(userDetails.getEmail());
        jwtTokenResponse.setId(userDetails.getId());
        jwtTokenResponse.setJwt(jwt);
        return jwtTokenResponse;
    }

    public TenantDto signUpTenant(SignUpRequest signUpRequest){
        Tenant tenant = null;
        User user = null;
        TenantDto tenantDtoToUI = new TenantDto();
        //1] create user first for tenant
        try {
            user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            user.setUserName(signUpRequest.getUserName());
            user.setUserType("TENANT");
            user.setRole("ADMIN");
            userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }
        //2] create tenant
        try {
            tenant = new Tenant();
            tenant.setTenantName(signUpRequest.getUserName());
            tenant.setCreatedAt(String.valueOf(Instant.now()));
            tenantRepo.save(tenant);
        } catch (Exception e) {
            throw e;
        }
        //3] Add user under tenant
        tenant.addUserIntoList(user);
        tenantDtoToUI.setId(tenant.getId());
        tenantDtoToUI.setTenantName(tenant.getTenantName());
        tenantDtoToUI.setEmail(signUpRequest.getEmail());
        return tenantDtoToUI;
    }

    /***
     *
     * @param signUpRequest
     */
    public TenantDto signUpUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomResponseException(ER10013, HttpStatus.BAD_REQUEST);
        }

        Tenant tenant = null;
        User user = null;
        Cart cart = null;
        TenantDto tenantDtoToUI = new TenantDto();
        //1] create user first for tenant
        try {
            user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            user.setUserName(signUpRequest.getUserName());
            user.setUserType("CONSUMER");
            user.setRole("USER");
            userRepository.save(user);
        } catch (Exception e) {
            throw new CustomResponseException(ER10001, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //2] fetch tenant
        try {
            tenant = tenantRepo.findById(signUpRequest.getTenantId()).orElseThrow(() -> new RuntimeException("tenant does not exist"));
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
        tenantDtoToUI.setTenantName(user.getUserName());
        tenantDtoToUI.setEmail(signUpRequest.getEmail());
        return tenantDtoToUI;
    }

}