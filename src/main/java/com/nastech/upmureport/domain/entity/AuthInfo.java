package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
//시작
public class AuthInfo {
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger aid;
	private String username;
	private String password;
	@Enumerated(EnumType.STRING)
    private Role role;

	@OneToOne
	@JoinColumn(name = "mid")
	private Member member;
}