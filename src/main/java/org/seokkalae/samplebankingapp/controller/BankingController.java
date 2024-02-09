package org.seokkalae.samplebankingapp.controller;

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
        return bankingService.deposit(bankAccountId, request);
    }

    @PostMapping("{bank_account_id}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse withdraw(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            WithdrawRequest request
    ) {
        return bankingService.withdraw(bankAccountId, request);
    }

    @PostMapping("{bank_account_id}/transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse transfer(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            TransferRequest request
    ) {
        return bankingService.transfer(bankAccountId, request);
    }

    @GetMapping("{bank_account_id}")
    @ResponseStatus(HttpStatus.OK)
    public HistoryResponse getBankAccountInfo(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId
    ) {
        return historyService.getBankAccountHistory(bankAccountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBankAccountResponse createBankAccount(
            @RequestBody
            CreateBankAccountRequest request
    ) {
        return bankingService.createBankAccount(request);
    }
}
