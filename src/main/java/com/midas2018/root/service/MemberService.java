package com.midas2018.root.service;

import com.midas2018.root.model.Member;
import com.midas2018.root.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member findUserByEmail(String email) {
        return memberRepository.findUserByEmail(email);
    }

    public Member memberRegister(Member member) {
        memberRepository.memberRegister(member);
        Member result = memberRepository.findUserByEmail(member.getEmail());
        result.setPassword("");
        return result;
    }

    public Boolean isExitstEmail(String email) {
        return (memberRepository.isExistEmail(email) > 0)? true : false;
    }
}
