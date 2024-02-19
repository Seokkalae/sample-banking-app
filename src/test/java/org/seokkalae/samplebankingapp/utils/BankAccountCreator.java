package org.seokkalae.samplebankingapp.utils;

import org.seokkalae.samplebankingapp.dto.BankAccountDto;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.banking.*;
import org.seokkalae.samplebankingapp.model.banking.submodel.BankAccountInfoModel;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class BankAccountCreator {
    public static UUID randomUUID = UUID.randomUUID();
    public static BigDecimal moneyFunds = BigDecimal.ZERO;
    public static String pin = "0000";

    /**
     * Генерация BankAccountDto
     *
     * @param forCreate     id банковского аккаунта
     * @param isRequiredPin если мы никакк не списываем деньги, тогда указываем false (как {@link DepositRequest})
     * @return {@link BankAccountDto}
     */
    public static BankAccountDto createDefaultBankAccountDto(
            Boolean forCreate,
            Boolean isRequiredPin
    ) {
        var id = forCreate ? null : randomUUID;
        var money = forCreate ? null : moneyFunds;
        var _pin = isRequiredPin ? pin : null;
        return new BankAccountDto(
                id,
                money,
                _pin
        );
    }

    public static List<BankAccountDto> createDefaultListBankAccountDto(
            Boolean forCreate,
            Boolean isRequiredPin,
            Boolean singletonList
    ) {
        if (singletonList)
            return Collections.singletonList(createDefaultBankAccountDto(forCreate, isRequiredPin));
        else
            return List.of(
                    createDefaultBankAccountDto(forCreate, isRequiredPin),
                    createDefaultBankAccountDto(forCreate, isRequiredPin)
            );
    }

    public static DepositRequest createDefaultDepositRequest() {
        return new DepositRequest(
                moneyFunds
        );
    }

    public static WithdrawRequest createDefaultWithdrawRequest() {
        return new WithdrawRequest(
                pin,
                moneyFunds
        );
    }

    public static TransferRequest createDefaultTransferRequest() {
        return new TransferRequest(
                pin,
                randomUUID,
                moneyFunds
        );
    }

    public static CreateBankAccountRequest createDefaultCreateBankAccountRequest() {
        return new CreateBankAccountRequest(
                randomUUID,
                pin
        );
    }

    public static BankAccountEntity createDefaultBankAccountEntity(Boolean forCreate) {
        UUID id = forCreate ? null : randomUUID;
        BigDecimal money = forCreate ? null : moneyFunds;
        var entity = new BankAccountEntity();
        entity.setId(id);
        entity.setMoneyFunds(money);
        entity.setPin(pin);
        return entity;
    }

    public static BankingResponse createDefaultBankingResponse(OperationType operationType) {
        return new BankingResponse(
                randomUUID,
                operationType,
                moneyFunds
        );
    }

    public static List<BankAccountDto> singletonListBankAccountDtoWithDefaultPin(Boolean forCreate) {
        return Collections.singletonList(createDefaultBankAccountDto(forCreate, true));
    }

    public static List<BankAccountDto> listBankAccountDtoWithDefaultPin(Boolean forCreate) {
        List<BankAccountDto> list = new LinkedList<>();
        list.add(createDefaultBankAccountDto(forCreate, true));
        list.add(createDefaultBankAccountDto(forCreate, true));
        return list;
    }

    public static List<BankAccountEntity> singletonListBankAccountEntity(Boolean forCreate) {
        return Collections.singletonList(createDefaultBankAccountEntity(forCreate));
    }

    public static List<BankAccountEntity> listBankAccountEntity(Boolean forCreate) {
        List<BankAccountEntity> list = new LinkedList<>();
        list.add(createDefaultBankAccountEntity(forCreate));
        list.add(createDefaultBankAccountEntity(forCreate));
        return list;
    }

    public static BankAccountInfoModel createDefaultBankAccountInfoModel() {
        return new BankAccountInfoModel(
                randomUUID,
                moneyFunds
        );
    }

    public static List<BankAccountInfoModel> createDefaultListBankAccountInfoModel(Boolean singletonList) {
        if (singletonList)
            return Collections.singletonList(createDefaultBankAccountInfoModel());
        else
            return List.of(
                    createDefaultBankAccountInfoModel(),
                    createDefaultBankAccountInfoModel()
            );
    }
}
