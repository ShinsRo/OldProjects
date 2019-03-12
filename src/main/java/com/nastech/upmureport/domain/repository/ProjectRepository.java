package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
