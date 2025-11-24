package com.blog.mapper;

import com.blog.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User selectByUid(Integer uid);
    
    User selectByUsername(String username);
    
    User selectByEmail(String email);
    
    int insert(User user);
    
    int update(User user);
    
    int updateStatus(@Param("uid") Integer uid, @Param("status") Integer status);
    
    List<User> selectAll();
    
    List<User> selectByRole(Integer role);
}
