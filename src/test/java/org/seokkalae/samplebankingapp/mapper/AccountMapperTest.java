package org.seokkalae.samplebankingapp.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.utils.AccountCreator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AccountMapperTest {
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void fromAccountCreateRequestToAccountDto() {
        var request = AccountCreator.createDefaultAccountCreateRequest();

        var expected = AccountCreator.createDefaultAccountDto(true, true);
        var actual = mapper.toAccountDto(request);

        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.birthday()).isEqualTo(expected.birthday());
        assertThat(actual.listBankAccount()).isNotNull();
        assertThat(actual.listBankAccount()).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expected.listBankAccount());
    }

    @Test
    void fromAccountDtoToAccountEntity() {
        var dto = AccountCreator.createDefaultAccountDto(true, true);

        var expected = AccountCreator.createDefaultAccountEntity(true, true);
        var actual = mapper.toAccountEntity(dto);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getPatronymic()).isEqualTo(expected.getPatronymic());
        assertThat(actual.getBirthday()).isEqualTo(expected.getBirthday());
        assertThat(actual.getListBankAccount()).isNotNull();
        assertThat(actual.getListBankAccount()).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expected.getListBankAccount());
    }

    @Test
    void fromAccountDtoToAccountEntityWithBankAccountDtoListMoreThenOneElement() {
        var dto = AccountCreator.createDefaultAccountDto(false, true);

        AccountEntity expected = AccountCreator.createDefaultAccountEntity(false, true);
        AccountEntity actual = mapper.toAccountEntity(dto);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getPatronymic()).isEqualTo(expected.getPatronymic());
        assertThat(actual.getBirthday()).isEqualTo(expected.getBirthday());
        assertThat(actual.getListBankAccount()).isNotNull();
        assertThat(actual.getListBankAccount()).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expected.getListBankAccount());
    }

    @Test
    void fromAccountEntityToAccountDto() {
        var accountEntity = AccountCreator.createDefaultAccountEntity(true, false);

        var expected = AccountCreator.createDefaultAccountDto(true, false);
        var actual = mapper.toAccountDto(accountEntity);

        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.birthday()).isEqualTo(expected.birthday());
        assertThat(actual.listBankAccount()).isNotNull();
        assertThat(actual.listBankAccount()).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expected.listBankAccount());
    }

    @Test
    void fromAccountEntityToAccountDtoWithBankAccountEntityMoreThanOneElement() {
        var accountEntity = AccountCreator.createDefaultAccountEntity(false, false);

        var expected = AccountCreator.createDefaultAccountDto(false, false);
        var actual = mapper.toAccountDto(accountEntity);

        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.birthday()).isEqualTo(expected.birthday());
        assertThat(actual.listBankAccount()).isNotNull();
        assertThat(actual.listBankAccount()).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expected.listBankAccount());
    }

    @Test
    void fromAccountDtoToAccountCreateResponse() {
        var dto = AccountCreator.createDefaultAccountDto(false, false);

        var expected = AccountCreator.createDefaultAccountCreateResponse();
        var actual = mapper.toAccountCreateResponse(dto);

        assertThat(actual.accountId()).isEqualTo(expected.accountId());
        assertThat(actual.bankAccountId()).isEqualTo(expected.bankAccountId());
    }

    @ParameterizedTest
    @ValueSource(booleans = {
            true, false
    })
    void toAccountInfoResponseFromAccountDto(boolean singletonList) {
        var dto = AccountCreator.createDefaultAccountDto(singletonList, false);

        var expected = AccountCreator.createDefaultAccountInfoResponse(singletonList);
        var actual = mapper.toAccountInfoResponse(dto);

        assertThat(actual.accountId()).isNotNull();
        assertThat(actual.firstName()).isNotNull();
        assertThat(actual.lastName()).isNotNull();
        assertThat(actual.patronymic()).isNotNull();
        assertThat(actual.bankAccountInfo()).isNotNull();
        assertThat(actual.bankAccountInfo().size()).isGreaterThan(0);
        assertThat(actual.accountId()).isEqualTo(expected.accountId());
        assertThat(actual.firstName()).isEqualTo(expected.firstName());
        assertThat(actual.lastName()).isEqualTo(expected.lastName());
        assertThat(actual.patronymic()).isEqualTo(expected.patronymic());
        assertThat(actual.bankAccountInfo()).usingRecursiveComparison()
                .isEqualTo(expected.bankAccountInfo());

    }
}