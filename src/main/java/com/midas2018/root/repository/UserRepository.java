package com.midas2018.root.repository;

import com.midas2018.root.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    public void userRegister(User user) {
        userMapper.userRegister(user);
    }

    public int isThereEmail(String email) {
        return userMapper.isThereEmail(email);
    }
}
