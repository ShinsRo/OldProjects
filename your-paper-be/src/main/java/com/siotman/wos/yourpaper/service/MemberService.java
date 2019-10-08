package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<Member> foundMember = memberRepository.findById(username);

        if (!foundMember.isPresent()) {
            throw new NoSuchMemberException();
        }

        return MemberDto.builder()
                .member(foundMember.get())
                .build();
    }

    public Boolean availableCheck(String username) {
        return memberRepository.findById(username).isPresent();
    }

    public MemberDto register(final MemberDto dto) {
        final Member newMember = Member.builder()
                .encoder(passwordEncoder)
                .dto(dto)
                .build();

        final Member savedMember = memberRepository.save(newMember);

        return MemberDto.builder()
                .member(savedMember)
                .build();
    }

    public MemberDto update(final MemberDto dto) throws NoSuchMemberException {
        Optional<Member> foundMember = memberRepository.findById(dto.getUsername());

        if (!foundMember.isPresent()) {
            throw new NoSuchMemberException();
        }

        Member targetMember = foundMember.get();
        targetMember.updateMember(passwordEncoder, dto);

        return MemberDto.builder()
                .member(targetMember).build();
    }
}
