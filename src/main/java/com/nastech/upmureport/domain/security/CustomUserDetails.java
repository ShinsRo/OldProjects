package com.nastech.upmureport.domain.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.UserRole;

public class CustomUserDetails extends AuthInfo implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<UserRole> hasRoles;

	
	public CustomUserDetails(AuthInfo authInfo, List<UserRole> hasRoles) {
		
		
		super(authInfo);
		System.out.println("슈퍼수퓨ㅓ"+super.getUsername());
		this.hasRoles=hasRoles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.hasRoles;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}
	
	@Override
	public String getUsername() {
		return super.getUsername();
	}

}
