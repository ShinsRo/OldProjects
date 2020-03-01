package com.nastech.upmureport.feature.user.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nastech.upmureport.feature.user.domain.entity.AuthInfo;
import com.nastech.upmureport.feature.user.domain.entity.Member;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, BigInteger> {
	public AuthInfo findOneByUsername(String username);
	public AuthInfo findOneByMember(Member member);
}