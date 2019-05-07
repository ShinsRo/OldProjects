package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.PfileLog;

@Transactional
@Repository
public interface PfileLogRepository extends JpaRepository<PfileLog, BigInteger>{

	@Query("select p from PfileLog p where p.pdir = :pdir order by p.newDate desc")
	List<PfileLog> findAllByPdir(Pdir pdir);

}
