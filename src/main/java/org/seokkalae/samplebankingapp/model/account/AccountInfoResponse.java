package org.seokkalae.samplebankingapp.model.account;

import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;
import org.seokkalae.samplebankingapp.model.banking.submodel.BankAccountInfoModel;

import java.util.List;
import java.util.UUID;

public record AccountInfoResponse(UUID accountId, AccountFullNameModel fullName,
                                  List<BankAccountInfoModel> bankAccountInfo) {
}
