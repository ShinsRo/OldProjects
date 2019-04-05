package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Dir;

@Transactional
public interface DirRepository extends JpaRepository<Dir, BigInteger>{
	@Query(value = "select * from dir where parentProjId = :parentProjId and deleteFlag = false", nativeQuery = true)
	List<Dir> findAllByParentProjId(@Param(value = "parentProjId") Integer parentProjId);
}
