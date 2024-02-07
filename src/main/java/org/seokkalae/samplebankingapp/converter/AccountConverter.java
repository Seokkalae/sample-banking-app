package org.seokkalae.samplebankingapp.converter;

import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;
import org.seokkalae.samplebankingapp.model.banking.submodel.BankAccountInfoModel;

import java.util.List;

public class AccountConverter {
    public static AccountCreateResponse fromBankAccountEntityToAccountCreateResponse(BankAccountEntity source) {
        return new AccountCreateResponse(source.getAccount().getId(), source.getId());
    }

    public static AccountInfoResponse fromBankAccountEntityToAccountInfoResponse (List<BankAccountEntity> source) {
        List<BankAccountInfoModel> bankAccountInfo = source.stream()
                .map(account -> new BankAccountInfoModel(
                        account.getId(),
                        account.getMoneyFunds()
                ))
                .toList();
        var account = source.getFirst().getAccount();
        var accountFullName = new AccountFullNameModel(account.getFirstName(),
                account.getLastName(),
                account.getPatronymic()
        );
        return new AccountInfoResponse(source.getFirst().getAccount().getId(),
                accountFullName,
                bankAccountInfo
        );
    }
}
