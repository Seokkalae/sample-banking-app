package org.seokkalae.samplebankingapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.seokkalae.samplebankingapp.dto.AccountDto;
import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.mapper.AccountMapper;
import org.seokkalae.samplebankingapp.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepo;
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    public AccountDto createAccount(AccountDto dto) {
        String firstName = dto.firstName();
        String lastName = dto.lastName();
        String patronymic = dto.patronymic();
        LocalDate birthday = dto.birthday();

        log.info("trying to find duplicate bankAccount with name: {}", dto.getFullName());

        Optional<AccountEntity> existingAccount = accountRepo.findByFirstNameAndLastNameAndPatronymicAndBirthday(
                firstName, lastName, patronymic, birthday
        );

        if (existingAccount.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        var accountEntity = accountMapper.toAccountEntity(dto);

        log.info("save account with name {} {} {}",
                firstName,
                lastName,
                patronymic
        );
        var savedAccount = accountRepo.save(accountEntity);
        log.info("account created with id: {}", savedAccount.getId());

        return accountMapper.toAccountDto(savedAccount);
    }

    public AccountDto getAccountInfo(UUID id) {
        log.info("trying to find accounts with id: {}", id);
        Optional<AccountEntity> account = accountRepo.findById(id);
        if (account.isEmpty())
            throw new EntityNotFoundException();
        return accountMapper.toAccountDto(account.get());
    }
}
