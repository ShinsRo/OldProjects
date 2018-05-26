package com.midas2018.root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.midas2018.root.exceptions.UserAlreadyExistsException;
import com.midas2018.root.model.User;
import com.midas2018.root.model.UserStatus;
import com.midas2018.root.model.UserVO;
import com.midas2018.root.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public UserVO signup(UserVO user) throws UserAlreadyExistsException {
        UserVO userVO = userRepository.selectUserByEmail(user.getEmail());
        if (userVO == null) {
            throw new UserAlreadyExistsException();
        }
        userRepository.signup(user);
        return user;
    }
    public UserVO userSignin(UserVO user) {
        return userRepository.signin(user.getEmail(), user.getPassword());
//        Map<String, Object> sessionAndValidity = new HashMap<>();
//        boolean isValid = false;
//        User foundUser = null;
//        if (isThereEmail(user.getEmail())) {
//            foundUser = userRepository.findUserByEmail(user.getEmail());
//            if (foundUser != null)
//                isValid = foundUser.getPassword().equals(user.getPassword());
//            foundUser.setPassword("");
//            sessionAndValidity.put("user", foundUser);
//        }
//
//        sessionAndValidity.put("isValid", isValid);
//        return sessionAndValidity;
    }

    public UserStatus getUserStatusByUserId(String userAuth) {
        return userRepository.selectUserStatusByUserId(Integer.valueOf(userAuth));
    }
}
