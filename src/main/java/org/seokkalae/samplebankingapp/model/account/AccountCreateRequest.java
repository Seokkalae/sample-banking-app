package org.seokkalae.samplebankingapp.model.account;

import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;

public record AccountCreateRequest(AccountFullNameModel fullName, String pin) {
}
