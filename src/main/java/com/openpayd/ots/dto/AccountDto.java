package com.openpayd.ots.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
        private Integer type;
        private Integer balanceStatus;
        private Double balance;
        private Date createDate;
        private Long clientId;
}
