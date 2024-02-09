package org.seokkalae.samplebankingapp.controller;

import org.seokkalae.samplebankingapp.model.banking.BankingResponse;
import org.seokkalae.samplebankingapp.model.banking.DepositRequest;
import org.seokkalae.samplebankingapp.model.banking.TransferRequest;
import org.seokkalae.samplebankingapp.model.banking.WithdrawRequest;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.service.BankingService;
import org.seokkalae.samplebankingapp.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "banking/{bank_account_id}", consumes = "application/json")
public class BankingController {
    private final BankingService bankingService;
    private final HistoryService historyService;

    public BankingController(BankingService bankingService, HistoryService historyService) {
        this.bankingService = bankingService;
        this.historyService = historyService;
    }

    @PostMapping("deposit")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse deposit(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            DepositRequest request
    ) {
        return bankingService.deposit(bankAccountId, request);
    }

    @PostMapping("withdraw")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse withdraw(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            WithdrawRequest request
    ) {
        return bankingService.withdraw(bankAccountId, request);
    }

    @PostMapping("transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse transfer(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId,
            @RequestBody
            TransferRequest request
    ) {
        return bankingService.transfer(bankAccountId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HistoryResponse getBankAccountInfo(
            @PathVariable(name = "bank_account_id")
            UUID bankAccountId
    ) {
        return historyService.getBankAccountHistory(bankAccountId);
    }
}
