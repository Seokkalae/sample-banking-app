package org.seokkalae.samplebankingapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.dto.AccountDto;
import org.seokkalae.samplebankingapp.dto.BankAccountDto;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.mapper.BankAccountMapper;
import org.seokkalae.samplebankingapp.mapper.HistoryMapper;
import org.seokkalae.samplebankingapp.model.banking.*;
import org.seokkalae.samplebankingapp.repository.AccountRepository;
import org.seokkalae.samplebankingapp.repository.BankingAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BankingService {
    private final Logger log = LoggerFactory.getLogger(BankingService.class);
    private final HistoryService history;
    private final BankingAccountRepository bankingRepo;
    private final AccountRepository accountRepo;
    private final BankAccountMapper bankAccountMapper = Mappers.getMapper(BankAccountMapper.class);
    private final HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);

    public BankingService(HistoryService history, BankingAccountRepository bankingRepo, AccountRepository accountRepo) {
        this.history = history;
        this.bankingRepo = bankingRepo;
        this.accountRepo = accountRepo;
    }

    @Transactional
    public BankAccountDto deposit(BankAccountDto dto) {
        log.info("trying to find banking account with id: {}", dto.id());
        var bankAccountEntity = bankingRepo.findById(dto.id())
                .orElseThrow(
                        () -> new RuntimeException("Banking account doesn't exist")
                );
        var resultSum = bankAccountEntity.getMoneyFunds().add(dto.moneyFunds());
        bankAccountEntity.setMoneyFunds(resultSum);
        log.info("deposit money to banking account with sum: {}", dto.moneyFunds());
        var save = bankingRepo.save(bankAccountEntity);
        log.info("deposit successful");

        history.createEvent(historyMapper.toHistoryDto(
                bankAccountEntity,
                OperationType.DEPOSIT,
                null,
                dto.moneyFunds()
        ));

        return bankAccountMapper.toBankAccountDto(save);
    }

    @Transactional
    public BankAccountDto withdraw(BankAccountDto dto) {
        log.info("trying to find banking account with id: {}", dto.id());
        var bankAccountEntity = bankingRepo.findBankAccountEntitiesByIdAndPin(
                        dto.id(),
                        dto.pin()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account with id " + dto.id() + " doesn't found"
                        )
                );

        var resultSum = bankAccountEntity.getMoneyFunds().subtract(dto.moneyFunds());
        if (resultSum.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("total amount can't be negative");
        bankAccountEntity.setMoneyFunds(resultSum);

        log.info("withdraw money to banking account with sum: {}", dto.moneyFunds());
        BankAccountEntity save = bankingRepo.save(bankAccountEntity);
        log.info("withdraw successful");

        history.createEvent(historyMapper.toHistoryDto(
                save,
                OperationType.WITHDRAW,
                null,
                dto.moneyFunds()
        ));

        return bankAccountMapper.toBankAccountDto(save);
    }

    @Transactional
    public BankAccountDto transfer(BankAccountDto dto, UUID toBankAccountId) {
        log.info("trying to find banking account for writing off money with id: {}", dto.id());
        var fromAccountEntity = bankingRepo.findBankAccountEntitiesByIdAndPin(
                        dto.id(),
                        dto.pin()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account for writing off money with id: " + dto.id()
                        )
                );

        log.info("trying to find banking account for replenishment money with id: {}", toBankAccountId);
        var toBankAccountEntity = bankingRepo.findById(toBankAccountId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "banking account for writing off money with id: " + toBankAccountId
                        )
                );

        var fromResultSum = fromAccountEntity.getMoneyFunds().subtract(dto.moneyFunds());
        if (fromResultSum.compareTo(BigDecimal.ZERO) < 0)
            throw new RuntimeException("total amount can't be negative");
        fromAccountEntity.setMoneyFunds(fromResultSum);

        log.info("writing off money from banking account with sum: {}", dto.moneyFunds());
        var saveFrom = bankingRepo.save(fromAccountEntity);
        log.info("writing off successful");

        history.createEvent(historyMapper.toHistoryDto(
                saveFrom,
                OperationType.TRANSFER_OUT,
                toBankAccountEntity,
                dto.moneyFunds()
        ));

        var toResultSum = toBankAccountEntity.getMoneyFunds().add(dto.moneyFunds());
        toBankAccountEntity.setMoneyFunds(toResultSum);

        log.info("replenishment money to banking account with sum: {}", dto.moneyFunds());
        var saveTo = bankingRepo.save(toBankAccountEntity);
        log.info("replenishment successful");

        history.createEvent(historyMapper.toHistoryDto(
                saveTo,
                OperationType.TRANSFER_IN,
                fromAccountEntity,
                dto.moneyFunds()
        ));

        return bankAccountMapper.toBankAccountDto(saveFrom);
    }

    @Transactional
    public BankAccountDto createBankAccount(UUID accountId, String pin) {
        log.info("trying to find account with {}", accountId);
        var account = accountRepo.findById(accountId);
        if (account.isEmpty())
            throw new EntityNotFoundException();

        //Есть ли уже банковский счет у аккаунта с таким пином
        var bankAccountWithPinExist = account.get().getListBankAccount().stream()
                .anyMatch(bankAccount -> bankAccount.getPin().equals(pin));
        if (bankAccountWithPinExist)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        log.info("create bankAccount with accountId {}", account);

        var bankAccount = new BankAccountEntity();
        bankAccount.setAccount(account.get());
        bankAccount.setPin(pin);
        var savedBankAccount = bankingRepo.save(bankAccount);
        return bankAccountMapper.toBankAccountDto(savedBankAccount);
    }
}
