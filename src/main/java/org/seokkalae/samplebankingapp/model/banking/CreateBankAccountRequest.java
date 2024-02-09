package org.seokkalae.samplebankingapp.model.banking;

import java.util.UUID;

public record CreateBankAccountRequest(UUID accountId, String pin) {
}
