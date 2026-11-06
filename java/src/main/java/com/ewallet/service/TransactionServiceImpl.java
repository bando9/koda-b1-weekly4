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
    public void Transfer(String senderId, String receiverId, Scanner input) {
        Helper helper = new Helper();

        // TODO: wallet & user from sender & receiver
        User sender = _userServiceImpl.getUserByUserId(senderId);
        User receiver = _userServiceImpl.getUserByUserId(receiverId);
        Wallet senderWallet = _walletServiceImpl.getWalletByUserId(senderId); 
        Wallet receiverWallet = _walletServiceImpl.getWalletByUserId(receiverId); 

        System.out.println("Mau Transfer berapa: ");
        long transferAmount = input.nextLong();

        System.out.println("Tujuan transfer: ");
        String description = input.nextLine();
        input.nextLine();
        
        System.out.println("Konfirmasi (Y/N): ");
        String confirmation = input.nextLine();

        if(!("Y".equalsIgnoreCase(confirmation)))
        {
            System.out.println("Transaksi dibatalkan oleh pengguna.");
            return;
        }

        System.out.println("\n\n-------- TRANSFER --------");
        System.out.println("Nomor Wallet Tujuan: "+ receiverWallet.id+"\nPenerima: "+ receiver.getFullName()+"\nNominal Transfer: "+helper.FormattedToRupiah(transferAmount));
        
        Transaction newTransaction = new Transaction();
        _context.getTransactions().add(newTransaction);

        Mutation senderMutation = new Mutation(transferAmount,description,TransactionTypeEnum.Expense,senderWallet.id,newTransaction.getId()); 
        Mutation receiverMutation = new Mutation(transferAmount,description,TransactionTypeEnum.Income,receiverWallet.id,newTransaction.getId()); 
                
        _context.getMutations().add(senderMutation);
        _context.getMutations().add(receiverMutation);


        System.out.println("Sedang melakukan transaksi...");
        
        try{
            Thread.sleep(2000);

            System.out.println("Transaksi Berhasil!\n");
            
            
            senderWallet.Credit(transferAmount);
            receiverWallet.Debit(transferAmount);
            
            System.out.println("Pengirim: "+ sender.getFullName()+"\nPenerima:"+receiver.getFullName() + "\nNominal: "+helper.FormattedToRupiah(transferAmount)+"\nSisa Saldo:"+helper.FormattedToRupiah(senderWallet.balance));
        }catch(Exception e){}
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
