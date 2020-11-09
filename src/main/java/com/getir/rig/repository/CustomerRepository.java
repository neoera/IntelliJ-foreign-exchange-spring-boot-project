package com.getir.rig.repository;

import com.getir.rig.entity.Customer;
import com.getir.rig.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Customer Repository
 */
public interface CustomerRepository extends BaseJpaRepository<Customer, Long> {

    List<Customer> findAllByCustomerId(Long id);

}
