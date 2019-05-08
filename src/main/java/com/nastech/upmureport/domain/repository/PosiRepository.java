package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Posi;

public interface PosiRepository extends JpaRepository<Posi,BigInteger> {
	public Posi findByPosiName(String posiName);

}
