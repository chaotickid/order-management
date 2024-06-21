/**
 * Copyright © 2024 Mavenir Systems
 */
package com.dep.ordermanagement.common;

import lombok.Getter;

/***
 * @author Aditya Patil
 * @date 21-06-2024
 */

@Getter
public enum ErrorCodeEnum {

    ER10001("ER10001", "Failed to save tenant"),
    ER10002("ER10002", "Failed to get tenant"),
    ER10003("ER10003", "Failed to save cart"),
    ER10004("ER10004", "Tenant does not exist"),
    ER10005("ER10005", "Failed to request discount for a tenant item"),
    ER10006("ER10006", "Something went wrong while creating tenant item"),
    ER10007("ER10007", "Failed to get tenant items list"),
    ER10008("ER10008", "User does not exist"),
    ER10009("ER10009", "Order does not exist"),
    ER10010("ER10010", "Order id is not associated with requested user"),
    ER10011("ER10011", "Something went wrong while creating order receipt");
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10011("ER10002", ""),
//    ER10012("ER10002", "");

    ErrorCodeEnum(String er10001, String s) {
    }

    private String errorCode;

    private String errorMessage;
}