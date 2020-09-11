package com.openpayd.ots.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ClientDto {
        private String name;
        private String surname;
        private AddressDto primaryAddress;
        private AddressDto secondaryAddress;
        private ArrayList<Long> accountIdList;
}
