package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nastech.upmureport.domain.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, BigInteger> {
	public List<UserRole> findAllByUsername(String id);

}
