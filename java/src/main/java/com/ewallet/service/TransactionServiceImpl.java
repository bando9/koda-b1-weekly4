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

        User sender = _userServiceImpl.getUserByUserId(senderId);
        User receiver = _userServiceImpl.getUserByUserId(receiverId);
        Wallet senderWallet = _walletServiceImpl.getWalletByUserId(senderId); 
        Wallet receiverWallet = _walletServiceImpl.getWalletByUserId(receiverId); 

        System.out.println("Mau Transfer berapa: ");
        long transferAmount = input.nextLong();
        input.nextLine();

        if(transferAmount > senderWallet.balance){
            System.out.println("Uangmu dikit, gausah transfer segitu\n");
            return;
        }

        if(transferAmount <= 0){
            System.out.println("Gabisa ngirim kurang dari 0 cuy.");
        }

        if(sender == null || receiver == null){
            System.out.println("Penerima tidak ditemukan\n");
            return ;
        }

        if(sender == receiver){
            System.out.println("Percuma dong, manabisa transfer diri sendiri dari akun yg sama\n");
            return;
        }

        



        System.out.println("Tujuan transfer: ");
        String description = input.nextLine();
        
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

        if(topUpAmount <= 0){
            System.out.println("Transaksi gagal. `Masak mau topup minus bang?`");
            return;
        }

        System.out.println("Apa tujuan Top Up: ");
        String description = input.nextLine();

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
        return _context.getMutations().stream()
                .filter(m->walletId.equals(m.getWalletId()))
                .filter(m-> typeEnum == null || m.getTransactionType() == typeEnum)
                .toList();
    }

    public long GetTotalIncomeMutation(String walletId){
        List<Mutation> mutations = GetMutationHistory(walletId, TransactionTypeEnum.Income);
        long result = 0;
        for(Mutation m:mutations){
            result+=m.getAmount();
        }
        return result;
    }

    public long GetTotalExpenseMutation(String walletId){
        List<Mutation> mutations = GetMutationHistory(walletId, TransactionTypeEnum.Expense);
        long result = 0;
        for(Mutation m:mutations){
            result+=m.getAmount();
        }
        return result;
    }

    public long GetTotalAllMutation(String walletId){
        List<Mutation> mutations = GetMutationHistory(walletId, null);
        long result = 0;
        for(Mutation m:mutations){
            result+=m.getAmount();
        }
        return result;
    }

    public void GetMutationHistoryFilter(String userLogin, Scanner input){
        Helper helper =  new Helper();
        Wallet walletUserLogin = _walletServiceImpl.getWalletByUserId(userLogin);
        if(GetMutationHistory(walletUserLogin.id, null).size() <=0){
            System.out.println("\nKamu blm ada transaksi apapun.\n");
            return;
        }
                            System.out.println();
                            List<String> filterOption = List.of("1. Income", "2. Expense", "3. All");
                            
                            for(String f:filterOption){
                                System.out.println(f);
                            }
                            
                            System.out.println("Mau lihat mutasi berdasarkan apa: ");
                            int filterMutation = input.nextInt();
                            
                            System.out.println("\n\n-------- MUTASI --------");

                            switch (filterMutation) {
                                case 1->{
                                    List<Mutation> mutations =GetMutationHistory(walletUserLogin.id, TransactionTypeEnum.Income);
                                    int j =0;
                                    for(Mutation m : mutations){
                                        j++;
                                        System.out.println(j+". "+m.getDescription()+" - ("+m.getTransactionType()+") = "+helper.FormattedToRupiah(m.getAmount()) +" "+ helper.formatDate(m.getCreatedAt()));
                                    }
                                    System.out.println("\nTotal Pemasukan: "+helper.FormattedToRupiah(GetTotalIncomeMutation(walletUserLogin.id)));
                                    System.out.println();
                                }
                                case 2->{
                                    List<Mutation> mutations = GetMutationHistory(walletUserLogin.id, TransactionTypeEnum.Expense);
                                    
                                    int i=0;
                                    for(Mutation m : mutations){
                                        i++;
                                        System.out.println(i+". "+m.getDescription()+" - ("+m.getTransactionType()+") = "+helper.FormattedToRupiah(m.getAmount()) +" "+ helper.formatDate(m.getCreatedAt()));
                                    }
                                    System.out.println("\nTotal Pengeluaran: "+helper.FormattedToRupiah(GetTotalExpenseMutation(walletUserLogin.id)));
                                    System.out.println();
                                }
                                case 3->{
                                    List<Mutation> mutations = GetMutationHistory(walletUserLogin.id, null);
                                    int k=0;
                                    for(Mutation m : mutations){
                                        k++;
                                        System.out.println(k+". "+m.getDescription()+" - ("+m.getTransactionType()+") = "+helper.FormattedToRupiah(m.getAmount()) +" "+ helper.formatDate(m.getCreatedAt()));
                                    }

                                    System.out.println("\nTotal Pemasukan: "+helper.FormattedToRupiah(GetTotalIncomeMutation(walletUserLogin.id)));

                                    System.out.println("Total Pengeluaran: "+ helper.FormattedToRupiah(GetTotalExpenseMutation(walletUserLogin.id)));

                                    System.out.println("Total Transaksi: "+helper.FormattedToRupiah(GetTotalAllMutation(walletUserLogin.id)));
                                    System.out.println();
                                }
                                default->{
                                    System.out.println("ga ada dipilihan. Mau apa ngab?");
                                }
                                    
                            }
    }


    
}
