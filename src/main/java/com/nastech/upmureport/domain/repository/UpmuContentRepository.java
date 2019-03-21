package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.UpmuContent;

@Transactional
public interface UpmuContentRepository extends JpaRepository<UpmuContent, Integer>{
	
	
}
