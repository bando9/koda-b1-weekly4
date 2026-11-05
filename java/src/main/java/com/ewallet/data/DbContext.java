package com.ewallet.data;

import java.util.ArrayList;
import java.util.List;

import com.ewallet.model.Mutation;
import com.ewallet.model.Transaction;
import com.ewallet.model.User;
import com.ewallet.model.Wallet;

public class DbContext {
    private List<Mutation> mutations = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Wallet> wallets = new ArrayList<>(List.of(
            new Wallet( "88990001", "hash_pin_1", "1", 1500000),
            new Wallet("88990002", "hash_pin_2", "2", 2500000),
            new Wallet( "88990003", "hash_pin_3", "3", 500000),
            new Wallet( "88990004", "hash_pin_4", "4", 3000000),
            new Wallet( "88990005", "hash_pin_5", "5", 100000)
    ));
    private final List<User> users = new ArrayList<>(List.of(
        new User("1","bandomega", "password_hash", "bandomega@gmail.com", "url_link_photo", "Bando Mega", "89530950881"),
        new User("2","keysa", "hash_pw_2", "keysa@email.com", "url_photo_2", "Keysa Maharani", "081200000002"),
        new User("3","dika", "hash_pw_3", "dika@email.com", "url_photo_3", "Dika Pratama", "081200000003"),
        new User("4","nayati", "hash_pw_4", "nayati@email.com", "url_photo_4", "Nayati Ayu", "081200000004"),
        new User("5","dany", "hash_pw_5", "dany@email.com", "url_photo_5", "Dany Setiawan", "081200000005")
    ));


    public List<Mutation> getMutations(){return mutations;}
    public List<Transaction> getTransactions(){return transactions;}
    public List<User> getUsers(){return users;}
    public List<Wallet> getWallets(){return wallets;}

}
