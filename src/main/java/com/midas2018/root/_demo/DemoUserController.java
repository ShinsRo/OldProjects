package com.midas2018.root._demo;

import com.midas2018.root._demo.domain.DemoUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoUserController {

    @Autowired
    private DemoUserMapper demoUserMapper;

    @GetMapping(value = "/getDemoMemberById")
    public DemoUserVO getMemberById(){
        System.out.println("getMemberById");
        return demoUserMapper.findUserById("test");
    }

    @GetMapping(value = "/getUserList")
    public List<DemoUserVO> getUserList(){
        return demoUserMapper.getUserList();
    }


}
