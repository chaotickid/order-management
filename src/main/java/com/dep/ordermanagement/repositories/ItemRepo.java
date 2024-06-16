/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.repositories;

import com.dep.ordermanagement.pojo.Items;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */
public interface ItemRepo extends JpaRepository<Items, Integer> {
}