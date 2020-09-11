package com.openpayd.ots.entity;

import com.openpayd.ots.entity.base.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="TRANSACTION")
public class Transaction extends GenericEntity<Long> {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DEBIT_ACCOUNT_ID", nullable=false)
    private Account debitAccount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREDIT_ACCOUNT_ID", nullable=false)
    private Account creditAccount;

    @Column(name="AMOUNT", length=50, nullable=false)
    private Double amount;

    @Column(name="MESSAGE", nullable=false)
    private String message;

    @Column(name="CREATE_DATE", nullable=false)
    private Date createDate;

    @Column(name="TRANSACTION_UUID", length=50, nullable=false)
    private String transactionUUID;

}