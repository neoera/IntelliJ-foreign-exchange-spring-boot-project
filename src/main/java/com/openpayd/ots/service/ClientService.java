package com.openpayd.ots.service;

import com.openpayd.ots.dto.ClientDto;
import com.openpayd.ots.dto.page.ClientPageResult;
import com.openpayd.ots.entity.Account;
import com.openpayd.ots.entity.Address;
import com.openpayd.ots.entity.Client;
import com.openpayd.ots.exception.NotNullException;
import com.openpayd.ots.exception.RecordNotFoundException;
import com.openpayd.ots.repository.AccountRepository;
import com.openpayd.ots.repository.ClientRepository;
import com.openpayd.ots.util.AtsConstants;
import com.openpayd.ots.util.AtsUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private Mapper mapper = new DozerBeanMapper();
    private final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    public Client create(ClientDto clientDto) {
        if (AtsUtils.isStringNullOrEmpty(clientDto.getName())) {
            logger.error(AtsConstants.NAME + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.NAME);
        }

        if (AtsUtils.isStringNullOrEmpty(clientDto.getSurname())) {
            logger.error(AtsConstants.SURNAME + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.SURNAME);
        }

        if (clientDto.getPrimaryAddress() == null) {
            logger.error(AtsConstants.ADDRESS + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.ADDRESS);
        }

        if (AtsUtils.isStringNullOrEmpty(clientDto.getPrimaryAddress().getCity())
                || AtsUtils.isStringNullOrEmpty(clientDto.getPrimaryAddress().getCountry())
                || AtsUtils.isStringNullOrEmpty(clientDto.getPrimaryAddress().getInfo())) {
            logger.error(AtsConstants.ADDRESS_DETAIL + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.ADDRESS_DETAIL);
        }

        Client client = new Client();
        client.setName(clientDto.getName());
        client.setSurname(clientDto.getSurname());

        Address primaryAddress = mapper.map(clientDto.getPrimaryAddress(), Address.class);
        client.setPrimaryAddress(primaryAddress);

        Address secondaryAddress = mapper.map(clientDto.getSecondaryAddress(), Address.class);
        client.setSecondaryAddress(secondaryAddress);

        Set<Account> accounts = new HashSet<>();
        if (clientDto.getAccountIdList() != null && clientDto.getAccountIdList().size() > 0){
            clientDto.getAccountIdList().forEach(accountId -> {
                Account account = accountRepository.getOne(accountId);
                if (account == null || account.getId() == null) {
                    logger.error(AtsConstants.ACCOUNT + " " + AtsConstants.NOT_FOUND_DB);
                    throw new RecordNotFoundException(AtsConstants.ACCOUNT);
                }
                accounts.add(accountRepository.getOne(accountId));
            });
        }

        client.setAccounts(accounts);
        client = clientRepository.save(client);
        return client;
    }

    public ClientDto get(long id) {
        Client client = clientRepository.getOne(id);
        if (client.getId() == null) {
            logger.error(AtsConstants.CLIENT + " " + AtsConstants.NOT_FOUND_DB);
            throw new RecordNotFoundException(AtsConstants.CLIENT);
        }
        return mapper.map(client, ClientDto.class);
    }

    public ClientPageResult list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clients = clientRepository.findAll(pageable);
        ClientPageResult result = new ClientPageResult();

        List<ClientDto> clientDtoList = new ArrayList<>();
        clients.forEach(client -> {
            ClientDto clientDto = mapper.map(client,ClientDto.class);
            clientDtoList.add(clientDto);
        });

        result.setClientDtoList(clientDtoList);
        result.setTotalPages(clients.getTotalPages());
        result.setGetTotalElements(clients.getTotalElements());

        return result;
    }

    public boolean exists(ClientDto clientDto) {
        return false;
    }
}
