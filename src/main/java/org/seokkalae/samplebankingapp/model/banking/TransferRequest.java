package org.seokkalae.samplebankingapp.model.banking;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(String pin, UUID toBankingAccountId, BigDecimal sum) {
}
