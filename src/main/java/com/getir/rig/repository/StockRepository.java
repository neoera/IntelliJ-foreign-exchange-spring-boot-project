package com.getir.rig.repository;

import com.getir.rig.entity.Stock;
import com.getir.rig.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Stock Repository
 */
public interface StockRepository extends BaseJpaRepository<Stock, Long> {

    List<Stock> findAllByStockId(Long id);

}
