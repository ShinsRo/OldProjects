package com.nastech.upmureport.feature.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.feature.project.domain.entity.Pdir;
import com.nastech.upmureport.feature.project.domain.entity.Pfile;

import java.math.BigInteger;
import java.util.List;

@Transactional
public interface PfileRepository extends JpaRepository<Pfile, BigInteger>{
	
	@Query("select p from Pfile p where p.pdir = :pdir and p.deleteFlag = 0")
	List<Pfile> findByDirId(@Param(value = "pdir") Pdir pdir);
	
}