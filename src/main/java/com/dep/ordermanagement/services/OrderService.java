/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.constants.OrderStatus;
import com.dep.ordermanagement.pojo.db.*;
import com.dep.ordermanagement.pojo.dto.OrderedItemDto;
import com.dep.ordermanagement.repositories.OrderItemsRepo;
import com.dep.ordermanagement.repositories.OrderRepo;
import com.dep.ordermanagement.repositories.ProductsRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */

@Service
@Transactional
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductsRepo productsRepo;


    /***
     * 1] fetch order by order id
     * 2] fetch associated ordered items
     * 3] create list and send back to UI
     * @param orderId
     * @return
     */
    public List<OrderedItemDto> viewOrder(int orderId) {
        Order order = null;
        List<OrderItems> dbFetchedOrderedItem = null;
        List<OrderedItemDto> orderedItemDtoList = new ArrayList<>();
        try {
            order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("order not exists"));
        } catch (Exception e) {
            throw e;
        }
        dbFetchedOrderedItem = order.getOrderItemsList();

        for (int i = 0; i < dbFetchedOrderedItem.size(); i++) {
            OrderItems orderItems = dbFetchedOrderedItem.get(i);
            OrderedItemDto orderedItemDto = new OrderedItemDto();
            orderedItemDto.setProductName(orderItems.getProductName());
            orderedItemDto.setPrice(orderItems.getPrice());
            orderedItemDto.setDescription(orderItems.getDescription());
            orderedItemDto.setSpecifications(orderItems.getSpecifications());
            orderedItemDto.setQuantity(orderItems.getQuantity());
            orderedItemDtoList.add(orderedItemDto);
        }
        return orderedItemDtoList;
    }

    /***
     * 1] fetch user from db
     * 2] fetch the cart
     * 3] create a order for user
     * 4] map order under user
     * 5] if products are in the cart, and product selected for final submission create respected orderItems from products
     * @param userId
     */
    public void submitOrder(int userId) {
        User fetchedUserFromDb = null;
        Cart cart = null;
        //1] fetch user from db
        try {
            fetchedUserFromDb = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User does not exist"));
        } catch (Exception e) {
            throw e;
        }

        //2] fetch the cart
        cart = fetchedUserFromDb.getCart();

        //3] create a order for user
        Order order = new Order();
        order.setOrderStatus(String.valueOf(OrderStatus.PENDING));
        order.setCreatedAt(String.valueOf(Instant.now()));
        orderRepo.save(order);

        //4] map order under user
        fetchedUserFromDb.addOrderToUser(order);

        List<Products> listOfProductsMarkedForRemoval = new ArrayList<>();

        if (!CollectionUtils.isEmpty(cart.getProductsList())) {
            for (int i = 0; i < cart.getProductsList().size(); i++) {
                Products products = cart.getProductsList().get(i);
                if (products.isSelectedForFinalOrder()) {
                    //5] if products are in the cart, and product selected for final submission create respected orderItems from products
                    OrderItems orderItems = new OrderItems();
                    orderItems.setProductName(products.getProductName());
                    orderItems.setPrice(products.getPrice());
                    orderItems.setDescription(products.getDescription());
                    orderItems.setSpecifications(products.getSpecifications());
                    orderItemsRepo.save(orderItems);
                    //adding ordered item under order
                    order.addOrderItemUnderOrder(orderItems);

                    listOfProductsMarkedForRemoval.add(products);
                } else {
                    continue;
                }
            }
        }

        if (!CollectionUtils.isEmpty(listOfProductsMarkedForRemoval)) {
            for (int i = 0; i < listOfProductsMarkedForRemoval.size(); i++) {
                cart.removeProductFrmCart(listOfProductsMarkedForRemoval.get(i));
            }
        }

    }
}