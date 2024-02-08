package org.seokkalae.samplebankingapp.converter;

import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
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

        return new AccountInfoResponse(source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPatronymic(),
                bankAccountInfo
        );
    }
}
