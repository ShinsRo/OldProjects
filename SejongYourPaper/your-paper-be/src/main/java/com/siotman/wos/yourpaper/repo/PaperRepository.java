package com.siotman.wos.yourpaper.repo;

import com.siotman.wos.yourpaper.domain.entity.Paper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, String>, JpaSpecificationExecutor<Paper> {
    List<Paper> findAllByUid(List<String> targetUidList, Sort sort);
}
