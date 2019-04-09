package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findOneByMid(Long mid);
	public Member findOneByName(String name);
	public Member findOneByEid(String eid);
}
