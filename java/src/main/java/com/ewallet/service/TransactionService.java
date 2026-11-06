package com.ewallet.service;

import java.util.List;
import java.util.Scanner;

import com.ewallet.model.Mutation;
import com.ewallet.model.TransactionTypeEnum;

public interface TransactionService{
    void Transfer(String senderId, String receiverId, Scanner input);
    void TopUp(String userId, Scanner input);
    List<Mutation> GetMutationHistory(String walletId, TransactionTypeEnum typeEnum);

}