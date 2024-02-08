package org.seokkalae.samplebankingapp.model.history.submodel;

import org.seokkalae.samplebankingapp.enums.OperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record HistoryInfoModel(UUID historyId,
                               OperationType operationType,
                               BigDecimal operationSum,
                               OffsetDateTime operationTimestampTZ,
                               UUID toAccountId) {
}
