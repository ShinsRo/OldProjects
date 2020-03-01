package com.siotman.wos.yourpaper.domain.entity;

import lombok.Getter;

@Getter
public class BasicSecurityUser extends org.springframework.security.core.userdetails.User {
    public BasicSecurityUser(Member member) {
        super(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());
    }
}
