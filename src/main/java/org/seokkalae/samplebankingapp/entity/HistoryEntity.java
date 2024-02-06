package org.seokkalae.samplebankingapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.seokkalae.samplebankingapp.enums.OperationType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccountEntity bankAccount;

    @Column(name = "operation_type", nullable = false, columnDefinition = "operation_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Column(name = "operation_sum", nullable = false)
    private BigDecimal operationSum;

    @Column(name = "operation_timestamp", insertable = false)
    private OffsetDateTime operationTimestampTZ;

    @ManyToOne
    @JoinColumn(name = "to_bank_account_id", referencedColumnName = "id")
    private BankAccountEntity toBankAccount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity account) {
        this.bankAccount = account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(BigDecimal operationSum) {
        this.operationSum = operationSum;
    }

    public OffsetDateTime getOperationTimestampTZ() {
        return operationTimestampTZ;
    }

    public void setOperationTimestampTZ(OffsetDateTime operationTimestamp) {
        this.operationTimestampTZ = operationTimestamp;
    }

    public BankAccountEntity getToBankAccount() {
        return toBankAccount;
    }

    public void setToBankAccount(BankAccountEntity toAccount) {
        this.toBankAccount = toAccount;
    }
}
