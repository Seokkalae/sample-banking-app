package org.seokkalae.samplebankingapp.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.utils.AccountCreator;
import org.seokkalae.samplebankingapp.utils.HistoryCreator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HistoryMapperTest {
    private final HistoryMapper mapper = Mappers.getMapper(HistoryMapper.class);

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryEntityFromHistoryDtoWithoutToBankAccount(OperationType operationType) {
        var dto = HistoryCreator.createDefaultHistoryDto(true, operationType, false);

        var excepted = HistoryCreator.createDefaultHistoryEntity(true, operationType, false);
        var actual = mapper.toHistoryEntity(dto);

        assertThat(actual.getId()).isNull();
        assertThat(actual.getBankAccount()).isNotNull();
        assertThat(actual.getOperationType()).isNotNull();
        assertThat(actual.getOperationSum()).isNull();
        assertThat(actual.getOperationTimestampTZ()).isNotNull();
        assertThat(actual.getToBankAccount()).isNull();
        assertThat(actual.getId()).isEqualTo(excepted.getId());
        assertThat(actual.getBankAccount()).usingRecursiveComparison()
                .isEqualTo(excepted.getBankAccount());
        assertThat(actual.getOperationType()).isEqualTo(excepted.getOperationType());
        assertThat(actual.getOperationSum()).isEqualTo(excepted.getOperationSum());
        assertThat(actual.getOperationTimestampTZ()).isEqualTo(excepted.getOperationTimestampTZ());
        assertThat(actual.getToBankAccount()).usingRecursiveComparison()
                .isEqualTo(excepted.getToBankAccount());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryEntityFromHistoryDtoWithToBankAccount(OperationType operationType) {
        var dto = HistoryCreator.createDefaultHistoryDto(true, operationType, true);

        var excepted = HistoryCreator.createDefaultHistoryEntity(true, operationType, true);
        var actual = mapper.toHistoryEntity(dto);

        assertThat(actual.getId()).isNull();
        assertThat(actual.getBankAccount()).isNotNull();
        assertThat(actual.getOperationType()).isNotNull();
        assertThat(actual.getOperationSum()).isNull();
        assertThat(actual.getOperationTimestampTZ()).isNotNull();
        assertThat(actual.getToBankAccount()).isNotNull();
        assertThat(actual.getId()).isEqualTo(excepted.getId());
        assertThat(actual.getBankAccount()).usingRecursiveComparison()
                .isEqualTo(excepted.getBankAccount());
        assertThat(actual.getOperationType()).isEqualTo(excepted.getOperationType());
        assertThat(actual.getOperationSum()).isEqualTo(excepted.getOperationSum());
        assertThat(actual.getOperationTimestampTZ()).isEqualTo(excepted.getOperationTimestampTZ());
        assertThat(actual.getToBankAccount()).usingRecursiveComparison()
                .isEqualTo(excepted.getToBankAccount());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toListHistoryEntityFromListHistoryDtoWithoutToBankAccount(OperationType operationType) {
        var dtos = HistoryCreator.createListHistoryDto(true, operationType, false);

        var expected = HistoryCreator.createListHistoryEntity(true, operationType, false);
        var actual = mapper.toHistoryEntity(dtos);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isNotEqualTo(0);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toListHistoryEntityFromListHistoryDtoWithToBankAccount(OperationType operationType) {
        var dtos = HistoryCreator.createListHistoryDto(true, operationType, true);

        var expected = HistoryCreator.createListHistoryEntity(true, operationType, true);
        var actual = mapper.toHistoryEntity(dtos);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isNotEqualTo(0);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryDtoFromHistoryEntityWithoutToBankAccount(OperationType operationType) {
        var entity = HistoryCreator.createDefaultHistoryEntity(false, operationType, false);

        var expected = HistoryCreator.createDefaultHistoryDto(false, operationType, false);
        var actual = mapper.toHistoryDto(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.bankAccount()).isNotNull();
        assertThat(actual.operationType()).isNotNull();
        assertThat(actual.operationSum()).isNotNull();
        assertThat(actual.operationTimestampTZ()).isNotNull();
        assertThat(actual.toBankAccount()).isNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.bankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.bankAccount());
        assertThat(actual.operationType()).isEqualTo(expected.operationType());
        assertThat(actual.operationSum()).isEqualTo(expected.operationSum());
        assertThat(actual.operationTimestampTZ()).isEqualTo(expected.operationTimestampTZ());
        assertThat(actual.toBankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.toBankAccount());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryDtoFromHistoryEntityWithToBankAccount(OperationType operationType) {
        var entity = HistoryCreator.createDefaultHistoryEntity(false, operationType, true);

        var expected = HistoryCreator.createDefaultHistoryDto(false, operationType, true);
        var actual = mapper.toHistoryDto(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.bankAccount()).isNotNull();
        assertThat(actual.operationType()).isNotNull();
        assertThat(actual.operationSum()).isNotNull();
        assertThat(actual.operationTimestampTZ()).isNotNull();
        assertThat(actual.toBankAccount()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.bankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.bankAccount());
        assertThat(actual.operationType()).isEqualTo(expected.operationType());
        assertThat(actual.operationSum()).isEqualTo(expected.operationSum());
        assertThat(actual.operationTimestampTZ()).isEqualTo(expected.operationTimestampTZ());
        assertThat(actual.toBankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.toBankAccount());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toListHistoryDtoFromListHistoryEntityWithoutToBankAccount(OperationType operationType) {
        var entities = HistoryCreator.createListHistoryEntity(false, operationType, false);

        var expected = HistoryCreator.createListHistoryDto(false, operationType, false);
        var actual = mapper.toHistoryDto(entities);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isNotEqualTo(0);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toListHistoryDtoFromListHistoryEntityWithToBankAccount(OperationType operationType) {
        var entities = HistoryCreator.createListHistoryEntity(false, operationType, true);

        var expected = HistoryCreator.createListHistoryDto(false, operationType, true);
        var actual = mapper.toHistoryDto(entities);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isNotEqualTo(0);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryResponseFromListHistoryDtoWithoutToBankAccountAndSingletonHistoryInfoList(OperationType operationType) {
        var dtos = HistoryCreator.createDefaultHistoryDtoList(true, operationType, false);

        var expected = HistoryCreator.createDefaultHistoryResponse(true, operationType, false);
        var actual = mapper.toHistoryResponse(dtos);

        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.historyInfo()).isNotNull();
        assertThat(actual.historyInfo().size()).isEqualTo(1);
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.historyInfo()).usingRecursiveComparison()
                .isEqualTo(expected.historyInfo());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryResponseFromListHistoryDtoWithoutToBankAccountAndMoreThanOneElementHistoryInfoList(OperationType operationType) {
        var dtos = HistoryCreator.createDefaultHistoryDtoList(false, operationType, false);

        var expected = HistoryCreator.createDefaultHistoryResponse(false, operationType, false);
        var actual = mapper.toHistoryResponse(dtos);

        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.historyInfo()).isNotNull();
        assertThat(actual.historyInfo().size()).isGreaterThan(1);
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.historyInfo()).usingRecursiveComparison()
                .isEqualTo(expected.historyInfo());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryResponseFromListHistoryDtoWithToBankAccountAndSingletonHistoryInfoList(OperationType operationType) {
        var dtos = HistoryCreator.createDefaultHistoryDtoList(true, operationType, true);

        var expected = HistoryCreator.createDefaultHistoryResponse(true, operationType, true);
        var actual = mapper.toHistoryResponse(dtos);

        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.historyInfo()).isNotNull();
        assertThat(actual.historyInfo().size()).isEqualTo(1);
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.historyInfo()).usingRecursiveComparison()
                .isEqualTo(expected.historyInfo());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryResponseFromListHistoryDtoWithToBankAccountAndMoreThanOneElementHistoryInfoList(OperationType operationType) {
        var dtos = HistoryCreator.createDefaultHistoryDtoList(false, operationType, true);

        var expected = HistoryCreator.createDefaultHistoryResponse(false, operationType, true);
        var actual = mapper.toHistoryResponse(dtos);

        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.historyInfo()).isNotNull();
        assertThat(actual.historyInfo().size()).isGreaterThan(1);
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.historyInfo()).usingRecursiveComparison()
                .isEqualTo(expected.historyInfo());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryDtoFromBankAccountEntity(OperationType operationType) {
        var entity = AccountCreator.createDefaultAccountEntity(true, false)
                .getListBankAccount()
                .getFirst();

        var toEntity = AccountCreator.createDefaultAccountEntity(true, false)
                .getListBankAccount()
                .getFirst();

        var expected = HistoryCreator.createHistoryDtoForCreateHistory(operationType, true);
        var actual = mapper.toHistoryDto(entity, operationType, toEntity, HistoryCreator.operationSum);

        assertThat(actual.id()).isNull();
        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.bankAccount()).isNotNull();
        assertThat(actual.operationType()).isNotNull();
        assertThat(actual.operationSum()).isNotNull();
        assertThat(actual.operationTimestampTZ()).isNull();
        assertThat(actual.toBankAccount()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.bankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.bankAccount());
        assertThat(actual.operationType()).isEqualTo(expected.operationType());
        assertThat(actual.operationSum()).isEqualTo(expected.operationSum());
        assertThat(actual.operationTimestampTZ()).isEqualTo(expected.operationTimestampTZ());
        assertThat(actual.toBankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.toBankAccount());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toHistoryDtoFromBankAccountEntityWithoutToBankAccount(OperationType operationType) {
        var entity = AccountCreator.createDefaultAccountEntity(true, false)
                .getListBankAccount()
                .getFirst();

        var expected = HistoryCreator.createHistoryDtoForCreateHistory(operationType, false);
        var actual = mapper.toHistoryDto(entity, operationType, null, HistoryCreator.operationSum);

        assertThat(actual.id()).isNull();
        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.bankAccount()).isNotNull();
        assertThat(actual.operationType()).isNotNull();
        assertThat(actual.operationSum()).isNotNull();
        assertThat(actual.operationTimestampTZ()).isNull();
        assertThat(actual.toBankAccount()).isNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.bankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.bankAccount());
        assertThat(actual.operationType()).isEqualTo(expected.operationType());
        assertThat(actual.operationSum()).isEqualTo(expected.operationSum());
        assertThat(actual.operationTimestampTZ()).isEqualTo(expected.operationTimestampTZ());
        assertThat(actual.toBankAccount()).usingRecursiveComparison()
                .isEqualTo(expected.toBankAccount());
    }


}