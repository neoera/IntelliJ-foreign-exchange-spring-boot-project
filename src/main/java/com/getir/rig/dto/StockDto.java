package com.getir.rig.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockDto {

   private Long stockId;
   private Long productId;
   private Integer quantity;
   private Boolean isActive;
}
