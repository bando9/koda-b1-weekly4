package com.ewallet.model;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Transaction {
    private UUID id = UUID.randomUUID();
    private String referenceId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public UUID getId(){
        return this.id;
    }    

    public Transaction() {
        Random rand = new Random();
        int randomNum = rand.nextInt(9000)+1000;
        
        this.referenceId = "REF-"+randomNum;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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
