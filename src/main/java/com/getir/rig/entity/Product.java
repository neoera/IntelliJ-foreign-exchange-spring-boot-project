package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="PRODUCT")
@Audited
public class Product implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(sequenceName = "PRODUCT_SEQ", allocationSize = 1, name = "productSequence")
    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private Long productId;

    @Column(name="NAME", length=50, nullable=false)
    private String name;

    @Column(name="PRICE", nullable=false)
    private Double price;

    @Column(name="SERIAL_NUMBER", nullable=false)
    private String serialNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID", referencedColumnName = "STOCK_ID")
    private Stock stock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

}
