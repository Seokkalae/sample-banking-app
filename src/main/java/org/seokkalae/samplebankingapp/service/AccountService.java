package org.seokkalae.samplebankingapp.service;

import org.seokkalae.samplebankingapp.converter.AccountConverter;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
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
        log.info("trying to find duplicate bankAccount with name: {}", request.fullName());
        boolean accountAlreadyExist = bankAccountRepo
                .findBankAccountEntitiesByAccount_FirstNameAndAccount_LastNameAndAccount_PatronymicAndPin(
                        request.fullName().firstName(),
                        request.fullName().lastName(),
                        request.fullName().patronymic(),
                        request.pin()
                ).isPresent();

        if (accountAlreadyExist)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setAccount(new AccountEntity());
        bankAccount.setPin(request.pin());
        bankAccount.getAccount().setFirstName(request.fullName().firstName());
        bankAccount.getAccount().setLastName(request.fullName().lastName());
        bankAccount.getAccount().setPatronymic(request.fullName().patronymic());

        log.info("save bankAccount with name {} {} {}",
                bankAccount.getAccount().getFirstName(),
                bankAccount.getAccount().getLastName(),
                bankAccount.getAccount().getPatronymic()
        );
        AccountCreateResponse accountCreateResponse = AccountConverter
                .fromBankAccountEntityToAccountCreateResponse(bankAccountRepo.save(bankAccount));
        log.info("account created with id: {}", bankAccount.getAccount().getId());
        return accountCreateResponse;
    }

    public AccountInfoResponse getAccountInfo(UUID id) {
        log.info("trying to find accounts with id: {}", id);
        return AccountConverter.fromBankAccountEntityToAccountInfoResponse(
                bankAccountRepo.findAllByAccount_Id(id)
        );
    }
}
