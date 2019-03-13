package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	public User findOneByUserName(String userName);

	public User findOneByUserId(String userId);
}
