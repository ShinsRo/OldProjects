package com.nastech.upmureport.feature.user.domain.dto;

import java.math.BigInteger;

import com.nastech.upmureport.feature.user.domain.entity.Member;
import com.nastech.upmureport.feature.user.domain.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthInfoDto {
	private BigInteger aid;
	private String username;
	private String password;
    private Role role;
	private Member member;
	
}