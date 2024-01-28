package org.seokkalae.samplebankingapp.repository;

import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, UUID> {
}
