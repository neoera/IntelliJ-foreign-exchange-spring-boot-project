package com.openpayd.ots.dto;

import lombok.Data;

@Data
public class TransactionDto {
        private String name;
        private String surname;
        private AddressDto primaryAddress;
        private AddressDto secondaryAddress;
}
