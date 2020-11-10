package com.getir.rig.dto.page;

import com.getir.rig.dto.ProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPageResult extends PageResult {

    List<ProductDto> productDtoList;

}
