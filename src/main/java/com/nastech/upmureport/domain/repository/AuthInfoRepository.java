package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.AuthInfo;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, BigInteger> {

}
