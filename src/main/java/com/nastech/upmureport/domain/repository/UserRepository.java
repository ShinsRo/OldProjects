package com.nastech.upmureport.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
