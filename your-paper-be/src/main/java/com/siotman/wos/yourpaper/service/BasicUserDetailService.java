package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.entity.BasicSecurityUser;
import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasicUserDetailService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> user = memberRepository.findById(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new BasicSecurityUser(user.get());
    }
}
