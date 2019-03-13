package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.entity.LogAttachment;
import com.nastech.upmureport.domain.pk.LogAttachmentPK;

@Transactional
@Repository
public interface LogAttachmentRepository extends JpaRepository<LogAttachment, LogAttachmentPK>{

}
