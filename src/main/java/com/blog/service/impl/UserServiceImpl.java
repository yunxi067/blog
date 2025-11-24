package com.blog.service.impl;

import com.blog.domain.User;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer uid) {
        return userMapper.selectByUid(uid);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public boolean register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        user.setPassword(md5Password(user.getPassword()));
        user.setCreateTime(new Date());
        user.setRole(2);
        user.setStatus(1);
        
        int result = userMapper.insert(user);
        return result > 0;
    }

    @Override
    public boolean login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return false;
        }
        return MD5Util.verify(password, user.getPassword());
    }

    @Override
    public User getLoginUser(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        if (MD5Util.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getUid() == null) {
            return false;
        }
        user.setUpdateTime(new Date());
        int result = userMapper.update(user);
        return result > 0;
    }

    @Override
    public boolean freezeUser(Integer uid) {
        int result = userMapper.updateStatus(uid, 0);
        return result > 0;
    }

    @Override
    public boolean unfreezeUser(Integer uid) {
        int result = userMapper.updateStatus(uid, 1);
        return result > 0;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> getUsersByRole(Integer role) {
        return userMapper.selectByRole(role);
    }

    @Override
    public boolean isAdminUser(Integer uid) {
        User user = userMapper.selectByUid(uid);
        return user != null && user.getRole() == 1;
    }

    @Override
    public String md5Password(String password) {
        return MD5Util.md5(password);
    }
}
