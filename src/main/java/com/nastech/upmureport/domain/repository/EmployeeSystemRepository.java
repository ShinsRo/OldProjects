package com.nastech.upmureport.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.EmployeeSystem;
import com.nastech.upmureport.domain.entity.EmployeeSystemPK;
import com.nastech.upmureport.domain.entity.User;

public interface EmployeeSystemRepository extends JpaRepository<EmployeeSystem, EmployeeSystemPK> {
	public List<EmployeeSystem> findAllBySenior(User senior);
	public List<EmployeeSystem> findAllByJunior(User junior);
}
