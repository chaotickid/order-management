/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.db.*;
import com.dep.ordermanagement.pojo.dto.CartDto;
import com.dep.ordermanagement.pojo.dto.ProductsDto;
import com.dep.ordermanagement.pojo.dto.TenantItemsDto;
import com.dep.ordermanagement.repositories.DiscountedProductsRepo;
import com.dep.ordermanagement.repositories.ProductsRepo;
import com.dep.ordermanagement.repositories.TenantItemsRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@Service
@Transactional
public class CartService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TenantItemsRepo tenantItemsRepo;

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private DiscountedProductsRepo discountedProductsRepo;

    /***
     * 1] fetch user from db
     * 2] fetch cart
     * @param userId
     */
    public List<ProductsDto> viewItemsInTheCart(int userId) {
        User dbUser = null;
        Cart userCart = null;
        List<Products> productsList = null;
        List<ProductsDto> productsDtoListToUI = new ArrayList<>();
        try {
            dbUser = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user does not exist"));
        } catch (Exception e) {
            throw e;
        }
        userCart = dbUser.getCart();
        productsList = userCart.getProductsList();

        for (int i = 0; i < productsList.size(); i++) {
            TenantItems tenantItems = null;
            String discountedPrice = "";
            tenantItems = tenantItemsRepo.findById(Integer.parseInt(productsList.get(i).getTenantItemId())).orElseThrow(() -> new RuntimeException("tenant item not found"));
            Optional<DiscountedProducts> discountedProducts =
                    discountedProductsRepo.findByTenantItemId(String.valueOf(productsList.get(i).getTenantItemId()));
            if (discountedProducts.isEmpty()) {
                //
                discountedPrice = tenantItems.getPrice();
            } else {
                discountedPrice = discountedProducts.get().getDiscountedPrice();
            }
            Products products = productsList.get(i);
            products.setPrice(discountedPrice);
            ProductsDto productsDto = new ProductsDto();
            productsDto.setId(products.getId());
            productsDto.setProductName(products.getProductName());
            productsDto.setPrice(products.getPrice());
            productsDto.setDescription(products.getDescription());
            productsDto.setSpecifications(products.getSpecifications());
            productsDto.setSelectedForFinalOrder(products.isSelectedForFinalOrder());
            productsDtoListToUI.add(productsDto);
        }
        return productsDtoListToUI;
    }

    /***
     *
     * 1] first fetch user from db
     * 2] fetch cart for that user
     * 3] if operation = update -> fetch product and update
     * 4] if operation == delete product from cart -> remove product from cart
     * @param cartDto
     * @return
     */
    public List<ProductsDto> modifyCart(CartDto cartDto, String operation) {
        User fetchedUser = null;
        Cart usersCart = null;
        List<ProductsDto> productsDtoList = null;
        if (!CollectionUtils.isEmpty(cartDto.getProductsList())) {
            throw new RuntimeException("product list is empty");
        }
        productsDtoList = cartDto.getProductsList();

        //1] first fetch user from db
        try {
            fetchedUser = userRepo.findById(Integer.parseInt(cartDto.getUserId()))
                    .orElseThrow(() -> new RuntimeException("user not exists"));
        } catch (Exception e) {
            throw e;
        }

        //2] fetch cart for that user
        usersCart = fetchedUser.getCart();

        try {
            for (int i = 0; i < productsDtoList.size(); i++) {
                ProductsDto productsDto1 = productsDtoList.get(i);
                Products fetchedProducts = productsRepo.findById(productsDto1.getId()).orElseThrow(() -> new RuntimeException("product not exists"));
                if (operation.equalsIgnoreCase("update")) {
                    if (null != productsDto1.getQuantity()) {
                        //3] if operation = update -> fetch product and update
                        fetchedProducts.setQuantity(productsDto1.getQuantity());
                    }
                } else if (operation.equalsIgnoreCase("delete")) {
                    //4] if operation == delete product from cart -> remove product from cart
                    usersCart.removeProductFrmCart(fetchedProducts);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return productsDtoList;
    }

    /***
     * 1] First fetch user
     * 2] iterate over tenant items and create products and add into product table and map it to cart
     * 3] fetch the cart
     * @param cartDto
     */
    public CartDto addProductsToCart(CartDto cartDto) {
        User fetchedUserFromDb = null;
        Cart fetchedCartForUser = null;
        //1] First fetch tenant from DB
        try {
            fetchedUserFromDb = userRepo.findById(Integer.parseInt(cartDto.getUserId())).orElseThrow(() -> new RuntimeException("User does not exist"));
        } catch (Exception e) {
            throw e;
        }

        try {
            fetchedCartForUser = fetchedUserFromDb.getCart();
        } catch (Exception e) {
            throw e;
        }

        try {
            //2] iterate over tenant items and create products and add into product table and map it to cart
            if (!CollectionUtils.isEmpty(cartDto.getTenantItemsList())) {
                for (int i = 0; i < cartDto.getTenantItemsList().size(); i++) {
                    TenantItemsDto tenantItemsDto = cartDto.getTenantItemsList().get(i);
                    //fetch tenant item
                    TenantItems tenantItems = tenantItemsRepo.findById(tenantItemsDto.getId()).orElseThrow(() ->
                            new RuntimeException("Tenant item not found"));
                    String discountedPrice = "";
                    Optional<DiscountedProducts> discountedProducts =
                            discountedProductsRepo.findByTenantItemId(String.valueOf(tenantItems.getId()));
                    if (discountedProducts.isEmpty()) {
                        //
                        discountedPrice = tenantItems.getPrice();
                    } else {
                        discountedPrice = discountedProducts.get().getDiscountedPrice();
                    }
                    //create a product from it
                    Products products = new Products();
                    products.setTenantItemId(String.valueOf(tenantItems.getId()));
                    products.setProductName(tenantItems.getProductName());
                    products.setPrice(discountedPrice);
                    products.setDescription(tenantItems.getDescription());
                    products.setSpecifications(tenantItems.getSpecifications());
                    products.setQuantity(tenantItemsDto.getQuantity());
                    productsRepo.save(products);
                    //by default product is selected
                    products.setSelectedForFinalOrder(Boolean.TRUE);
                    fetchedCartForUser.addProductToCart(products);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return cartDto;
    }
}