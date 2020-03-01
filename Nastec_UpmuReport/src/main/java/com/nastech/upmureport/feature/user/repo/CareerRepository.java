package com.nastech.upmureport.feature.user.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.feature.user.domain.entity.Career;
import com.nastech.upmureport.feature.user.domain.entity.Member;

public interface CareerRepository extends JpaRepository<Career, BigInteger> {
	public List<Career> findAllByMember(Member mem);
}