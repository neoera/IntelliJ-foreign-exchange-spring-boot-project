package com.getir.rig.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.rig.entity.base.GenericEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ORDERI")
@Audited
public class Order implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    @SequenceGenerator(sequenceName = "ORDER_SEQ", allocationSize = 1, name = "orderSequence")
    @Column(name = "ORDER_ID", updatable = false, nullable = false)
    private Long orderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name="CREATE_DATE", nullable = false)
    private Date createDate;

}

