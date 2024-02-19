package org.seokkalae.samplebankingapp.utils;

import org.seokkalae.samplebankingapp.dto.HistoryDto;
import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HistoryCreator {
    public static UUID randomUUID = UUID.randomUUID();
    public static BigDecimal operationSum = BigDecimal.ZERO;
    public static OffsetDateTime operationTimestampTZ = OffsetDateTime.now();

    public static HistoryDto createDefaultHistoryDto(
            Boolean forCreate,
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        var toBankAccount = createToBankAccount
                ? AccountCreator.createDefaultAccountDto(true, forCreate).listBankAccount().getFirst()
                : null;
        var id = forCreate ? null : randomUUID;
        var money = forCreate ? null : operationSum;
        var firstName = forCreate ? null : AccountCreator.firstName;
        var lastName = forCreate ? null : AccountCreator.lastName;
        var patronymic = forCreate ? null : AccountCreator.patronymic;
        return new HistoryDto(
                id,
                firstName,
                lastName,
                patronymic,
                AccountCreator.createDefaultAccountDto(true, forCreate).listBankAccount().getFirst(),
                operationType,
                money,
                operationTimestampTZ,
                toBankAccount
        );
    }

    public static HistoryEntity createDefaultHistoryEntity(
            Boolean forCreate,
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        var entity = new HistoryEntity();
        var id = forCreate ? null : randomUUID;
        entity.setId(id);
        var bankAccountEntity = forCreate
                ? BankAccountCreator.createDefaultBankAccountEntity(forCreate)
                : AccountCreator.createDefaultAccountEntity(true, forCreate)
                .getListBankAccount().getFirst();
        entity.setBankAccount(bankAccountEntity);
        entity.setOperationType(operationType);

        var money = forCreate ? null : operationSum;
        entity.setOperationSum(money);
        entity.setOperationTimestampTZ(operationTimestampTZ);

        var toBankAccount = createToBankAccount
                ? BankAccountCreator.createDefaultBankAccountEntity(forCreate)
                : null;
        entity.setToBankAccount(toBankAccount);
        return entity;
    }

    public static List<HistoryDto> createListHistoryDto(
            Boolean forCreate,
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        List<HistoryDto> dtos = new LinkedList<>();
        dtos.add(createDefaultHistoryDto(
                forCreate,
                operationType,
                createToBankAccount));
        dtos.add(createDefaultHistoryDto(
                forCreate,
                operationType,
                createToBankAccount));
        return dtos;
    }

    public static List<HistoryEntity> createListHistoryEntity(
            Boolean forCreate,
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        List<HistoryEntity> entityList = new LinkedList<>();
        entityList.add(createDefaultHistoryEntity(
                forCreate,
                operationType,
                createToBankAccount
        ));
        entityList.add(createDefaultHistoryEntity(
                forCreate,
                operationType,
                createToBankAccount
        ));
        return entityList;
    }

    public static HistoryResponse createDefaultHistoryResponse(
            Boolean singletonHistoryInfos,
            OperationType operationType,
            Boolean createToBankAccountId
    ) {
        return new HistoryResponse(
                AccountCreator.firstName,
                AccountCreator.lastName,
                AccountCreator.patronymic,
                singletonHistoryInfos
                        ? createSingletoneHistoryInfoModelList(operationType, createToBankAccountId)
                        : createHistoryInfoModelList(operationType, createToBankAccountId)
        );
    }

    public static List<HistoryInfoModel> createSingletoneHistoryInfoModelList(
            OperationType operationType,
            Boolean createToBankAccountId
    ) {
        return Collections.singletonList(createDefaultHistoryInfoModel(operationType, createToBankAccountId));
    }

    public static List<HistoryInfoModel> createHistoryInfoModelList(
            OperationType operationType,
            Boolean createToBankAccountId
    ) {
        List<HistoryInfoModel> list = new LinkedList<>();
        list.add(createDefaultHistoryInfoModel(operationType, createToBankAccountId));
        list.add(createDefaultHistoryInfoModel(operationType, createToBankAccountId));
        return list;
    }

    public static HistoryInfoModel createDefaultHistoryInfoModel(
            OperationType operationType,
            Boolean createToBankAccountId) {
        return new HistoryInfoModel(
                randomUUID,
                operationType,
                operationSum,
                operationTimestampTZ,
                createToBankAccountId ? BankAccountCreator.randomUUID : null
        );
    }

    public static List<HistoryDto> createDefaultSingletonHistoryDtoList(
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        return Collections.singletonList(createDefaultHistoryDto(
                false,
                operationType,
                createToBankAccount
        ));
    }

    public static List<HistoryDto> createDefaultMoreThanOneHistoryDtoList(
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        List<HistoryDto> list = new LinkedList<>();
        list.add(createDefaultHistoryDto(
                false,
                operationType,
                createToBankAccount
        ));
        list.add(createDefaultHistoryDto(
                false,
                operationType,
                createToBankAccount
        ));
        return list;
    }

    public static List<HistoryDto> createDefaultHistoryDtoList(
            Boolean singletonList,
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        return singletonList
                ? createDefaultSingletonHistoryDtoList(operationType, createToBankAccount)
                : createDefaultMoreThanOneHistoryDtoList(operationType, createToBankAccount);
    }

    public static HistoryDto createHistoryDtoForCreateHistory(
            OperationType operationType,
            Boolean createToBankAccount
    ) {
        var toBankAccount = createToBankAccount
                ? AccountCreator.createDefaultAccountDto(true, false).listBankAccount().getFirst()
                : null;
        return new HistoryDto(
                null,
                AccountCreator.firstName,
                AccountCreator.lastName,
                AccountCreator.patronymic,
                AccountCreator.createDefaultAccountDto(true, false).listBankAccount().getFirst(),
                operationType,
                operationSum,
                null,
                toBankAccount
        );
    }
}
