package com.midas2018.root.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = {"/signup", "/insertUser"})
    public ResultContainer<UserVO> userSignup(@RequestBody UserVO user) throws UserAlreadyExistsException {
        return new ResultContainer<>(
                userService.signup(user)
        );
    }

    @PostMapping("/updateUser")
    public ResultContainer<Void> updateUser(@RequestBody UserVO editedUser) {
        userService.updateUser(editedUser);
        return new ResultContainer();
    }

    @PostMapping("/deleteUser")
    public ResultContainer<Void> deleteUser(@RequestBody UserVO user) {
        userService.deleteUser(user);
        return new ResultContainer();
    }

    @PostMapping("/signin")
    public ResultContainer<UserVO> userSignin(@RequestBody UserVO user) {
        return new ResultContainer<>(
                userService.userSignin(user)
        );
    }

    @GetMapping("/getUserListAll")
    public ResultContainer<List<UserVO>> getUserListAll() {
        return new ResultContainer<>(userService.getUserListAll());
    }


}
