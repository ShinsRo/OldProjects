package com.siotman.wos.yourpaper.repo;

import com.siotman.wos.yourpaper.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
