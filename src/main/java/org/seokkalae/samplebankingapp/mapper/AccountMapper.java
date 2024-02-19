package org.seokkalae.samplebankingapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.seokkalae.samplebankingapp.dto.AccountDto;
import org.seokkalae.samplebankingapp.dto.BankAccountDto;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mapper(uses = BankAccountMapper.class)
public interface AccountMapper {
    @Mapping(target = "listBankAccount", source = "pin", qualifiedByName = "createListWithPin")
    AccountDto toAccountDto(AccountCreateRequest request);

    @Named("createListWithPin")
    default List<BankAccountDto> createListWithPin(String pin) {
        return Collections.singletonList(new BankAccountDto(
                null,
                null,
                pin
        ));
    }

    AccountEntity toAccountEntity(AccountDto dto);

    AccountDto toAccountDto(AccountEntity entity);

    @Mapping(target = "accountId", source = "dto.id")
    @Mapping(target = "bankAccountId", source = "dto.listBankAccount", qualifiedByName = "mapBankAccountId")
    AccountCreateResponse toAccountCreateResponse(AccountDto dto);

    @Named("mapBankAccountId")
    default UUID mapBankAccountId(List<BankAccountDto> source) {
        if (source == null || source.isEmpty())
            return null;

        return source.stream()
                .findFirst()
                .get().id();
    }

    @Mapping(target = "accountId", source = "dto.id")
    @Mapping(target = "bankAccountInfo", source = "dto.listBankAccount")
    AccountInfoResponse toAccountInfoResponse(AccountDto dto);
}
