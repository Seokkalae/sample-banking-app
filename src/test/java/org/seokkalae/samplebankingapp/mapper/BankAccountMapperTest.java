package org.seokkalae.samplebankingapp.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.utils.BankAccountCreator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BankAccountMapperTest {
    private final BankAccountMapper mapper = Mappers.getMapper(BankAccountMapper.class);

    @Test
    void toBankAccountDtoFromDepositRequest() {
        var request = BankAccountCreator.createDefaultDepositRequest();
        var id = BankAccountCreator.randomUUID;

        var expected = BankAccountCreator.createDefaultBankAccountDto(false, false);
        var actual = mapper.toBankAccountDto(id, request);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.moneyFunds()).isNotNull();
        assertThat(actual.pin()).isNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
        assertThat(actual.pin()).isEqualTo(expected.pin());
    }

    @Test
    void toBankAccountDtoFromWithdrawRequest() {
        var request = BankAccountCreator.createDefaultWithdrawRequest();
        var id = BankAccountCreator.randomUUID;

        var expected = BankAccountCreator.createDefaultBankAccountDto(false, true);
        var actual = mapper.toBankAccountDto(id, request);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.moneyFunds()).isNotNull();
        assertThat(actual.pin()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
        assertThat(actual.pin()).isEqualTo(expected.pin());
    }

    @Test
    void toBankAccountDtoFromTransferRequest() {
        var request = BankAccountCreator.createDefaultTransferRequest();
        var id = BankAccountCreator.randomUUID;

        var expected = BankAccountCreator.createDefaultBankAccountDto(false, true);
        var actual = mapper.toBankAccountDto(id, request);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.moneyFunds()).isNotNull();
        assertThat(actual.pin()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
        assertThat(actual.pin()).isEqualTo(expected.pin());
    }

    @Test
    void toBankAccountDtoFromCreateBankAccountRequest() {
        var request = BankAccountCreator.createDefaultCreateBankAccountRequest();

        var expected = BankAccountCreator.createDefaultBankAccountDto(true, true);
        var actual = mapper.toBankAccountDto(request);

        assertThat(actual.id()).isNull();
        assertThat(actual.moneyFunds()).isNull();
        assertThat(actual.pin()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
        assertThat(actual.pin()).isEqualTo(expected.pin());
    }

    @Test
    void toBankAccountEntityFromBankAccountDto() {
        var dto = BankAccountCreator.createDefaultBankAccountDto(true, true);

        var expected = BankAccountCreator.createDefaultBankAccountEntity(true);
        var actual = mapper.toBankAccountEntity(dto);

        assertThat(actual.getId()).isNull();
        assertThat(actual.getMoneyFunds()).isNull();
        assertThat(actual.getPin()).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getMoneyFunds()).isEqualTo(expected.getMoneyFunds());
        assertThat(actual.getPin()).isEqualTo(expected.getPin());
    }

    @Test
    void toBankAccountDtoFromBankAccountEntity() {
        var entity = BankAccountCreator.createDefaultBankAccountEntity(false);

        var expected = BankAccountCreator.createDefaultBankAccountDto(false, true);
        var actual = mapper.toBankAccountDto(entity);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.moneyFunds()).isNotNull();
        assertThat(actual.pin()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
        assertThat(actual.pin()).isEqualTo(expected.pin());
    }

    @ParameterizedTest
    @EnumSource(OperationType.class)
    void toBankAccountDtoFromBankingResponse(OperationType operationType) {
        var dto = BankAccountCreator.createDefaultBankAccountDto(false, false);

        var expected = BankAccountCreator.createDefaultBankingResponse(operationType);
        var actual = mapper.toBankingResponse(dto, operationType);

        assertThat(actual.bankingAccountId()).isNotNull();
        assertThat(actual.operationType()).isNotNull();
        assertThat(actual.currentFunds()).isNotNull();
        assertThat(actual.bankingAccountId()).isEqualTo(expected.bankingAccountId());
        assertThat(actual.operationType()).isEqualTo(expected.operationType());
        assertThat(actual.currentFunds()).isEqualTo(expected.currentFunds());
    }

    @Test
    void toBankAccountInfoModelFromBankAccountDto() {
        var dto = BankAccountCreator.createDefaultBankAccountDto(false, true);

        var expected = BankAccountCreator.createDefaultBankAccountInfoModel();
        var actual = mapper.toBankAccountInfoModel(dto);

        assertThat(actual.id()).isNotNull();
        assertThat(actual.moneyFunds()).isNotNull();
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.moneyFunds()).isEqualTo(expected.moneyFunds());
    }

    @ParameterizedTest
    @ValueSource(booleans = {
            true, false
    })
    void toListBankAccountInfoModelFromListBankAccountDto(boolean singleton) {
        var dtos = BankAccountCreator.createDefaultListBankAccountDto(false, true, singleton);

        var expected = BankAccountCreator.createDefaultListBankAccountInfoModel(singleton);
        var actual = mapper.toBankAccountInfoModel(dtos);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isGreaterThan(0);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}