package com.getir.rig.repository;

import com.getir.rig.entity.Product;
import com.getir.rig.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Product Repository
 */
public interface ProductRepository extends BaseJpaRepository<Product, Long> {

    List<Product> findAllByProductId(Long id);

}
