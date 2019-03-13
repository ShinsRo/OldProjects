package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.UpmuContents;

@Transactional
public interface UpmuContentsRepository extends JpaRepository<UpmuContents, Integer>{
	
	
}
