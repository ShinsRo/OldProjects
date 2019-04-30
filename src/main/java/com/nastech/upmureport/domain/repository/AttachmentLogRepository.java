package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.AttachmentLog;
import com.nastech.upmureport.domain.entity.Pdir;

@Transactional
@Repository
public interface AttachmentLogRepository extends JpaRepository<AttachmentLog, BigInteger>{
	
	
	@Query("select a from AttachmentLog a where a.pdir = :pdir and a.deleteFlag = false order by a.newDate desc")
	List<AttachmentLog> findAllByPdir(@Param(value="pdir") Pdir pdir);

}
