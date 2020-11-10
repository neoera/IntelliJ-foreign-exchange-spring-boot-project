package com.getir.rig.dto.page;

import com.getir.rig.dto.StockDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StockPageResult extends PageResult {

    List<StockDto> stockDtoList;

}
