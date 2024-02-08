package org.seokkalae.samplebankingapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.seokkalae.samplebankingapp.converter.AccountConverter;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.model.account.AccountCreateRequest;
import org.seokkalae.samplebankingapp.model.account.AccountCreateResponse;
import org.seokkalae.samplebankingapp.model.account.AccountInfoResponse;
import org.seokkalae.samplebankingapp.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate now = LocalDate.now();

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    public AccountCreateResponse createAccount(AccountCreateRequest request) {
        String firstName = request.fullName().firstName();
        String lastName = request.fullName().lastName();
        String patronymic = request.fullName().patronymic();
        LocalDate birthday = request.birthday();

        log.info("trying to find duplicate bankAccount with name: {}", request.fullName());

        Optional<AccountEntity> existingAccount = accountRepo.findByFirstNameAndLastNameAndPatronymicAndBirthday(
                firstName, lastName, patronymic, birthday
        );

        if (existingAccount.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        var accountEntity = new AccountEntity();
        accountEntity.setFirstName(firstName);
        accountEntity.setLastName(lastName);
        accountEntity.setPatronymic(patronymic);
        accountEntity.setBirthday(birthday);

        var bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setAccount(accountEntity);
        bankAccountEntity.setPin(request.pin());

        if (accountEntity.getListBankAccount() == null)
            accountEntity.setListBankAccount(new LinkedList<>());
        accountEntity.getListBankAccount().add(bankAccountEntity);

        log.info("save account with name {} {} {}",
                firstName,
                lastName,
                patronymic
        );
        var savedAccount = accountRepo.save(accountEntity);
        log.info("account created with id: {}", savedAccount.getId());

        return new AccountCreateResponse(savedAccount.getId(), savedAccount.getId());
    }

    public AccountInfoResponse getAccountInfo(UUID id) {
        log.info("trying to find accounts with id: {}", id);
        Optional<AccountEntity> account = accountRepo.findById(id);
        if (account.isEmpty())
            throw new EntityNotFoundException();
        return AccountConverter.fromAccountEntityToAccountInfoResponse(account.get());
    }
}
