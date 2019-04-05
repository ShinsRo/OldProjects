package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.LogAttachment;

@Transactional
@Repository
public interface LogAttachmentRepository extends JpaRepository<LogAttachment, Integer>{

}
