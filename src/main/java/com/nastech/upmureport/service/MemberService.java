package com.nastech.upmureport.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;

@Service
public class MemberService {
	
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	MemberRepository memberRepository;
	
	
	public MemberDto searchMemberByMid(MemberDto memberDto) {
		Long id = memberDto.getMid();
		Member member = memberRepository.findOneByMid(id);
		if(member != null)
			return member.toDto();
		else return null;
	}
	public MemberDto searchMemberByEid(MemberDto memberDto) {
		String id=memberDto.getEid();
		Member member = memberRepository.findOneByEid(id);
		if(member != null)
			return member.toDto();
		else return null;
	}
	
	public MemberDto userRegister(MemberDto member) {
		Member newMember = member.toEntity();
		if ( memberRepository.findOneByMid(newMember.getMid()) == null) {
			memberRepository.save(newMember);
			return member;
		}
		else
			return null;
	}
	
	public MemberDto searchMemberByName(MemberDto memberDto) {
		String memberName=memberDto.getName();
		Member member = memberRepository.findOneByName(memberName);
		
		if(member != null) 
			return member.toDto();
		else return null;
	}
	public List<Member> searchMembers(){
		List<Member> users;
		users=memberRepository.findAll();
		return users;
	}
//	public MemberDto userLogin(MemberDto user) {
//		String id = user.getUserId();
//		String pass = user.getUserPass();
//		System.out.println("id:"+id+"\npass:"+pass);
//		Member loginedUser = memberRepository.findOneByUserId(id);
//		if(loginedUser == null) return null;
//		if(pass.equals(loginedUser.getUserPass())) {
//			return loginedUser.toDto();
//		}
//		else
//			return null;
//	}
//	public String searchPassword(UserDto userDto) {
//		String id=userDto.getUserId();
//		Member user = memberRepository.findOneByUserId(id);
//		return user.getUserPass();
//	}
	public void deleteMember(Member member) {
		memberRepository.delete(member);
	}
	
	public List<MemberDto> findMyJuniors(MemberDto user){
		Queue<MemberDto> q = new LinkedList();
		List<MemberDto> juniorList = new LinkedList();
		q.add(user);
		List<MemberSystem> temp = memberSystemRepository.findAllBySenior(user.toEntity());
		while( !(q.isEmpty()) ) {
			temp=memberSystemRepository.findAllBySenior(q.poll().toEntity());
			for (MemberSystem memberSystem : temp) {
				q.add(memberSystem.getJunior().toDto());
				juniorList.add(memberSystem.getJunior().toDto());
			}
		}
		return juniorList;
	}
	
}
