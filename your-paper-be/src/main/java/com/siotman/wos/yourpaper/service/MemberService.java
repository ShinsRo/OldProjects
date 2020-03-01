package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.exception.MemberIsAlreadyPresentException;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public MemberDto findById(String username) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(username);

        if (!memberOptional.isPresent()) {
            throw new NoSuchMemberException();
        }
        Member member = memberOptional.get();

        return MemberDto.buildWithMember(member);
    }

    public Boolean availableCheck(String username) {
        return !memberRepository.findById(username).isPresent();
    }

    public MemberDto register(final MemberDto dto) throws MemberIsAlreadyPresentException {
        if (!this.availableCheck(dto.getUsername())) throw new MemberIsAlreadyPresentException();
        Member newMember = Member.builder().build();
        newMember.update(dto);

        final Member savedMember = memberRepository.save(newMember);

        return MemberDto.buildWithMember(savedMember);
    }

    public MemberDto update(final MemberDto dto) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(dto.getUsername());
        if (!memberOptional.isPresent()) {
            throw new NoSuchMemberException();
        }

        Member targetMember = memberOptional.get();
        targetMember.update(dto);

        return MemberDto.buildWithMember(targetMember);
    }
}
