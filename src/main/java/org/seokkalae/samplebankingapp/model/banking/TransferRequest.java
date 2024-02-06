package org.seokkalae.samplebankingapp.model.banking;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(UUID fromBankingAccountId, String pin, UUID toBankingAccountId, BigDecimal sum) {
}
