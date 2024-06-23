/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.configs.security;

/***
 * @author Aditya Patil
 * @date 22-06-2024
 */

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dep.ordermanagement.configs.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    private AppConfig appConfig;

    public String generateJwtToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String, String> claims = new HashMap<>();
        claims.put("tenantId", customUserDetails.getTenantId());
        claims.put("userId", customUserDetails.getUserId());
        claims.put("role", customUserDetails.getRole());
        claims.put("email", customUserDetails.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setIssuer(appConfig.getIssuer())
                .setExpiration(new Date((new Date()).getTime() + Integer.parseInt(appConfig.getJwtExpirationMs())))
                .setSubject((customUserDetails.getEmail()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(appConfig.getJwtSecret()));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
