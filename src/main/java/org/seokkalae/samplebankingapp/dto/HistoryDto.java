package org.seokkalae.samplebankingapp.dto;

import org.seokkalae.samplebankingapp.enums.OperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record HistoryDto(
        UUID id,
        String firstName,
        String lastName,
        String patronymic,
        BankAccountDto bankAccount,
        OperationType operationType,
        BigDecimal operationSum,
        OffsetDateTime operationTimestampTZ,
        BankAccountDto toBankAccount
) {
}
