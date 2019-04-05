package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nastech.upmureport.domain.entity.PfileLog;

@Transactional
@Repository
public interface PfileLogRepository extends JpaRepository<PfileLog, BigInteger>{

}
