package org.seokkalae.samplebankingapp.controller;

import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.model.history.AccountHistoryResponse;
import org.seokkalae.samplebankingapp.service.AccountService;
import org.seokkalae.samplebankingapp.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/account", consumes = "application/json")
public class AccountController {
    private final AccountService accountService;
    private final HistoryService historyService;

    public AccountController(AccountService accountService, HistoryService historyService) {
        this.accountService = accountService;
        this.historyService = historyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountCreateResponse createAccount(
            @RequestBody
            AccountCreateRequest request
    ) {
        return accountService.createAccount(request);
    }

    @GetMapping("{account_id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoResponse getAccountInfo(
            @PathVariable("account_id")
            UUID id
    ) {
        return accountService.getAccountInfo(id);
    }

    @GetMapping("{account_id}/history")
    @ResponseStatus(HttpStatus.OK)
    public AccountHistoryResponse getAccountHistoryInfo(
            @PathVariable("account_id")
            UUID id
    ) {
        return historyService.getAccountHistory(id);
    }
}
