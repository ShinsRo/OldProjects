package com.siotman.wos.yourpaper.repo;

import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPaperRepository extends JpaRepository<MemberPaper, Long> {
    List<MemberPaper> findAllByMember(Member member);

    List<MemberPaper> findAllByMember(Member member, Pageable pageable);

    Integer countByMember(Member member);
}
