package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, BigInteger> {
	
	public List<Member> findAllByUserName(String userName);

	public Member findOneByUserId(String userId);
}
