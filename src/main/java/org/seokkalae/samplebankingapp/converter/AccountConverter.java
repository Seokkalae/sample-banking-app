package org.seokkalae.samplebankingapp.converter;

import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;
import org.seokkalae.samplebankingapp.model.banking.submodel.BankAccountInfoModel;

import java.util.List;

public class AccountConverter {
    public static AccountInfoResponse fromAccountEntityToAccountInfoResponse(AccountEntity source) {
        List<BankAccountInfoModel> bankAccountInfo = source.getListBankAccount().stream()
                .map(account -> new BankAccountInfoModel(
                        account.getId(),
                        account.getMoneyFunds()
                ))
                .toList();
        var accountFullName = new AccountFullNameModel(
                source.getFirstName(),
                source.getLastName(),
                source.getPatronymic() == null
                        ? null
                        : source.getPatronymic()
        );
        return new AccountInfoResponse(source.getId(),
                accountFullName,
                bankAccountInfo
        );
    }
}
