package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.entity.UserRole;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.UserRoleRepository;

@Service
public class MemberService {
	
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AuthInfoRepository authInfoRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	
	/**
	 * @author 마규석
	 * @param memberDto
	 * @return 
	 */
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
	
	
	/**
	 * @author 마규석
	 * @param member
	 * @return
	 * 새로운 사원을 등록할 때 사원에 대한 정보를 등록 하는 것
	 */
	public MemberDto userRegister(MemberDto member) {
		Member newMember = member.toEntity();
		if ( memberRepository.findOneByMid(newMember.getMid()) == null) {
			newMember.setJoinDate(LocalDate.now());
			Member savedMem = memberRepository.save(newMember);
			return savedMem.toDto();
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
	public MemberDto retireMember(MemberDto memDto) {
		Long mid= memDto.getMid();
		Member retireMem=memberRepository.findOneByMid(mid);
		AuthInfo retireAuth=authInfoRepository.findOneByMember(retireMem);
		List<UserRole> retireRole = userRoleRepository.findAllByUsername(retireAuth.getUsername());
		retireMem.setDflag(true);
		retireMem.setRetireDate(LocalDate.now());
		retireMem.setRetireEid(retireMem.getEid());
		retireMem.setEid(null);
		if(retireAuth == null | retireMem==null)
		{
			System.out.println("retireMember 오류");
			return null;
		}
		memberRepository.save(retireMem);
		authInfoRepository.delete(retireAuth);
		userRoleRepository.deleteInBatch(retireRole);
		return retireMem.toDto();
	}

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
				//// 퇴사 안한사람들만 가져오기
				MemberDto activeMem = memberSystem.getJunior().toDto();
				if(activeMem.getDflag()==false) {   //// 퇴사 안한사람들만 가져오기
					q.add(memberSystem.getJunior().toDto());
					juniorList.add(memberSystem.getJunior().toDto());
				}
			}
		}
		return juniorList;
	}
	
    ////현재 사원들만 (퇴사x)
	public List<MemberDto> listAll() {
		List<MemberDto> mDtos = new ArrayList<MemberDto>();
		List<Member> members = memberRepository.findAllByDflagFalse();
		members.forEach(m -> { mDtos.add(m.toDto()); });
		return mDtos;
	}
	public Member modifyPhone(Member mem) {
		Long mid= mem.getMid();
		Member modifyMem=memberRepository.findOneByMid(mid);
		if(modifyMem==null) return null;
		else {
			modifyMem.setPhoneNum(mem.getPhoneNum());
			memberRepository.save(modifyMem);
		}
		return modifyMem;
	}
	
//	public MemberDto userLogin(MemberDto user) {
//	String id = user.getUserId();
//	String pass = user.getUserPass();
//	System.out.println("id:"+id+"\npass:"+pass);
//	Member loginedUser = memberRepository.findOneByUserId(id);
//	if(loginedUser == null) return null;
//	if(pass.equals(loginedUser.getUserPass())) {
//		return loginedUser.toDto();
//	}
//	else
//		return null;
//}
//public String searchPassword(UserDto userDto) {
//	String id=userDto.getUserId();
//	Member user = memberRepository.findOneByUserId(id);
//	return user.getUserPass();
//}
}
