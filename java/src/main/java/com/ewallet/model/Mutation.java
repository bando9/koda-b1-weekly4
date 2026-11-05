package com.ewallet.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Mutation {
    private UUID id = UUID.randomUUID();
    private double amount;
    private String description;
    private TransactionTypeEnum transactionType;
    private String walletId;
    private String transactionId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public Mutation(double amount,String description, TransactionTypeEnum transactionType, String walletId, String transactionId){
        this.amount = amount;
        this.description=description;
        this.transactionType = transactionType;
        this.walletId = walletId;
        this.transactionId = transactionId;
    }
    

}
