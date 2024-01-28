package org.seokkalae.samplebankingapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "bank_account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "money_funds", nullable = false)
    private BigDecimal moneyFunds;

    @Column(name = "pin", nullable = false)
    private String pin;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getMoneyFunds() {
        return moneyFunds;
    }

    public void setMoneyFunds(BigDecimal moneyFunds) {
        this.moneyFunds = moneyFunds;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity accountId) {
        this.account = accountId;
    }
}
