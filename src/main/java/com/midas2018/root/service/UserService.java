package com.midas2018.root.service;

import java.util.List;

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
        if (userVO != null) {
            throw new UserAlreadyExistsException();
        }
        user.setStatus(UserStatus.USER);
        userRepository.signup(user);
        return user;
    }

    public UserVO userSignin(UserVO user) {
        UserVO userVO = userRepository.signin(user.getEmail(), user.getPassword());
        return userVO;
    }

    public UserStatus getUserStatusByUserId(String userAuth) {
        return userRepository.selectUserStatusByUserId(Integer.valueOf(userAuth));
    }

    public List<UserVO> getUserListAll() {
        return userRepository.selectUserListAll();
    }

    public void updateUser(UserVO user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(UserVO user) {
        user.setStatus(UserStatus.DELETE);
        userRepository.deleteUser(user);
    }
}
