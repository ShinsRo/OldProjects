package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Project;

public interface PdirRepository extends JpaRepository<Pdir, BigInteger>{

	List<Pdir> findAllByProjectAndDflagFalse(Project project);

	Pdir findByDidAndDflagFalse(BigInteger did);

	List<Pdir> findAllByDidOrParentDirAndDflagFalse(BigInteger did, Pdir parentDir);

	void deleteAllByProject(Project project);
}

