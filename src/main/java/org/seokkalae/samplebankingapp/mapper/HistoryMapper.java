package org.seokkalae.samplebankingapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seokkalae.samplebankingapp.dto.HistoryDto;
import org.seokkalae.samplebankingapp.entity.BankAccountEntity;
import org.seokkalae.samplebankingapp.entity.HistoryEntity;
import org.seokkalae.samplebankingapp.enums.OperationType;
import org.seokkalae.samplebankingapp.model.history.HistoryResponse;
import org.seokkalae.samplebankingapp.model.history.submodel.HistoryInfoModel;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = BankAccountMapper.class)
public interface HistoryMapper {
    HistoryEntity toHistoryEntity(HistoryDto dto);

    List<HistoryEntity> toHistoryEntity(List<HistoryDto> dtos);

    @Mapping(target = "firstName", source = "entity.bankAccount.account.firstName")
    @Mapping(target = "lastName", source = "entity.bankAccount.account.lastName")
    @Mapping(target = "patronymic", source = "entity.bankAccount.account.patronymic")
    HistoryDto toHistoryDto(HistoryEntity entity);

    List<HistoryDto> toHistoryDto(List<HistoryEntity> entity);

    @Mapping(target = "bankAccount", source = "entity")
    @Mapping(target = "toBankAccount", source = "toEntity")
    @Mapping(target = "firstName", source = "entity.account.firstName")
    @Mapping(target = "lastName", source = "entity.account.lastName")
    @Mapping(target = "patronymic", source = "entity.account.patronymic")
    @Mapping(target = "id", ignore = true)
    HistoryDto toHistoryDto(BankAccountEntity entity, OperationType operationType, BankAccountEntity toEntity, BigDecimal operationSum);

    default HistoryResponse toHistoryResponse(List<HistoryDto> dtos) {
        if (dtos == null || dtos.isEmpty())
            return null;

        String firstName = dtos.getFirst().firstName();
        String lastName = dtos.getFirst().lastName();
        String patronymic = dtos.getFirst().patronymic();
        var historyInfo = mapHistoryInfo(dtos);
        return new HistoryResponse(firstName, lastName, patronymic, historyInfo);
    }

    default List<HistoryInfoModel> mapHistoryInfo(List<HistoryDto> dtos) {
        return dtos.stream()
                .map(dto -> new HistoryInfoModel(
                        dto.id(),
                        dto.operationType(),
                        dto.operationSum(),
                        dto.operationTimestampTZ(),
                        dto.toBankAccount() == null ? null : dto.toBankAccount().id()
                ))
                .toList();
    }

}
