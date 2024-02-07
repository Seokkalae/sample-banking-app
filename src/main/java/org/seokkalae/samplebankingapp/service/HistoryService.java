package org.seokkalae.samplebankingapp.service;

import org.seokkalae.samplebankingapp.converter.HistoryConverter;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.history.AccountHistoryResponse;
import org.seokkalae.samplebankingapp.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class HistoryService {
    private final static Logger log = LoggerFactory.getLogger(HistoryService.class);
    private final HistoryRepository historyRepo;

    public HistoryService(HistoryRepository historyRepo) {
        this.historyRepo = historyRepo;
    }

    public AccountHistoryResponse getAccountHistory(UUID accountId) {
        log.info("trying to find history for account with id: {}", accountId);
        return HistoryConverter
                .fromHistoryEntityToAccountHistoryResponse(
                        historyRepo.findAllByBankAccount_Account_IdOrderByOperationTimestampTZDesc(accountId)
                );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createEvent(
            BankAccountEntity bankAccount,
            OperationType operationType,
            BigDecimal operationSum,
            BankAccountEntity toBankAccount
    ) {
        HistoryEntity history = new HistoryEntity();
        history.setBankAccount(bankAccount);
        history.setOperationType(operationType);
        history.setOperationSum(operationSum);
        history.setToBankAccount(toBankAccount);
        historyRepo.save(history);

    }

}
