package com.nastech.upmureport.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.pk.EmployeeSystemPK;

public interface EmployeeSystemRepository extends JpaRepository<MemberSystem, EmployeeSystemPK> {
	public List<MemberSystem> findAllBySenior(Member senior);
	public List<MemberSystem> findAllByJunior(Member junior);
	public List<MemberSystem> findAllBySeniorAndJunior(Member senior,Member junior);
}
