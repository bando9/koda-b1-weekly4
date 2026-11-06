package com.ewallet.service;

import java.util.List;

import com.ewallet.data.DbContext;
import com.ewallet.model.User;

public class UserServiceImpl {
    private DbContext _context = new DbContext(); 

    public UserServiceImpl(DbContext context){
        this._context=context;
    }
    
    public void createUser(String id, String username, String passwordHash, String email, String photo, String fullName, String phone){
        User newUser = new User(id, username, passwordHash, email, photo, fullName, phone);
        
        _context.getUsers().add(newUser);

        System.out.println("Berhasil membuat akun: "+ newUser.getUsername());
    }

    public User getUserByUserId(String userId){
        return _context.getUsers().stream().filter(u -> userId.equals(u.getId())).findFirst().orElse(null);
    }

    public List<User> getAllUsers(){
        return _context.getUsers();
    }
}
