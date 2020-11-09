package com.getir.rig.viewobject;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
public class CustomerRequest {

   @NotNull
   @NotEmpty
   private String name;
   @NotNull
   @NotEmpty
   private String surname;
   @NotNull
   @NotEmpty
   @Size(max = 20, min = 8)
   private String phone;
   @NotNull
   @Email
   private String email;
}
