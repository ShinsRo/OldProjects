package com.nastech.upmureport.feature.user.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.feature.user.domain.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept,BigInteger> {
	public Dept findByDeptName(String deptName);
}
