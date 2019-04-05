package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nastech.upmureport.domain.entity.UpmuLog;

@Transactional
@Repository
public interface UpmuLogRepository extends JpaRepository<UpmuLog, Integer>{

}
