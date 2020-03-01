package com.siotman.wos.yourpaper.repo;

import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberPaperRepository extends JpaRepository<MemberPaper, Long> {
    List<MemberPaper> findAllByMember(Member member);

    Page<MemberPaper> findAllByMember(Member member, Pageable pageable);

    Integer countByMember(Member member);

    Optional<MemberPaper> findOneByMemberAndPaper(Member member, Paper paper);

    Page<MemberPaper> findAll(Specification<MemberPaper> specification, Pageable pageable);
}
