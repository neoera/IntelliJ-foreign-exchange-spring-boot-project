package com.openpayd.ots.repository;


import com.openpayd.ots.entity.Transaction;
import com.openpayd.ots.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Transaction Repository
 */
public interface TransactionRepository extends BaseJpaRepository<Transaction, Long> {

    List<Transaction> findAllById(Long id);

}
