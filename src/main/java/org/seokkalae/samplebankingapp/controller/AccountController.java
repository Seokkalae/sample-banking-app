package org.seokkalae.samplebankingapp.controller;

import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.mapper.AccountMapper;
import org.seokkalae.samplebankingapp.mapper.HistoryMapper;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.service.AccountService;
import org.seokkalae.samplebankingapp.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "account", consumes = "application/json")
public class AccountController {
    private final AccountService accountService;
    private final HistoryService historyService;
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private final HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);

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
        var result = accountService.createAccount(accountMapper.toAccountDto(request));
        return accountMapper.toAccountCreateResponse(result);
    }

    @GetMapping("{account_id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountInfoResponse getAccountInfo(
            @PathVariable("account_id")
            UUID id
    ) {
        var result = accountService.getAccountInfo(id);
        return accountMapper.toAccountInfoResponse(result);
    }

    @GetMapping("{account_id}/history")
    @ResponseStatus(HttpStatus.OK)
    public HistoryResponse getAccountHistoryInfo(
            @PathVariable("account_id")
            UUID id
    ) {
        var result = historyService.getAccountHistory(id);
        return historyMapper.toHistoryResponse(result);
    }
}
