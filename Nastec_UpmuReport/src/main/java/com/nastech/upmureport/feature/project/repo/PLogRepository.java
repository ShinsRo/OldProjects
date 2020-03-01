package com.nastech.upmureport.feature.project.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.feature.project.domain.entity.PLog;
import com.nastech.upmureport.feature.project.domain.entity.Project;

@Transactional
public interface PLogRepository extends JpaRepository<PLog, BigInteger>{
	
	@Query("select l from PLog l where l.project = :project and l.deleteFlag = false order by l.newDate desc")
	List<PLog> findAllByProject(@Param(value="project") Project project);
	
}
