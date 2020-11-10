package com.getir.rig.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {

   private String name;
   private Double price;
   private String serialNumber;
   private Long stockId;
}
