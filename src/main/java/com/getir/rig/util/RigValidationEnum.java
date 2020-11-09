package com.getir.rig.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RigValidationEnum {

    CUSTOMER_NAME_CAN_NOT_BE_EMPTY("RIG-001", "Customer name can not be empty"),
    CUSTOMER_ID_CAN_NOT_BE_EMPTY("RIG-002", "Customer id can not be empty"),
    ORDER_ITEM_LIST_CAN_NOT_BE_EMPTY("RIG-003", "Order item list can not be empty"),
    AVAILABLE_PRODUCT_QUANTITY("RIG-004", "Maximum quantity of product you can buy: "),
    STOCK_IS_EMPTY("RIG-005", "Product stock is empty with product id: "),
    PRODUCT_NOT_FOUND("RIG-006", "Product not found"),
    CUSTOMER_NOT_FOUND("RIG-007", "Customer not found"),
    CUSTOMER_SURNAME_CAN_NOT_BE_EMPTY("RIG-008", "Customer surname can not be empty"),
    CUSTOMER_EMAIL_CAN_NOT_BE_EMPTY("RIG-009", "Customer email can not be empty"),
    CUSTOMER_PHONE_NOT_VALID("RIG-010", "Customer phone not be valid"),
    STOCK_NOT_FOUND("RIG-011", "Stock not found with product id: "),
    ORDER_ID_CAN_NOT_BE_EMPTY("RIG-012", "Order id can not be empty"),
    ORDER_NOT_FOUND("RIG-023", "Order not found");


    private final String key;
    private final String message;

}
