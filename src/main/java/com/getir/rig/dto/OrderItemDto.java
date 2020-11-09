package com.getir.rig.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderItemDto {
    private Integer quantity;
    private Long productId;
}
