package com.openpayd.fex.dto;

import lombok.Data;

@Data
public class ConversionResult {

    Double targetCurrencyAmount;
    String transactionUUID;

}
