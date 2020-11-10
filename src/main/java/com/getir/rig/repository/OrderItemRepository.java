package com.getir.rig.repository;

import com.getir.rig.entity.OrderItem;
import com.getir.rig.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Order Repository
 */
public interface OrderItemRepository extends BaseJpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderItemId(Long id);

}
