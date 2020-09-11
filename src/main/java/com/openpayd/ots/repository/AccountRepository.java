package com.openpayd.ots.repository;


import com.openpayd.ots.entity.Account;
import com.openpayd.ots.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Account Repository
 */
public interface AccountRepository extends BaseJpaRepository<Account, Long> {

    List<Account> findAllById(Long id);

}
