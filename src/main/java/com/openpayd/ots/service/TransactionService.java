package com.openpayd.ots.service;

import com.openpayd.ots.dto.TransactionDto;
import com.openpayd.ots.dto.page.TransactionPageResult;
import com.openpayd.ots.entity.Account;
import com.openpayd.ots.entity.Transaction;
import com.openpayd.ots.exception.NotNullException;
import com.openpayd.ots.exception.NotCompatibleException;
import com.openpayd.ots.repository.AccountRepository;
import com.openpayd.ots.repository.TransactionRepository;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private Mapper mapper = new DozerBeanMapper();
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public TransactionPageResult list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        TransactionPageResult result = new TransactionPageResult();

        if (transactions != null && transactions.getSize() > 0){
            List<TransactionDto> transactionDtoList = new ArrayList<>();
            transactions.forEach(transaction -> {
                TransactionDto transactionDto = mapper.map(transaction, TransactionDto.class);
                transactionDtoList.add(transactionDto);
            });
            result.setTransactionDtoList(transactionDtoList);
            result.setTotalPages(transactions.getTotalPages());
            result.setGetTotalElements(transactions.getTotalElements());
        }

        return result;
    }

    @Transactional
    public Transaction transfer(Long creditAccountId, Long debitAccountId, Double amount, Integer transferType, HttpSession session){

        if (AtsUtils.isDoubleNullOrZero(amount)) {
            logger.error(AtsConstants.AMOUNT + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.AMOUNT);
        }

        if (transferType == null) {
            logger.error(AtsConstants.TRANSFER_TYPE + " " + AtsConstants.CANNOT_NULL);
            throw new NotNullException(AtsConstants.TRANSFER_TYPE);
        }

        if (AtsConstants.BalanceStatus.contains(transferType)) {
            logger.error(AtsConstants.TRANSFER_TYPE + " " + AtsConstants.NOT_COMPATIBLE);
            throw new NotCompatibleException(transferType);
        }

        Account creditAccount = accountRepository.getOne(creditAccountId);
        Account debitAccount = accountRepository.getOne(debitAccountId);

        Double currentCreditAccountBalance = creditAccount.getBalance();
        Double currentDebitAccountBalance = debitAccount.getBalance();

        if (AtsConstants.BalanceStatus.DR.getValue().equals(transferType)){
            if (currentCreditAccountBalance < amount) throw new RuntimeException(AtsConstants.AMOUNT_CAN_NOT_HIGHER_THAN_CREDIT);
            else currentCreditAccountBalance = currentCreditAccountBalance - amount;
            currentDebitAccountBalance = currentDebitAccountBalance + amount;
        }else if (AtsConstants.BalanceStatus.CR.getValue().equals(transferType)){
            if (currentDebitAccountBalance < amount) throw new RuntimeException(AtsConstants.AMOUNT_CAN_NOT_HIGHER_THAN_DEBIT);
            currentDebitAccountBalance = currentDebitAccountBalance - amount;
            currentCreditAccountBalance = currentCreditAccountBalance + amount;
        }

        creditAccount.setBalance(currentCreditAccountBalance);
        debitAccount.setBalance(currentDebitAccountBalance);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCreateDate(new Date());
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);
        transaction.setTransactionUUID(session.getId());

        creditAccount = accountRepository.save(creditAccount);
        debitAccount = accountRepository.save(debitAccount);

        transaction.setMessage("Transaction success between CR Account Id: " + creditAccount.getId() + "DR Account Id: " + debitAccount.getId());

        transactionRepository.save(transaction);
        return transaction;
    }
}
