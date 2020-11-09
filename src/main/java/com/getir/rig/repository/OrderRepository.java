package com.getir.rig.repository;

import com.getir.rig.entity.Customer;
import com.getir.rig.entity.Order;
import com.getir.rig.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Order Repository
 */
public interface OrderRepository extends BaseJpaRepository<Order, Long> {

    List<Order> findAllByOrderId(Long id);

    /**
     * Find Order(Child) entities knowing the Customer(Parent).
     */
    /*@Query("select order from Customer c inner join c.orders order where c = :customer")
    Page<Order> findBy(@Param("customer") Customer customer, Pageable pageable);*/

    Page<Order> findByCustomer(Customer customer, Pageable pageable);

}
