package com.nastech.upmureport.feature.user.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.feature.user.domain.entity.Posi;

public interface PosiRepository extends JpaRepository<Posi,BigInteger> {
	public Posi findByPosiName(String posiName);

}
