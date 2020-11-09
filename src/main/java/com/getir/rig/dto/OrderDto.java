package com.getir.rig.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class OrderDto {
    private Long id;
    private Long customerId;
    private List<OrderItemDto> orderItemDtos;
}
