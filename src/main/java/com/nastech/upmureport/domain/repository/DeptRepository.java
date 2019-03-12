package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer>{

}
