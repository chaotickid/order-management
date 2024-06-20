/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.pojo.db.DiscountedProducts;
import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.db.TenantItems;
import com.dep.ordermanagement.pojo.dto.TenantItemsDto;
import com.dep.ordermanagement.repositories.DiscountedProductsRepo;
import com.dep.ordermanagement.repositories.TenantItemsRepo;
import com.dep.ordermanagement.repositories.TenantRepo;
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
public class TenantItemsService {

    @Autowired
    private TenantItemsRepo tenantItemsRepo;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private DiscountedProductsRepo discountedProductsRepo;

    public void requestForDiscount(List<TenantItemsDto> tenantItemsDtoList) {

        try {
            if (!CollectionUtils.isEmpty(tenantItemsDtoList)) {
                for (int i = 0; i < tenantItemsDtoList.size(); i++) {
                    TenantItemsDto tenantItemsDto = tenantItemsDtoList.get(i);
                    TenantItems fetchedTenantItemFromDb = tenantItemsRepo.findById(tenantItemsDto.getId())
                            .orElseThrow(() -> new RuntimeException("tenant item does not exists"));
                    DiscountedProducts discountedProducts = new DiscountedProducts();
                    discountedProducts.setDiscountedPrice(tenantItemsDto.getDiscountedPrice());
                    discountedProducts.setTenantItemId(String.valueOf(tenantItemsDto.getId()));
                    discountedProductsRepo.save(discountedProducts);
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    /***
     * 1] First fetch tenant from DB
     * 2] create TenantItems from List add save and add it under Tenant
     *
     * @param tenantItemsDtoList
     * @return
     */
    public List<TenantItemsDto> createTenantItem(List<TenantItemsDto> tenantItemsDtoList, int tenantId) {
        Tenant fetchedFromDb = null;
        TenantItems tenantItems = null;
        //1] First fetch tenant from DB
        try {
            fetchedFromDb = tenantRepo.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant does not exist"));
        } catch (Exception e) {
            throw e;
        }
        //2] create TenantItems from List add save and add it under Tenant
        try {
            if (!CollectionUtils.isEmpty(tenantItemsDtoList)) {
                for (int i = 0; i < tenantItemsDtoList.size(); i++) {
                    tenantItems = new TenantItems();
                    tenantItems.setProductName(tenantItemsDtoList.get(i).getProductName());
                    tenantItems.setDescription(tenantItemsDtoList.get(i).getDescription());
                    tenantItems.setPrice(tenantItemsDtoList.get(i).getPrice());
                    tenantItems.setDescription(tenantItemsDtoList.get(i).getDescription());
                    tenantItems.setSpecifications(tenantItemsDtoList.get(i).getSpecifications());
                    tenantItemsRepo.save(tenantItems);

                    //adding under tenant
                    fetchedFromDb.addTenantItemsToList(tenantItems);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return tenantItemsDtoList;
    }

    /***
     * TODO: need to check first who is logged in if user logged in need to look up for discounted products
     *
     * 1] fetch tenant by tenant id
     * 2] if Tenant item list is empty then return []
     * 3] convertItemList to itemDto and return in from of []
     * 4] if discountedProduct is available for that product fetch price from discounted product or
     *    fall back to tenantItemPrice
     * @param tenantId
     * @param userId
     * @return
     */
    public List<TenantItemsDto> getTenantItemsDto(int tenantId, int userId) {
        Tenant fetchedFromDb = null;
        List<TenantItemsDto> tenantItemsDtoList = new ArrayList<>();
        //1] First fetch tenant from DB
        try {
            fetchedFromDb = tenantRepo.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant does not exist"));
        } catch (Exception e) {
            throw e;
        }

        //2] if Tenant item list is empty then return []
        if (CollectionUtils.isEmpty(fetchedFromDb.getTenantItemsList())) {
            return tenantItemsDtoList;
        }

        //3] convertItemList to itemDto and return in from of []
        for (int i = 0; i < fetchedFromDb.getTenantItemsList().size(); i++) {
            String discountedPrice = "";
            Optional<DiscountedProducts> discountedProducts = discountedProductsRepo.findByTenantItemId(String.valueOf(fetchedFromDb.getTenantItemsList().get(i).getId()));
            if (discountedProducts.isEmpty()) {
                //
                discountedPrice = fetchedFromDb.getTenantItemsList().get(i).getPrice();
            } else {
                discountedPrice = discountedProducts.get().getDiscountedPrice();
            }
            TenantItemsDto tenantItemsDto = new TenantItemsDto();
            tenantItemsDto.setId(fetchedFromDb.getTenantItemsList().get(i).getId());
            tenantItemsDto.setProductName(fetchedFromDb.getTenantItemsList().get(i).getProductName());
            tenantItemsDto.setPrice(discountedPrice);
            tenantItemsDto.setDescription(fetchedFromDb.getTenantItemsList().get(i).getDescription());
            tenantItemsDto.setSpecifications(fetchedFromDb.getTenantItemsList().get(i).getSpecifications());
            tenantItemsDtoList.add(tenantItemsDto);
        }
        return tenantItemsDtoList;
    }
}
