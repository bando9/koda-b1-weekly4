package com.ewallet.service;

import java.util.List;
import java.util.Scanner;

import com.ewallet.data.DbContext;
import com.ewallet.model.Mutation;
import com.ewallet.model.Transaction;
import com.ewallet.model.TransactionTypeEnum;
import com.ewallet.model.User;
import com.ewallet.model.Wallet;
import com.ewallet.utils.Helper;

public class TransactionServiceImpl implements TransactionService {
    private final DbContext _context;
    private final UserServiceImpl _userServiceImpl;
    private final WalletServiceImpl _walletServiceImpl;

    public TransactionServiceImpl(DbContext context){
        this._context=context;
        this._userServiceImpl=new UserServiceImpl(_context);
        this._walletServiceImpl=new WalletServiceImpl(_context);
    }

    @Override
    public void Transfer(String senderId, String receiverId, long transferAmount, String description) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Transfer'");
    }

    @Override
    public void TopUp(String userId, Scanner input) {
        Helper helper = new Helper();

        System.out.println("Berikan nominal Top Up: ");
        long topUpAmount = input.nextLong();
        input.nextLine();

        System.out.println("Apa tujuan Top Up: ");
        String description = input.nextLine();

        if(topUpAmount <= 0){
            System.out.println("jumlah uang tidak boleh kurang dari 0.");
        }

        Transaction newTransaction = new Transaction();
        _context.getTransactions().add(newTransaction);
        Wallet wallet = _walletServiceImpl.getWalletByUserId(userId);
        
        Mutation mutation = new Mutation(topUpAmount, description, TransactionTypeEnum.Income, wallet.getId(), newTransaction.getId());
        _context.getMutations().add(mutation);

        System.out.println("\n\n-------- TOP UP --------");

        System.out.println("\nWallet id: "+ wallet.getId()+"\nSaldo Awal: "+helper.FormattedToRupiah(wallet.getBalance())+"\n");

        _walletServiceImpl.debitWallet(userId, topUpAmount);
        
        System.out.println("--- Menjalankan Top Up "+helper.FormattedToRupiah(topUpAmount)+" ---");
        try{
            Thread.sleep(2000);
            System.out.println("Top up berhasil! Saldo sekarang: "+helper.FormattedToRupiah(wallet.getBalance())+"\n");
        } catch(Exception e){

        }
    }

    @Override
    public List<Mutation> GetMutationHistory(String walletId, TransactionTypeEnum typeEnum) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GetMutationHistory'");
    }
    
}
