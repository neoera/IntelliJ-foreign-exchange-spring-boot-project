package com.openpayd.ots.entity;

import com.openpayd.ots.entity.base.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="ACCOUNT")
public class Account extends GenericEntity<Long> {

    @Column(name="TYPE", length=50, nullable=false)//CURRENT(is meant for daily transactions)(cari) or SAVINGS(deposit account which allows limited transactions)(vadeli)
    private Integer type;

    @Column(name="BALANCE_STATUS", nullable=false)//Debit(DR) or Credit(CR)
    private Integer balanceStatus;

    @Column(name="BALANCE", nullable=false)
    private Double balance;

    @Column(name="CREATE_DATE", nullable=false)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private Client client;

}