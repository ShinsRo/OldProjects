package com.midas2018.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midas2018.root.exceptions.UserAlreadyExistsException;
import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.User;
import com.midas2018.root.model.UserVO;
import com.midas2018.root.service.UserService;
import com.midas2018.root.support.Constant;

@CrossOrigin
@RestController
@RequestMapping(Constant.USER_API_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/findUserByEmail")
    public ResultContainer<User> findUserByEmail(@RequestParam String email) {
        return new ResultContainer<>(
                userService.findUserByEmail(email)
        );
    }

    @PostMapping("/signup")
    public ResultContainer<UserVO> userRegister(UserVO user) throws UserAlreadyExistsException {
        return new ResultContainer<>(
                userService.signup(user)
        );
    }

    @PostMapping("/signin")
    public ResultContainer<UserVO> userSignin(UserVO user) {
        return new ResultContainer<>(
                userService.userSignin(user)
        );
    }


}
