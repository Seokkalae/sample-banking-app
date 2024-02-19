package org.seokkalae.samplebankingapp.utils;

import org.seokkalae.samplebankingapp.dto.AccountDto;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AccountCreator {
    public final static LocalDate birthday = LocalDate.parse("2000-01-01", DateTimeFormatter.ISO_DATE);
    public final static String firstName = "Иван";
    public final static String lastName = "Иванов";
    public final static String patronymic = "Иванович";
    public final static String pin = "0000";

    public static final UUID randomUUID = UUID.randomUUID();

    public static AccountDto createDefaultAccountDto(Boolean singletonList, Boolean forCreate) {
        var listBankAccount = singletonList
                ? BankAccountCreator.singletonListBankAccountDtoWithDefaultPin(forCreate)
                : BankAccountCreator.listBankAccountDtoWithDefaultPin(forCreate);
        UUID id = forCreate ? null : randomUUID;

        return new AccountDto(
                id,
                firstName,
                lastName,
                patronymic,
                birthday,
                listBankAccount
        );
    }

    public static AccountCreateRequest createDefaultAccountCreateRequest() {
        return new AccountCreateRequest(
                firstName,
                lastName,
                patronymic,
                birthday,
                pin
        );
    }

    public static AccountEntity createDefaultAccountEntity(
            Boolean singletonList,
            Boolean forCreate
    ) {
        var listBankAccount = singletonList
                ? BankAccountCreator.singletonListBankAccountEntity(forCreate)
                : BankAccountCreator.listBankAccountEntity(forCreate);
        UUID accountId = forCreate
                ? null
                : randomUUID;
        AccountEntity entity = new AccountEntity();
        entity.setId(accountId);
        entity.setFirstName(AccountCreator.firstName);
        entity.setLastName(AccountCreator.lastName);
        entity.setPatronymic(AccountCreator.patronymic);
        entity.setBirthday(AccountCreator.birthday);
        entity.setListBankAccount(listBankAccount);
        entity.getListBankAccount()
                .forEach(bankAccount -> bankAccount.setAccount(entity));
        return entity;
    }

    public static AccountCreateResponse createDefaultAccountCreateResponse() {
        return new AccountCreateResponse(
                randomUUID,
                BankAccountCreator.randomUUID
        );
    }

    public static AccountInfoResponse createDefaultAccountInfoResponse(Boolean singletonBankAccountInfoModel) {
        return new AccountInfoResponse(
                randomUUID,
                firstName,
                lastName,
                patronymic,
                BankAccountCreator.createDefaultListBankAccountInfoModel(singletonBankAccountInfoModel)
        );
    }
}
