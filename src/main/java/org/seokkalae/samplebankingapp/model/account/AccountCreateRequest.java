package org.seokkalae.samplebankingapp.model.account;

import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;

import java.time.LocalDate;

public record AccountCreateRequest(
        AccountFullNameModel fullName,
        LocalDate birthday,
        String pin) {
}
