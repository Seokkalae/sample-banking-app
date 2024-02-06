package org.seokkalae.samplebankingapp.repository;

import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankingAccountRepository extends CrudRepository<BankAccountEntity, UUID> {

    Optional<BankAccountEntity>
    findBankAccountEntitiesByAccount_FirstNameAndAccount_LastNameAndAccount_PatronymicAndPin(String firstName,
                                                                                       String lastName,
                                                                                       String patronymic,
                                                                                             String pin);

    Optional<BankAccountEntity> findBankAccountEntitiesByIdAndPin(UUID id, String pin);

    List<BankAccountEntity> findAllByAccount_Id(UUID id);
}
