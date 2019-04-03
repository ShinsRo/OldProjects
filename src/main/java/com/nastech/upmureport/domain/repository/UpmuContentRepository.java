package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.UpmuContent;
import java.util.List;

@Transactional
public interface UpmuContentRepository extends JpaRepository<UpmuContent, Integer>{
	
	@Query("select u from UpmuContent u where u.dirId = :dirId and u.deleteFlag = 0")
	List<UpmuContent> findByDirId(@Param(value = "dirId") Dir dirId);
}