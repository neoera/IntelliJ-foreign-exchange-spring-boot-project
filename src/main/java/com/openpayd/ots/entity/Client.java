package com.openpayd.ots.entity;

import com.openpayd.ots.entity.base.GenericEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="CLIENT")
public class Client extends GenericEntity<Long> {

    @Column(name="NAME", length=50, nullable=false)
    private String name;

    @Column(name="SURNAME", nullable=false)
    private String surname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRIMARY_ADDRESS_ID", nullable=false)
    private Address primaryAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SECONDARY_ADDRESS_ID", nullable=false)
    private Address secondaryAddress;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

}