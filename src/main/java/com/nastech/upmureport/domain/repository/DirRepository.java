package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Dir;

@Transactional
public interface DirRepository extends JpaRepository<Dir, Integer>{

	Dir findByDirName(String name);
}
