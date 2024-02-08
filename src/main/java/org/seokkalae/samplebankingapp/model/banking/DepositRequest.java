package org.seokkalae.samplebankingapp.model.banking;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequest(BigDecimal sum) {
}
