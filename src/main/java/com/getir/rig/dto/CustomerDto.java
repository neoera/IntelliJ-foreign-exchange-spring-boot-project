package com.getir.rig.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerDto {
   private String name;
   private String surname;
   private Long phone;
   private String email;
}
