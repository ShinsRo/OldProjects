package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.UserProject;

public interface UserProjectRepository extends JpaRepository<UserProject, Integer> {

}
