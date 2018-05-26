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

    public User userRegister(User user) throws UserAlreadyExistsException {
        boolean isThereSameOne = this.isThereEmail(user.getEmail());
        if(isThereSameOne) throw new UserAlreadyExistsException();
        userRepository.userRegister(user);
        user.setPassword("");
        return user;
    }

    public Boolean isThereEmail(String email) {
        return (userRepository.isThereEmail(email) > 0)? true : false;
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
