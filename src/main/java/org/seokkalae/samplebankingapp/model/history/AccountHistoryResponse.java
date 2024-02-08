package org.seokkalae.samplebankingapp.model.history;

import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.util.List;

public record AccountHistoryResponse(
        String firstName,
        String lastName,
        String patronymic,
        List<HistoryInfoModel> historyInfo) {
}
