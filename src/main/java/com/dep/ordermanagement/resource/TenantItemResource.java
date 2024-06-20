/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.pojo.dto.TenantItemsDto;
import com.dep.ordermanagement.services.TenantItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/***
 * @author Aditya Patil
 * @date 18-06-2024
 */
@RestController
@RequestMapping("/api/v1")
public class TenantItemResource {

    @Autowired
    private TenantItemsService tenantItemsService;

    @PostMapping(value = "/tenant-items/{tenantId}"/*,consumes = {"*"},
            produces = MediaType.ALL_VALUE*/)
    public ResponseEntity<?> createTenantItems(@RequestPart(value = "content")List<TenantItemsDto> tenantItemsDto,
                                               @PathVariable(value = "tenantId") int tenantId,
                                               @RequestPart(value = "file")MultipartFile file) throws IOException {
        return new ResponseEntity<>(tenantItemsService.createTenantItem(tenantItemsDto, tenantId, file), HttpStatus.CREATED);
    }

    //TODO: need to check first who is logged in if user logged in need to look up for discounted products
    @GetMapping("/tenant-items")
    public ResponseEntity<?> getTenantItems(@RequestParam(value = "tenantId") int tenantId,
                                            @RequestParam(value = "userId", required = false)int userId){
        return new ResponseEntity<>(tenantItemsService.getTenantItemsDto(tenantId, userId), HttpStatus.OK);
    }

    @PostMapping("/tenant-items/requestDiscount")
    public ResponseEntity<?> requestFroDiscount(@RequestBody List<TenantItemsDto> tenantItemsDto){
        tenantItemsService.requestForDiscount(tenantItemsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
