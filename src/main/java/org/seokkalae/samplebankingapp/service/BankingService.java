package org.seokkalae.samplebankingapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.banking.BankingResponse;
import org.seokkalae.samplebankingapp.model.banking.DepositRequest;
import org.seokkalae.samplebankingapp.model.banking.TransferRequest;
import org.seokkalae.samplebankingapp.model.banking.WithdrawRequest;
import org.seokkalae.samplebankingapp.repository.AccountRepository;
import org.seokkalae.samplebankingapp.repository.BankingAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BankingService {
    private final Logger log = LoggerFactory.getLogger(BankingService.class);
    private final HistoryService history;
    private final BankingAccountRepository bankingRepo;
    private final AccountRepository accountRepo;

    public BankingService(HistoryService history, BankingAccountRepository bankingRepo, AccountRepository accountRepo) {
        this.history = history;
        this.bankingRepo = bankingRepo;
        this.accountRepo = accountRepo;
    }

    @Transactional
    public BankingResponse deposit(UUID bankAccountId, DepositRequest request) {
        log.info("trying to find banking account with id: {}", bankAccountId);
        BankAccountEntity bankAccountEntity = bankingRepo.findById(bankAccountId)
                .orElseThrow(
                        () -> new RuntimeException("Banking account doesn't exist")
                );
        BigDecimal resultSum = bankAccountEntity.getMoneyFunds().add(request.sum());
        bankAccountEntity.setMoneyFunds(resultSum);
        log.info("deposit money to banking account with sum: {}", request.sum());
        BankAccountEntity save = bankingRepo.save(bankAccountEntity);
        log.info("deposit successful");

        history.createEvent(
                bankAccountEntity,
                OperationType.DEPOSIT,
                request.sum(),
                null
        );

        return new BankingResponse(
                save.getId(),
                OperationType.DEPOSIT,
                save.getMoneyFunds()
        );
    }

    @Transactional
    public BankingResponse withdraw(UUID bankAccountId, WithdrawRequest request) {
        log.info("trying to find banking account with id: {}", bankAccountId);
        BankAccountEntity accountEntity = bankingRepo.findBankAccountEntitiesByIdAndPin(
                        bankAccountId,
                        request.pin()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account with id " + bankAccountId + " doesn't found"
                        )
                );

        BigDecimal resultSum = accountEntity.getMoneyFunds().subtract(request.sum());
        if (resultSum.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("total amount can't be negative");
        accountEntity.setMoneyFunds(resultSum);

        log.info("withdraw money to banking account with sum: {}", request.sum());
        BankAccountEntity save = bankingRepo.save(accountEntity);
        log.info("withdraw successful");

        history.createEvent(
                accountEntity,
                OperationType.WITHDRAW,
                request.sum(),
                null
        );

        return new BankingResponse(
                save.getId(),
                OperationType.WITHDRAW,
                save.getMoneyFunds()
        );
    }

    @Transactional
    public BankingResponse transfer(UUID bankAccountId,TransferRequest request) {
        log.info("trying to find banking account for writing off money with id: {}", bankAccountId);
        BankAccountEntity fromAccountEntity = bankingRepo.findBankAccountEntitiesByIdAndPin(
                        bankAccountId,
                        request.pin()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account for writing off money with id: " + bankAccountId
                        )
                );

        log.info("trying to find banking account for replenishment money with id: {}", request.toBankingAccountId());
        BankAccountEntity toAccountEntity = bankingRepo.findById(request.toBankingAccountId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account for writing off money with id: " + bankAccountId
                        )
                );


        BigDecimal fromResultSum = fromAccountEntity.getMoneyFunds().subtract(request.sum());
        if (fromResultSum.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("total amount can't be negative");
        fromAccountEntity.setMoneyFunds(fromResultSum);

        log.info("writing off money from banking account with sum: {}", request.sum());
        BankAccountEntity saveFrom = bankingRepo.save(fromAccountEntity);
        log.info("writing off successful");

        history.createEvent(
                fromAccountEntity,
                OperationType.TRANSFER_OUT,
                request.sum(),
                toAccountEntity
        );

        BigDecimal toResultSum = toAccountEntity.getMoneyFunds().add(request.sum());
        toAccountEntity.setMoneyFunds(toResultSum);

        log.info("replenishment money to banking account with sum: {}", request.sum());
        BankAccountEntity saveTo = bankingRepo.save(toAccountEntity);
        log.info("replenishment successful");

        history.createEvent(
                toAccountEntity,
                OperationType.TRANSFER_IN,
                request.sum(),
                fromAccountEntity
        );

        return new BankingResponse(
                saveFrom.getId(),
                OperationType.TRANSFER_OUT,
                saveFrom.getMoneyFunds()
        );
    }

    @Transactional
    public BankAccountEntity createBankAccount(UUID accountId, String pin) {
        log.info("trying to find account with {}", accountId);
        var account = accountRepo.findById(accountId);
        if (account.isEmpty())
            throw new EntityNotFoundException();
        log.info("create bankAccount with accountId {}", accountId);

        var bankAccount = new BankAccountEntity();
        bankAccount.setAccount(account.get());
        bankAccount.setPin(pin);
        return bankingRepo.save(bankAccount);
    }
}
