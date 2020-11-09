package com.getir.rig.entity;

import com.getir.rig.entity.base.GenericEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ORDER_ITEM")
@IdClass(OrderItem.OrderItemIdClass.class)
@Audited
public class OrderItem implements GenericEntity<OrderItem.OrderItemIdClass> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderItemSequence")
    @SequenceGenerator(sequenceName = "ORDER_ITEM_SEQ", allocationSize = 1, name = "orderItemSequence")
    @Column(name = "ORDER_ITEM_ID", updatable = false, nullable = false)
    private Long orderItemId;

    @Id
    @Column(name = "ORDER_ID", updatable = false, nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product;

    @Column(name="QUANTITY")
    private Integer quantity;

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    static class OrderItemIdClass implements Serializable {
        private Integer orderItemId;
        private Long orderId;
    }
}
