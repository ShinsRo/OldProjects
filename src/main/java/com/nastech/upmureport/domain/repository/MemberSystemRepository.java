package com.nastech.upmureport.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.pk.MemberSystemPK;

public interface MemberSystemRepository extends JpaRepository<MemberSystem, MemberSystemPK> {
	public List<MemberSystem> findAllBySenior(Member senior);
	public List<MemberSystem> findAllByJunior(Member junior);
	public List<MemberSystem> findAllBySeniorAndJunior(Member senior,Member junior);
	public MemberSystem findOneBySeniorAndJunior(Member senior,Member junior);
}
