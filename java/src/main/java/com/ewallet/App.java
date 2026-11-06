package com.ewallet;

import java.util.List;
import java.util.Scanner;

import com.ewallet.data.DbContext;
import  com.ewallet.model.*;
import com.ewallet.service.TransactionServiceImpl;
import com.ewallet.service.UserServiceImpl;
import com.ewallet.service.WalletServiceImpl;

public class App 
{
    private static final DbContext _context = new DbContext(); 
    
    public static void main( String[] args )
    {
        try (Scanner input = new Scanner(System.in)) {

            UserServiceImpl userService = new UserServiceImpl(_context);
            WalletServiceImpl walletService = new WalletServiceImpl(_context);
            TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl(_context);
            
            boolean isRunningApp = true;
            while(isRunningApp){               
                System.out.println("================= KODA EWALLET ================="); 
                List<User> users = _context.getUsers();
                int i = 0;
                for(User user : users){
                    i++;
                    System.out.println(i+". "+user.getFullName());
                }
                int maxUser = _context.getUsers().size();
                System.out.println(maxUser+1+". Exit");

                System.out.println("Pilih pengguna: ");
                String userLogin = input.nextLine();

                if(userLogin.equals(String.valueOf(maxUser+1))){
                    System.out.println("Keluar...");
                    isRunningApp=false;
                    return;
                }

                if(!userLogin.matches("[1-"+maxUser+"]")){
                    System.out.println("Pilihan tidak valid. coba lagi...");
                    continue;
                }

                List<User> usersToTransfer = users.stream().filter(u-> !userLogin.equals(u.getId())).toList();

                
                boolean isLogIn = true;
                while(isLogIn){
                    User user = userService.getUserByUserId(userLogin);
                    
                    if(user == null){
                        System.out.println("user tidak ditemukan");
                        return;
                    }

                    System.out.println("======== WALLET "+ user.getFullName().toUpperCase() +" =========");

                    List<String> menu = List.of(
                        "1. Lihat Saldo", "2. Top Up", "3. Transfer", "4. Riwayat Transaksi", "5. Log out"
                    );

                    for(String m : menu){
                        System.out.println(m);
                    }

                    System.out.println("Silahkan Pilih menu: ");
                    int chooseMenu = input.nextInt();
                    input.nextLine();

                    switch (chooseMenu) {
                        case 1 -> {
                            walletService.getBalanceByUserId(userLogin);
                        }
                        case 2 -> {
                            transactionServiceImpl.TopUp(userLogin, input);
                        }
                        case 3 -> {
                            System.out.println();

                            for(User u : usersToTransfer){
                                System.out.println(u.getId()+". "+u.getFullName());
                            }
                            System.out.println("Mau kirim ke siapa(id): ");
                            String receiverId = input.nextLine(); 
                            transactionServiceImpl.Transfer(userLogin, receiverId, input);
                            
                            System.out.println("transfer");
                         
                        }
                        case 4 -> {

                            System.out.println("mutasi");
                         
                        }
                        case 5 -> {

                            System.out.println("Logout");
                            isLogIn=false;
                         
                        }

                        default -> {

                            System.out.println("Pilih menu yg bener bos (1-5).");
                         
                        }
                    }

                    


                }

            }
        }
    }

    // static Input
}
