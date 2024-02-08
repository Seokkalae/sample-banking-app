package org.seokkalae.samplebankingapp.model.banking;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawRequest(String pin, BigDecimal sum) {
}
