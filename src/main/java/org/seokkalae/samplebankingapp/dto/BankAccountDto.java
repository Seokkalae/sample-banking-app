package org.seokkalae.samplebankingapp.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountDto(
        UUID id,
        BigDecimal moneyFunds,
        String pin
) {
}
