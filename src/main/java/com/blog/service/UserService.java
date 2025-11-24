package com.blog.service;

import com.blog.domain.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer uid);
    
    User getUserByUsername(String username);
    
    User getUserByEmail(String email);
    
    boolean register(User user);
    
    boolean login(String username, String password);
    
    User getLoginUser(String username, String password);
    
    boolean updateUser(User user);
    
    boolean freezeUser(Integer uid);
    
    boolean unfreezeUser(Integer uid);
    
    List<User> getAllUsers();
    
    List<User> getUsersByRole(Integer role);
    
    boolean isAdminUser(Integer uid);
    
    String md5Password(String password);
}
