/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.utils;

import com.dep.ordermanagement.configs.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/***
 * @author Aditya Patil
 * @date 23-06-2024
 */
@Component
public class SecurityContextHolderUtils {

    public CustomUserDetails getUserDetailsFromContext() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication();
    }

    public int getTenantId() {
        CustomUserDetails customUserDetails = getUserDetailsFromContext();
        return Integer.parseInt(customUserDetails.getTenantId());
    }

    public int getUserId() {
        CustomUserDetails customUserDetails = getUserDetailsFromContext();
        return Integer.parseInt(customUserDetails.getUserId());
    }

}