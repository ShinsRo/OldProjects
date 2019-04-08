package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, BigInteger> {
	public Member findOneByEid(String eid);
}
