package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.nastech.upmureport.domain.dto.AuthInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthInfo {
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger aid;
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
    private Role role;

	@OneToOne(cascade= {CascadeType.REFRESH})
	@JoinColumn(name = "mid")
	private Member member;
	
	public AuthInfoDto toDto() {
		return AuthInfoDto.builder()
				.aid(aid)
				.username(username)
				.password(password)
				.role(role)
				.member(member)
				.build();
	}

	public AuthInfo(AuthInfo authInfo) {
		this.aid=authInfo.aid;
		this.username=authInfo.username;
		this.password=authInfo.password;
		this.role=authInfo.role;
		this.member=authInfo.member;
		// TODO Auto-generated constructor stub
	}
	
}

