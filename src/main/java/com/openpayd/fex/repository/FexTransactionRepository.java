package com.openpayd.fex.repository;


import com.openpayd.fex.entity.FexTransaction;
import com.openpayd.fex.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Fex Transaction Repository
 */
public interface FexTransactionRepository extends BaseJpaRepository<FexTransaction, Long> {

    List<FexTransaction> findAllById(Long id);

    Page<FexTransaction> findByTransactionUUID(String string, Pageable pageable);

}
