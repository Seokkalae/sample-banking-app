package org.seokkalae.samplebankingapp.service;

import org.seokkalae.samplebankingapp.converter.AccountConverter;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoRequest;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.repository.BankingAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final BankingAccountRepository bankAccountRepo;

    public AccountService(BankingAccountRepository bankAccountRepo) {
        this.bankAccountRepo = bankAccountRepo;
    }

    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest request) {
        log.info("trying to find duplicate account with name: {}", request.fullName());
        boolean accountAlreadyExist = bankAccountRepo
                .findBankAccountEntitiesByAccount_FirstNameAndAccount_LastNameAndAccount_PatronymicAndPin(
                        request.fullName().firstName(),
                        request.fullName().lastName(),
                        request.fullName().patronymic(),
                        request.pin()
                ).isPresent();

        if (accountAlreadyExist)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        BankAccountEntity account = new BankAccountEntity();
        account.setAccount(new AccountEntity());
        account.setPin(request.pin());
        account.getAccount().setFirstName(request.fullName().firstName());
        account.getAccount().setLastName(request.fullName().lastName());
        account.getAccount().setPatronymic(request.fullName().patronymic());

        log.info("save account with name {} {} {}",
                account.getAccount().getFirstName(),
                account.getAccount().getLastName(),
                account.getAccount().getPatronymic()
        );
        AccountCreateResponse accountCreateResponse = AccountConverter
                .fromBankAccountEntityToAccountCreateResponse(bankAccountRepo.save(account));
        log.info("account created with id: {}", account.getId());
        return accountCreateResponse;
    }

    public AccountInfoResponse getAccountInfo(AccountInfoRequest request) {
        log.info("trying to find accounts with id: {}", request.accountId());
        return AccountConverter.fromBankAccountEntityToAccountInfoResponse(
                bankAccountRepo.findAllByAccount_Id(request.accountId())
        );
    }
}
