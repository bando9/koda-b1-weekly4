package com.ewallet.service;

public interface WalletService {
    void getBalanceByUserId(String userId);
    void debitWallet(String walletId, long amount);    
    void creaditWallet(String walletId,long amount);    
}