package com.openpayd.ots.service;

import com.openpayd.ots.dto.AccountDto;
import com.openpayd.ots.dto.page.AccountPageResult;
import com.openpayd.ots.entity.Account;
import com.openpayd.ots.entity.Client;
import com.openpayd.ots.exception.NotCompatibleException;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private Mapper mapper = new DozerBeanMapper();
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public AccountPageResult list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts = accountRepository.findAll(pageable);
        AccountPageResult result = new AccountPageResult();

        if (accounts != null && accounts.getSize() > 0){
            List<AccountDto> accountDtoList = new ArrayList<>();
            accounts.forEach(transaction -> {
                AccountDto accountDto = mapper.map(transaction,AccountDto.class);
                accountDtoList.add(accountDto);
            });

            result.setAccountDtoList(accountDtoList);
            result.setTotalPages(accounts.getTotalPages());
            result.setGetTotalElements(accounts.getTotalElements());
        }

        return result;
    }

    public Account create(AccountDto accountDto) {
        if (AtsUtils.isDoubleNullOrZero(accountDto.getBalance())) {
            logger.error(AtsConstants.BALANCE + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.BALANCE);
        }

        if (accountDto.getBalanceStatus() == null) {
            logger.error(AtsConstants.BALANCE_TYPE + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.BALANCE_TYPE);
        }
        if (AtsConstants.BalanceStatus.contains(accountDto.getBalanceStatus())) {
            logger.error(AtsConstants.BALANCE_TYPE + " " + AtsConstants.NOT_COMPATIBLE);
            throw new NotCompatibleException(accountDto.getBalanceStatus());
        }

        Account account = mapper.map(accountDto, Account.class);
        account.setCreateDate(new Date());

        if (accountDto.getClientId() != null){
            Client client = clientRepository.getOne(accountDto.getClientId());
            if (client == null || client.getId() == null) {
                logger.error(AtsConstants.CLIENT + " " + AtsConstants.NOT_FOUND_DB);
                throw new RecordNotFoundException(AtsConstants.CLIENT);
            }
            account.setClient(client);
        }

        account = accountRepository.save(account);
        return account;
    }
}
