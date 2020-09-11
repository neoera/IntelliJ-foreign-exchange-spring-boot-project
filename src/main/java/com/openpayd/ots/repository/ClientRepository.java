package com.openpayd.ots.repository;


import com.openpayd.ots.entity.Client;
import com.openpayd.ots.repository.base.BaseJpaRepository;

import java.util.List;

/**
 * Client Repository
 */
public interface ClientRepository extends BaseJpaRepository<Client, Long> {

    List<Client> findAllById(Long id);

}
