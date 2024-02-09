package org.seokkalae.samplebankingapp.converter;

import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.util.List;

public class HistoryConverter {
    public static HistoryResponse fromHistoryEntityListToHistoryResponse(List<HistoryEntity> source) {
        var historyInfos = source
                .stream()
                .map(historyInfo -> new HistoryInfoModel(
                        historyInfo.getId(),
                        historyInfo.getOperationType(),
                        historyInfo.getOperationSum(),
                        historyInfo.getOperationTimestampTZ(),
                        historyInfo.getToBankAccount() == null
                                ? null
                                : historyInfo.getToBankAccount().getId()
                ))
                .toList();
        AccountEntity account = source.getFirst().getBankAccount().getAccount();
        return new HistoryResponse(
                account.getFirstName(),
                account.getLastName(),
                account.getPatronymic(),
                historyInfos
        );
    }
}
