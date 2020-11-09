package com.getir.rig.dto.page;

import com.getir.rig.dto.OrderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPageResult extends PageResult {

    List<OrderDto> orderDtoList;

}
