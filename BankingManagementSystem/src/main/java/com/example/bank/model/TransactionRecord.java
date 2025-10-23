package com.example.bank.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class TransactionRecord {
    private long txnId;
    private long accountId;
    private String txnType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String relatedAccount;
    private String description;
    private LocalDateTime txnTime;
    public TransactionRecord() {}
    // getters and setters
    public long getTxnId() { return txnId; }
    public void setTxnId(long txnId) { this.txnId = txnId; }
    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }
    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter =
            balanceAfter; }
    public String getRelatedAccount() { return relatedAccount; }
    public void setRelatedAccount(String relatedAccount) { this.relatedAccount
            = relatedAccount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description =
            description; }
    public LocalDateTime getTxnTime() { return txnTime; }
    public void setTxnTime(LocalDateTime txnTime) { this.txnTime = txnTime; }
}