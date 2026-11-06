package com.ewallet.model;

import java.time.LocalDateTime;

public class Wallet {
    public String id;
    public String accountNumber;
    public String pinHash;
    public String userId;
    public long balance;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    

    public Wallet(String id,String accountNumber, String pINHash, String userId, long balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.pinHash = pINHash;
        this.balance = balance;
        this.userId = userId;
    }


    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pINHash) {
        pinHash = pINHash;
    }

    public long getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
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

    public long Debit(long amount){
        return this.balance+=amount;
    }
    public long Credit(long amount){
        return balance-=amount;
    }
    


    
}
