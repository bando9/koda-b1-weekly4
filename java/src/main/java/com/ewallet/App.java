package com.ewallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.ewallet.data.DbContext;
import  com.ewallet.model.*;
import com.ewallet.service.UserServiceImpl;

public class App 
{
    private static final DbContext _context = new DbContext(); 
    
    public static void main( String[] args )
    {
        try (Scanner input = new Scanner(System.in)) {

            UserServiceImpl userService = new UserServiceImpl(_context);
            boolean isRunningApp = true;
            while(isRunningApp){               
                System.out.println("================= KODA EWALLET ================="); 
                List<User> users = _context.getUsers();
                int i = 0;
                for(User user : users){
                    i++;
                    System.out.println(i+". "+user.getFullName());
                }

                System.out.println("Pilih pengguna: ");
                String userLogin = input.nextLine();
                
                boolean isLogIn = true;
                while(isLogIn){
                    User user = userService.getUserByUserId(userLogin);

                    System.out.println("======== WALLET "+ user.getFullName().toUpperCase() +" =========");

                    List<String> menu = List.of(
                        "1. Lihat Saldo", "2. Top Up", "3. Transfer", "4. Riwayat Transaksi", "5. Log out"
                    );

                    for(String m : menu){
                        System.out.println(m);
                    }

                    System.out.println("Silahkan Pilih menu: ");
                    int chooseMenu = input.nextInt();

                    switch (chooseMenu) {
                        case 1 -> {

                            System.out.println("tes");
                         
                        }
                        case 2 -> {

                            System.out.println("tes");
                         
                        }
                        case 3 -> {

                            System.out.println("tes");
                         
                        }
                        case 4 -> {

                            System.out.println("tes");
                         
                        }
                        case 5 -> {

                            System.out.println("tes");
                         
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
