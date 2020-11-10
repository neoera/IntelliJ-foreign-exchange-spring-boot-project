package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ADDRESS")
@Audited
class Address implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressSequence")
    @SequenceGenerator(sequenceName = "ADDRESS_SEQ", allocationSize = 1, name = "addressSequence")
    @Column(name = "ADDRESS_ID", updatable = false, nullable = false)
    private Long addressId;

    @Column(name="INFO", length=50, nullable=false)
    private String info;

    @Column(name="CITY", nullable=false)
    private String city;

    @Column(name="COUNTRY", length=3, nullable=false)
    private String country;

    @Column(name="TYPE", length=4, nullable=false)
    private String type;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    private Customer customer;*/

}