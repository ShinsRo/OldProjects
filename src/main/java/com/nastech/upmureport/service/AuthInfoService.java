package com.nastech.upmureport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;

@Service
public class AuthInfoService {
	@Autowired
	AuthInfoRepository authInfoRepository;
	
//	public AuthInfo userLogin(AuthInfo authinfo) {
//	String id = authinfo.getUsername();
//	String pass = authinfo.getPassword();
//	System.out.println("id:"+id+"\npass:"+pass);
//	AuthInfo loginedUser = authInfoRepository.findOneByUsername(id);
//	if(loginedUser == null) return null;
//	if(pass.equals(loginedUser.getPassword())) {
//		System.out.println("로그인 성공");
//		return loginedUser;
//		}
//	else
//		return null;
//	}
	
	public MemberDto userLogin(AuthInfo authinfo) {
	String id = authinfo.getUsername();
	String pass = authinfo.getPassword();
	System.out.println("id:"+id+"\npass:"+pass);
	AuthInfo loginedUser = authInfoRepository.findOneByUsername(id);
	if(loginedUser == null) return null;
	if(pass.equals(loginedUser.getPassword())) {
		System.out.println("로그인 성공");
		System.out.println("로그인한 멤버정보");
		System.out.println(loginedUser.getMember());
		System.out.println("--------------------------");
		MemberDto loginedMemDto = loginedUser.getMember().toDto();
		
		return loginedMemDto;
		}
	else
		return null;
	}
	
	public void authModify(String id,AuthInfo newAuthinfo) {
		AuthInfo auth=authInfoRepository.findOneByUsername(id);
		auth.setPassword(newAuthinfo.getPassword());
		authInfoRepository.save(auth);
	}
	
	public String searchPassword(AuthInfo auth) {
		String id=auth.getUsername();
		AuthInfo findAuth = authInfoRepository.findOneByUsername(id);
		if (findAuth != null)
			return findAuth.getPassword();
		else
			return null;
	}
	
}
