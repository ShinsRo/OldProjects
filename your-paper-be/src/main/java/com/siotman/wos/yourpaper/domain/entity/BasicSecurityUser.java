package com.siotman.wos.yourpaper.domain.entity;

import lombok.Data;

@Data
public class BasicSecurityUser extends org.springframework.security.core.userdetails.User {
    public BasicSecurityUser(User user) {
        super(user.getUsername(), user.getPassword(), user.getGrantedAuthorities());
    }
}
