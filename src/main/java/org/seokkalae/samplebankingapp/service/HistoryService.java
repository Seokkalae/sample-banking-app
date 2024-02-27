package org.seokkalae.samplebankingapp.service;

import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.dto.HistoryDto;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.mapper.HistoryMapper;
import org.seokkalae.samplebankingapp.repository.AccountRepository;
import org.seokkalae.samplebankingapp.repository.BankingAccountRepository;
import org.seokkalae.samplebankingapp.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoryService {
    private final static Logger log = LoggerFactory.getLogger(HistoryService.class);
    private final HistoryRepository historyRepo;
    private final HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);
    private final BankingAccountRepository bankAccountRepository;

    public HistoryService(HistoryRepository historyRepo, BankingAccountRepository bankAccountRepository) {
        this.historyRepo = historyRepo;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<HistoryDto> getAccountHistory(UUID accountId) {
        log.info("trying to find history for account with id: {}", accountId);
        var entities = historyRepo.findAllByBankAccount_Account_IdOrderByOperationTimestampTZDesc(accountId);
        return historyMapper.toHistoryDto(entities);
    }

    public List<HistoryDto> getBankAccountHistory(UUID bankAccountId) {
        log.info("trying to find history for bank account with id: {}", bankAccountId);
        var entities = historyRepo.findAllByBankAccount_IdOrderByOperationTimestampTZDesc(bankAccountId);
        return historyMapper.toHistoryDto(entities);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void createEvent(HistoryDto dto) {
        var entity = historyMapper.toHistoryEntity(dto);
        var bankAccountEntity = bankAccountRepository.findById(dto.bankAccount().id()).get();
        entity.getBankAccount().setAccount(bankAccountEntity.getAccount());

        BankAccountEntity toBankAccountEntity = null;
        if (dto.toBankAccount() != null) {
            toBankAccountEntity = bankAccountRepository.findById(dto.toBankAccount().id()).get();
            entity.getBankAccount().setAccount(toBankAccountEntity.getAccount());
        }
        historyRepo.save(entity);
    }

}
