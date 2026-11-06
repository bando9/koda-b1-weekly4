package com.ewallet.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Mutation {
    private UUID id = UUID.randomUUID();
    private long amount;
    private String description;
    private TransactionTypeEnum transactionType;
    private String walletId;
    private UUID transactionId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public Mutation(long amount,String description, TransactionTypeEnum transactionType, String walletId, UUID transactionId){
        this.amount = amount;
        this.description=description;
        this.transactionType = transactionType;
        this.walletId = walletId;
        this.transactionId = transactionId;
    }
    

}
