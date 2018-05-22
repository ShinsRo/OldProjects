package com.midas2018.root.repository;

import com.midas2018.root.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @Autowired
    private MemberMapper memberMapper;

    public Member findUserByEmail(String email) {
        return memberMapper.findUserByEmail(email);
    }

    public void memberRegister(Member member) {
        memberMapper.memberRegister(member);
    }

    public int isExistEmail(String email) {
        return memberMapper.isExistEmail(email);
    }
}
