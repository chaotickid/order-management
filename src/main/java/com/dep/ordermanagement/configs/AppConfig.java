/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/***
 * @author Aditya Patil
 * @date 20-06-2024
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String imageFolderName;

    private String jwtSecret;

    private String jwtExpirationMs;

    private String issuer;

}