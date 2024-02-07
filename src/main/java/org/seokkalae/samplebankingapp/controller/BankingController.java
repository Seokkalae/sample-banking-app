package org.seokkalae.samplebankingapp.controller;

import org.seokkalae.samplebankingapp.model.banking.BankingResponse;
import org.seokkalae.samplebankingapp.model.banking.DepositRequest;
import org.seokkalae.samplebankingapp.model.banking.TransferRequest;
import org.seokkalae.samplebankingapp.model.banking.WithdrawRequest;
import org.seokkalae.samplebankingapp.service.BankingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "banking", consumes = "application/json")
public class BankingController {
    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("deposit")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse deposit(
            @RequestBody
            DepositRequest request
    ) {
        return bankingService.deposit(request);
    }

    @PostMapping("withdraw")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse withdraw(
            @RequestBody
            WithdrawRequest request
    ) {
        return bankingService.withdraw(request);
    }

    @PostMapping("transfer")
    @ResponseStatus(HttpStatus.OK)
    public BankingResponse transfer(
            @RequestBody
            TransferRequest request
    ) {
        return bankingService.transfer(request);
    }
}
