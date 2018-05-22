package com.midas2018.root.controller;

import com.midas2018.root.model.DemoUserVO;
import com.midas2018.root.model.User;
import com.midas2018.root.service.DemoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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

    @RequestMapping(value="/getUserById", method=RequestMethod.GET)
    public User getUserById(@RequestParam long id) {
        return demoService.getUserById(id);
    }


}
