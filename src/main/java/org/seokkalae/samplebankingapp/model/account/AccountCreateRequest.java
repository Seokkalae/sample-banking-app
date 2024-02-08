package org.seokkalae.samplebankingapp.model.account;

import java.time.LocalDate;
import java.util.StringJoiner;

public record AccountCreateRequest(
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthday,
        String pin)
{
    public String getFullName() {
        StringJoiner joiner  = new StringJoiner(" ");
        return joiner.add(firstName).add(lastName).add(patronymic).toString();
    }
}
