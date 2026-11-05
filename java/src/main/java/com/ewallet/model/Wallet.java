package com.ewallet.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    public UUID Id = UUID.randomUUID();
    public String AccountNumber;
    public String PINHash;
    public String UserId;
    public double Balance;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    

    public Wallet(String accountNumber, String pINHash, String userId, double balance) {
        AccountNumber = accountNumber;
        PINHash = pINHash;
        Balance = balance;
        UserId = userId;
    }


    public UUID getId() {
        return Id;
    }



    public void setId(UUID id) {
        Id = id;
    }



    public String getAccountNumber() {
        return AccountNumber;
    }



    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }



    public String getPINHash() {
        return PINHash;
    }



    public void setPINHash(String pINHash) {
        PINHash = pINHash;
    }



    public double getBalance() {
        return Balance;
    }



    public void setBalance(double balance) {
        Balance = balance;
    }



    public String getUserId() {
        return UserId;
    }



    public void setUserId(String userId) {
        UserId = userId;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }



    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    


    
}
