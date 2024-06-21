/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.resource;

import com.dep.ordermanagement.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */
@RestController
@RequestMapping("/api/v1")
public class PdfResource {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/generatePdf/{userId}/{orderId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable(value = "userId") String userId,
                                              @PathVariable(value = "orderId") String orderId) throws IOException {
        ByteArrayInputStream bis = pdfService.generateOrderReceipt(userId, orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=order_receipt.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
        //return new ResponseEntity<>(HttpStatus.OK);
    }
}