package com.nastech.upmureport.feature.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.feature.user.domain.entity.Member;
import com.nastech.upmureport.feature.user.domain.entity.MemberSystem;
import com.nastech.upmureport.feature.user.repo.MemberSystemRepository;

@Service
public class MemberSystemService {
	@Autowired
	MemberSystemRepository memberSystemRepository;
	public void addMemberSystem(Member Senior,Member Junior)
	{
		MemberSystem newMemberSystem = MemberSystem.builder()
				.junior(Junior)
				.senior(Senior)
				.build();
		memberSystemRepository.save(newMemberSystem);
	}
	public void deleteMemberSystem(Member Senior,Member Junior)
	{
		MemberSystem delMemberSystem = memberSystemRepository.findOneBySeniorAndJunior(Senior, Junior);
		memberSystemRepository.delete(delMemberSystem);
	}

}
