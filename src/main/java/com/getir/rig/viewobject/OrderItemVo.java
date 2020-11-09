package com.getir.rig.viewobject;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class OrderItemVo {
    @NotNull
    @NotEmpty(message = "Quantity id can not be empty")
    private Integer quantity;
    @NotNull
    @NotEmpty(message = "Empty product id")
    private Long productId;
}
