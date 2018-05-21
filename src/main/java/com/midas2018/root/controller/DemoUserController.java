package com.midas2018.root.controller;

import com.midas2018.root.model.DemoUserVO;
import com.midas2018.root.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoUserController {

    @Autowired
    private DemoService demoService;

    /* CrossOrigin : front-end devServer를 위한 크로스 오리진 허용 설정*/
    @CrossOrigin
    @GetMapping(value = "/findUserById.do")
    public DemoUserVO findUserById(){
        System.out.println("findUserById");
        return demoService.findUserById("test");
    }
}
