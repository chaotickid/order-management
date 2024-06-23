/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Configuration
public class BeanConfigs {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}