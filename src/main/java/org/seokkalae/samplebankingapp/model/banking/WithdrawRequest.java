package org.seokkalae.samplebankingapp.model.banking;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawRequest(UUID bankingAccountId, String pin, BigDecimal sum) {
}
