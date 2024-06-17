/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.db.Cart;
import com.dep.ordermanagement.pojo.db.Items;
import com.dep.ordermanagement.pojo.db.Order;
import com.dep.ordermanagement.pojo.db.User;
import com.dep.ordermanagement.pojo.dto.CartDto;
import com.dep.ordermanagement.pojo.dto.ItemList;
import com.dep.ordermanagement.pojo.dto.ItemsDto;
import com.dep.ordermanagement.repositories.CartRepo;
import com.dep.ordermanagement.repositories.ItemRepo;
import com.dep.ordermanagement.repositories.OrderRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 16-06-2024
 */

@Slf4j
@Service
@Transactional
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    public CartDto createOrdersAndAddToCart(ItemList itemsDtoList, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not exits"));


        //get item list from db
        List<Items> itemsList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        int cartPrice = 0;
        if (!CollectionUtils.isEmpty(itemsDtoList.getItemList())) {

            for (ItemsDto itemsDto : itemsDtoList.getItemList()) {
                itemsList.add(itemRepo.findById(itemsDto.getId()).orElseThrow(() -> new RuntimeException("item not found")));
            }
        }

        //create orders from item list
        Cart cart;

        if (null == user.getCart()) {
            cart = new Cart();
        } else {
            cart = user.getCart();
        }

        if (!CollectionUtils.isEmpty(itemsList)) {
            for (Items items : itemsList) {
                Order order = new Order();
                order.setFinalPrice(items.getPrice());
                order.setOrderStatus("CREATED");
                order.mapItemIdToOrder(items);
                orderRepo.save(order);
                //setting price and adding order into the cart
                cartPrice = cart.getCartPrice() + Integer.parseInt(order.getFinalPrice());
                cart.addOrder(order);
                log.debug("order saved successfully in db");
            }
            cart.setCartPrice(cartPrice);
            if (null == user.getCart()) {
                cartRepo.save(cart);

            }
        }

        cart.mapUserToCart(user);

        return null;
    }
}
