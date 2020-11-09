package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="STOCK")
@Audited
public class Stock implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockSequence")
    @SequenceGenerator(sequenceName = "STOCK_SEQ", allocationSize = 1, name = "stockSequence")
    @Column(name = "STOCK_ID", updatable = false, nullable = false)
    private Long stockId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product;

    @Column(name="QUANTITY")
    private Integer quantity;

    @Column(name="IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

}
