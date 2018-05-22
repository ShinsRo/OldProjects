package com.midas2018.root.controller;

import com.midas2018.root.model.Member;
import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.service.MemberService;
import com.midas2018.root.support.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constant.API_URI)
public class MemberController {

    @Autowired
    private MemberService memberService;

    @CrossOrigin
    @GetMapping(value = "/member/findMemberByEmail")
    public ResultContainer<Member> findUserByEmail(@RequestParam String email) {
        return new ResultContainer<>(
                memberService.findUserByEmail(email)
        );
    }

    @CrossOrigin
    @GetMapping(value = "/member/isExistEmail")
    public ResultContainer<Boolean> isExistEmail(@RequestParam String email) {
        return new ResultContainer<>(
                memberService.isExitstEmail(email)
        );
    }

    @CrossOrigin
    @PostMapping(value ="/member/userRegister")
    public ResultContainer<Member> memberRegister(Member member
            ){
        return new ResultContainer<>(
                memberService.memberRegister(member)
        );
    }


}
