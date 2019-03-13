package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Project;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
