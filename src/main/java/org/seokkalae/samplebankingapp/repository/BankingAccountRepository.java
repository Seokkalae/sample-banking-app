package org.seokkalae.samplebankingapp.repository;

import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankingAccountRepository extends CrudRepository<BankAccountEntity, UUID> {
}
