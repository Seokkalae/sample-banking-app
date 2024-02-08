package org.seokkalae.samplebankingapp.repository;

import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByFirstNameAndLastNameAndPatronymicAndBirthday(
            String firstName,
            String lastName,
            String patronymic,
            LocalDate birthday
    );
}
