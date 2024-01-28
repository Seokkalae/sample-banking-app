package org.seokkalae.samplebankingapp.entity;

import jakarta.persistence.*;
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
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private OperationType operationType;

    @Column(name = "operation_sum", nullable = false)
    private BigDecimal operationSum;

    @Column(name = "operation_timestamp", nullable = false)
    private OffsetDateTime operationTimestamp;

    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "id")
    private AccountEntity toAccount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
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

    public OffsetDateTime getOperationTimestamp() {
        return operationTimestamp;
    }

    public void setOperationTimestamp(OffsetDateTime operationTimestamp) {
        this.operationTimestamp = operationTimestamp;
    }

    public AccountEntity getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountEntity toAccount) {
        this.toAccount = toAccount;
    }
}
