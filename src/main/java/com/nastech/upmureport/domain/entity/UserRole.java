package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole implements GrantedAuthority, Serializable{
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger rid;
	private String username;
	@Enumerated(EnumType.STRING)
    private Role role;
	
	@Override
	public String getAuthority() {
		return this.role.toString();
	}
	

}
