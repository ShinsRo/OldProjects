package com.nastech.upmureport.feature.project.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.feature.project.domain.entity.Project;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, BigInteger>{
}
