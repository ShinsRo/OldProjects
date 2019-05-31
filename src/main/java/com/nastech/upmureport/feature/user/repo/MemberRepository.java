package com.nastech.upmureport.feature.user.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.feature.user.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findOneByMid(Long mid);
	public Member findOneByName(String name);
	public Member findOneByEid(String eid);
	public Member findOneByEidAndDflagFalse(String eid);
	public List<Member> findAllByDflagFalse();
}
