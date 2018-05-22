package com.midas2018.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midas2018.root.model.DemoUserVO;
import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.User;
import com.midas2018.root.service.DemoService;
import com.midas2018.root.support.Constant;

@RestController
@CrossOrigin
@RequestMapping(Constant.API_URI)
public class DemoUserController {

    @Autowired
    private DemoService demoService;

    /* CrossOrigin : front-end devServer를 위한 크로스 오리진 허용 설정*/
    @GetMapping(value = "/findUserById.do")
    public DemoUserVO findUserById(){
        System.out.println("findUserById");
        return demoService.findUserById("test");
    }

    @PostMapping(value ="/userRegister.do")
    public String userRegister(String email, String password){
        System.out.println(email + " " + password);
        return email;
    }

    @GetMapping("/user/getUserById")
    public ResultContainer<User> getUserById(@RequestParam long id) {
        return new ResultContainer<>(demoService.getUserById(id));
    }
}
