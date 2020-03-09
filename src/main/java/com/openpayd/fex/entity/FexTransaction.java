package com.openpayd.fex.entity;

import com.openpayd.fex.entity.base.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="FEX_TRANSACTION")
public class FexTransaction extends GenericEntity<Long> {

    @Column(name="TRANSACTION_UUID", length=50, nullable=false)
    private String transactionUUID;

    @Column(name="TRANSACTION_DATE", nullable=false)
    private Date transactionDate;

    @Column(name="SOURCE_CURRENCY", length=3, nullable=false)
    private String sourceCurrency;

    @Column(name="TARGET_CURRENCY", length=3, nullable=false)
    private String targetCurrency;

    @Column(name="TARGET_CURRENCY_AMOUNT", nullable=false)
    private Double targetCurrencyAmount;

}