package org.seokkalae.samplebankingapp.model.banking.submodel;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountInfoModel(UUID id, BigDecimal moneyFunds) {
}
