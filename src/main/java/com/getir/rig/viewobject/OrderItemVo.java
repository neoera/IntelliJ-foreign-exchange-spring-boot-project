package com.getir.rig.viewobject;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
public class OrderItemVo {
    @NotNull(message = "Quantity can not be null")
    @Size(max = 1000, min = 1)
    private Integer quantity;
    @NotNull(message = "Product id can not be null")
    private Long productId;
}
