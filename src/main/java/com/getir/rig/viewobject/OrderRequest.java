package com.getir.rig.viewobject;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
public class OrderRequest {

    @NotNull(message = "Customer id can not be null")
    private Long customerId;
    @NotNull
    @NotEmpty(message = "Order item list can not be empty")
    private List<OrderItemVo> orderItemList;
}
