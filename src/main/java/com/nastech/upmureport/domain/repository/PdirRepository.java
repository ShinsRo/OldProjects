package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Pdir;

public interface PdirRepository extends JpaRepository<Pdir, BigInteger>{

	List<Pdir> findAllByPidAndDflagFalse(BigInteger pid);

	Pdir findByDidAndDflagFalse(BigInteger did);
}

