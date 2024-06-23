/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.services;

import com.dep.ordermanagement.common.CustomResponseException;
import com.dep.ordermanagement.pojo.db.Order;
import com.dep.ordermanagement.pojo.db.OrderItems;
import com.dep.ordermanagement.pojo.db.Tenant;
import com.dep.ordermanagement.pojo.db.User;
import com.dep.ordermanagement.repositories.OrderRepo;
import com.dep.ordermanagement.repositories.UserRepo;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dep.ordermanagement.common.ErrorCodeEnum.*;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */
@Slf4j
@Service
public class PdfService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    /***
     * generateOrderReceipt
     *
     * @param userId
     * @param orderId
     * @return
     * @throws IOException
     */
    public ByteArrayInputStream generateInvoice(String userId, String orderId) throws IOException {
        Tenant tenant = null;
        User user = null;
        Order order = null;
        List<OrderItems> orderItemsList = new ArrayList<>();
        try {
            user = userRepo.findById(Integer.valueOf(userId)).orElseThrow(() -> new RuntimeException("user does not exist"));
        } catch (Exception e) {
            throw new CustomResponseException(ER10008, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        tenant = user.getTenant();

        try {
            order = orderRepo.findById(Integer.parseInt(orderId)).orElseThrow(() -> new RuntimeException("order des not exists"));
        } catch (Exception e) {
            throw new CustomResponseException(ER10009, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        orderItemsList = order.getOrderItemsList();

        if (order.getUser().getId().intValue() != user.getId().intValue()) {
            log.debug("order id is not associated with requested user");
            throw new CustomResponseException(ER10010, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            // Set font
            PdfFont font = PdfFontFactory.createFont("src/main/resources/fonts/JetBrainsMono-SemiBold.ttf",
                    PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            document.setFont(font);


            // Add title
            document.add(new Paragraph("*** INVOICE ***")
                    .setFont(font)
                    .setFontSize(24)
                    .setBold()
                    .setMarginBottom(20)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));

            // Add order ID
            document.add(new Paragraph("SERVICE PROVIDER:  " + tenant.getTenantName().toUpperCase())
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("USER NAME:  " + user.getUserName().toUpperCase())
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("ORDER ID:  " + orderId)
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            // Add order date
            document.add(new Paragraph("ORDERED AT: " + order.getCreatedAt())
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(5));

            document.add(new Paragraph("ORDER STATUS:  " + order.getOrderStatus().toUpperCase())
                    .setFont(font)
                    .setFontSize(12)
                    .setMarginBottom(10));

            // Add table with items
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 1, 1}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell("S.NO");
            table.addHeaderCell("ITEM");
            table.addHeaderCell("QUANTITY");
            table.addHeaderCell("PRICE");

            int serialNo = 1;
            int total = 0;
            for (OrderItems item : orderItemsList) {
                table.addCell(String.valueOf(serialNo++));
                table.addCell(item.getProductName().toUpperCase());
                table.addCell(item.getQuantity());
                table.addCell("Rs." + item.getPrice());
                total = total + (Integer.parseInt(item.getQuantity()) * Integer.parseInt(item.getPrice()));
            }

            document.add(table);

            // Add total amount
            document.add(new Paragraph("Total: Rs." + total)
                    .setFont(font)
                    .setFontSize(12)
                    .setBold()
                    .setMarginTop(20));

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e){
            throw new CustomResponseException(ER10011, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}