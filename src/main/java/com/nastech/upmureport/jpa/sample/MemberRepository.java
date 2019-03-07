package com.nastech.upmureport.jpa.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByName(String name);
}
