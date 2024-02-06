package org.seokkalae.samplebankingapp.model.banking;

import org.seokkalae.samplebankingapp.enums.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public record BankingResponse(UUID bankingAccountId, OperationType operationType, BigDecimal currentFunds) {
}
