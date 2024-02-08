package org.seokkalae.samplebankingapp.controller;

import org.seokkalae.samplebankingapp.model.banking.BankingResponse;
import org.seokkalae.samplebankingapp.model.banking.DepositRequest;
import org.seokkalae.samplebankingapp.model.banking.TransferRequest;
import org.seokkalae.samplebankingapp.model.banking.WithdrawRequest;
import org.seokkalae.samplebankingapp.service.BankingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "banking/{bank_account_id}", consumes = "application/json")
public class BankingController {
    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("deposit")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse deposit(
            @PathVariable(name = "bank_account_id")
            UUID accountId,
            @RequestBody
            DepositRequest request
    ) {
        return bankingService.deposit(accountId, request);
    }

    @PostMapping("withdraw")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse withdraw(
            @PathVariable(name = "bank_account_id")
            UUID accountId,
            @RequestBody
            WithdrawRequest request
    ) {
        return bankingService.withdraw(accountId, request);
    }

    @PostMapping("transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse transfer(
            @PathVariable(name = "bank_account_id")
            UUID accountId,
            @RequestBody
            TransferRequest request
    ) {
        return bankingService.transfer(accountId, request);
    }
}
