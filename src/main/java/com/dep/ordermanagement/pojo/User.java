/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Cart cart;

    @ManyToOne
    private Tenant tenant;
}
