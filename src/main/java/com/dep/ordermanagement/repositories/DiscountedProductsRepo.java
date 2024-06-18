/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.repositories;

import com.dep.ordermanagement.pojo.db.DiscountedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
public interface DiscountedProductsRepo extends JpaRepository<DiscountedProducts, Integer> {
}
