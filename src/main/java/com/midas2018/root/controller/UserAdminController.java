package com.midas2018.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.UserVO;
import com.midas2018.root.service.UserService;
import com.midas2018.root.support.Constant;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Constant.ADMIN_URL)
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResultContainer<UserVO> adminSignup(@RequestBody UserVO user) {
        return new ResultContainer<>(userService.userSignin(user));
    }

    @GetMapping("/test")
    public ResultContainer<String> adminSignup() {
        return new ResultContainer<>("test");
    }

    @GetMapping("/getUserList")
    public ResultContainer<List<UserVO>> adminGetUserList(@RequestParam int pageNo) {
        System.out.println("asd" + userService.getUserList(pageNo));
        return new ResultContainer<>(null);
    }

}
