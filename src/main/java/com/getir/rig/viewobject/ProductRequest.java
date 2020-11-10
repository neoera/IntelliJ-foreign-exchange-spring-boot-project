package com.getir.rig.viewobject;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
public class ProductRequest {

   @NotNull
   @NotEmpty
   private String name;
   @NotNull
   private Double price;
   @NotNull
   @NotEmpty
   @Size(max = 20, min = 8)
   private String serialNumber;
   @NotNull
   private Long stockId;
}
