package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ORDER_ITEM")
@Audited
public class OrderItem implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderItemSequence")
    @SequenceGenerator(sequenceName = "ORDER_ITEM_SEQ", allocationSize = 1, name = "orderItemSequence")
    @Column(name = "ORDER_ITEM_ID", updatable = false, nullable = false)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product;

    @Column(name="QUANTITY")
    private Integer quantity;

}
