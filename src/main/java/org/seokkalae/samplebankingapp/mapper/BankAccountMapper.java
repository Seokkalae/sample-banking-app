package org.seokkalae.samplebankingapp.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.seokkalae.samplebankingapp.dto.BankAccountDto;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.banking.*;
import org.seokkalae.samplebankingapp.model.banking.submodel.BankAccountInfoModel;

import java.util.List;
import java.util.UUID;

@Mapper
public interface BankAccountMapper {
    BankAccountEntity toBankAccountEntity(BankAccountDto dto);
    BankAccountDto toBankAccountDto(BankAccountEntity entity);

    List<BankAccountEntity> toBankAccountEntityList(List<BankAccountDto> listDto);
    List<BankAccountDto> toBankAccountDtoList(List<BankAccountEntity> listEntity);

    @AfterMapping
    default void mapBidirectional (@MappingTarget AccountEntity accountEntity) {
        accountEntity.getListBankAccount()
                .forEach(bankAccount -> bankAccount.setAccount(accountEntity));
    }

    @Mapping(target = "id", source = "bankAccountId")
    @Mapping(target = "moneyFunds", source = "source.sum")
    BankAccountDto toBankAccountDto(UUID bankAccountId, DepositRequest source);

    @Mapping(target = "id", source = "bankAccountId")
    @Mapping(target = "moneyFunds", source = "source.sum")
    BankAccountDto toBankAccountDto(UUID bankAccountId, WithdrawRequest source);

    @Mapping(target = "id", source = "bankAccountId")
    @Mapping(target = "moneyFunds", source = "source.sum")
    BankAccountDto toBankAccountDto(UUID bankAccountId, TransferRequest source);

    BankAccountDto toBankAccountDto(CreateBankAccountRequest source);

    @Mapping(target = "operationType", source = "operationType")
    @Mapping(target = "bankingAccountId", source = "dto.id")
    @Mapping(target = "currentFunds", source = "dto.moneyFunds")
    BankingResponse toBankingResponse(BankAccountDto dto, OperationType operationType);

    BankAccountInfoModel toBankAccountInfoModel(BankAccountDto dto);
    List<BankAccountInfoModel> toBankAccountInfoModel(List<BankAccountDto> dtos);
}
