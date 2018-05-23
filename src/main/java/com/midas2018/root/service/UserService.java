package com.midas2018.root.service;

import com.midas2018.root.model.User;
import com.midas2018.root.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User userRegister(User user) {
        userRepository.userRegister(user);
        User result = userRepository.findUserByEmail(user.getEmail());
        result.setPassword("");
        return result;
    }

    public Boolean isThereEmail(String email) {
        return (userRepository.isThereEmail(email) > 0)? true : false;
    }

    public Map<String, Object> userSignin(User user) {
        Map<String, Object> sessionAndValidity = new HashMap<>();
        boolean isValid = false;
        User foundUser = null;
        if (isThereEmail(user.getEmail())) {
            foundUser = userRepository.findUserByEmail(user.getEmail());
            if (foundUser != null)
                isValid = foundUser.getPassword().equals(user.getPassword());
            foundUser.setPassword("");
            sessionAndValidity.put("user", foundUser);
        }

        sessionAndValidity.put("isValid", isValid);
        return sessionAndValidity;
    }
}
