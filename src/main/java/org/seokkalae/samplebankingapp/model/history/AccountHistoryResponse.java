package org.seokkalae.samplebankingapp.model.history;

import org.seokkalae.samplebankingapp.model.account.submodel.AccountFullNameModel;
import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.util.List;

public record AccountHistoryResponse(AccountFullNameModel fullName, List<HistoryInfoModel> historyInfo) {
}
