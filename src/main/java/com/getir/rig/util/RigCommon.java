package com.getir.rig.util;

import com.getir.rig.dto.OrderItemDto;
import com.getir.rig.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class RigCommon {

    private RigCommon() {
    }

    public static boolean isStringNullOrEmpty(String str) { return str == null || str.isEmpty(); }

    public static boolean isDoubleNullOrZero(Double val) { return val == null || val == 0; }

    public static boolean isLongNullOrZero(Long val) { return val == null || val == 0; }

    public static List<OrderItemDto> getOrderItemDtos(Order order) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(orderItem.getProduct().getProductId());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDtos.add(orderItemDto);
        });
        return orderItemDtos;
    }

}
