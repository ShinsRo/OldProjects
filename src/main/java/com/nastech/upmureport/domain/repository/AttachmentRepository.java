package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.Attachment;
import com.nastech.upmureport.domain.entity.Pdir;

@Transactional
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, BigInteger>{

	@Query("select a from Attachment a where a.pdir = :pdir and a.deleteFlag = false")
	List<Attachment> findByDid(@Param(value="pdir") Pdir pdir);
}
