package org.seokkalae.samplebankingapp.controller;

import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.mapper.BankAccountMapper;
import org.seokkalae.samplebankingapp.mapper.HistoryMapper;
import org.seokkalae.samplebankingapp.model.banking.*;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.service.BankingService;
import org.seokkalae.samplebankingapp.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "banking", consumes = "application/json")
public class BankingController {
    private final BankingService bankingService;
    private final HistoryService historyService;
    private final BankAccountMapper bankAccountMapper = Mappers.getMapper(BankAccountMapper.class);
    private final HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);

    public BankingController(BankingService bankingService, HistoryService historyService) {
        this.bankingService = bankingService;
        this.historyService = historyService;
    }

    @PostMapping("{bank_account_id}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse deposit(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            DepositRequest request
    ) {
        var result = bankingService.deposit(bankAccountMapper.toBankAccountDto(
                bankAccountId,
                request
        ));
        return bankAccountMapper.toBankingResponse(result, OperationType.DEPOSIT);
    }

    @PostMapping("{bank_account_id}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse withdraw(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            WithdrawRequest request
    ) {
        var result = bankingService.withdraw(bankAccountMapper.toBankAccountDto(
                bankAccountId,
                request
        ));
        return bankAccountMapper.toBankingResponse(result, OperationType.WITHDRAW);
    }

    @PostMapping("{bank_account_id}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse transfer(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            TransferRequest request
    ) {
        var result = bankingService.transfer(bankAccountMapper.toBankAccountDto(
                        bankAccountId,
                        request
                )
                , request.toBankingAccountId()
        );
        return bankAccountMapper.toBankingResponse(result, OperationType.TRANSFER_OUT);
    }

    @GetMapping("{bank_account_id}")
    @ResponseStatus(HttpStatus.OK)
    public HistoryResponse getBankAccountInfo(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId
    ) {
        var result = historyService.getBankAccountHistory(bankAccountId);
        return historyMapper.toHistoryResponse(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBankAccountResponse createBankAccount(
            @RequestBody
            CreateBankAccountRequest request
    ) {
        var result = bankingService.createBankAccount(request.accountId(), request.pin());
        return new CreateBankAccountResponse(
                result.id()
        );
    }
}
