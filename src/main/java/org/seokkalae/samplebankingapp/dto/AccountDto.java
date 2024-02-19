package org.seokkalae.samplebankingapp.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

public record AccountDto(
        UUID id,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthday,
        List<BankAccountDto> listBankAccount
) {
    public String getFullName() {
        StringJoiner joiner = new StringJoiner(" ");
        return joiner.add(firstName).add(lastName).add(patronymic).toString();
    }
}
