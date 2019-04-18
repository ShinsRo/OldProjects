package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.Role;
import com.nastech.upmureport.domain.entity.AuthInfo.AuthInfoBuilder;

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