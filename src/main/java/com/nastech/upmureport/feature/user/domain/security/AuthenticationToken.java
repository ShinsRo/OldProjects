package com.nastech.upmureport.feature.user.domain.security;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationToken {
	private String username;
	private Collection authorities;
	private String token;
	
	public AuthenticationToken(String username,Collection authorities,String token) {
		this.username=username;
		this.authorities = authorities;
		this.token = token;	
	}
	
	
}
