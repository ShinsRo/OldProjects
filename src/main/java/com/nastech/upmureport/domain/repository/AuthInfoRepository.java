package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nastech.upmureport.domain.entity.AuthInfo;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, BigInteger> {
	public AuthInfo findOneByUsername(String username);
}