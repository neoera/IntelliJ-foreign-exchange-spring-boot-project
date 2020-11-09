package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CUSTOMER")
@Audited
public class Customer implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    @SequenceGenerator(sequenceName = "CUSTOMER_SEQ", allocationSize = 1, name = "customerSequence")
    @Column(name = "CUSTOMER_ID", updatable = false, nullable = false)
    private Long customerId;

    @Column(name="NAME", length=50, nullable=false)
    private String name;

    @Column(name="SURNAME", length=50, nullable=false)
    private String surname;

    @Column(name="PHONE")
    private String phone;

    @Column(name="EMAIL", length=50, nullable=false)
    private String email;

    @Column(name="CREATE_DATE", nullable=false)
    private Date createDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

}