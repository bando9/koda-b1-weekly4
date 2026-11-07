package com.ewallet.service;

import com.ewallet.data.DbContext;
import com.ewallet.model.User;
import com.ewallet.model.Wallet;
import com.ewallet.utils.Helper;

public class WalletServiceImpl implements WalletService{
    private DbContext _context = new DbContext();
    private final UserServiceImpl _userService;

    public WalletServiceImpl(DbContext context){
        this._context=context;
        this._userService= new UserServiceImpl(_context);
    }

    @Override 
    public void getBalanceByUserId(String userId) {
       Helper helper = new Helper();

       Wallet wallet = getWalletByUserId(userId);
       User user = _userService.getUserByUserId((userId));

       System.out.println("\n\n-------- LIHAT SALDO --------");

       System.out.println("\nNomor Wallet: "+wallet.id);
       System.out.println("Nama Pemilik: "+user.getFullName());
       System.out.println("Saldo: "+helper.FormattedToRupiah(wallet.getBalance())+"\n");
    }

    public Wallet getWalletByUserId(String userId){
        Wallet wallet = _context.getWallets().stream().filter(w-> userId.equals(w.getUserId())).findFirst().orElse(null);
        return  wallet;
    }

    @Override
    public void debitWallet(String userId, long amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.Debit(amount);
    }

    @Override
    public void creaditWallet(String userId, long amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.Credit(amount);
    }




}