package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nastech.upmureport.domain.entity.PLog;
import com.nastech.upmureport.domain.entity.Project;

public interface PLogRepository extends JpaRepository<PLog, BigInteger>{
	
	@Query("select l from PLog l where l.project = :project and l.deleteFlag = false order by l.newDate desc")
	List<PLog> findAllByProject(@Param(value="project") Project project);
	
}
