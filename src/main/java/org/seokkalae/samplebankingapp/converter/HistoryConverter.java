package org.seokkalae.samplebankingapp.converter;

import org.seokkalae.samplebankingapp.entity.AccountEntity;
import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.seokkalae.samplebankingapp.model.history.AccountHistoryResponse;
import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.util.List;

public class HistoryConverter {
    public static AccountHistoryResponse fromHistoryEntityToAccountHistoryResponse(List<HistoryEntity> source) {
        List<HistoryInfoModel> historyInfos = source
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
        return new AccountHistoryResponse(
                account.getFirstName(),
                account.getLastName(),
                account.getPatronymic(),
                historyInfos
        );
    }
}
