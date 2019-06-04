package com.nastech.upmureport.feature.user.domain.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.feature.user.domain.entity.AuthInfo;
import com.nastech.upmureport.feature.user.domain.entity.UserRole;
import com.nastech.upmureport.feature.user.repo.AuthInfoRepository;
import com.nastech.upmureport.feature.user.repo.UserRoleRepository;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	AuthInfoRepository authInfoRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthInfo user = authInfoRepository.findOneByUsername(username);
		List<UserRole> userRoles = userRoleRepository.findAllByUsername(username);
		if(user != null && ! userRoles.isEmpty()) {
			String encodePass = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodePass);
			CustomUserDetails loginedCustom= new CustomUserDetails(user,userRoles);
			
			return loginedCustom;
			//retunr User
		}else {
			//아이디가 없는 경우
			return null;
		}
	}
	
	public PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}

}
